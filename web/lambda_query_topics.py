#!/usr/bin/env python
# coding: utf-8

import json
import os
os.environ["PATH"] += os.pathsep + "/opt/python"

import boto3
from boto3.dynamodb.conditions import Key, Attr

dynamodb = boto3.client('dynamodb')
#dynamodb = boto3.resource('dynamodb', region_name=Regions.fromName(System.getenv("AWS_DEFAULT_REGION")), endpoint_url="http://localhost:8000")

def lambda_handler(event, context):
     
     # Get query item from args
     query = event['query']
     
     # Query dynamodb for all topics currently in the topics table
     dbTable = dynamodb.Table('TopicTable')
     response = dbTable.query( KeyConditionExpression=Key('Topic').contains(query) )
     
     # Order this list and return it
     print(response)
     return response