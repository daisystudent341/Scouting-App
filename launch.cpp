#include <iostream>
#include <fstream>


using namespace std;

int main() {

    ifstream ifile;
    ifile.open("./src/pass.json");
    if(ifile) {
        cout << "Running app...\n";
     system("py ./src/MainWindow.py");
        
    } else {
        cout << "Create a pass.json file in src formatted like:\n"
    <<
    R"(
    {
    "ssh_user": "",
    "ssh_pass": "",
    "us": "",
    "pass": "!"
    })";
    }
    

}