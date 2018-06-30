package framework.controller.back.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import framework.common.CollectionUtils;
import framework.common.JSONExt;
import framework.common.JSONText;
import framework.common.TianDianPage;
import framework.common.jpa.Query;
import framework.common.jpa.Select;
import framework.common.result.Code;
import framework.common.result.Message;
import framework.common.result.Result;
import framework.dao.auth.AuthorityRepository;
import framework.entity.po.auth.Authority;
import framework.service.interf.auth.AuthorityService;




@RestController
@RequestMapping("/back/auth/authority")
public class AuthorityController {
	
	@Autowired
	AuthorityService authorityService;
	
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	
	@RequestMapping("/add")
	public Result add(Authority authority){
		if(null == authority ||  StringUtils.isBlank(authority.getAuthorityCode()) ||   StringUtils.isBlank(authority.getAuthorityName())){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		
		JSONObject jo=null;
		try {
			Authority dbAuthority=	authorityService.saveAuthority(authority);
			if(null != dbAuthority){
				jo=JSONText.JavaBeanToJsonObject(dbAuthority);
			}
			
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
				
		return Result.setData(true, jo);
	}
	
	@RequestMapping("/del")
	public  Result  del(Long authorityId ){
		if(null == authorityId ){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		boolean  bool=false;
		try {
			authorityRepository.delete(authorityId);
			bool=true;
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return Result.setData(true, bool);
	}
	
	
	@RequestMapping("/getList")
	public  Result  getList(String   authorityCode ,String authorityName ,Integer  page,Integer rows){
		if(null ==page || null ==rows){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		TianDianPage tp=new   TianDianPage();
		try {
			Query<Authority> query=new  Query<Authority>();
			if(StringUtils.isNotBlank(authorityCode)){
				query.add(Select.eq("authorityCode", authorityCode));
			}
			if(StringUtils.isNotBlank(authorityName)){
				query.add(Select.eq("authorityName", authorityName));
			}
			Pageable pageable=new  PageRequest(page-1, rows);
			Page<Authority> pages=authorityRepository.findAll(query,pageable);
			JSONArray ja=new  JSONArray();
			if(CollectionUtils.isNotEmpty(pages.getContent())){			
				for (Authority authority : pages.getContent()) {
					ja.add(JSONExt.toJSON(authority));
				}
				
			}
			
			tp.setContent(ja);
			tp.setCurrentPage(pages.getNumber());
			tp.setPageCount(pages.getTotalPages());
			tp.setTotal(pages.getTotalElements());
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
			
		return  Result.setData(true, tp);
	}
	
	
	

}
