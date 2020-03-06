#!/bin/bash

# Create a namespace for data warehouse
kubectl create ns warehouse

# Store S3 access key in secret
kubectl -n warehouse create secret generic metastore-s3-keys \
	--from-literal=access-key='my_aws_s3_access_key' \
	--from-literal=secret-key='my_aws_s3_secret_key'

# Create configMap
kubectl -n warehouse create configmap metastore-cfg \
	--from-file=metastore-site.xml \
	--from-file=core-site.xml
