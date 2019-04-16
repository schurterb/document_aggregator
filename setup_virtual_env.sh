#!/bin/bash

project_name='document_aggregator'

rm -fr $project_name
python3 -m venv $project_name
source $project_name/bin/activate

pip install -r lambda-requirements.txt

deactivate