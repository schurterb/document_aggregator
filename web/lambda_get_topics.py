#!/usr/bin/env python
# coding: utf-8

import json
import os
os.environ["PATH"] += os.pathsep + "/opt/python"

import boto3
from boto3.dynamodb.conditions import Key, Attr

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('DocumentationAggregatorTopics')

def lambda_handler(event, context):
     
     print(event)
     
     try:
          #First, try parsing the JSON
          body = json.loads(event['body'])
     except:
          #If that fails, which it probably will for web api calls
          body = event['body']
          splitBody = body.split("&")
          body = {}
          for string in splitBody:
               splitString = string.split("=")
               key = splitString[0]
               value = splitString[1]
               body[key] = value
     
     # Get query item from args
     query = body['query']
     username = str(body['username'])
     
     # Scan dynamodb for all topics
     results = table.query(KeyConditionExpression=Key('Username').eq('Test'))
     
     # List all the topics returned and send that list to the user
     topics = []
     for item in results['Items']:
          if 'Topic' in item.keys() and query in item['Topic']:
               topics.append(item['Topic'])
     
     # Order this list and return it
     
     # Prepare an output format that API Gateway can handle
     output = {
          "statusCode": 200,
          "headers": event['headers'],
          "isBase64Encoded": 'false',
          "body": json.dumps(topics)
     }
     
     return output