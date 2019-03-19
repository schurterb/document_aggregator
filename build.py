
# coding: utf-8

# This notebook is for creating the python script necessary to build the documentation aggregator project

# In[1]:


import boto3
import json
from zipfile import ZipFile

lambda_client = boto3.client('lambda')

filebase = "deploy/"
filedir = "scraper/"
filename = "test.py"
project_bucket = "documentation-aggregator"


#Compress the input file
with ZipFile(filedir+filename, 'w') as zf:
    zf.write(filename+'.zip')
    
with open(filename+'.zip', 'rb') as f:
    body_data = f.read()
    
data = { "bucket": project_bucket, "key": filebase+filedir+filename+'.zip', "body": str(body_data) }
    
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

