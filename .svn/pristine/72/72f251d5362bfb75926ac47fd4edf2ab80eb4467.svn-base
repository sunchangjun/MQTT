package framework.mqtt.util;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.aliyun.openservices.shade.org.apache.commons.codec.binary.Base64;


public class MacSignature {
    /**
     * @param text      要签名的文本
     * @param secretKey 阿里云 MQ SecretKey
     * @return 加密后的字符串
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String macSignature(String text, String secretKey) throws InvalidKeyException, NoSuchAlgorithmException {
        Charset charset = Charset.forName("UTF-8");
        String algorithm = "HmacSHA1";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(secretKey.getBytes(charset), algorithm));
        byte[] bytes = mac.doFinal(text.getBytes(charset));
        return new String(Base64.encodeBase64(bytes), charset);
    }
    /**
     * 发送方签名方法
     *
     * @param clientId  MQTT ClientID
     * @param secretKey 阿里云 MQ SecretKey
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String publishSignature(String clientId, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        return macSignature(clientId, secretKey);
    }
    /**
     * 订阅方签名方法
     *
     * @param topics    要订阅的 Topic 集合
     * @param clientId  MQTT ClientID
     * @param secretKey 阿里云 MQ SecretKey
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String subSignature(List<String> topics, String clientId, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Collections.sort(topics); //以字典顺序排序
        String topicText = "";
        for (String topic : topics) {
            topicText += topic + "\n";
        }
        String text = topicText + clientId;
        return macSignature(text, secretKey);
    }
    /**
     * 订阅方签名方法
     *
     * @param topic     要订阅的 Topic
     * @param clientId  MQTT ClientID
     * @param secretKey 阿里云 MQ SecretKey
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String subSignature(String topic, String clientId, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        List<String> topics = new ArrayList<String>();
        topics.add(topic);
        return subSignature(topics, clientId, secretKey);
    }
    
    
    
    
    /**
	 * 计算签名，参数分别是参数对以及密钥
	 *
	 * @param requestParams
	 *            参数对，即参与计算签名的参数
	 * @param secretKey
	 *            密钥
	 * @return 签名字符串
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static String doHttpSignature(Map<String, String> requestParams, String secretKey)
			throws NoSuchAlgorithmException, InvalidKeyException {
		List<String> paramList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : requestParams.entrySet()) {
			paramList.add(entry.getKey() + "=" + entry.getValue());
		}
		Collections.sort(paramList);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < paramList.size(); i++) {
			if (i > 0) {
				sb.append('&');
			}
			sb.append(paramList.get(i));
		}
		return macSignature(sb.toString(), secretKey);
	}


}
