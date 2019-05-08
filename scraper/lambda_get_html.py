#!/usr/bin/env python
# coding: utf-8

import os
os.environ["PATH"] += os.pathsep + "/opt/python"

from selenium import webdriver

def lambda_handler(event, context):
    url = event['url']
    if url is not None:
        print("Configuring webdriver")
        chrome_options = webdriver.ChromeOptions()
        chrome_options.add_argument('--headless')
        chrome_options.add_argument('--no-sandbox')
        chrome_options.add_argument('--disable-gpu')
        chrome_options.add_argument('--window-size=1280x1696')
        chrome_options.add_argument('--user-data-dir=/tmp/user-data')
        chrome_options.add_argument('--hide-scrollbars')
        chrome_options.add_argument('--enable-logging')
        chrome_options.add_argument('--log-level=0')
        chrome_options.add_argument('--v=99')
        chrome_options.add_argument('--single-process')
        chrome_options.add_argument('--data-path=/tmp/data-path')
        chrome_options.add_argument('--ignore-certificate-errors')
        chrome_options.add_argument('--homedir=/tmp')
        chrome_options.add_argument('--disk-cache-dir=/tmp/cache-dir')
        chrome_options.add_argument('user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36')
        chrome_options.binary_location = "/opt/bin/headless-chromium"
    
        print("Launching webdriver")
        driver = webdriver.Chrome(executable_path="/opt/bin/chromedriver", chrome_options=chrome_options)
        page_data = ""
        print("Getting url: "+url)
        driver.get(url)
        html = driver.page_source
        if html is not None:
            print("Data retrieved")
        else:
            print("No data found")
        driver.close()
        
        return html
    else:
        print("No url provided.")
        return None
        
"""


  TopicResearchEventMapping:
    Type: AWS::Lambda::EventSourceMapping
    Properties:
      EventSourceArn: arn:aws:dynamodb:us-east-1:353290830413:table/DocumentationAggregatorTopics/stream/2019-05-07T20:03:36.640
      FunctionName: ResearchTopic
      StartingPosition: LATEST
"""