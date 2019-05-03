#!/usr/bin/env python
# coding: utf-8

import os
from zipfile import ZipFile
from sh import mkdir, cp, rm

from buildtools import updateLambdaFunction, invokeLambdaFunction, createLambdaFunction, injectLambdaCodeIntoYAML

filesToInject = {}
filesToInject["GetHTMLFromURL"] = "scraper/lambda_get_html.py"
filesToInject["QueryTopic"] = "scraper/lambda_websearch.py"
filesToInject["ExtractText"] = "scraper/lambda_extract_text.py"
filesToInject["ResearchTopic"] = "scraper/lambda_research_topic.py"
yamlFile = "scraper/scraper_deploy.yml"

print("Injecting python code into lambda files")
injectLambdaCodeIntoYAML(yamlFile, filesToInject)
print("Finished injecting python code into lambda files")
