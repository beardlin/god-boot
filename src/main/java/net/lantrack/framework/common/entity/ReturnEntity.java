
package net.lantrack.framework.common.entity;

import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *@Description 返回对象
 *@Author lantrack
 *@Date 2019/8/22  16:53
 */
public class ReturnEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;

	private int status = 200;//系统状态码
	private String message = "正常";//系统状态信息
	private Object result = new HashMap<String,Object>();//返回结果


	public  ReturnEntity put(String key,Object val) {
		if(result instanceof Map){
			((Map) result).put(key,val);
		}
		return  this;
	}


	public ReturnEntity result(Object result) {
		this.result = result;
		return  this;
	}

	public ReturnEntity suc(String msg) {
		this.message = msg;
		return  this;
	}
	public ReturnEntity err() {
		this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
		this.message = "系统异常，请稍后再试";
		return  this;
	}
	public ReturnEntity err(String msg) {
		this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
		this.message = msg;
		return  this;
	}
	public ReturnEntity err(int status,String msg) {
		this.status = status;
		this.message = msg;
		return  this;
	}


	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getResult() {
		return result;
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
