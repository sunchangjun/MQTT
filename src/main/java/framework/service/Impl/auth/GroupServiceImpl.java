package framework.service.Impl.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import framework.common.CollectionUtils;
import framework.dao.auth.AuthorityRepository;
import framework.dao.auth.GroupAuthorityRepository;
import framework.dao.auth.GroupMenuRepository;
import framework.dao.auth.GroupRepository;
import framework.dao.auth.MenuRepository;
import framework.entity.po.auth.Group;
import framework.entity.po.auth.GroupAuthority;
import framework.entity.po.auth.GroupMenu;
import framework.service.interf.auth.GroupService;




@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	GroupAuthorityRepository groupAuthorityRepository;
	
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	GroupMenuRepository  groupMenuRepository;

	public Group saveGroup(Group group) {
		return groupRepository.save(group);
	}

	@Transactional
	public boolean updAuth(Long groupId, String authorityIds) {

		String[] strArray = authorityIds.split(",");
		List<Long> currIdList = new ArrayList<Long>();

		for (String string : strArray) {
			if(StringUtils.isBlank(string)){
				continue;
			}
			currIdList.add(Long.valueOf(string));
		}

		List<GroupAuthority> gaList = groupAuthorityRepository.getGroupAuthorityListByGroupId(groupId);
		List<Long> dbIdList = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(gaList)) {
			for (GroupAuthority groupAuthority : gaList) {
				dbIdList.add(groupAuthority.getAuthorityId());
				if (!currIdList.contains(groupAuthority.getAuthorityId())) {
					groupAuthorityRepository.delete(groupAuthority);
				}
			}
		}
		List<GroupAuthority> saveGAList=new  ArrayList<GroupAuthority>();
		for (Long long1 : currIdList) {
			if(!dbIdList.contains(long1)){
				GroupAuthority ga=new  GroupAuthority();
				ga.setAuthority(
				authorityRepository.findOne(long1));
				ga.setGroup(
				groupRepository.findOne(groupId));
				saveGAList.add(ga);
			}
		}
	
		groupAuthorityRepository.save(saveGAList);
		
		return true;
	}
	
	@Transactional
	public  boolean  updMenu(Long  groupId,String  ids){
		String[]  strArray=ids.split(",");
		List<Long>  currIdList=new  ArrayList<Long>();
		for (String string : strArray) {
			if(StringUtils.isBlank(string)){
				continue;
			}
			currIdList.add(Long.valueOf(string));
	
		}
		
		List<Long>  dbIdList=new  ArrayList<Long>();
		List<GroupMenu> gmList=groupMenuRepository.getGroupMenuByGroupId(groupId);
		if(CollectionUtils.isNotEmpty(gmList)){
			for (GroupMenu groupMenu : gmList) {
				dbIdList.add(groupMenu.getMenuId());
				if(!currIdList.contains(groupMenu.getMenuId())){
					groupMenuRepository.delete(groupMenu);
				}
			}
		}
		
		List<GroupMenu>  newGMList=new  ArrayList<GroupMenu>();
		for (Long long1 : currIdList) {
			if(!dbIdList.contains(long1)){
				GroupMenu groupMenu=new  GroupMenu();
				groupMenu.setGroup(
						groupRepository.findOne(groupId));
				groupMenu.setMenu(
				menuRepository.findOne(long1));
				newGMList.add(groupMenu);
			}
		}
		
		if(CollectionUtils.isNotEmpty(newGMList)){
			groupMenuRepository.save(newGMList);
		}
		
		return  true;
	}

}
