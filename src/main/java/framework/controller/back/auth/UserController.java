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
import framework.common.util.MD5.Md5Util;
import framework.dao.auth.GroupRepository;
import framework.dao.auth.GroupUserRepository;
import framework.dao.auth.UserRepository;
import framework.entity.po.auth.Group;
import framework.entity.po.auth.User;
import framework.enums.EnabledEnum;
import framework.service.interf.auth.UserService;

@RestController
@RequestMapping("/back/auth/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	GroupUserRepository groupUserRepository;

	@RequestMapping("/addManager")
	public Result addManager(User user) {
		if (null == user || StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getUserPwd())) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		JSONObject jo = null;
		try {	
			String md5Pwd=Md5Util.toMd5(user.getUserPwd());
			user.setUserPwd(md5Pwd);
			user.setEnabled(EnabledEnum.ENABLED.getType());
			User dbUser = userService.saveUser(user);
			if (null != dbUser) {
				jo = JSONText.JavaBeanToJsonObject(dbUser);
			}
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
 		return Result.setData(true, jo);
	}
	
	@RequestMapping("/getManagerList")
	public Result getManagerList(String userName,Integer page ,Integer rows){
		if(null  == page || null == rows){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		
		Query<User> query=new Query<User>();
		if(StringUtils.isNotBlank(userName)){
			query.add(Select.eq("userName", userName));
		}
		TianDianPage tp=new  TianDianPage();
		try {
			Pageable pageable=new PageRequest(page-1, rows);
			Page<User> pages=userRepository.findAll(query,pageable);
			if(null !=pages){
				JSONArray ja=new JSONArray();
				for (User user : pages) {
					ja.add(JSONText.JavaBeanToJsonObject(user));
				}
				tp.setContent(ja);
				tp.setCurrentPage(pages.getNumber());
				tp.setPageCount(pages.getTotalPages());
				tp.setTotal(pages.getTotalElements());
			}
			
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		
		return Result.setData(true, tp);
	}
	
	@RequestMapping("/getGroup")
	public Result getGroup(Long userId ){
		if(null== userId){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		JSONArray ja=new  JSONArray();
		try {
		List<Group> groupList=	groupRepository.getGroupByUserId(userId);
		if(CollectionUtils.isNotEmpty(groupList)){
			for (Group group : groupList) {
				ja.add(JSONExt.toJSON(group));
			}
			
		}
			
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return Result.setData(true, ja);
	}
	
	@RequestMapping("/updGroup")
	public Result  updGroup(Long  userId ,String  ids ){
		if(null == userId ||  StringUtils.isBlank(ids)){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		boolean bool=false;
		try {
			bool=	userService.updGroup(userId, ids);
			
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		
		
		return Result.setData(true, bool);
	}
	

}
