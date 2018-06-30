package framework.pojo;

import com.alibaba.fastjson.JSON;

public class Request {
	private String reqid = null;
	private String device;
	private byte[] data;

	public void setReqid(String reqid) {
		this.reqid = reqid;
	}

	private DataType dataType;

	public Request() {

	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getDevice() {
		return device;
	}

	public String getReqid() {
		return reqid;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public byte[] toBytes() {
		if (reqid == null) {
			reqid = "req_" + System.currentTimeMillis();
		}
		return JSON.toJSONString(this).getBytes();
	}
	
	public static byte[] Object2Bytes(Object o) {
		
		
		
		return JSON.toJSONString(o).getBytes();
	}

	public static Request toBean(byte[] data) {
		return JSON.parseObject(new String(data), Request.class);

	}
}
