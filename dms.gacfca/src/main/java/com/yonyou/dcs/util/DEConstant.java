package com.yonyou.dcs.util;

public interface DEConstant {
	public static Integer WEEK_REPORT_CUSTOMER_TIMES = 5;//经销商拜访的客户有效数
	public static Integer MAX_WEEK = 4;//用于存储过程中的未提报的周
	public static Long DE_CREATE_BY = 2222222222l;
	
	public static Long DE_UPDATE_BY = 2222222222l;
		
	public static final String DE_SOURCE = "JMCSALES";
	
	public static final int DOWN_PRE = 0; // is_down标识为可扫描
	
	public static final int DOWN_FINISH = 1; // is_down标识为已扫描
	
	public static final int PRIORITY_ONE = 1;
	
	public static final int PRIORITY_TWO = 2;
	
	public static final int PRIORITY_THREE = 3;
	
	public static final int PRIORITY_FOUR = 4;
	
	public static final int PRIORITY_FIVE = 5;
	
	public static final int PRIORITY_SIX = 6;
	
	public static final int PRIORITY_SEVEN = 7;
	
	public static final int PRIORITY_EIGHT = 8;
	
	public static final int PRIORITY_NINE = 9;
	
	public static final int PRIORITY_TEN = 10;
	
//  DMS处理数据outbound状态ACT_TYPE标识: 1,增量; 2,更新; 3,删除; 4,抽取出错; 5,抽取成功;
	public static final int EXECHANGE_DATA_ADD = 1;   //增量记录
	public static final int EXECHANGE_DATA_UPDATE = 2;//更新记录
	public static final int EXECHANGE_DATA_DEL = 3;   //删除记录
	public static final int EXECHANGE_DATA_ERROR = 4; //抽取出错
	public static final int EXECHANGE_DATA_DONE = 5;  //抽取成功
	
//调拨类型
	public static Integer DEALER_TO_DEALER=10041001; //调拨类型：代理商-代理商
	
	public static Integer UNSALED_VEHICLES=10041002; //调拨类型：滞销车
	
	public static Integer AGENT_RECEIVE=10041003; //调拨类型：代理验收
	
	public static Integer INNER_EXCHANGE=10041004;  //调拨类型：内部调拨
	
	
	String INFOX_ADAPTER = "InfoxAdapter";
	//未下发
	int IF_STATUS_0 = 0;
	//发送中
	int IF_STATUS_1 = 1;
	//已发送
	int IF_STATUS_2 = 2;
	//发送失败
	int IF_STATUS_3 = 3;
	//和接口通信的应用名称
	String APP_NAME = "InfoxAdapter";
	//源,代码上端
	//String SOURCE = "00000000";
	String SOURCE = "GMS000000";
	String APPLY_RESULT_01 = "11231001";//实销审核通过 add by lishuai103@yahoo.cn
	
	String BUSINESS_TYPE_00 = "0";//通知结果
	String BESINESS_TYPE_01 = "1";//通知一级经销商
	
	String IS_VALID_01 = "12781001";//有效
	String IS_VALID_02 = "12781002";//无效
	
	String SUCCESS = "success";
	String FAIL = "fail";
}
