package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.PicUploadService;

@Controller
public class PicUploadController {
	@Autowired
	private PicUploadService picService;
	
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult upload(MultipartFile uploadFile){
		 return picService.upload(uploadFile);
	}
}
