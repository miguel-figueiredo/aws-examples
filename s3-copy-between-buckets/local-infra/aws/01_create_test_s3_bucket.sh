#!/bin/bash
set -x
awslocal s3 mb s3://test-bucket-source
awslocal s3 cp /etc/issue s3://test-bucket-source/issue
awslocal s3 mb s3://test-bucket-destination
set +x
