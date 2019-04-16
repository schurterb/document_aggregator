#!/usr/bin/env python
# coding: utf-8

import os
from zipfile import ZipFile
from sh import mkdir, cp, rm

from buildtools import updateLambdaFunction, invokeLambdaFunction, createLambdaFunction, createOrUpdateLambdaLayer

lambdaLayers = {}

resourceOriginalDirectory = "document_aggregator/lib64/python3.6/site-packages/"
resourceZipDirectory = "python/"
#Create a lambda layer for boto3
lambdaLayers['boto3'] = ["boto3"]

#Create a lambda layer for web scraping
lambdaLayers['scraping'] = ["bs4", "selenium"]

#Create a place to put the resources while zipping them.
mkdir("-p", resourceZipDirectory)

layerPublishResponse = []
for name, sources in lambdaLayers.items():
    print("Creating lambda layer for "+name+" with "+str(sources))
    for source in sources:
        cp("-rf", resourceOriginalDirectory+source, resourceZipDirectory)
    with ZipFile(name+".zip", 'w') as ziph:
        for root, dirs, files in os.walk(resourceZipDirectory):
            for file in files:
                ziph.write(os.path.join(root, file))
    response = createOrUpdateLambdaLayer(name, name+".zip", runtimes=['python3.6','python3.7'])
    layerPublishResponse.append(response)
    for source in sources:
        rm("-rf", resourceZipDirectory+source)
    print("Finished creating lambda layer")

rm("-rf", resourceZipDirectory)

#Create a lambda function
