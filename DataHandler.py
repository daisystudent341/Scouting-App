from pymongo import MongoClient
import pymongo

class DataHandler:
         

    def _unpackData(self, data):
        
        data = str(data)
        data = data.split(",")
        if len(data) is not len(self._stored_data):
            return None
        
        ret = dict()
        
        for i, val in enumerate(self._stored_data):
            if not data[i].isnumeric():
                return None

            temp = {val : data[i]}
            ret.update(temp)

      
        return ret

    def __init__(self, pass_file_loc):
        s = open(pass_file_loc, "r").read()
        self.alive = True
        self.synced = True
        try:
            self._client = MongoClient(f"mongodb+srv://mod:{s}@cluster0.7pmqtxc.mongodb.net/?retryWrites=true&w=majority", socketTimeoutMS=500, connectTimeoutMS=500, serverSelectionTimeoutMS=50)
            self._match_data = self._client.data.matches
            self._stored_data = ['match', 'auton_balls_scored']
        except Exception as e:
            print(e)
            self.alive = False

    def add_entry(self, data):
        data = self._unpackData(data)
        if data and self.alive:
            try:
                self._match_data.replace_one(data, data, upsert=True)
                
                return 0, data

            except Exception as e:
                self.alive = False
                print(e)
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
        print(self.alive)
        try:
            self._client.admin.command('ismaster')
            self.alive = True
        except Exception as e:
            print(e)
            self.alive = False

