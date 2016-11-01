/**
 * Project Name : domain File Name : Notification.java Package Name :
 * com.gamaxpay.domain.model.suc Create Date : 2012-11-20下午5:52:10
 * 
 * Copyright ©2011 moKredit Inc. All Rights Reserved
 */
package com.mo9.thirdapi.domain;

import com.mo9.thirdapi.domain.sharding.ShardingTableUtil;
import org.hibernate.annotations.GenericGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * <p>
 * 系统通知
 * </p>
 * 
 * <p>
 * 系统通知，主要用于SNC(System Notify Center)模块,持久化每一条通知记录,执行通知业务的跟踪及存档
 * </p>
 * 
 ******************************************************** 
 * Date Author Changes <br/>
 * 2012-11-20下午5:52:10 Eric Cao 创建
 ******************************************************** 
 */
@Entity(name = "Notification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Notification")
@Table(name = "T_NOTIFICATION")
public abstract class Notification extends Persistable {

	/**
	 * serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1319404900983533003L;

	/** 待通知到目标对象的消息内容 */
	private String message;

	/** 当前的消息状态 */
	private String status;

	/** 消息状态：正在排队(消息已经初始化，但是尚未执行通知) */
	public static final String STATUS_QUEUEING = "1";

	/** 消息状态：正在重试(消息由于首次通知失败，正在执行重发策略) */
	public static final String STATUS_RETRYING = "2";

	/** 消息状态：已经完成(消息已经正确通知到目标用户) */
	public static final String STATUS_DONE = "3";

	/** 消息状态：已经取消(由于重发策略执行失败，或者管理员手动取消尚未发送到通知) */
	public static final String STATUS_UNDO = "4";

	/** 通知消息的创建时间 */
	private Date createTime= new Date();

	/** 通知消息的过期时间,如果消息已经超过过期时间都没有发送成功，则自动切换到UNDO状态 */
	private Date expiredTime;

	/** 消息已经尝试发送到次数(包括最后一次成功通知) */
	private int notifyTimes = 0;

	/** 上一次通知时间 */
	private Date lastNotifyTime;

	/** 下一次通知时间 */
	private Date nextNotifyTime;

	/** 备注,如果消息处于重发状态，则记录上一次失败的原因. */
	private String remark;

	/**分表ID*/
	private String notificationId;

	
	public Notification()
	{
		this.notificationId = ShardingTableUtil.createShardingTableId();
	}

	/**
	 * 计算下一次发送到时间. 当前重试时间按如下规则计算.
	 * 首次发送失败后延后1分钟，执行第1次重试，第1次重试失败后，延后2分钟执行重试,以后各次重试延迟按费伯拉契数列递增.
	 * 
	 */
	public void calculateNextNotifyTime() {

		int fisrtDelayMin = 1;// 延时分钟数
		int secondDelayMin = 1;// 上一次延迟分钟数
		for ( int i = 2; i <= notifyTimes; i++ ) {
			int temp = fisrtDelayMin + secondDelayMin;
			secondDelayMin = fisrtDelayMin;
			fisrtDelayMin = temp;
		}
		Calendar nextNotifyTime = Calendar.getInstance();
		nextNotifyTime.setTime(lastNotifyTime);
		nextNotifyTime.add(Calendar.MINUTE, fisrtDelayMin);
		this.nextNotifyTime = nextNotifyTime.getTime();

		if ( this.nextNotifyTime.compareTo(expiredTime) > 0 ) {
			/** 下一次执行时间，已经超过了消息的有效时间，则将该消息取消 */
			this.status = STATUS_UNDO;
		}
	}

	public static void main(String[] args) {

		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, 2);
		Notification n = new HttpNotification();
		n.setLastNotifyTime(new Date());
		n.setExpiredTime(now.getTime());
		n.setStatus(STATUS_QUEUEING);
		for ( int i = 1; i < 50; i++ ) {

			n.setNotifyTimes(i);
			n.calculateNextNotifyTime();
			System.out.println(i + ":" + n.getNextNotifyTime());
			if ( n.getStatus().equals(STATUS_UNDO) ) {
				break;
			}
			n.setLastNotifyTime(n.getNextNotifyTime());
		}

	}

	// /////////////////////Getter And Setter/////////////////////////
	@Id
	@GeneratedValue(generator = "notificationId")
	@GenericGenerator(name = "notificationId", strategy = "native")
	@Override
	public long getId() {

		// TODO Auto-generated method stub
		return id;
	}

	@Column(name = "message")
	public String getMessage() {

		return message;
	}

	@Column(name = "status", length = 2)
	public String getStatus() {

		return status;
	}

	@Column(name = "createtime")
	public Date getCreateTime() {

		return createTime;
	}

	@Column(name = "expiredTime")
	public Date getExpiredTime() {

		return expiredTime;
	}

	@Column(name = "notifyTimes")
	public int getNotifyTimes() {

		return notifyTimes;
	}

	@Column(name = "lastNotifyTime")
	public Date getLastNotifyTime() {

		return lastNotifyTime;
	}

	@Column(name = "nextNotifyTime")
	public Date getNextNotifyTime() {

		return nextNotifyTime;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public void setExpiredTime(Date expiredTime) {

		this.expiredTime = expiredTime;
	}

	public void setNotifyTimes(int notifyTimes) {

		this.notifyTimes = notifyTimes;
	}

	public void setLastNotifyTime(Date lastNotifyTime) {

		this.lastNotifyTime = lastNotifyTime;
	}

	public void setNextNotifyTime(Date nextNotifyTime) {

		this.nextNotifyTime = nextNotifyTime;
	}

	@Column(name = "remark")
	public String getRemark() {

		return remark;
	}

	public void setRemark(String remark) {

		this.remark = remark;
	}

	@Column(name = "notification_id")
	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	
}
