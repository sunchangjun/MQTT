package framework.controller.box;

import java.util.List;


public class MyKeyCreater {


	private static String KEY = "WKuier_234390_dkjoDH_03-dfQz";

	public static String createKey(List<String> values) {
		StringBuffer sb = new StringBuffer();
		if (null != values && !values.isEmpty()) {
			for (String value : values) {
				sb.append(value);
			}
		}
		sb.append(KEY);
		String $key = MD5Transform.EncoderByMd5(sb.toString(), "utf-8");
		return $key;
	}


}
