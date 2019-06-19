#!/usr/bin/env python
# coding: utf-8

import os
from zipfile import ZipFile

from buildtools import invokeLambdaFunction, injectLambdaCodeIntoYAML

#Add python code to lambda functions
filesToInject = {}
filesToInject["GetTopics"] = "web/lambda_get_topic.py"
yamlFile = "web/web_deploy.yml"

print("Injecting python code into lambda files")
injectLambdaCodeIntoYAML(yamlFile, filesToInject)
print("Finished injecting python code into lambda files")


# Deploy a static web page to S3.

sourceFile = "web/index.html"
destinationFile = "index.html"
project_bucket = "documentation-aggregator-web"
print("Storing "+sourceFile+" in s3...")

with open(sourceFile, 'rb') as f:
    body_data = f.read()

data = { "bucket": project_bucket, "key": destinationFile, "body": str(body_data), "content_type": "text/html" }
#Cannot have ACL set to public for static webpage

print("Storing web pages in s3")
print(invokeLambdaFunction("storeObjectInS3", data))
print("Finished storing web pages in s3")