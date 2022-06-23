import sys
from PyQt5.QtGui import *
from PyQt5.QtWidgets import *
from PyQt5.QtCore import *
import cv2
from CameraQR import CameraQRGUI
from DataHandler import DataHandler
import pickle
import queue, time

camera = CameraQRGUI()
dataHandler = DataHandler("./src/pass.txt")

q = queue.Queue()

try:
    with open('./bin/offline_buffer.pkl', 'rb') as f:
        loaded_dict_arr = pickle.load(f)
except FileNotFoundError:
    with open('./bin/offline_buffer.pkl', 'wb') as f:
        pickle.dump(set(), f)

with open('./bin/offline_buffer.pkl', 'rb') as f:
    loaded_dict_arr = pickle.load(f)


class TextObject:
    txt = ""
    formatting = ""

class MainWindow(QWidget):
    def __init__(self):
        super(MainWindow, self).__init__()
        
        

        self.VBL = QGridLayout()
       

        self.FeedLabel = QLabel()
        self.VBL.addWidget(self.FeedLabel,0,0)

        self.StatusLabel = QLabel()
        self.StatusLabel.setText("Scan QR code to camera!")
       
        self.VBL.addWidget(self.StatusLabel,0,1)

        self.NetworkStatus = QLabel()
        
        if dataHandler.alive:
            self.NetworkStatus.setStyleSheet("QLabel { color : green; }")
            self.NetworkStatus.setText("Connected to server! Local save still on.")
        else:
            self.NetworkStatus.setStyleSheet("QLabel { color : red; }")
            self.NetworkStatus.setText("Not connected to server! Local save on.")

        self.VBL.addWidget(self.NetworkStatus,1,1)


        self.changeCamBTN = QPushButton("Change Camera")
        self.changeCamBTN.clicked.connect(self._changeCam)
        self.VBL.addWidget(self.changeCamBTN,1,0)


        self.syncBTN = QPushButton("Sync Offline Data to Server")
        self.syncBTN.clicked.connect(self._syncOfflineData)
        self.VBL.addWidget(self.syncBTN,0,2)

        self.mainT = MainThread()

        self.mainT.start()
        self.mainT.ImageUpdate.connect(self.ImageUpdateSlot)
       


        self.networkT = NetworkThread()

        self.networkT.start()
        self.networkT.NetworkStatusUpdate.connect(self.NetworkStatusUpdateSlot)


        self.dataT = DataThread()
        
        self.dataT.start()
        self.dataT.StatusUpdate.connect(self.StatusUpdateSlot)

        self.setLayout(self.VBL)
        self.name = "QR Code and Data Handler"
        self.setWindowTitle(self.name)

    def ImageUpdateSlot(self, Image):
        self.FeedLabel.setPixmap(QPixmap.fromImage(Image))

    def StatusUpdateSlot(self, Status):
        self.StatusLabel.setStyleSheet(Status.formatting)
        self.StatusLabel.setText(Status.txt)

       

    def NetworkStatusUpdateSlot(self, Status):
        if Status:
            self.NetworkStatus.setStyleSheet("QLabel { color : green; }")
            self.NetworkStatus.setText("Connected to server! Local save still on.")
        else:
            self.NetworkStatus.setStyleSheet("QLabel { color : red; }")
            self.NetworkStatus.setText("Not connected to server! Local save on.")
        

    def _changeCam(self):
        camera._incrementCamera()

    def closeEvent(self, event:QCloseEvent):
        self.mainT.ThreadActive = False
        with open('./bin/offline_buffer.pkl', 'wb') as f:
            pickle.dump(loaded_dict_arr, f)
        sys.exit(0)
    
    def _syncOfflineData(self):
        for el in loaded_dict_arr:
            q.put(el)
        

class MainThread(QThread):
    ImageUpdate = pyqtSignal(QImage)

    def run(self):
        self.ThreadActive = True
        while self.ThreadActive:
            img = camera.updateCameraData()
            img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
            ConvertToQtFormat = QImage(img.data, img.shape[1], img.shape[0], QImage.Format_RGB888)
            self.ImageUpdate.emit(ConvertToQtFormat.scaled(640, 480, Qt.KeepAspectRatio))

            data = camera.getRawData()
            
            if data:
                q.put(data)
            
            time.sleep(0.03)
                
            
        
    def stop(self):
        self.camera.clearCapture()
        self.ThreadActive = False

        self.quit()
        sys.exit(App.exec())




class DataThread(QThread):

    StatusUpdate = pyqtSignal(TextObject)
    
    def run(self):
        self.ThreadActive = True
        while self.ThreadActive:
            item = q.get()
            ret, data = dataHandler.add_entry(item)
            response = str()
            formatting = "QLabel { color : red; }"
            if ret == -1:
                response = "Error: Could not upload data to server. Saving locally if not already... " + str(data)
                loaded_dict_arr.add(str(item))
            elif ret == -2:
                response = "Error: Invalid QR code"
            else:
                formatting = "QLabel { color : green; }"
                response = "Uploaded data online! Saving locally if not already... " + str(data)
                loaded_dict_arr.add(str(item))

            textObj = TextObject()
            textObj.txt = response
            textObj.formatting = formatting
            self.StatusUpdate.emit(textObj)
            q.task_done()

            time.sleep(0.03)
            
        
    def stop(self):
        self.ThreadActive = False

        self.quit()
        sys.exit(App.exec())


class NetworkThread(QThread):

    NetworkStatusUpdate = pyqtSignal(bool)
    
    def run(self):
        self.ThreadActive = True
        while self.ThreadActive:
            dataHandler.client_alive()
            self.NetworkStatusUpdate.emit(dataHandler.alive)
            time.sleep(2)
            

            
        
    def stop(self):
        self.ThreadActive = False

        self.quit()
        sys.exit(App.exec())

if __name__ == "__main__":
    App = QApplication(sys.argv)
    Root = MainWindow()
    Root.show()
    sys.exit(App.exec())