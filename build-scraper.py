#!/usr/bin/env python
# coding: utf-8

import os
from zipfile import ZipFile

from buildtools import updateLambdaFunction, invokeLambdaFunction, createLambdaFunction, createOrUpdateLambdaLayer

lambdaLayers = {}

#Create a lambda layer for boto3
lambdaLayers['boto3'] = ""

#Create a lambda layer for web scraping
lambdaLayers['scraping'] = ""

layerPublishResponse = []
for name, source in lambdaLayers:
    with ZipFile(name+".zip", 'w') as zip:
        zip.write(source)
    response = createOrUpdateLambdaLayer(name, name+".zip", runtimes=['python3.6','python3.7'])
    layerPublishResponse.append(response)

#Create a lambda function

sourceFile = "scraper/test.py"
zipFile = "test.zip"
print("Creating lambda function with "+sourceFile+"...")

with ZipFile(zipFile, 'w') as zip:
    zip.write(sourceFile)

print(updateLambdaFunction("test_function_2", zipFile))

os.remove(zipFile)
