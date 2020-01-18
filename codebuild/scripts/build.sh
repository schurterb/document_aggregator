#!/bin/bash

#To simplify dealing with codebuild and codepipeline quirks, this script is executed in the build segment of code build.

echo "project_name="$project_name
source $project_name/bin/activate

echo "Building scraper"
python3 build-scraper.py
echo "Finished building scraper"

echo "Building webpage"
echo " ################ BEGIN SANITY CHECK ##################"
cat build-webpage.py
echo " ################ END SANITY CHECK ##################"

echo ""
ls
echo ""

python3 build-webpage.py

echo ""
ls
echo ""

echo " ################ BEGIN SECOND SANITY CHECK ##################"
cat build-webpage.py > tmp.py
echo ""
ls
echo ""
cat tmp.py
echo ""
python3 tmp.py
echo " ################ END SECOND SANITY CHECK ##################"

echo "Finished building webpage"

deactivate