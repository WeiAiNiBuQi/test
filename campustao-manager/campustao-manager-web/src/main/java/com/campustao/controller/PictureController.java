package com.campustao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.campustao.common.utils.JsonUtils;
import com.campustao.service.PictureService;

@Controller
public class PictureController {

	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map pictureUpload(MultipartFile uploadFile) {
		//因为插件的兼容性不好，返回json数据会导致部分浏览器上传失败，所以先将json数据转成String再返回
		Map result = pictureService.uploadPicture(uploadFile);
		//为了保证功能的兼容性，需要把result转换成json格式的字符串
		//String json = JsonUtils.objectToJson(result);
		//json是一个字符串对象，没有添加@ResponseBody注解相当于是逻辑视图
		//添加了@ResponseBody，相当调用Response对象的write方法写回一个字符串
		return result;
	}
}
