#!/usr/bin/env python
# coding: utf-8

import os
from zipfile import ZipFile

from buildtools import updateLambdaFunction, invokeLambdaFunction, createLambdaFunction, createOrUpdateLambdaLayer

# Deploy a static web page to S3.

sourceFile = "web/index.html"
destinationFile = "index.html"
project_bucket = "documentation-aggregator-web"
print("Storing "+sourceFile+" in s3...")

with open(sourceFile, 'rb') as f:
    body_data = f.read()

data = { "bucket": project_bucket, "key": destinationFile, "body": str(body_data), "content_type": "text/html" }
#Cannot have ACL set to public for static webpage

print(invokeLambdaFunction("storeObjectInS3", data))
print("")