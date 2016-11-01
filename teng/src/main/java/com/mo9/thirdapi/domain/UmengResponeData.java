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
// 当"ret"为"SUCCESS"时,包含如下参数:
public class UmengResponeData implements Serializable {
		private static final long serialVersionUID = -5224636795372393622L;
		// 当type为unicast、listcast或者customizedcast且alias不为空时:
		private String msg_id;
		// 当type为于broadcast、groupcast、filecast、customizedcast 且file_id不为空的情况(任务)
		private String task_id;
		// 当"ret"为"FAIL"时,包含如下参数:
		private String error_code;
		// 如果开发者填写了thirdparty_id, 接口也会返回该值。
		private String thirdpart_id;
		//错误信息
		private String errMsg;
		public String getMsg_id() {
			return msg_id;
		}

		public void setMsg_id(String msg_id) {
			this.msg_id = msg_id;
		}

		public String getTask_id() {
			return task_id;
		}

		public void setTask_id(String task_id) {
			this.task_id = task_id;
		}

		public String getError_code() {
			return error_code;
		}

		public void setError_code(String error_code) {
			this.error_code = error_code;
		}

		public String getThirdpart_id() {
			return thirdpart_id;
		}

		public void setThirdpart_id(String thirdpart_id) {
			this.thirdpart_id = thirdpart_id;
		}

		public String getErrMsg() {
			return errMsg;
		}

		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}

}
