from distutils.sysconfig import EXEC_PREFIX
from typing import List
import mysql.connector
import sshtunnel
import pymysql
import requests
import json
import pandas

ADDQ = '''INSERT IGNORE INTO {} VALUES ({});'''
GETQ = '''SELECT * FROM {} WHERE {}'''
DISTINCTQ = '''SELECT DISTINCT {} FROM {}'''

class DataHandler:

    def add_query(self, table, dataArr):
        self.run_query(ADDQ.format(table, ','.join(dataArr)))

    def get_query(self, table, filter : dict()):
        st = ""
        for key, val in filter.items():
            st += str(key) + '="' + str(val) + '" AND '
        st = st.removesuffix(' AND ')
        print(GETQ.format(table, st))
        return self.run_query(GETQ.format(table, st), get=True)
    
    def get_distinct_query(self, table, columns : List[str]):
        return self.run_query(DISTINCTQ.format(','.join(columns), table), get=True)

    def _unpackMatchData(self, data):
        ret = dict()
        for i, val in enumerate(self._stored_data):
            datapoint = data[i]
           
            temp = {val : datapoint}

            ret.update(temp)
        return ret



    def _unpackPitData(self, data):
        ret = dict()
        for i, val in enumerate(self._pit_data):
            datapoint = data[i]
           
            temp = {val : datapoint}

            ret.update(temp)
        return ret
         

    def _unpackData(self, data):
        
        data = str(data)
        data = data.split(self.SUB_SEP)
        
        if len(data) == len(self._stored_data):
            return "M", self._unpackMatchData(data)
        elif len(data) == len(self._pit_data):
            return "P", self._unpackPitData(data)
        else:
            return "",None
       
      

    def unpack_raw_str(self, data : str):
        data = str(data)
        return data.split(self.SEP)
  
        
    def unpack_raw_datapoint_to_list(self, data):
       
        return data.split(self.SUB_SEP)
        

    def unpack_raw_data_from_set(self, data : set(), sort_data=False, reverse=True):
        lst = []

        for raw_entry in data:
            lst.append(self.unpack_raw_datapoint_to_list(raw_entry))

        if sort_data:
            lst.sort()
        if reverse:
            lst.reverse()
        return lst

    def format_data_from_dict_datapoint(self, data : dict()):
        text = ""
       
        for dataType, val in data.items():
            text += dataType + ": " + str(val) + ",  "
        text = text[:-3]
        res = ""
        idx = 0
        for ch in text:
            idx += 1
            res += ch
            if idx == self.MAX_FORMAT_LEN:
                idx = 0
                res += '\n'
        return res

    def __init__(self, pass_file_loc):
        # self._pass = open(pass_file_loc, "r").read()x
        with open(pass_file_loc) as json_file:
            data = json.load(json_file)
            self.sh_us = data['ssh_user']
            self.sh_pass = data['ssh_pass']
            self.us = data['us']
            self.passw = data['pass']
            self.COMP = data['competition_name']



        self.alive = True
        self.SEP = 'ｧ'
        self.MAX_FORMAT_LEN = 45
        self._stored_data = open('./bin/match_cols.txt').readline().split(',')

        self._stored_data.pop(0)
        self._pit_data = open('./bin/pit_cols.txt').readline().split(',')
        self._pit_data.pop(0)
        self.MATCH_COLUMNS = open('./bin/match_cols.txt').readline().split(',')
        self.PIT_COMUMNS = open('./bin/pit_cols.txt').readline().split(',')

        
        self.SUB_SEP = 'ｦ'
        self.reconnect()


 

    def add_entry(self, data):
        typ, udata = self._unpackData(data)
        print(udata)
        if udata is not None and self.alive:
            try:
                dt = ["'"+self.COMP+"'"]

                for _, val in udata.items():
                    try:
                        float(val)
                        dt.append(val)

                    except Exception:
                        dt.append("'"+val+"'")
                r = "MATCH_DATA"
                if typ=="P":
                    r="PIT_DATA"
                self.add_query(r, dt)

                
                return typ,0, udata

            except Exception as e:
                self.alive = False
                print("err", e)
                # could not send data
                return typ,-1, udata
        elif udata:
            self.alive = False
            return typ,-1, udata
        else:
            # invalid qr code
            return "",-2, None

    def run_query(self, q, get=False):
        try:
            if self.alive:

                self._cursor.execute(query=q)
                if get:
                    res = self._cursor.fetchall()
                    self._client.commit()

                    return res
                self._client.commit()
            else:
                return None
        except Exception as e:
            print("error running query", e)
        return None
    
    def client_alive(self):
            self.reconnect()

    def internet_connection(self):
        import urllib.request
        try:
            urllib.request.urlopen('https://team341.com', timeout=2)
            return True
        except:
            return False



    
    def reconnect(self):
        if self.internet_connection():
            try:
                try:
                    self.tunnel.close()
                    self._client.close()
                    self._cursor.close()
                except:
                    pass

                
                self.tunnel = sshtunnel.SSHTunnelForwarder(
                    ('team341.com', 22),
                    ssh_username=self.sh_us,
                    ssh_password=self.sh_pass,
                    remote_bind_address=('mysql.team341.com', 3306)
                    )
                self.tunnel.start()
                            
                self._client = pymysql.connect(
                                    host='127.0.0.1',
                                    user=self.us,
                                    passwd=self.passw,
                                    port=self.tunnel.local_bind_port, 
                                    db='341_scouting_2022'
                                    )
                self._cursor = self._client.cursor()
                self.alive = True
                print('connected!')

            except Exception as ee:
                print('threw: ',ee)
                self.alive = False
        else:
            self.alive = False

            
    def saveColumnNames(self):
        r = self.run_query('DESCRIBE TABLE MATCH_DATA')
        if r:
            print(r)

    
    
    

    
                