package Test.Dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import Test.Base.BaseJunitTest;
import framework.common.JSONExt;
import framework.common.jpa.Query;
import framework.common.jpa.Select;
import framework.dao.AdvRepository;
import framework.dao.auth.GroupUserRepository;
import framework.dao.auth.UserRepository;
import framework.entity.po.auth.GroupUser;

public class testAdv extends BaseJunitTest {
	@Autowired
	AdvRepository advRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	GroupUserRepository groupUserRepository;

	@Test
	public void test() {
		Query<GroupUser> query = new Query<GroupUser>();
		query.add(Select.eq("user.userId", 1L));
		List<GroupUser> list = groupUserRepository.findAll(query);
		for (GroupUser groupUser : list) {
			System.out.println(JSONExt.toJSON(groupUser));
		}
	
	}

}
