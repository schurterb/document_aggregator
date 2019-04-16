#!/bin/bash

#clean up stuff we don't want to commit'
rm -rf document_aggregator
rm -rf __pycache__ web/__pycache__ scraper/__pycache__
rm -rf *zip web/*zip scraper/*zip

#Only commit changes if there is a comment to commit with
if [ "$#" -ne 0 ]
then
  git add * -A
  git commit -m "$@"
  git push
fi