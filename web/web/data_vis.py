from http.client import ImproperConnectionState
from matplotlib.figure import Figure
import pickle
from abc import ABCMeta, abstractmethod

from DataHandler import DataHandler

dataHandler = DataHandler


class GameConstants:
    AUTO_LOW = 2
    AUTO_HIGH = 4
    TELEOP_LOW = 1
    TELEOP_HIGH = 2
    TAXI = 2

    LOW_CLIMB = 4
    MID_CLIMB = 6
    HIGH_CLIMB = 10
    TRAVERSAL_CLIMB = 15


    
class VisualizedData(metaclass=ABCMeta):

    @abstractmethod
    def getFigure(self):
        pass

    @abstractmethod
    def getStoredDataType(self):
        pass

    @abstractmethod
    def getRawData(self):
        pass

    @abstractmethod
    def getTitle(self):
        pass
    





class RawDataTable(VisualizedData):
    def __init__(self, data : dict()) -> None:
        super().__init__()





