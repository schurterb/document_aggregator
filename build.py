
# coding: utf-8

# This notebook is for creating the python script necessary to build the documentation aggregator project

# In[1]:


import boto3
import json

lambda_client = boto3.client('lambda')

filebase = "deploy/"
filename = "scraper/test.py"
project_bucket = "documentation-aggregator"

data = dict()
data["bucket"] = project_bucket
data["key"] = filebase+filename
with open(filename, 'rb') as f:
    data["body"] = f.read()

    
def invokeLambdaFunction(functionName, eventData):
    invoke_response = lambda_client.invoke(FunctionName=functionName,
                                       LogType='Tail',
                                       Payload=eventData,
                                       InvocationType='RequestResponse')
    return invoke_response

print(invokeLambdaFunction("storeObjectInS3", json.dumps(data)))

