#!/usr/bin/env python
# coding: utf-8

import os
from zipfile import ZipFile
from sh import mkdir, cp, rm

from buildtools import updateLambdaFunction, invokeLambdaFunction, createLambdaFunction, createOrUpdateLambdaLayer

resourceOriginalDirectory = "document_aggregator/lib/python3.6/site-packages/"

lambdaLayers = {}
resourceZipDirectories = {}

#Create a lambda layer for boto3
lambdaLayers['boto3'] = [resourceOriginalDirectory+"boto3"]
resourceZipDirectories['boto3'] = "python/"

#Create a lambda layer for web scraping
lambdaLayers['scraping'] = [resourceOriginalDirectory+"bs4", resourceOriginalDirectory+"selenium"]
resourceZipDirectories['scraping'] = "python/"

#Create a place to put the resources while zipping them.

layerPublishResponse = []
for name, sources in lambdaLayers.items():
    print("Creating lambda layer for "+name+" with "+str(sources))
    resourceZipDirectory = resourceZipDirectories[name]
    mkdir("-p", resourceZipDirectory)
    for source in sources:
        cp("-rf", source, resourceZipDirectory)
    with ZipFile(name+".zip", 'w') as ziph:
        for root, dirs, files in os.walk(resourceZipDirectory):
            for file in files:
                ziph.write(os.path.join(root, file))
                
    response = createOrUpdateLambdaLayer(name, name+".zip", runtimes=['python3.6','python3.7'])
    layerPublishResponse.append(response)
    print(response)
    print("Finished creating lambda layer")
    rm("-rf", resourceZipDirectory)

#Create a lambda function
lambdaFunctions = {}
lambdaFunctionDependencies = {}

#Basic Web scraper
lambdaFunctions['basicLambdaScraper'] = ["scraper/document_scraper.py"]
lambdaFunctionDependencies['basicLambdaScraper'] = [""]



#Create a place to put the resources while zipping them.
mkdir("-p", resourceZipDirectory)

for name, sources in lambdaFunctions.items():
    print("Creating lambda function for "+name+" with "+str(sources))
    for source in sources:
        cp("-rf", source, resourceZipDirectory)
    with ZipFile(name+".zip", 'w') as ziph:
        for root, dirs, files in os.walk(resourceZipDirectory):
            for file in files:
                ziph.write(os.path.join(root, file))
    try:
        print("Attempting to create lambda function")
        response = createLambdaFunction(name, name+".zip")
    except Exception as e:
        print("Lambda function already exists.  Attempting to update")
        response = updateLambdaFunction(name, name+".zip")
    
    for source in sources:
        rm("-rf", resourceZipDirectory+"*")
    print(response)
    print("Finished creating lambda function")
    
rm("-rf", resourceZipDirectory)

