package Test.Dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Array;

import Test.Base.BaseJunitTest;
import framework.dao.BoxRepository;
import framework.entity.po.BoxPort;

public class testBoxrepository extends BaseJunitTest {
	@Autowired
	BoxRepository boxRepository;
	@Test
	public void test() {
		List<BoxPort> list=new   ArrayList<BoxPort>();
		BoxPort boxPort=new  BoxPort();
		boxPort.setBoxPortId(2L);
		list.add(boxPort);
		System.out.println(JSONObject.toJSONString(boxRepository.findByBoxCode2("/dev/ttyS3")));
	}

}
