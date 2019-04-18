#!/usr/bin/env python
# coding: utf-8

from selenium import webdriver

def lambda_handler(event, context):
    url = "https://www.google.com/"
    
    print("Begin chrome test")
    
    print("Configuring options")
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
    chrome_options.binary_location = "/home/ubuntu/environment/document_aggregator/bin/headless-chromium"
    
    print("Launching driver")
    driver = webdriver.Chrome(executable_path="/home/ubuntu/environment/document_aggregator/bin/chromedriver", chrome_options=chrome_options)
    page_data = ""
    print("Getting url: "+url)
    driver.get(url)
    html = driver.page_source
    if html is not None:
        print("Data retrieved!")
    else:
        print("No data returned...")
    driver.close()
    
    print("Chrome test complete")
    
