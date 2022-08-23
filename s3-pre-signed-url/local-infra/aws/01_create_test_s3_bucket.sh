#!/bin/bash
set -x
awslocal s3 mb s3://test-bucket
awslocal s3 cp /etc/issue s3://test-bucket/issue
set +x
