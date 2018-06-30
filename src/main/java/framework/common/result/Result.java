package framework.common.result;

public class Result {

	// 是否成功
	private boolean success;
	// //返回码
	private String $key;

	public String get$key() {
		return $key;
	}

	public void set$key(String $key) {
		this.$key = $key;
	}

	// //返回信息
	// private String message;
	// 对象1
	private Object result;
	// 对象2
	private Object extraData;

	// public String getCode() {
	// return code;
	// }

	// public void setCode(String code) {
	// this.code = code;
	// }
	// public String getMessage() {
	// return message;
	// }
	// public void setMessage(String message) {
	// this.message = message;
	// }
	public Object getResult() {
		return result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getExtraData() {
		return extraData;
	}

	public void setExtraData(Object extraData) {
		this.extraData = extraData;
	}

	// /**
	// * 设置返回结果
	// * @param retResult
	// * @param code
	// * @param message
	// * @param object
	// * @return
	// */
	// public static Result setRetDate(String code,String message,Object
	// object){
	// Result retResult = new Result();
	// retResult.setCode(code);
	// retResult.setMessage(message);
	// retResult.setDate(null==object?"":object);
	// return retResult;
	// }
	/**
	 * 设置返回结果
	 * 
	 * @param retResult
	 * @param code
	 * @param message
	 * @param object
	 * @return
	 */
	public static Result setData(boolean success, Object object) {
		Result retResult = new Result();
		retResult.setSuccess(success);
		retResult.setResult(null == object ? "" : object);
		return retResult;
	}

	public static Result setData(boolean success, Object object, Object extraObject) {
		Result retResult = new Result();
		retResult.setSuccess(success);
		retResult.setResult(null == object ? "" : object);
		retResult.setExtraData(null == extraObject ? "" : extraObject);
		return retResult;
	}

	// public static Result setRetDate(String code,String message,Object object,
	// Object extraObject){
	// Result retResult = new Result();
	// retResult.setCode(code);
	// retResult.setMessage(message);
	// retResult.setDate(null==object?"":object);
	// retResult.setExtraData(null==extraObject?"":extraObject);
	// return retResult;
	// }

}
