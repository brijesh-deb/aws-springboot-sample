package com.java.sample.aws.springbootaws.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class s3Service {
	
	AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon()
    {
    	/* If credentials are not from application.yml
    	//AWS Credentials is picked from C:\Users\USERNAME \.aws\credentials on Windows
    	//AWS Region details is picked from C:\Users\USERNAME\.aws\config on Windows
    	ProfileCredentialsProvider profileCredentialsProvider = new ProfileCredentialsProvider();
    	s3client =	AmazonS3ClientBuilder.standard().withCredentials(profileCredentialsProvider).withRegion(Regions.US_EAST_1).build();
    	 */

    	AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(credentials); 
        this.s3client = AmazonS3ClientBuilder.standard().withCredentials(awsStaticCredentialsProvider).withRegion(Regions.US_EAST_1).build();
    }
	
	public String listFile()
	{
		List<Bucket> buckets = s3client.listBuckets();
		for(Bucket bucket : buckets) {
		    System.out.println(bucket.getName());
		}
		return "testing";
	}
	
	public String uploadFile(MultipartFile multipartFile) {
	        String fileUrl = "";
	        try {
	            File file = convertMultiPartToFile(multipartFile);
	            String fileName = generateFileName(multipartFile);
	            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
	            uploadFileTos3(fileName, file);
	            file.delete();
	        } catch (Exception e) {
	           e.printStackTrace();
	        }
	        return fileUrl;
	    }
	 
	    private File convertMultiPartToFile(MultipartFile file) throws IOException {
	        File convertedFile = new File(file.getOriginalFilename());
	        FileOutputStream fos = new FileOutputStream(convertedFile);
	        fos.write(file.getBytes());
	        fos.close();
	        return convertedFile;
	    }

	    private String generateFileName(MultipartFile multiPart) {
	        return multiPart.getOriginalFilename().replace(" ", "_");
	    }

	    private void uploadFileTos3(String fileName, File file) {
	        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
	                .withCannedAcl(CannedAccessControlList.PublicRead));
	    }

	    public String deleteFileFroms3(String fileUrl) {
	        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
	        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
	        return "Successfully deleted";
	    }

}