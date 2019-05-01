#!/usr/bin/env python
# coding: utf-8

import json
import os
os.environ["PATH"] += os.pathsep + "/opt/python"

import boto3
from bs4 import BeautifulSoup
from selenium import webdriver

def lambda_handler(event, context):
    
    #url = event['url']
    #if url is None:
    #    url = "https://www.google.com/"
    query = event['query']
    numberOfLinks= int(event['numberOfLinks'])
    
    print("Creating query url for query: "+query)
    #https://www.google.com/search?q=cows&btnK=Google+Search
    query = query.replace(" ", "+");
    url = "https://www.google.com/search?q="+query+"&btnK=Google+Search"
    
    print("Getting html for query url: "+url)
    #Get html from query
    eventData = dict(url=url)
    response = invokeLambdaFunction("GetHTMLFromURL", eventData)
    html = str(response['Payload'])
    
    if html is not None:
        print("Parsing html result")
        #Extract urls
        soup = BeautifulSoup(html, 'html.parser')
        links = soup.find_all('a', href=True)
        searchResult = []
        for link in links:
            if link.find('h3'):
                if len(searchResult) < numberOfLinks:
                    searchResult.append(link['href'])
                else:
                    break;

        print("Returning "+str(len(searchResult))+" links")
        return searchResult
        

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