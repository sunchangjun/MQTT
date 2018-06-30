package framework.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import framework.dao.ImageResourceRepository;
import framework.entity.po.ImageResource;
import framework.enums.IsDeleteEnum;
import framework.service.interf.ImageResourceService;
@Service
public class ImageResourceServiceImpl implements ImageResourceService {
	
	@Autowired
	ImageResourceRepository imageResourceRepository;
	
	public  ImageResource addImageResource(String  picUrl) {
		ImageResource imageResource=new  ImageResource();
		imageResource.setEnabled(1);
		imageResource.setUrl(picUrl);
		imageResourceRepository.save(imageResource);
		return  imageResource;
	}

}
