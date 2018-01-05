package com.java.sample.aws.springbootaws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.sample.aws.springbootaws.service.s3Service;

@RestController
@RequestMapping("/data")
public class s3Controller {

	@Autowired
	s3Service s3service; 
	
	@RequestMapping(method=RequestMethod.GET)
	public String test() 
	{
		return s3service.listFile();
	}

	@RequestMapping(value="upload",method=RequestMethod.POST)
	public String upload(@RequestPart(value = "file") MultipartFile file) 
	{
		return s3service.uploadFile(file);
	}
}