package com.suning.epp.eppscbp.dto.res;

import java.util.List;

import com.suning.epp.eppscbp.dto.OrderAmountDto;
import com.suning.epp.eppscbp.dto.OrderNumDto;
import com.suning.epp.eppscbp.dto.SettleAmountDto;

public class OcaHomeResultDto {
	//第一条公告信息
	private String noticeMsg;
	//多条公告
	private List<String> noticeMsgList;
	
	//拒付单数量
	private int rejectOrderNum;
	
	//提现地址
	private String withdrawPath;
	
	//传送门地址
	private String mtradePath;
	
	//背景图片路径
	private String targetURL;
	
	//可用余额
	private String availableBalance;
	
	//在途资金
	private String onTheWayMoney;
	
	//保证金
	private String earnestAmount;
	
	//订单笔数
	private List<OrderNumDto> orderNum;
	
	//订单总笔数
	private int orderNumSum;
	
	//订单金额
	private List<OrderAmountDto> orderAmount;
	
	//订单总金额
	private String orderAmountSum;
	
	//结算金额
	private List<SettleAmountDto> settleAmount;
	
	//结算总金额
	private String settleAmountSum;
	
	public String getNoticeMsg() {
		return noticeMsg;
	}

	public void setNoticeMsg(String noticeMsg) {
		this.noticeMsg = noticeMsg;
	}

	public List<String> getNoticeMsgList() {
		return noticeMsgList;
	}

	public void setNoticeMsgList(List<String> noticeMsgList) {
		this.noticeMsgList = noticeMsgList;
	}

	public int getRejectOrderNum() {
		return rejectOrderNum;
	}

	public void setRejectOrderNum(int rejectOrderNum) {
		this.rejectOrderNum = rejectOrderNum;
	}

	public String getWithdrawPath() {
		return withdrawPath;
	}

	public void setWithdrawPath(String withdrawPath) {
		this.withdrawPath = withdrawPath;
	}

	public String getMtradePath() {
		return mtradePath;
	}

	public void setMtradePath(String mtradePath) {
		this.mtradePath = mtradePath;
	}

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getOnTheWayMoney() {
		return onTheWayMoney;
	}

	public void setOnTheWayMoney(String onTheWayMoney) {
		this.onTheWayMoney = onTheWayMoney;
	}

	public String getEarnestAmount() {
		return earnestAmount;
	}

	public void setEarnestAmount(String earnestAmount) {
		this.earnestAmount = earnestAmount;
	}

	public List<OrderNumDto> getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(List<OrderNumDto> orderNum) {
		this.orderNum = orderNum;
	}

	public int getOrderNumSum() {
		return orderNumSum;
	}

	public void setOrderNumSum(int orderNumSum) {
		this.orderNumSum = orderNumSum;
	}

	public List<OrderAmountDto> getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(List<OrderAmountDto> orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderAmountSum() {
		return orderAmountSum;
	}

	public void setOrderAmountSum(String orderAmountSum) {
		this.orderAmountSum = orderAmountSum;
	}

	public List<SettleAmountDto> getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(List<SettleAmountDto> settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getSettleAmountSum() {
		return settleAmountSum;
	}

	public void setSettleAmountSum(String settleAmountSum) {
		this.settleAmountSum = settleAmountSum;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	@Override
    public String toString() {
        return "OcaHomeResultDto{" +
        		", noticeMsg='" + noticeMsg + '\'' +
                ", rejectOrderNum='" + rejectOrderNum + '\'' +
                ", availableBalance='" + availableBalance + '\'' +
                ", onTheWayMoney='" + onTheWayMoney + '\'' +
                ", withdrawPath='" + withdrawPath + '\'' +
                ", mtradePath='" + mtradePath + '\'' +
                ", earnestAmount='" + earnestAmount + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                ", settleAmount='" + settleAmount + '\'' +
                ", orderNumSum='" + orderNumSum + '\'' +
                ", orderAmountSum='" + orderAmountSum + '\'' +
                ", settleAmountSum='" + settleAmountSum + '\'' +
                '}';
    }
		
}
