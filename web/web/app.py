from cv2 import dft
from flask import Flask, render_template, request
import base64
from io import BytesIO
import data_vis
from flask import Flask
from matplotlib.figure import Figure
import pandas as pd

app = Flask(__name__)


@app.route('/')
def home():
    return render_template('home.html')
@app.route('/raw_data')
def raw_data():
    htmlTables = []
    htmlTitles = [""]
    seed = 0
    for num in data_vis.getMatchNumbers():
        print(num)
        table = data_vis.RawDataTable(num, seed)
        html = table.fetchDataAndGetHTML()
        htmlTables.append(html)
        title = table.getTitle()
        htmlTitles.append(title)
        seed+=1
    
    return render_template('raw_data.html', tables=htmlTables, titles=htmlTitles)

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
    data_vis.setCompetition(request.form.get('databases'))
    return '', 204


if __name__ == '__main__':
    app.run()









