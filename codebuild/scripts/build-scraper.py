#!/usr/bin/env python
# coding: utf-8

import os
from zipfile import ZipFile
from sh import mkdir, cp, rm

from buildtools import updateLambdaFunction, invokeLambdaFunction, createLambdaFunction, injectLambdaCodeIntoYAML

#Add python code to lambda functions
filesToInject = {}
filesToInject["GetHTMLFromURL"] = "src/main/python/scraper/lambda_get_html.py"
filesToInject["QueryTopic"] = "src/main/python/scraper/lambda_websearch.py"
filesToInject["ExtractText"] = "src/main/python/scraper/lambda_extract_text.py"
filesToInject["ResearchTopic"] = "src/main/python/scraper/lambda_research_topic.py"
yamlFile = "cloudformation/scraper_deploy.yml"

print("Injecting python code into lambda files")
injectLambdaCodeIntoYAML(yamlFile, filesToInject)
print("Finished injecting python code into lambda files")
