from pymongo import MongoClient
import pymongo

class DataHandler:
         

    def _unpackData(self, data, castValToInt=False):
        
        data = str(data)
        data = data.split(",")
        if len(data) is not len(self._stored_data):
            return None
        
        ret = dict()
        
        for i, val in enumerate(self._stored_data):
            if not data[i].isnumeric():
                return None
            if castValToInt:
                datapoint = int(data[i])
            else:
                datapoint = data[i]
            temp = {val : datapoint}

            ret.update(temp)

      
        return ret
    


    def format_data_from_set(self, data : set(), sort_data=False):
        text = ""
        lst = []

        for raw_entry in data:
            lst.append(self._unpackData(raw_entry, castValToInt=True))
            
        if sort_data:
            lst = sorted(lst, key=lambda d : d[self._stored_data[0]])
            
        for entry in lst:
            for dataType, val in entry.items():
                text += dataType + ": " + str(val) + ",  "                
            text = text[:-3]
            text += '\n'
        return text

    def __init__(self, pass_file_loc):
        self._pass = open(pass_file_loc, "r").read()
        self.alive = True
        
        self._stored_data = ['match', 'auton_balls_scored']
        try:
            self._client = MongoClient(f"mongodb+srv://mod:{self._pass}@cluster0.7pmqtxc.mongodb.net/?retryWrites=true&w=majority", socketTimeoutMS=500, connectTimeoutMS=500, serverSelectionTimeoutMS=50)
            self._match_data = self._client.data.matches
            self.initialized = True
        except Exception as e:
            print(e)
            self.alive = False
            self.initialized = False

    def add_entry(self, data):
        data = self._unpackData(data)
        if data and self.alive:
            try:
                self._match_data.replace_one(data, data, upsert=True)
                
                return 0, data

            except Exception as e:
                self.alive = False
                # could not send data
                return -1, data
        elif data:
            self.alive = False
            return -1, data
        else:
            # invalid qr code
            return -2, None

    def get_entry_from_match(self, match_num : str):
        return self._match_data.find_one({'match': match_num})
    
    def client_alive(self):
        if not self.initialized:
            try:
                self._client = MongoClient(f"mongodb+srv://mod:{self._pass}@cluster0.7pmqtxc.mongodb.net/?retryWrites=true&w=majority", socketTimeoutMS=500, connectTimeoutMS=500, serverSelectionTimeoutMS=50)
                self._match_data = self._client.data.matches
                self.initialized = True
            except Exception as e:
                print(e)
                self.alive = False
                self.initialized = False
        else:
            try:
                self._client.admin.command('ismaster')
                self.alive = True
            except Exception as e:
                print(e)
                self.alive = False

