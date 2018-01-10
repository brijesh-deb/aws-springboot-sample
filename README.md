Sample - Springboot application deployed on AWS
===========================================
### Config changes (for local)
- Add AWS credentials(access key and secret) in application.yml for AWS account
- Note: For running the application from EC2 instance, instead of providing credential in application.tml, use attach IAM Role with EC2 instance to grant access to S3. [Refer](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-roles.html)
### Usage of S3 SDK
- Get list of files in bucket
  - URL: localhost:9001/data; Method: GET
- Upload a file
  - URL: localhost:9001/data/upload; Method: POST
  - [Check how to send multipart file as request](https://gist.github.com/brijesh-deb/d976984070945ad58cb0ced12ec85466)
