#!/usr/bin/env python
# coding: utf-8

# This notebook is for creating the python script necessary to build the documentation aggregator project.

# In[6]:


#General
working_dir = ""
working_dir = "document_aggregator/"


# For this test, we will inject the python code into the YAML for building the lambda functions.

# In[7]:


#Method for injecting python code into YAML files for CloudFormation
import yaml
import json

"""
@param yamlFile  the path to the yaml file to modify
@param filesToInject  a map contianing the path to python files to inject
                      and the lambda function they belong to
"""
def injectLambdaCodeIntoYAML(yamlFile, filesToInject):
    
    with open(yamlFile, 'r') as f:
        yamlData = yaml.load(f.read())
       
    for lambdaName in filesToInject.keys():
        for resource in yamlData['Resources']:
            if lambdaName == resource:
                if yamlData['Resources'][lambdaName]['Type'] == 'AWS::Lambda::Function':
                    try:
                        with open(filesToInject[lambdaName], 'r') as f:
                            yamlData['Resources'][lambdaName]['Properties']['Code']['ZipFile'] = f.read()
                    except Exception as e:
                        print("Failed to load",filesToInject[lambdaName],"for lambda function",lambdaName,"  Reason:",str(e))
                        
    with open(yamlFile, 'w') as f:
        f.write(yaml.dump(yamlData))


# In[8]:


#Test the above method definition

cfyFile = working_dir+"scraper/web_scraper_deploy.yml"
filesToInject = dict()
filesToInject["LambdaTest"] = working_dir+"scraper/test.py"

injectLambdaCodeIntoYAML(cfyFile, filesToInject)


# For the second part, lets deploy a static web page to S3.

# In[9]:


import boto3
import json

lambda_client = boto3.client('lambda')

sourceFile = working_dir+"web/index.html"
destinationFile = "index.html"
project_bucket = "documentation-aggregator-web"


with open(sourceFile, 'rb') as f:
    body_data = f.read()

data = { "bucket": project_bucket,          "key": destinationFile,          "body": str(body_data),          "content_type": "text/html" }
#Cannot have ACL set to public for static webpage


def invokeLambdaFunction(functionName, eventData):
    
    print(data)
    
    #Write data to a text file
    with open("tmp.txt", 'w') as wf:
        json.dump(eventData, wf)
    
    with open("tmp.txt", 'rb') as rbf:
        invoke_response = lambda_client.invoke(FunctionName=functionName,
                                               LogType='Tail',
                                               Payload=rbf,
                                               InvocationType='RequestResponse')
    return invoke_response

print(invokeLambdaFunction("storeObjectInS3", data))


# In[ ]:




