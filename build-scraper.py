#!/usr/bin/env python
# coding: utf-8

import os
from zipfile import ZipFile
from sh import mkdir, cp, rm

from buildtools import updateLambdaFunction, invokeLambdaFunction, createLambdaFunction, injectLambdaCodeIntoYAML

filesToInject = {}
filesToInject["GetHTMLFromURL"] = "scraper/lambda_get_html.py"
yamlFile = "scraper/scraper_deploy.yml"

print("Injecting python code into lambda files")
injectLambdaCodeIntoYAML(yamlFile, filesToInject)
print("Finished injecting python code into lambda files")

"""
lambdaLayers = {}
resourceZipDirectories = {}

#Create a lambda function
lambdaFunctions = {}
lambdaFunctionDependencies = {}

#Basic Web scraper
lambdaFunctionCreationArgs = {}
lambdaFunctionCreationArgs['name'] = "basicLambdaScraper"
lambdaFunctionCreationArgs['description'] = "A basic lambda function for scraping data from the internet"
lambdaFunctionCreationArgs['code'] = ["scraper/document_scraper.py"]
lambdaFunctionCreationArgs['handler'] = "document_scraper.lambda_handler"
lambdaFunctionCreationArgs['layers'] = ["arn:aws:lambda:us-east-1:353290830413:layer:headlessChromium:1",
                                        "arn:aws:lambda:us-east-1:353290830413:layer:scraping:16"]

#Create a place to put the resources while zipping them.
resourceZipDirectory = lambdaFunctionCreationArgs['name']
name = lambdaFunctionCreationArgs['name']
sources = lambdaFunctionCreationArgs['code']
mkdir("-p", resourceZipDirectory)

name = "scraper/document_scraper.py"
sources = ["scraper/document_scraper.py"]

print("Creating lambda function for "+name+" with "+str(sources))

with ZipFile(name+".zip", 'w') as ziph:
    ziph.write(sources[0])

print("Finished reating lambda function for "+name+" with "+str(sources))

for source in sources:
    cp("-rf", source, resourceZipDirectory)
with ZipFile(name+".zip", 'w') as ziph:
    for root, dirs, files in os.walk(resourceZipDirectory):
        for file in files:
            ziph.write(os.path.join(root, file))
"""

"""
try:
    print("Attempting to create lambda function")
    response = createLambdaFunction(name, name+".zip", 
                    description = lambdaFunctionCreationArgs['description'],
                    handler = lambdaFunctionCreationArgs['handler'],
                    layers = lambdaFunctionCreationArgs['layers'])
except Exception as e:
    print("Lambda function already exists.  Attempting to update")
    response = updateLambdaFunction(name, name+".zip")

for source in sources:
    rm("-rf", resourceZipDirectory+"*")
print(response)
print("Finished creating lambda function")
    
rm("-rf", resourceZipDirectory)
"""
