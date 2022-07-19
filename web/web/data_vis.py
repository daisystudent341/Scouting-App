from http.client import ImproperConnectionState
from typing import List
from matplotlib.figure import Figure
import pickle
from abc import ABCMeta, abstractmethod
import pandas as pd
from DataHandler import DataHandler



dataHandler = DataHandler('pass.json')

COMPETITION = "Ramp Riot"


class CONSTANTS:
    AUTO_LOW = 2
    AUTO_HIGH = 4
    TELEOP_LOW = 1
    TELEOP_HIGH = 2
    TAXI = 2
    LOW_CLIMB = 4
    MID_CLIMB = 6
    HIGH_CLIMB = 10
    TRAVERSAL_CLIMB = 15


    RED='#ea4335'
    BLUE='#3399ff'



def listit(t):
    return list(map(listit, t)) if isinstance(t, (list, tuple)) else t

def unpackList(list_of_lists):
    return [val for sublist in list_of_lists for val in sublist]


def setCompetition(comp):
    global COMPETITION
    COMPETITION = comp

def getCompetition():
    return COMPETITION

def getMatchNumbers() -> List[int]:
    l = dataHandler.get_distinct_query('MATCH_DATA', ['MATCH_NUMBER'])
    while l is None:
        l = dataHandler.get_distinct_query('MATCH_DATA', ['MATCH_NUMBER'])
    return unpackList(listit(l))
    
class VisualizedData(metaclass=ABCMeta):

    @abstractmethod
    def _fetchData(self):
        ...

    @abstractmethod
    def getTitle(self) -> str:
        ...

    @abstractmethod
    def _getHTML(self) -> str:
        ...

    def fetchDataAndGetHTML(self):
        self._fetchData()
        return self._getHTML()




    

def colorColumn(dat, color):
    return [f'background-color: {color}; color: black' for i in dat]

def colorRedBlue(dat, idx, length):
    lst = []
    for i in dat:
        if idx<=dat.name<=length:
            lst.append(f'background-color: {CONSTANTS.RED}; color: #FFFFFF')
        else:
            lst.append(f'background-color: {CONSTANTS.BLUE}; color: #FFFFFF')

    

    return lst


class RawDataTable(VisualizedData):

    
    '''
    
    TO FORMAT COLUMN NAMES, CHANGE bin/match_cols.txt AND bin/pit_cols.txt BUT KEEP SAME ORDERING

    '''

    def _formatTable(self):
        #  READS FROM bin/raw_data_table_order.txt FOR HEADER, KEEPS bin/match_cols.txt which HOLDS mySQL ORDERING ORDERING.
        #  DENOTES A HEADER ROW OF LENGTH PROVIDED AFTER COLUMN 4 (AFTER COLUMN 5 of match_cols.txt)
        #  (FIRST 4 COLUMNS DENOTE BASIC UNIVERSAL MATCH DATA AND HANDLED AUTOMATICALLY)
        #  IN SEGMENTS OF LENGTH N, IN ORDER FROM TOP TO BOTTOM
        #  GIVE EACH SEGMENT ITS OWN LINE!
        #  EXAMPLE:
        #  COLUMN_GROUP_NAME1 LENGTH1 HEX_COLUMN_GROUP_COLOR1
        #  COLUMN_GROUP_NAME2 LENGTH2 HEX_COLUMN_GROUP_COLOR2
        #  EXAMPLE FOUND IN FILE 
       
       
        colColors = []
        colNames = [('', 'MATCH_NUMBER'), ('', 'SCOUT_NAME'), ('', 'TEAM')]
        
        with open('./bin/raw_data_table_order.txt') as f:
            lines = f.readlines()
            pointer = 5
            for line in lines:
                arr = (','.join(line.split())).split(',')
                if len(arr) == 0: continue
                assert len(arr) == 3
                name = arr[0]
                length = int(arr[1])
                colorCode = arr[2]
                prev = pointer
                pointer = prev + length
                assert pointer <= len(dataHandler.MATCH_COLUMNS)



                for col in dataHandler.MATCH_COLUMNS[prev:pointer]:
                    colNames.append((name, col))
                    colColors.append(colorCode)


        df = pd.DataFrame(self.data, columns=dataHandler.MATCH_COLUMNS)
        #  print(df)
        df = df.sort_values(by=['TEAM_COLOR'])
        df = df.reset_index(drop=True)
        #  print(df)

        idx = len(df.index)
        if 'R' in df['TEAM_COLOR'].values:
            tempVal = df.loc[df['TEAM_COLOR'] == 'R']
            idx = tempVal.index[0]
        #  print(idx, len(df.index))
        
        df = df.drop(columns=['TEAM_COLOR', 'COMP_NAME'])
        
        df = pd.DataFrame(df.values.tolist(), columns=pd.MultiIndex.from_tuples(colNames))

        s = df.style
        #  s = s.set_table_attributes('class="styled-table"')
        s = s.apply(colorRedBlue, axis=1, idx=idx, length=len(df.index))
        i = 0
        if len(colNames) >= 4:
            for colorC in colColors:
                s = s.apply(colorColumn, axis=1,subset=colNames[i+3][0], color=colorC)
                i+=1
        s=s.set_uuid(f'styled-table-{self.seed}_dummy')
        s=s.hide(axis=0)
        #  print(s.to_html())
        return s.to_html()


    def getTitle(self):
        return f"Match {self.match}"

    def _getHTML(self) -> str:
        
        return self._formatTable().replace('style="text-align: right;"', '')
    

    def _fetchData(self):
        
        self.data = listit(dataHandler.get_query('MATCH_DATA', filter={'COMP_NAME': COMPETITION, 'MATCH_NUMBER': str(self.match)}))

    def __init__(self, match_num, uniqueTableSeed) -> None:
        super().__init__()
        self.match = match_num
        self.seed = uniqueTableSeed



    






