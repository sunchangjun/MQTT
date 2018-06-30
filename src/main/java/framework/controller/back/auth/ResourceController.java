package framework.controller.back.auth;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import framework.common.CollectionUtils;
import framework.common.JSONExt;
import framework.common.TianDianPage;
import framework.common.jpa.Query;
import framework.common.jpa.Select;
import framework.common.result.Code;
import framework.common.result.Message;
import framework.common.result.Result;
import framework.dao.auth.AuthorityRepository;
import framework.dao.auth.ResourceRepository;
import framework.dao.auth.ResourceTypeRepository;
import framework.entity.po.auth.Authority;
import framework.entity.po.auth.Resource;
import framework.entity.po.auth.ResourceType;
import framework.service.interf.auth.ResourceService;


@RestController
@RequestMapping("/back/auth/resource")
public class ResourceController {
	
	@Autowired
	ResourceRepository resourceRepository;
	
	@Autowired
	ResourceService resourceService;
	
	@Autowired
	ResourceTypeRepository  resourceTypeRepository;
	
	@Autowired
	AuthorityRepository  authorityRepository;
	
	@RequestMapping({"/add","edit"})
	public Result add(Resource resource){
		if(null == resource ||  StringUtils.isBlank(resource.getUrl()) ||  StringUtils.isBlank(resource.getTitle())){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		
		boolean bool=false;
		try {
//			resource.setResourceType(resourceTypeRepository.findOne(resource.getResourceTypeId()));		
			Resource dbResource=	resourceService.saveResource(resource);
			if(null != dbResource){
				bool= true;
			}		
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		
		return Result.setData(true, bool);
	}
	
	
	@RequestMapping("/getList")
	public Result  getList(String  title ,String  url,Integer page, Integer  rows){
		if(null == page  ||  null == rows){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		Query<Resource> query=new  Query<Resource>();
		if(StringUtils.isNotBlank(title)){
			query.add(Select.eq("title", title));
		}
		if(StringUtils.isNotBlank(url)){
			query.add(Select.eq("url", url));
		}
		TianDianPage tp=new  TianDianPage();
		try {
			Pageable pageable=new  PageRequest(page-1, rows);
			Page<Resource> pages=resourceRepository.findAll(query,pageable);
			if(null != pages){
				JSONArray ja=new  JSONArray();
				for (Resource resource : pages.getContent()) {
					ja.add(JSONExt.toJSON(resource));
				}
				tp.setContent(ja);
				tp.setCurrentPage(pages.getNumber());
				tp.setPageCount(pages.getTotalPages());
				tp.setTotal(pages.getTotalElements());
			}			
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
	
		return  Result.setData(true, tp);
	}
	
	@RequestMapping("/del")
	public Result del(Long resourceId){
		
		if(null==resourceId){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		boolean bool=false;
		try {
			resourceRepository.delete(resourceId);
			bool=true;
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		
		
		return  Result.setData(true, bool);
	}
	
	@RequestMapping("/updAuth")
	public  Result updAuth(Long resourceId,String authorityIds ){
		if(null == resourceId ){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		boolean bool=false;
		try {
			bool=resourceService.updAuth(resourceId, authorityIds);
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
			
		return  Result.setData(true, bool);
	}
	
	@RequestMapping("/getAuth")
	public Result  getAuth(Long resourceId){
		if(null == resourceId ){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		JSONArray ja=new JSONArray();
		try {
		List<Authority> arList=	authorityRepository.getAuthorityByResourceId(resourceId);
		if(CollectionUtils.isNotEmpty(arList)){
			for (Authority authority : arList) {
				ja.add(JSONExt.toJSON(authority));
			}
		}
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return Result.setData(true, ja);
	}
	
	@RequestMapping("/getResourceType")
	public  Result getResourceType(){
		JSONArray  ja=new JSONArray();
		try {
			List<ResourceType>  list=resourceTypeRepository.findAll();
			if(CollectionUtils.isNotEmpty(list)){
				for (ResourceType resourceType : list) {
					ja.add(JSONExt.toJSON(resourceType));
				}
			}
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		
		
		
		
		return Result.setData(true, ja);
	}

}
