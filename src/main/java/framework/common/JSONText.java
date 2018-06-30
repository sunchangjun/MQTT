package framework.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JSONText {

	public static JSONObject JavaBeanToJsonObject(Object object) {

		String entityString = JSONObject.toJSONString(object);
		JSONObject js = JSON.parseObject(entityString);
		return js;

	}

}
