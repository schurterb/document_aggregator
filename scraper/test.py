
# coding: utf-8

# This is a simple test to verify that I can use this as a development environment and push to code pipeline and deploy to lambda.

# In[1]:


import boto3
import json

print('Loading Test Function')

def lambda_handler(event, context):
    message = "Testing lambda and code pipeline"
    print(message)
    return message

