package Test.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

import Test.Base.BaseJunitTest;
import framework.common.jpa.Query;
import framework.common.jpa.Select;
import framework.dao.CommodityRepository;

import framework.entity.po.Commodity;

public class testQuery extends BaseJunitTest {
	
	@Autowired
	private CommodityRepository commodityRepository;
	
	
	@Test
	public void test(){
		Query<Commodity> criteria = new Query<>();
        criteria.add(Select.eq("commodity_id",1));

		System.out.println(JSONObject.toJSONString(commodityRepository.findAll(criteria)));;

	}
//	@Test
//	public void test1() {
//		System.out.println("11111111111111");
//		List<Object[]> list=commodityRepository.getCommodity();
//	System.out.println(JSONObject.toJSONString(list));;
//		
//	}

}
