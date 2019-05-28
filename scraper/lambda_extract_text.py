#!/usr/bin/env python
# coding: utf-8

import hashlib
import json
import os
os.environ["PATH"] += os.pathsep + "/opt/python"

import boto3
from bs4 import BeautifulSoup
from bs4.element import Comment
from selenium import webdriver

def lambda_handler(event, context):
    
    url = event['url']
    
    print("Getting html for url: "+url)
    #Get html from query
    eventData = dict(url=url)
    response = invokeLambdaFunction("GetHTMLFromURL", eventData)
    htmlBytes = response['Payload'].read()
    html = htmlBytes.decode("utf-8")
    
    if html is not None:
        print("Parsing html result")
        #Extract text
        soup = BeautifulSoup(html, 'html.parser')
        texts = soup.find_all(text=True)
        searchResult = filter(tag_visible, texts)
        searchResult = "\\n".join(result.replace("\\n", "").strip() for result in searchResult)

        #Prepare database entry: id, topic, url, text
        topic = event['topic']
        hash_object = hashlib.sha1(topic+searchResult)
        hash_object.update(topic.encode('utf-8'))
        hash_object.update(searchResult.encode('utf-8'))
        index = int(hash_object.hexdigest(), 16)
        
        dbEntry = {"id": index, "topic": topic, "url": url, "text": searchResult}
        
        table = "DocumentationAggregatorRawScrapingResults"
        print("Storing search results in dynamo db table "+table)
        eventData = {"table": table, "data": dbEntry}
        storeResponse = invokeLambdaFunction("storeDataInDynamoDB", eventData)
        
        print("Returning search result of "+str(len(searchResult))+" characters")
        return searchResult, storeResponse
        
"""
@param functionName  name of the lambda function to invoke
@param eventData     data to pass to the lambda function
"""
def invokeLambdaFunction(functionName, eventData):
    lambda_client = boto3.client('lambda')
    invoke_response = lambda_client.invoke(FunctionName=functionName,
                                           LogType='Tail',
                                           Payload=json.dumps(eventData),
                                           InvocationType='RequestResponse')
    return invoke_response

"""
Return false if the provided element is not visible to users
"""
def tag_visible(element):
    if element.parent.name in ['style', 'script', 'head', 'title', 'meta', '[document]']:
        return False
    if isinstance(element, Comment):
        return False
    if element.replace("\\n", "").strip() == "":
        return False
    return True