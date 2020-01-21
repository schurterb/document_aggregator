#!/bin/bash

if [ "$#" -ne 0 ]
then
   project_name=$1
else
   project_name=aws_project
fi

./scripts/cleanup_virtual_env.sh $project_name

pip install --upgrade virtualenv
virtualenv -p python3 $project_name
source $project_name/bin/activate

pip install -r scripts/requirements.txt

deactivate