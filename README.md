# Starkist
A simple scheduling service with Rest API (SpringBoot based).<br/>
<br/>
This service is meant to make job scheduling as simple as running a script.<br/>
In order to execute jobs, starkist needs a zip file path and a cron expression.<br/>
When execution time arrive, starkist will download the zip file, unzip it and will execute the start.sh script inside the unzipped folder.<br/>
This simple execution model makes starkist easy to use and powerful.<br/>

*NOTE: As for version v0.1 AWS S3 is the only supported file system for job files*
## Getting started
* create a folder with a start.sh script (other files required for the execution can be packed as well)
* zip the folder content and upload to S3 into your bucket 
* set your AWS credentials and the S3 bucket name in the `application.properties`
* pack & execute Starkist (details below)
* submit the job indicating the zip file name (without the bucket name), a cron expression and the rest of the required parameters
* have a beer and wait

#API
## Submit new job 
### path
/job POST 
### Body
~~~~
{
    "name": "<unique job name>",
    "cron": "<cron expression>",
    "file": "<zip file name>.zip",
    "source": "S3/HDFS/LOCAL"
}
~~~~
Example: job that triggers every 2 minutes and downloads job.zip file from S3 <br/>
~~~~
{
    "name": "some job on s3",
    "cron": "0 0/2 * 1/1 * ?",
    "file": "job.zip",
    "source": "S3"
}
~~~~

##Configuration
Place a file named `src/main/resources/application.properties` with the following content:
~~~~
file.download.path=/path/to/temp/folder
cloud.aws.credentials.accessKey=awsAccessKey
cloud.aws.credentials.secretKey=awsSecretKey
cloud.aws.s3.bucket=myBucket
~~~~

##Packing:
mvn clean package

##Executing:
java -jar starkist.jar
