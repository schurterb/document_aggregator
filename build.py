
# coding: utf-8

# This notebook is for creating the python script necessary to build the documentation aggregator project

# In[1]:


import boto3
import json

lambda_client = boto3.client('lambda')

filebase = "deploy/"
filename = "scraper/test.py"
project_bucket = "documentation-aggregator"

with open(filename, 'rb') as f:
    body_data = f.read()
    
data = { "bucket": project_bucket, "key": filebase+filename, "body": str(body_data) }
    
def invokeLambdaFunction(functionName, eventData):
    
    #Write data to a text file
    with open("tmp.txt", 'w') as wf:
        json.dump(eventData, wf)
        
    #Compress the text file
    with ZipFile('tmp.zip', 'w') as zf:
        zf.write('tmp.txt')
    
    with open("tmp.txt", 'rb') as rbzf:
        invoke_response = lambda_client.invoke(FunctionName=functionName,
                                               LogType="Tail",
                                               Payload=rbzf,
                                               InvocationType="RequestResponse")
    return invoke_response

print(invokeLambdaFunction("storeObjectInS3", data))

