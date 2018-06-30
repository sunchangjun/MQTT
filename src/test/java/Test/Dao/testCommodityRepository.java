package Test.Dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

import Test.Base.BaseJunitTest;
import framework.dao.CommodityRepository;

public class testCommodityRepository extends BaseJunitTest {
	@Autowired
	private CommodityRepository commodityRepository;
	@Test
	public void test() {
		System.out.println(JSONObject.toJSONString(commodityRepository.findAllByDevice("DLA364")));
	}

}
