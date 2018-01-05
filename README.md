Sample - Springboot application deployed on AWS
===========================================
### Setup
1. Add credentials in application.yml for AWS account
### Usage of S3 SDK
- Get list of files in bucket
  - URL: localhost:9001/data; Method: GET
- Upload a file
  - URL: localhost:9001/data/upload; Method: POST
  - [Check how to send multipart file as request](https://gist.github.com/brijesh-deb/d976984070945ad58cb0ced12ec85466)
