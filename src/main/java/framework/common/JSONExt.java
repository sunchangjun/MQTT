/**
 * JSON扩展库
 */
package framework.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONObject;

public class JSONExt {
	private final static String types = "java.lang.String,java.lang.Integer,java.lang.Long,java.lang.Integer,java.lang.Float,java.lang.Double,java.lang.Number,java.lang.Short,java.lang.Boolean";
	/**
	 * 针对多级对象处理
	 * @param jsonObject:待处理对象
	 * @param clazz:目标类
	 * @return 转换后对象
	 */
	public static Object toBean(JSONObject jsonObject,Class clazz){
		if(null == jsonObject)
			return null;
		List <String> lstName=new ArrayList<String>();
		List <Object> lstValue=new ArrayList<Object>();
		
		//避免jsonObject remove key 之后错误，放到数组中
		for(Iterator<String> ite= jsonObject.keys();ite.hasNext();){
			String key = ite.next();
			lstName.add(key);
			lstValue.add(jsonObject.get(key));
		}
		
		for(int i=0,len=lstName.size();i<len;i++){
			String key = lstName.get(i);
			if(key.contains(".")){
				String[] ks = key.split("\\.");
				jsonObject.put(ks[0], handleLevel(ks,jsonObject.get(key)));
				jsonObject.remove(key);
			}
		}
		try{
			return JSONObject.toBean(jsonObject, clazz);
		}catch(Exception  e){
			return null;
		}
	}
	
	/**
	 * entity to json
	 * @param entity
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws Exception
	 */
	public static com.alibaba.fastjson.JSONObject toJSON(Object entity){
		if(null == entity)
			return null;
		Class clazz = entity.getClass();
		com.alibaba.fastjson.JSONObject jret = new com.alibaba.fastjson.JSONObject();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field:fields){
			String fn = field.getName();
			if(types.contains(field.getType().getName())){
				Method m;
				try {
					m = clazz.getDeclaredMethod("get" + fn.substring(0,1).toUpperCase() + fn.substring(1));
					if(null != m){
						Object v = m.invoke(entity);
						if(null != v)
							jret.put(fn, v);
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return jret;
	}
	/**
	 * 多级处理
	 * @param ks:字段名数组
	 * @param v:值
	 * @return jsonobject 或  值
	 */
	private static Object handleLevel(String[] ks,Object v){
		Integer len = ks.length;
		if(len > 1){
			while(len>1){
				JSONObject jo = new JSONObject();
				jo.put(ks[len-1], v);
				v = jo;
				len--;
			}
		}
		return v;
	}
}
