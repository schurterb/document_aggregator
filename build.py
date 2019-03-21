
# coding: utf-8

# This notebook is for creating the python script necessary to build the documentation aggregator project.

# In[4]:


#General
working_dir = ""
#working_dir = "document_aggregator/"


# For this test, we will inject the python code into the YAML for building the lambda functions.

# In[26]:


#First find load the YAML file that CloudFormation will use
import yaml
import json
cfyFile = working_dir+"scraper/web_scraper_deploy.yml"
with open(cfyFile, 'r') as yamlFile:
    yamlData = yaml.load(yamlFile.read())
print(yamlData)


# In[17]:


#Now load the python code to inject
pyFile = working_dir+"scraper/test.py"
with open(pyFile, 'r') as f:
    pyData = f.read()
print(pyData)


# In[38]:


#Inject the python string into the yaml json at the the Code.ZipFile endpoint
print("")
print("Before:")
print(yamlData['Resources']['LambdaTest']['Properties']['Code']['ZipFile'])
print("")

yamlData['Resources']['LambdaTest']['Properties']['Code']['ZipFile'] = pyData

print("")
print("After:")
print(yamlData['Resources']['LambdaTest']['Properties']['Code']['ZipFile'])
print("")


# In[39]:


#Finally, lets store the updated YAML file
with open(cfyFile, 'w') as yamlFile:
    yamlFile.write(yaml.dump(yamlData))

