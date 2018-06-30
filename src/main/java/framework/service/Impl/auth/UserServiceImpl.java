package framework.service.Impl.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import framework.common.CollectionUtils;
import framework.common.jpa.Query;
import framework.common.jpa.Select;
import framework.dao.auth.GroupRepository;
import framework.dao.auth.GroupUserRepository;
import framework.dao.auth.UserRepository;
import framework.entity.po.auth.GroupUser;
import framework.entity.po.auth.User;
import framework.service.interf.auth.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GroupUserRepository groupUserRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	
	
	public User saveUser(User user){
			return userRepository.save(user);
	}

	@Transactional
	public  boolean updGroup(Long  userId ,String  ids){
		User user=userRepository.findOne(userId);
		String[]  strArray=ids.split(",");
		 List<String> list = Arrays.asList(strArray);


		Query<GroupUser> query=new Query<GroupUser>();
		query.add(Select.eq("userId", userId));
		List<GroupUser> guList=groupUserRepository.findAll(query);
		
		
		List<Long> dbGroupIdList=new ArrayList<Long>();
		for (GroupUser groupUser : guList) {
			dbGroupIdList.add(groupUser.getGroupId());
			if(!list.contains(groupUser.getGroupId().toString())){
				groupUserRepository.delete(groupUser);
			}		
		}
	
		List<GroupUser>  guLists=new ArrayList<GroupUser>();
		 for (String string : list) {
			 if(StringUtils.isBlank(string)){
				 continue;
			 }
			 if(dbGroupIdList.contains(Long.valueOf(string))){
				 continue;
			 }
			 GroupUser gu=new GroupUser();
			 gu.setGroupId(Long.valueOf(string));
			 gu.setUser(user);
			 gu.setGroup( groupRepository.findOne(Long.valueOf(string)));
			 guLists.add(gu);
		}
	
		 if(CollectionUtils.isNotEmpty(guLists)){
			 groupUserRepository.save(guLists);
					 
		 }
		
		 return true; 
	}
	
}
