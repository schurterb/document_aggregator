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
     
     # Get query item from args
     query = event['query']
     username = str(event['username'])
     
     # Scan dynamodb for all topics
     results = table.query(KeyConditionExpression=Key('Username').eq('Test'))
     
     # List all the topics returned and send that list to the user
     topics = []
     for item in results['Items']:
          if 'Topic' in item.keys():
               topics.append(item['Topic'])
     
     # Order this list and return it
     return topics