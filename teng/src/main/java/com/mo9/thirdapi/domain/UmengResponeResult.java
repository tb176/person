/**
 * 
 */
package com.mo9.thirdapi.domain;

import java.io.Serializable;

/**
 * 友盟推送消息反馈信息
 * 
 * @author lbqi/2016-4-14-16:37
 * 
 */
public class UmengResponeResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4235316800728059108L;
	// 返回结果，"SUCCESS"或者"FAIL"
	private String ret;
	// 结果数据
	private UmengResponeData data ;
   
	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public UmengResponeData getData() {
		return data;
	}

	public void setData(UmengResponeData data) {
		this.data = data;
	}

}
