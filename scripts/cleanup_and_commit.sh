#!/bin/bash

#clean up stuff we don't want to commit'
. cleanup_virtual_env.sh $1
rm -rf python
rm -rf __pycache__ src/main/python/scraper/__pycache__
rm -f *zip web/*zip scraper/*zip
rm -f *log web/*log scraper/*log bin/*log
rm -rf bin/locales
rm -rf targets

#Only commit changes if there is a comment to commit with
git add * -A
if [ "$#" -ne 1 ]
then
  git commit -m "$2"
elif [ "$#" -ne 0 ]
then
  git commit -m "$1"
else
  git commit -m "No comment"
fi
git push