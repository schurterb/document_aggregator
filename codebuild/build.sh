#!/bin/bash

#To simplify dealing with codebuild and codepipeline quirks, this script is executed in the build segment of code build.

echo "project_name="$project_name
source $project_name/bin/activate

echo "Building scraper"
python3 build-scraper.py
echo "Finished building scraper"

echo "Building webpage"
python3 build-webpage.py
echo "Finished building webpage"

deactivate