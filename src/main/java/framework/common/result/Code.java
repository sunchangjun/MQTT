package framework.common.result;

public class Code {

	// http请求返回信息
	public static final String CNUM000 = "000";
	public static final String CSTR000 = "success";

	public static final String CNUM001 = "001";
	public static final String CSTR001 = "系统响应异常,操作失败";

	public static final String CNUM002 = "002";
	public static final String CSTR002= "数据获取失败,非法请求,token不正确";

	public static final String CNUM003 = "003";
	public static final String CSTR003 = "参数非法";

	public static final String CNUM004 = "004";
	public static final String CSTR004 = "请求非法";

	public static final String CNUM005 = "005";
	public static final String CSTR005 = "验证码不正确，请重新校验！";

	public static final String CNUM006 = "006";
	public static final String CSTR006 = "该售货机下有商品存在,禁止删除！";

	public static Message getMessage(String codeStr) {
		
		Message message = new Message();
		if (CNUM000.equals(codeStr)) {
			message.setErrno(CNUM000);
			message.setErrmsg(CSTR000);
		} else if (CNUM001.equals(codeStr)) {
			message.setErrno(CNUM001);
			message.setErrmsg(CSTR001);
		} else if (CNUM002.equals(codeStr)) {
			message.setErrno(CNUM002);
			message.setErrmsg(CSTR002);
		} else if (CNUM003.equals(codeStr)) {
			message.setErrno(CNUM003);
			message.setErrmsg(CSTR003);
		} else if (CNUM004.equals(codeStr)) {
			message.setErrno(CNUM004);
			message.setErrmsg(CSTR004);
		} else if (CNUM005.equals(codeStr)) {
			message.setErrno(CNUM005);
			message.setErrmsg(CSTR005);
		} else if (CNUM006.equals(codeStr)) {
			message.setErrno(CNUM006);
			message.setErrmsg(CSTR006);
		}
		return message;
	}

}
