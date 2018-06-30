package Test.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.lang.reflect.Method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.mysql.fabric.xmlrpc.base.Array;

import Test.TestPojo;
import framework.common.JSONExt;
import framework.entity.dto.AreaDTO;
import framework.entity.po.Area;
import framework.entity.po.Box;
import framework.entity.po.Commodity;

public class tes {

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
//		List<Integer> now=new ArrayList<>(Arrays.asList(1,3));
//		List<Integer> b=new ArrayList<>(Arrays.asList(2,3));
//		b.removeAll(now);
//		System.out.println(JSONObject.toJSONString(now));
//		System.out.println(JSONObject.toJSONString(b));
	
//	    List<String> stringA = Arrays.asList("hello", "world","A");
//	    String[] stringArray = {"1","","2"};
//	    List<String> stringB = Arrays.asList(stringArray);
//	 
////	    System.out.println(stringA);
//	    stringB.removeAll(Collections.singleton(null)); 
//	    System.out.println(stringB);
	  Integer i=001;
	  System.out.println(i);
	}

}
