package framework.common.util.oss;



import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

public class AliOSSUtil {
	
	/**
	 * 阿里云保存图片客户端工具类
	 */
	private static String OSSURL = "http://tdgou.oss-cn-qingdao.aliyuncs.com/";
	private static String ENDPOINT = "http://oss-cn-qingdao.aliyuncs.com";
	private static String KEYID = "LTAIdgZS4Hgl2NOG";
	private static String KEYSECRET = "rI2XoTJo3A72oiY3KfZoDcbs69EPlc";
	private static String BUCKET = "tdgou";
	
	
	/**
	 * 保存到oss
	 * @param file 		文件
	 * @param key		存储路径
	 * @param client	ossclient
	 * @return
	 */
	public static String save(File file,String key,OSSClient client){
		if(file == null || !file.exists()){
			return null;
		}
		Boolean isNewClient = false;
		if(client == null){
			
			client = new OSSClient(ENDPOINT, KEYID, KEYSECRET);
			isNewClient = true;
		}
		if(file.exists()){
			try{
				PutObjectResult r = client.putObject(BUCKET,key,file);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		if(isNewClient){
			client.shutdown();
		}
		return OSSURL + key;
	}
	
	/**
	 * 保存到oss
	 * @param path 		文件路径
	 * @param key		存储路径
	 * @param client	ossclient
	 * @return
	 */
	public static String save(String path,String key,OSSClient client){
		return save(new File(path),key,client);
	}

	
	/**
	 * 移除oss文件
	 * @param url	文件url
	 * @return
	 */
	public static Boolean remove(String url,OSSClient client){
		if(StringUtils.isEmpty(url)){
			return true;
		}
		Boolean isNewClient = false;
		if(client == null){
			client = new OSSClient(ENDPOINT, KEYID, KEYSECRET);
			isNewClient = true;
		}
		String key = url.substring(OSSURL.length());
		if(!StringUtils.isEmpty(key)){
			//删除对象
			client.deleteObject(BUCKET, key);
		}
		if(isNewClient){
			client.shutdown();
		}
		return true;
	}
	/**
	 * 批量存储imgs
	 * @param lst [key,path]
	 * @return
	 */
	public static Boolean saveImgs(List<String[]> lst){
		OSSClient client = new OSSClient(ENDPOINT, KEYID, KEYSECRET);
		for(int i=0,len=lst.size();i<len;i++){
			String[] params = lst.get(i);
			save(params[1],params[0],client);
		}
		client.shutdown();
		return true;
	}

}
