#!/usr/bin/env python
# coding: utf-8

import os
from zipfile import ZipFile

from buildtools import invokeLambdaFunction, injectLambdaCodeIntoYAML, uploadFilesToS3

#Add python code to lambda functions
filesToInject = {}
filesToInject["GetTopics"] = "web/lambda_get_topics.py"
yamlFile = "web/web_deploy.yml"

print("Injecting python code into lambda files")
injectLambdaCodeIntoYAML(yamlFile, filesToInject)
print("Finished injecting python code into lambda files")

# Deploy a static web page to S3.

sourceFile = "web/index.html"
destinationFile = "index.html"

print(" ################ SANITY CHECK ##################")
print(" ################ SANITY CHECK ##################")

print("")
print("os.getcwd()=",os.getcwd())
print("")

html_path = os.getcwd()+"/web/html"
project_bucket = "documentation-aggregator-web"
content_type = "text/html"
print("Uploading "+html_path+" to s3 bucket "+project_bucket)
uploadFilesToS3(project_bucket, html_path, content_type)
print("Finished uploading "+html_path+" to s3 bucket "+project_bucket)

print(" ################ SANITY CHECK ##################")
print(" ################ SANITY CHECK ##################")