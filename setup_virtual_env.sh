#!/bin/bash

project_name=$1

rm -fr $project_name
python3 -m venv $project_name
source $project_name/bin/activate

pip install -r build-requirements.txt
pip install -r lambda-requirements.txt

deactivate