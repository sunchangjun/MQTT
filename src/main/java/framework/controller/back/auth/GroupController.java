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
import framework.dao.auth.AuthorityRepository;
import framework.dao.auth.GroupAuthorityRepository;
import framework.dao.auth.GroupRepository;
import framework.dao.auth.MenuRepository;
import framework.entity.po.auth.Authority;
import framework.entity.po.auth.Group;
import framework.entity.po.auth.Menu;
import framework.service.interf.auth.GroupService;


@RestController
@RequestMapping("/back/auth/group")
public class GroupController {

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	GroupService groupService;
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	GroupAuthorityRepository  groupAuthorityRepository;
	
	@Autowired
	MenuRepository menuRepository;

	@RequestMapping("/add")
	public Result add(Group group) {
		if (null == group || StringUtils.isBlank(group.getGroupName()) || StringUtils.isBlank(group.getRemarks())) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		JSONObject jo = new JSONObject();
		try {
			Group dbGroup = groupService.saveGroup(group);
			if (null != dbGroup) {
				jo = JSONText.JavaBeanToJsonObject(dbGroup);
			}
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}

		return Result.setData(true, jo);
	}

	@RequestMapping("/del")
	public Result del(Long groupId) {
		if (null == groupId) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}

		boolean bool = false;
		try {
			groupRepository.delete(groupId);
			bool = true;

		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return Result.setData(true, bool);
	}

	@RequestMapping("/getList")
	public Result getList(String groupName, String remarks, Integer page, Integer rows) {
		if (null == page || null == rows) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		TianDianPage tp = new TianDianPage();
		try {
			Query<Group> query=new Query<Group>();
			if(StringUtils.isNotBlank(groupName)){
				query.add(Select.eq("groupName", groupName));
			}
			if(StringUtils.isNotBlank(remarks)){
				query.add(Select.eq("remarks", remarks));
			}
			
			Pageable pageable=new PageRequest(page-1, rows);
			Page<Group> pages=groupRepository.findAll(query,pageable);
			JSONArray ja=new  JSONArray();
			if(CollectionUtils.isNotEmpty(pages.getContent())){
				for (Group group : pages.getContent()) {
					ja.add(JSONExt.toJSON(group));					
				}
			}
			
			tp.setContent(ja);
			tp.setCurrentPage(pages.getNumber());
			tp.setPageCount(pages.getTotalPages());
			tp.setTotal(pages.getTotalElements());
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}

		return Result.setData(true, tp);
	}
	
	@RequestMapping("/getAuth")
	public Result getAuth(Long  groupId){
		if(null == groupId){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		
		JSONArray ja=new  JSONArray();
		try {
			List<Authority> list= authorityRepository.getAuthorityListByGroupId(groupId);
			if(CollectionUtils.isNotEmpty(list)){
				for (Authority authority : list) {
					ja.add(JSONExt.toJSON(authority));
				}
			}
						
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}	
		return Result.setData(true, ja);
	}
	
	
	@RequestMapping("/updAuth")
	public  Result  updAuth(Long  groupId,String  authorityIds ){
		if(null  == groupId ||  StringUtils.isBlank(authorityIds)){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		boolean bool=false;
		try {		
		bool=	groupService.updAuth(groupId, authorityIds);
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		
		
		return  Result.setData(true, bool);
	}
	
	
	@RequestMapping("/updMenu")
	public  Result  updMenu(Long  groupId,String  ids){
		if(null == groupId  ||  StringUtils.isBlank(ids)){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		boolean bool=false;
		try {
			bool=	groupService.updMenu(groupId, ids);
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return  Result.setData(true, bool);
	}
	
	
	@RequestMapping("/getMenu")
	public  Result  getMenu(Long  groupId){
		if(null  == groupId){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		JSONArray  ja=new  JSONArray();
		try {
		List<Menu> menuList=	menuRepository.getMenuByGroupId(groupId);
		if(CollectionUtils.isNotEmpty(menuList)){
			for (Menu menu : menuList) {
				ja.add(JSONExt.toJSON(menu));
			}			
		}		
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return  Result.setData(true, ja);
	}


	

}
