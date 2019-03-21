
# coding: utf-8

# This notebook is for creating the python script necessary to build the documentation aggregator project

# In[2]:


import boto3
import json
from zipfile import ZipFile

lambda_client = boto3.client('lambda')

filebase = "deploy/"
filedir = "scraper/"
filename = "test.py"
archivename = "test.zip"
project_bucket = "documentation-aggregator"


#Compress the input file
with ZipFile(archivename, 'w') as zf:
    zf.write(filedir+filename)
    
with open(archivename, 'rb') as f:
    body_data = f.read()
    
data = { "bucket": project_bucket,          "key": filebase+filedir+archivename,          "body": str(body_data),          "acl": "bucket-owner-read" }
    
def invokeLambdaFunction(functionName, eventData):
    
    #Write data to a text file
    with open("tmp.txt", 'w') as wf:
        json.dump(eventData, wf)
        
    with open("tmp.txt", 'rb') as rbf:
        invoke_response = lambda_client.invoke(FunctionName=functionName,
                                               LogType="Tail",
                                               Payload=rbf,
                                               InvocationType="RequestResponse")
    return invoke_response

print(invokeLambdaFunction("storeObjectInS3", data))

