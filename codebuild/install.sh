#!/bin/bash

#To simplify dealing with codebuild and codepipeline quirks, this script is executed in the install segment of code build.

project_name=document_aggregator
export project_name

. setup_virtual_env.sh $project_name