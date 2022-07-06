import imp
import cv2
from pyzbar.pyzbar import decode

class CameraQR:


    def clearCapture(self, capture):
        capture.release()

    def updateCameraCount(self):
        n = 0   
        for i in range(10):
            try:
                cap = cv2.VideoCapture(i)
                ret, frame = cap.read()
                cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
                self.clearCapture(cap)
                n += 1
            except:
                self.clearCapture(cap)
                break
        self._num_cameras = n


    def chooseCamera(self, cam_num):
         self._cap = cv2.VideoCapture(cam_num, cv2.CAP_DSHOW)
       

    def _incrementCamera(self):
        self._cam_chosen += 1
        self._cam_chosen %= self._num_cameras
        self.chooseCamera(self._cam_chosen)

    

    
    def __init__(self):
        self._num_cameras = 0
        self.updateCameraCount()
        print(self._num_cameras)
        self._cam_chosen = 0
        self._cap = cv2.VideoCapture(self._cam_chosen, cv2.CAP_DSHOW)
      
        self._data = None
        self._cur_img = None
        
    
        
        

    def _decodeImg(self, img):
         try:
            for i in decode(img):
                print(i.data.decode('utf-8'))
                return str(i.data.decode('utf-8'))

         except Exception as e:
            return None

         return None

         


    def _getImg(self):
        _, img = self._cap.read()
        self._cur_img = img
        return img

    def getRawData(self):
        return self._data

    def updateCameraData(self):
        self._update()
        
        return self._cur_img


    def _update(self):
        data = self._decodeImg(self._getImg())
        if data:
            self._data = data
        else:
            self._data = None
        

    def __del__(self):
        self.clearCapture(self._cap)
