from cv2 import dft
from flask import Flask, render_template, request
import base64
from io import BytesIO
import data_vis
from flask import Flask
from matplotlib.figure import Figure
from DataHandler import DataHandler
import pandas as pd

app = Flask(__name__)
COMPETITION = "Ramp Riot"
dataHandler = DataHandler('pass.json')

def listit(t):
    return list(map(listit, t)) if isinstance(t, (list, tuple)) else t

@app.route('/')
def home():
    return render_template('home.html')
@app.route('/raw_data')
def raw_data():
    ret = listit(dataHandler.get_query('MATCH_DATA', {'COMP_NAME': COMPETITION}))
    df=pd.DataFrame(ret, columns=dataHandler.MATCH_COLUMNS)
    
    
    html = df.to_html(classes='styled-table').replace('style="text-align: right;"', '')
    print(html)
    return render_template('raw_data.html', tables=[html], titles=["", "Match 1","Match 2",])
@app.route('/pit_data')
def pit_data():
    return render_template('pit_data.html')
@app.route('/search_by_team')
def search_by_team():
    return render_template('search_by_team.html')
@app.route('/tba')
def tba():
    return render_template('tba.html')
@app.route('/driver_panel')
def driver_panel():
    return render_template('driver_panel.html')
@app.route('/match_predict')
def match_predict():
    return render_template('match_predict.html')
@app.route('/notes2')
def notes():
    return render_template('notes2.html')
@app.route('/about')
def about():
    return render_template('about.html')

@app.route("/" , methods=['GET', 'POST'])
def test():
    global COMPETITION
    COMPETITION = request.form.get('databases')
    print(COMPETITION)
    return '', 204


if __name__ == '__main__':
    app.run()









