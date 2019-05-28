#!/usr/bin/env python
# coding: utf-8

import json
import os
os.environ["PATH"] += os.pathsep + "/opt/python"

import boto3

def lambda_handler(event, context):
    
    #Get topic string from event data
    topic = event['topic']
    print("Researching topic: "+topic)
    
    #Get links by calling lambda_websearch synchronously
    eventData = dict(query=topic, numberOfLinks=10)
    response = invokeLambdaFunction("QueryTopic", eventData)
    links = response['Payload'].read().decode("utf-8")
    links = json.loads(links)
    print("Received "+str(len(links))+" related to topic")
    
    #Get text data by calling lambda_extract_text asynchronously
    # passing a different URL to each instance
    results = []
    for link in links:
        eventData = dict(url=link.replace("\\\"", ""), topic=topic)
        print("Extracting text from "+eventData['url']+"...")
        results.append(invokeLambdaFunction("ExtractText", eventData, asynchronous=True))
    return results
    
        
"""
@param functionName  name of the lambda function to invoke
@param eventData     data to pass to the lambda function
"""
def invokeLambdaFunction(functionName, eventData, asynchronous=False):
    lambda_client = boto3.client('lambda')
    if asynchronous:
        invoke_response = lambda_client.invoke(FunctionName=functionName,
                                           Payload=json.dumps(eventData),
                                           InvocationType='Event')
    else:
        invoke_response = lambda_client.invoke(FunctionName=functionName,
                                           LogType='Tail',
                                           Payload=json.dumps(eventData),
                                           InvocationType='RequestResponse')
        return invoke_response
        