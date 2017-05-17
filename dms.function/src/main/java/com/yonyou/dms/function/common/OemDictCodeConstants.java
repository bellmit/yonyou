/*
 * Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 *
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : cmol.common.function
 *
 * @File name : CommonConstants.java
 *
 * @Author : zhangxianchao
 *
 * @Date : 2016年2月24日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年2月24日    zhangxianchao    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.function.common;

/*
 *
 * @author xiawei
 * CommonConstants
 * @date 2017年2月16日
 */

public class OemDictCodeConstants {

	/*********************************************************************************************************/
	/* 固定代码：公共 */
	/*********************************************************************************************************/

	// 港口优先级
	public static String UN_ORDER_STATUS = "2012";// 撤单状态
	public static String UN_ORDER_STATUS1 = "20121001";// 撤单成功
	public static String UN_ORDER_STATUS2 = "20121002";// 撤单失败
	public static String UN_ORDER_STATUS3 = "20121003";// 撤单中

	public static String CANCEL_TYPE = "2001";// 操作类型
	public static String CANCEL_TYPE1 = "20011001";// 订单取消
	public static String CANCEL_TYPE2 = "20011002";// 订单撤单

	// 套餐类型
	public static String TAOCAN_TYPE1 = "90111001";// 正常套餐
	public static String TAOCAN_TYPE2 = "90111002";// 配件套餐

	/**
	 * 配置星期几
	 */
	public static String OEM_LOGING_DEALERCODE = "999999";

	public final static int REGION_TYPE_WEEK = 90001001;
	public final static int REGION_TYPE_WEEK2 = 90001002;
	public final static int REGION_TYPE_WEEK3 = 90001003;
	public final static int REGION_TYPE_WEEK4 = 90001004;
	public final static int REGION_TYPE_WEEK5 = 90001005;
	public final static int REGION_TYPE_WEEK6 = 90001006;
	public final static int REGION_TYPE_WEEK7 = 90001007;
	// 有效期状态
	public static String VALID_STATE = "2090";

	public static String VALID_STATE1 = "20901001";// 已启用

	public static String VALID_STATE2 = "20901002";// 未启用
	public static String VALID_STATE3 = "20901003";// 已过期
	// 库存类型
	public static Integer LIFCYCLE = 1379; // 生产线上
	public static Integer LIFCYCLE1 = 13791001; // 生产线上
	public static Integer LIFCYCLE2 = 13791002; // CG库存
	public static Integer LIFCYCLE3 = 13791003; // CATC库存
	public static Integer LIFCYCLE4 = 13791004; // 经销商库存
	public static Integer LIFCYCLE5 = 13791005; // 已实销

	public static Integer selctStore = 1378; // CG库存/CTCAI库存
	public static Integer selctStore1 = 13781001; // CG库存
	public static Integer selctStore2 = 13781002; // CTCAI库存
	public static Integer storeType = 1263; // 分配状态
	public static Integer storeType1 = 20261001; // 已分配
	public static Integer storeType2 = 20261002; // 未分配

	public static Integer IS_SUPPORT = 1263; // 任务类型
	public static Integer IS_SUPPORT1 = 12631001; // 任务nei

	public static Integer IS_SUPPORT2 = 12631002; // 任务wai

	// FLASH操作数据库的用户ID
	public static Integer CLEAN_DAY = 7; // 保留BI表几天的数据

	public static String FLASH_UID = "1234567890";

	public static String OEM_SALES_CODE = "8888888888";

	public static String replaceChar = "-";

	public static String HMCI = "31Q";

	public static String codeJsUrl = ""; // 字典表数据js的url

	public static String regionJsUrl = ""; // 省份城市表数据js的url

	public static String LOGON_USER = "LOGON_USER"; // session设置登录用户

	public static String PART_ADD_PER = "PART_ADD_PER";// 配件加价率

	public static Integer PAGE_SIZE = 10; // pageSize

	public static Integer PAGE_SIZE_100 = 100;

	public static Integer PAGE_SIZE200 = 200; // pageSize-200

	public static Integer DOWNLOAD_PAGE_SIZE_MAX = 50000; // 下载最大条数限制

	public static Integer PAGE_SIZE_MAX = 999999999; // pageSize 为了某些无需分页的查询

	public static int errorNum = 50;

	public static Integer SYS_USER = 1002; // 用户类型

	public static Integer SYS_USER_OEM = 10021001; // 用户类型主机厂端

	public static Integer SYS_USER_DEALER = 10021002; // 用户类型经销商端

	public static Integer SYS_USER_INTERFACE = 11111111;// 用户类型,接口用户

	public static Long SYS_USERID_INTERFACE = 12345678l;// 接口用户ID

	public static String INVENTORY_ALERT_URL = "/errorPD.jsp";

	public static String PROJECT_NAME = "JV"; // 国产车标识

	// GLO 接口用户ID

	public static Long SYS_USERID_GLO_INTERFACE = 9999999L;// 接口用户ID

	// GLO 接口编号
	public static Integer GLO_ORDER_INTERFACE_NO = 1001;// 订单上报-GLO接口编号

	public static Integer GLO_ORDER_CONFIRM_NO = 1002;// 订单确认-GLO接口编号

	public static Integer GLO_SHIPPING_ADVICE_NO = 1003;// 订单发货通知-GLO编号

	// GLO 接口处理状态
	public static Integer GLO_STATUS_DEAL = 1;// 已处理

	public static Integer GLO_STATUS_UNDEAL = 0;// 未处理

	// GLO 业务处理状态
	public static Integer GLO_BUSINESS_DEAL = 1;// 已处理

	public static Integer GLO_BUSINESS_UNDEAL = 0;// 未处理

	// GLO 文件处理状态
	public static Integer GLO_FILE_DEAL_UNDEAL = 1;// 文件未处理

	public static Integer GLO_FILE_DEAL_SUCDEAL = 2;// 文件成功处理

	public static Integer GLO_FILE_DEAL_WK = 3;// 文件属于WK(错误文件)

	public static Integer GLO_FILE_DEAL_TRASH = 4;// 文件属于垃圾文件

	// 公司类型
	// modified by andy.ten@tom.com
	public static String COMPANY_TYPE = "1053"; //

	/**
	 * 公司类型 - 车厂
	 */
	public static final String COMPANY_TYPE_OEM = "10531001";

	/**
	 * 公司类型 - 经销商
	 */
	public static final String COMPANY_TYPE_DEALER = "10531002";

	// end
	// 公共-是否
	public static String IF_TYPE = "1004";

	public static Integer IF_TYPE_YES = 10041001; // 是

	public static Integer IF_TYPE_NO = 10041002; // 否

	// 性别f
	public static Integer GENDER_TYPE = 1003;

	public static final Integer MAN = 10031001; // 男

	public static final Integer WOMEN = 10031002; // 女

	public static final Integer NONO = 10031003;

	// 组织类型
	// modified by andy.ten@tom.com
	public static Integer ORG_TYPE = 1019;

	public static Integer ORG_TYPE_OEM = 10191001; // 主机厂

	public static Integer ORG_TYPE_DEALER = 10191002; // 经销商

	// 组织层级
	public static Integer DUTY_TYPE = 1043;

	public static Integer DUTY_TYPE_COMPANY = 10431001; // 公司

	public static Integer DUTY_TYPE_DEPT = 10431002; // 部门

	public static Integer DUTY_TYPE_LARGEREGION = 10431003; // 大区

	public static Integer DUTY_TYPE_SMALLREGION = 10431004; // 小区

	public static Integer DUTY_TYPE_DEALER = 10431005; // 经销商

	// 组织业务类型
	public final static Integer ORG_BUSS_TYPE = 1235;
	public final static Integer ORG_BUSS_TYPE_01 = 12351001; // 销售组织
	public final static Integer ORG_BUSS_TYPE_02 = 12351002; // 售后组织
	// end

	/**
	 * mofied by andy.ten@tom.com 组织树根节点代码，可能每个车厂不同，需更改
	 */
	public static String ORG_ROOT_CODE = "40000";

	public static final String OEM_ACTIVITIES = "2010010100070674"; // 大区父节点
	/**
	 * mofied by andy.ten@tom.com 职位业务类型
	 */
	public static final int POSE_BUS_TYPE = 1078; // 职位业务类型
	public static final int POSE_BUS_TYPE_SYS = 10781001; // 系统管理(ALL)
	public static final int POSE_BUS_TYPE_VS = 10781002; // 销售管理职位
	public static final int POSE_BUS_TYPE_WR = 10781003; // 车厂售后
	public static final int POSE_BUS_TYPE_DVS = 10781004; // 经销商销售
	public static final int POSE_BUS_TYPE_DWR = 10781005; // 经销商售后
	public static final int POSE_BUS_TYPE_JSZX = 10781006; // 结算中心职位类型

	// 经销商类型
	public static final int DEALER_TYPE = 1077; // 经销商类型
	public static final int DEALER_TYPE_DVS = 10771001; // 经销商整车
	public static final int DEALER_TYPE_DWR = 10771002; // 经销商售后
	public static final int DEALER_TYPE_JSZX = 10771003;// 结算中心
	public static final int DEALER_TYPE_QYZDL = 10771004;// 区域总代理

	// 讴歌广本经销商类型
	public static final Integer ACURA_GHAS_TYPE = 1079; // 三包授权经销商类型
	public static final Integer ACURA_GHAS_TYPE_01 = 10791001; // 授权
	public static final Integer ACURA_GHAS_TYPE_02 = 10791002; // 非授权

	// 状态
	public static Integer STATUS = 1001; // 状态类型

	public static Integer STATUS_ENABLE = 10011001; // 有效

	public static Integer STATUS_DISABLE = 10011002; // 无效

	// 状态
	public static Integer FROZEN_STATUS = 1999; // 状态类型

	public static Integer FROZEN__ENABLE = 19990001; // 冻结

	public static Integer FROZEN__DISABLE = 19990002; // 无效

	// 处理结果
	public static String ACCEPT_RES = "2012";

	public static String ACCEPT_RES_UNACTION = "20121001"; // 未处理

	public static String ACCEPT_RES_ACCEPT = "20121002"; // 已处理

	public static String ACCEPT_RES_ABATE = "20121003"; // 已失效

	public static final String Login_Function_Id = "10000000";

	// 系统公用功能
	public static final String COMMON_URI = "/common";

	// 数据校验长度常量
	public static final int Length_Check_Char_1 = 1; // 长度不超过1，即数据库长度为1

	public static final int Length_Check_Char_6 = 6; // 长度不超过6，即数据库长度为20或者邮编的长度校验

	public static final int Length_Check_Char_8 = 8; // 整数长度不超过8，对应数据库长度为（10,2），小数长度最多为2

	public static final int Length_Check_Char_10 = 10; // 长度不超过10，数据库长度为30

	public static final int Length_Check_Char_12 = 12; // 长度不超过12,认证号校验

	public static final int Length_Check_Char_15 = 15; // 长度不超过15，数据库长度为50

	public static final int Length_Check_Char_17 = 17; // 长度不超过17，VIN校验

	public static final int Length_Check_Char_20 = 20; // 长度不超过20，数据库长度为60

	public static final int Length_Check_Char_30 = 30; // 长度不超过30，数据库长度为100

	// K4 接口ID
	public final static long K4_S0001 = -1; // S0001 接口
	public final static long K4_S0002 = -2; // S0002 接口
	public final static long K4_S0003 = -3; // S0003 接口
	public final static long K4_S0004 = -4; // S0004 接口
	public final static long K4_S0005 = -5; // S0005 接口
	public final static long K4_S0006 = -6; // S0006 接口
	public final static long K4_S0007 = -7; // S0007 接口
	public final static long K4_S0008 = -8; // S0008 接口
	public final static long K4_S0009 = -9; // S0009 接口
	public final static long K4_S0010 = -10; // S0010 接口
	public final static long K4_S0011 = -11; // S0011 接口
	public final static long K4_S0012 = -12; // S0012 接口
	public final static long K4_S0013 = -13; // S0013 接口
	public final static long K4_S0014 = -14; // S0014 接口
	public final static long K4_S0015 = -15; // S0015 接口
	public final static long K4_S0016 = -16; // S0016 接口
	public final static long K4_S0017 = -17; // S0017 接口

	// add by yuyong @ 2011-1-10
	public final static Integer WORK_ALERT_STATUS = 1227; // 待办提醒状态
	public final static Integer WORK_ALERT_STATUS_01 = 12271001; // 未完成
	public final static Integer WORK_ALERT_STATUS_02 = 12271002; // 知道了
	public final static Integer WORK_ALERT_STATUS_03 = 12271003; // 关闭

	public final static Integer WORK_ALERT_TYPE = 1228; // 待办提醒类型
	public final static Integer WORK_ALERT_TYPE_01 = 12281001; // 待办
	public final static Integer WORK_ALERT_TYPE_02 = 12281002; // 提醒

	// 提醒类型
	public static String RETRNN_TYPE = "2043";

	public static String RETURN_VISIT = "20431001"; // 回访

	public static String EXAMINE_AND_ENDORSE = "20431002"; // 审批

	public static String WAIT_TO_HANDLE = "20431003"; // 待办

	public static final int Length_Check_Char_50 = 50; // 长度不超过50，数据库长度为150

	public static final int Length_Check_Char_60 = 60; // 长度不超过60，数据库长度为200

	public static final int Length_Check_Char_100 = 100; // 长度不超过100，数据库长度为300

	// 操作类型
	public static String RPCH_AGRM_DISPOSITION = "20441001"; // 收购意向

	public static String PURCHASE_AGREEMENT = "20441002"; // 收购协议

	public static String SALE_ORDER_DISPOSITION = "20441003"; // 销售意向

	public static String CLIENT_LOVING = "20441004"; // 客户关怀

	public static String VEHICLE_CANNIBALIZE = "20441005"; // 车辆调拨调入

	public static String SALE_ORDER = "20441006"; // 销售订单

	public static String EQUIPPEN_NEWSREEL = "20441007"; // 整备记录

	public static String VEHICLE_COME_ON = "20441008"; // 车辆出库

	public static String VEHICLE_SELL_FOLD = "20441009"; // 车辆寄售调入

	public static String VEHICLE_SELL_EXIT = "20441010"; // 车辆寄售退回

	public static String VEHICLE_INCOME = "20441011"; // 车辆入库

	public static String SELL_REPORT = "20441012"; // 销售上报

	public static String VEHICLE_TRANSFER = "20441013"; // 车辆过户

	public static String OBLIGATE_CANCEL = "20441014"; // 预留取消

	public static String OPERATE_AUTH_SUB = "20441015"; // 认证申请

	public static String OPERATE_AUTH_APRV = "20441016"; // 认证审批

	public static String OPERATE_SALES_APRV = "20441017"; // 销售审批

	public static String OPERATE_VISIT_DEAL = "20441018"; // 来访处理

	// 经销商级别
	public final static Integer DEALER_LEVEL = 1085;
	public final static Integer DEALER_LEVEL_01 = 10851001; // 一级经销商
	public final static Integer DEALER_LEVEL_02 = 10851002; // 二级经销商

	/**
	 * 数据权限类别(经销商) - 本人
	 */
	public static final Long DLR_BR = new Long(1000000001);

	/**
	 * 数据权限类别(经销商) - 本组织及以下
	 */
	public static final Long DRL_BZZJYX = new Long(1000000002);

	/**
	 * 数据权限类别(经销商) - 整个经销商
	 */
	public static final Long DRL_ZGJXS = new Long(1000000003);

	/**
	 * 数据权限类别(车厂) - 本组织及以下
	 */
	public static final Long OEM_BZZJYX = new Long(1000000004);

	/**
	 * 数据权限类别(车厂) - 整个OEM经销商
	 */
	public static final Long OEM_ZGJXS = new Long(1000000005);

	// 车型组类型
	public final static Integer WR_MODEL_GROUP_TYPE = 1045;
	public final static Integer WR_MODEL_GROUP_TYPE_01 = 10451001; // 售后车型组
	public final static Integer WR_MODEL_GROUP_TYPE_02 = 10451002; // 销售车型组

	public static final Integer PARA_TYPE = 1133; // 可变代码维护类型
	public static final Integer PARA_TYPE_01 = 11331001;// 经销商类型
	public static final Integer PARA_TYPE_02 = 11331002;// 经销商评级

	// 删除标示
	public static final Integer IS_DEL_01 = 1; // 逻辑删除
	public static final Integer IS_DEL_00 = 0; // 逻辑未删除

	// 地区类型
	public final static Integer REGION_TYPE = 1054;
	public final static Integer REGION_TYPE_01 = 10541001; // 国家
	public final static Integer REGION_TYPE_02 = 10541002; // 省市
	public final static Integer REGION_TYPE_03 = 10541003; // 地区
	public final static Integer REGION_TYPE_04 = 10541004; // 区县

	public final static Integer DC_TYPE = 1233;// 配件仓库类型
	public final static Integer DC_TYPE_01 = 12331001; // 配件中心库
	public final static Integer DC_TYPE_02 = 12331002; // 配件中转库
	public final static Integer DC_TYPE_03 = 12331003; // 待检库
	public final static Integer DC_TYPE_04 = 12331004; // 质保库
	public final static Integer DC_TYPE_05 = 12331005; // 售后处理库

	// 服务活动管理--生产基地
	public static final Integer SERVICEACTIVITY_CAR_YIELDLY = 1131;
	public static final Integer SERVICEACTIVITY_CAR_YIELDLY_01 = 11311001;//
	public static final Integer SERVICEACTIVITY_CAR_YIELDLY_02 = 11311002;//
	public static final Integer SERVICEACTIVITY_CAR_YIELDLY_03 = 11311003;//

	// 经销商评级 add by yangzhen 2010-08-28
	public final static Integer DEALER_CLASS_TYPE = 1145;
	public final static Integer DEALER_CLASS_TYPE_01 = 11451001; // 服务中心
	public final static Integer DEALER_CLASS_TYPE_02 = 11451002; // 授权专营店
	public final static Integer DEALER_CLASS_TYPE_03 = 11451003; // 特约维修站
	public final static Integer DEALER_CLASS_TYPE_04 = 11451004; // 汽车超市
	public final static Integer DEALER_CLASS_TYPE_05 = 11451005; // 二级销售网点
	public final static Integer DEALER_CLASS_TYPE_06 = 11451006; // 结算中心
	public final static Integer DEALER_CLASS_TYPE_07 = 11451007; // 专用车公司
	public final static Integer DEALER_CLASS_TYPE_08 = 11451008; // 长安国际公司

	// add by yuyong 2012-05-16
	public final static Integer ORDER_RESOURCE_STATUS_PARA = 10011001; // 订单资源紧张数值

	// add by huyushan 2013-05-17
	public final static Integer TARGET_TYPE = 2078; // 目标类型
	public final static Integer TARGET_TYPE_01 = 20781001; // 批售
	public final static Integer TARGET_TYPE_02 = 20781002; // 零售

	/**
	 * 销售TC_CODE START
	 */

	public final static Integer MATERIAL_PRICE_MAX = 5000000; // 物料能提报订单的价格最大值

	public static final Integer PLAN_MANAGE = 2001;// 计划确认状态
	public static final Integer PLAN_MANAGE_01 = 20011001;// 待确认
	public static final Integer PLAN_MANAGE_02 = 20011002;// 已确认

	public static final Integer WAREHOUSE_TYPE = 2002;// 仓库可用状态
	public static final Integer WAREHOUSE_TYPE_01 = 20021001;// 可用
	public static final Integer WAREHOUSE_TYPE_02 = 20021002;// 不可用

	public static final Integer ORDER_REPORT_TYPE = 2003;// 订单提报状态
	public static final Integer ORDER_REPORT_TYPE_YES = 20031001;// 可提报
	public static final Integer ORDER_REPORT_TYPE_NO = 20031002;// 不可提报

	public static final Integer ORDER_STATUS = 2007;// 订单状态
	public static final Integer ORDER_STATUS_01 = 20071001;//
	public static final Integer ORDER_STATUS_02 = 20071002;//
	public static final Integer ORDER_STATUS_03 = 20071003;//
	public static final Integer ORDER_STATUS_04 = 20071004;// 定金未确认
	public static final Integer ORDER_STATUS_05 = 20071005;// 定金已确认
	public static final Integer ORDER_STATUS_06 = 20071006;// 订单已确认
	public static final Integer ORDER_STATUS_07 = 20071007;// 资源已分配
	public static final Integer ORDER_STATUS_08 = 20071008;// 已取消
	public static final Integer ORDER_STATUS_09 = 20071009;// 已撤单

	public static final Integer TRANSPORT_TYPE = 2008;// 运输类型
	public static final Integer TRANSPORT_TYPE_01 = 20081001;// 运输
	public static final Integer TRANSPORT_TYPE_02 = 20081002;// 自提

	public static final Integer FOP_OUT_TYPE = 2013;// FOP出库类型
	public static final Integer FOP_OUT_TYPE_01 = 20131001;// 销售出库
	public static final Integer FOP_OUT_TYPE_02 = 20131002;// HMCI自用出库
	public static final Integer FOP_OUT_TYPE_03 = 20131003;// 零部件领用
	public static final Integer FOP_OUT_TYPE_04 = 20131004;// 补发出库
	public static final Integer FOP_OUT_TYPE_05 = 20131005;// 出库未计入
	public static final Integer FOP_OUT_TYPE_06 = 20131006;// 报废
	public static final Integer FOP_OUT_TYPE_07 = 20131007;// 不良品退回
	public static final Integer FOP_OUT_TYPE_08 = 20131008;// 其他

	public static final Integer VEHICLE_CHANGE_TYPE = 2021;// 详细车籍类型状态
	public static final Integer VEHICLE_CHANGE_TYPE_01 = 20211001;// 装船登记
	public static final Integer VEHICLE_CHANGE_TYPE_02 = 20211002;// ZGOR-车辆到港
	public static final Integer VEHICLE_CHANGE_TYPE_03 = 20211003;// 点检
	public static final Integer VEHICLE_CHANGE_TYPE_04 = 20211004;// 通关完成
	public static final Integer VEHICLE_CHANGE_TYPE_05 = 20211005;// ZVP8-入VPC仓库
	public static final Integer VEHICLE_CHANGE_TYPE_06 = 20211006;// ZPDP-PDI检查通过
	public static final Integer VEHICLE_CHANGE_TYPE_07 = 20211007;// ZPDU-发车出库
	public static final Integer VEHICLE_CHANGE_TYPE_08 = 20211008;// 移库出库
	public static final Integer VEHICLE_CHANGE_TYPE_09 = 20211009;// 经销商库存
	public static final Integer VEHICLE_CHANGE_TYPE_10 = 20211010;// 已实销
	public static final Integer VEHICLE_CHANGE_TYPE_11 = 20211011;// 退车出库
	public static final Integer VEHICLE_CHANGE_TYPE_12 = 20211012;// ZTPR-退车入库
	public static final Integer VEHICLE_CHANGE_TYPE_13 = 20211013;// 调拨出库
	public static final Integer VEHICLE_CHANGE_TYPE_14 = 20211014;// 调拨入库
	public static final Integer VEHICLE_CHANGE_TYPE_15 = 20211015;// ZFSC-工厂订单冻结
	public static final Integer VEHICLE_CHANGE_TYPE_16 = 20211016;// ZVDU-工厂订单车辆数据更新
	public static final Integer VEHICLE_CHANGE_TYPE_17 = 20211017;// ZSHP-海运在途
	public static final Integer VEHICLE_CHANGE_TYPE_18 = 20211018;// ZCBC-车辆清关
	public static final Integer VEHICLE_CHANGE_TYPE_19 = 20211019;// ZBIL-一次开票
	public static final Integer VEHICLE_CHANGE_TYPE_20 = 20211020;// ZDRL-中进车款确认
	public static final Integer VEHICLE_CHANGE_TYPE_21 = 20211021;// ZDRQ-换车入库
	public static final Integer VEHICLE_CHANGE_TYPE_22 = 20211022;// ZPDF-PDI检查失败
	public static final Integer VEHICLE_CHANGE_TYPE_23 = 20211023;// ZFSN-工厂订单冻结
	public static final Integer VEHICLE_CHANGE_TYPE_24 = 20211024;// ZRL1-资源已分配
	public static final Integer VEHICLE_CHANGE_TYPE_25 = 20211025;// ZDRR-经销商订单确认
	public static final Integer VEHICLE_CHANGE_TYPE_26 = 20211026;// ZDRV-中进车款取消
	public static final Integer VEHICLE_CHANGE_TYPE_27 = 20211027;// 经销商订单撤单

	public static final Integer VEHICLE_NODE_TYPE = 2022;// 车辆节点状态
	public static final Integer VEHICLE_NODE_TYPE_01 = 20221001;// 装船登记
	public static final Integer VEHICLE_NODE_TYPE_02 = 20221002;// 到港登记
	public static final Integer VEHICLE_NODE_TYPE_03 = 20221003;// 点检
	public static final Integer VEHICLE_NODE_TYPE_04 = 20221004;// 通关完成
	public static final Integer VEHICLE_NODE_TYPE_05 = 20221005;// 入库
	public static final Integer VEHICLE_NODE_TYPE_06 = 20221006;// PDI检查完成

	// public static final Integer VEHICLE_LIFE_CYCLE_TYPE = 2023;//车辆生命周期
	// public static final Integer LIF_CYCLE_02 = 20231001;//采购在途
	// public static final Integer LIF_CYCLE_02 = 20231002;//销售库存
	// public static final Integer LIF_CYCLE_03 = 20231003;//经销商在途
	// public static final Integer LIF_CYCLE_04 = 20231004;//经销商库存
	// public static final Integer LIF_CYCLE_05 = 20231005;//已实销

	public static final Integer VEHICLE_SALE_PAY_TYPE = 2027;// 车辆付款方式
	public static final Integer VEHICLE_SALE_PAY_TYPE_01 = 20271001;// 现金支付
	public static final Integer VEHICLE_SALE_PAY_TYPE_02 = 20271002;// 菲亚特金融
	public static final Integer VEHICLE_SALE_PAY_TYPE_03 = 20271003;// 承兑汇票
	public static final Integer VEHICLE_SALE_PAY_TYPE_04 = 20271004;// 建行批售融资

	public static final Integer VEHICLE_NATURE_TYPE = 2028;// 车辆性质
	public static final Integer VEHICLE_NATURE_TYPE_01 = 20281001;// 商务车
	public static final Integer VEHICLE_NATURE_TYPE_02 = 20281002;// 私家车
	public static final Integer VEHICLE_NATURE_TYPE_03 = 20281003;// 警车车
	public static final Integer VEHICLE_NATURE_TYPE_04 = 20281004;// 出租车
	public static final Integer VEHICLE_NATURE_TYPE_05 = 20281005;// 租赁公司

	public static final Integer CTM_TYPE = 2029;// 客户类型
	public static final Integer CTM_TYPE_01 = 20291001;// 个人客户
	public static final Integer CTM_TYPE_02 = 20291002;// 公司客户

	public static final Integer CARD_TYPE = 2030;// 证据类型
	public static final Integer CARD_TYPE_01 = 20301001;// 身份证
	public static final Integer CARD_TYPE_02 = 20301002;// 军官证
	public static final Integer CARD_TYPE_03 = 20301003;// 警官证
	public static final Integer CARD_TYPE_04 = 20301004;// 护照
	public static final Integer CARD_TYPE_05 = 20301005;// 士兵证
	public static final Integer CARD_TYPE_06 = 20301006;// 其他
	public static final Integer CARD_TYPE_07 = 20301007;// 机构代码
	public static final Integer CARD_TYPE_08 = 20301008;// 公司证件

	public static final Integer INCOME_TYPE = 2031;// 家庭月收入
	public static final Integer INCOME_TYPE_01 = 20311001;// 其他
	public static final Integer INCOME_TYPE_02 = 20311002;// 小于5千
	public static final Integer INCOME_TYPE_03 = 20311003;// 5千-1万
	public static final Integer INCOME_TYPE_04 = 20311004;// 1万-1.5万
	public static final Integer INCOME_TYPE_05 = 20311005;// 1.5万-2万
	public static final Integer INCOME_TYPE_06 = 20311006;// 2万以上

	public static final Integer EDUCATION_TYPE = 2032;// 教育状况
	public static final Integer EDUCATION_TYPE_01 = 20321001;// 小学
	public static final Integer EDUCATION_TYPE_02 = 20321002;// 初中
	public static final Integer EDUCATION_TYPE_03 = 20321003;// 高中/中专/技校
	public static final Integer EDUCATION_TYPE_04 = 20321004;// 大专
	public static final Integer EDUCATION_TYPE_05 = 20321005;// 本科
	public static final Integer EDUCATION_TYPE_06 = 20321006;// 硕士或以上

	public static final Integer MARRIED_TYPE = 2033;// 婚姻状况类型
	public static final Integer MARRIED_TYPE_01 = 20331001;// 未婚
	public static final Integer MARRIED_TYPE_02 = 20331002;// 已婚

	public static final Integer COMMUNICATE_TYPE = 2034;// 喜好沟通方式
	public static final Integer COMMUNICATE_TYPE_01 = 20341001;// 面对面沟通
	public static final Integer COMMUNICATE_TYPE_02 = 20341002;// 电话沟通
	public static final Integer COMMUNICATE_TYPE_03 = 20341003;// 邮件沟通

	public static final Integer COMPETE_LEVEL = 2035;// 竞品级别
	public static final Integer BRAND = 20351001;// 品牌
	// public static final Integer MODEL = 20351002;// 车型

	public static final Integer FOP_STATUS = 2014;// FOP状态
	public static final Integer FOP_STATUS_01 = 20141001;// 专用
	public static final Integer FOP_STATUS_02 = 20141002;// 通用

	public static final Integer TAX_TYPE = 2036;// 税金类型
	public static final Integer TAX_TYPE_01 = 20361001;// 关税
	public static final Integer TAX_TYPE_02 = 20361002;// 消费税
	public static final Integer TAX_TYPE_03 = 20361003;// 反倾销税
	public static final Integer TAX_TYPE_04 = 20361004;// 其他
	public static final Integer TAX_TYPE_05 = 20361005;// 进口增值税

	public static final Integer REBATES_TYPE = 2040;// 税金类型
	public static final Integer REBATES_TYPE_01 = 20401001;// MDX
	public static final Integer REBATES_TYPE_02 = 20401002;// TL
	public static final Integer REBATES_TYPE_03 = 20401003;// ZDX
	public static final Integer REBATES_TYPE_04 = 20401004;// RDX
	public static final Integer REBATES_TYPE_05 = 20401005;// ILX
	public static final Integer REBATES_TYPE_06 = 20401006;// RL
	public static final Integer REBATES_TYPE_07 = 20401007;// J
	public static final Integer REBATES_TYPE_08 = 20401008;// H
	public static final Integer REBATES_TYPE_09 = 20401009;// W
	public static final Integer REBATES_TYPE_10 = 20401010;// O

	// add by huyu start
	public static final Integer INVOICE_CHARGE_TYPE = 2041;// 费用类型
	public static final Integer INVOICE_CHARGE_TYPE_01 = 20411001;// 购车费用
	public static final Integer INVOICE_CHARGE_TYPE_02 = 20411002;// 代办服务费
	public static final Integer INVOICE_CHARGE_TYPE_03 = 20411003;// 精品装潢费
	public static final Integer INVOICE_CHARGE_TYPE_04 = 20411004;// 保险费用
	public static final Integer INVOICE_CHARGE_TYPE_05 = 20411005;// 购税费用
	public static final Integer INVOICE_CHARGE_TYPE_06 = 20411006;// 牌照费用
	public static final Integer INVOICE_CHARGE_TYPE_07 = 20411007;// 信贷费用
	// end

	public static final Integer ACCOUNT_CHANGE_TYPE = 2011;// 资金操作类型
	public static final Integer ACCOUNT_CHANGE_TYPE_01 = 20111001;// 入账
	public static final Integer ACCOUNT_CHANGE_TYPE_02 = 20111002;// 扣款

	// 资金账户类型固定代码
	public static final String ACCOUNT_TYPE_CODE_01 = "CBJ";// 储备金

	public static final Integer ORDER_CHECK_TYPE = 2005;// 订单审核类型
	public static final Integer ORDER_CHECK_TYPE_01 = 20051001;// 资源审核
	public static final Integer ORDER_CHECK_TYPE_02 = 20051002;// 补贴审核

	public static final Integer CHECK_STATUS = 2006;// 订单审核类型
	public static final Integer CHECK_STATUS_01 = 20061001;// 审核通过
	public static final Integer CHECK_STATUS_02 = 20061002;// 审核驳回

	// 订单更改类型
	public final static Integer ORDER_CHNG_TYPE = 2037;
	public final static Integer ORDER_CHNG_TYPE_01 = 20371001;// 订单取消

	// 开票状态
	public final static Integer TICKET_STATUS = 2010;
	public final static Integer TICKET_STATUS_01 = 20101001;// 未申请
	public final static Integer TICKET_STATUS_02 = 20101002;// 已申请
	public final static Integer TICKET_STATUS_03 = 20101003;// 已开票

	// 配车状态
	public final static Integer MATCH_STATUS = 2009;
	public final static Integer MATCH_STATUS_01 = 20091001;// 已配
	public final static Integer MATCH_STATUS_02 = 20091002;// 配车取消

	// 发运状态
	public final static Integer DELIVERY_STATUS = 2019;
	public final static Integer DELIVERY_STATUS_01 = 20191001;// 待发运
	public final static Integer DELIVERY_STATUS_02 = 20191002;// 发运在途
	public final static Integer DELIVERY_STATUS_03 = 20191003;// 验收入库
	public final static Integer DELIVERY_STATUS_04 = 20191004;// 移库在途
	public final static Integer DELIVERY_STATUS_05 = 20191005;// 发运取消

	// 邮寄类型
	public final static Integer POST_TYPE = 2038;
	public final static Integer POST_TYPE_01 = 20381001;// 上牌文件
	public final static Integer POST_TYPE_02 = 20381002;// 增值税发票

	// 邮寄状态
	public final static Integer POST_STATUS = 2039;
	public final static Integer POST_STATUS_01 = 20391001;// 邮寄途中
	public final static Integer POST_STATUS_02 = 20391002;// 已签收

	// 发运类型
	public final static Integer DELIVERY_TYPE = 2018;
	public final static Integer DELIVERY_TYPE_01 = 20181001;// 销售发运
	public final static Integer DELIVERY_TYPE_02 = 20181002;// 移库发运

	// 调拨审核状态
	public final static Integer TRANSFER_CHECK_STATUS = 2020;
	public final static Integer TRANSFER_CHECK_STATUS_01 = 20201001;// 待审核
	public final static Integer TRANSFER_CHECK_STATUS_02 = 20201002;// 审核通过
	public final static Integer TRANSFER_CHECK_STATUS_03 = 20201003;// 审核驳回
	public final static Integer TRANSFER_CHECK_STATUS_04 = 20201004;// 已调拨
	public final static Integer TRANSFER_CHECK_STATUS_05 = 20201005;// 审核中

	// 退车申请状态
	public final static Integer RETURN_APPLY_STATUS = 2025;
	public final static Integer RETURN_APPLY_STATUS_01 = 20251001;// 待审核
	public final static Integer RETURN_APPLY_STATUS_02 = 20251002;// 审核通过
	public final static Integer RETURN_APPLY_STATUS_03 = 20251003;// 审核驳回
	public final static Integer RETURN_APPLY_STATUS_04 = 20251004;// 已退车

	// 承运商名称陆友物流
	public final static String SHIPPER_NAME = "陆友物流";

	// 公共资源状态
	public final static Integer COMMON_RESOURCE_STATUS = 2080;
	public final static Integer COMMON_RESOURCE_STATUS_01 = 20801001;// 未下发
	public final static Integer COMMON_RESOURCE_STATUS_02 = 20801002;// 已下发
	public final static Integer COMMON_RESOURCE_STATUS_03 = 20801003;// 已取消

	// 公共资源类型
	public final static Integer COMMON_RESOURCE_TYPE = 2081;
	public final static Integer COMMON_RESOURCE_TYPE_01 = 20811001;// 期货
	public final static Integer COMMON_RESOURCE_TYPE_02 = 20811002;// 现货
	public final static Integer COMMON_RESOURCE_TYPE_03 = 20811003;// 天津爆炸港资源池

	// 车辆匹配状态
	public final static Integer VEHICLE_MATE_STATUS = 2090;
	public final static Integer VEHICLE_MATE_STATUS_01 = 20901001;// 未匹配
	public final static Integer VEHICLE_MATE_STATUS_02 = 20901002;// 已匹配

	// 订单类型
	public final static Integer ORDER_TYPE = 2083;
	public final static Integer ORDER_TYPE_01 = 20831001;// 现货--订单类型名称：'现货'需要改成'资源池'
															// update by
															// dengweili
															// 20131107
	public final static Integer ORDER_TYPE_02 = 20831002;// 期货
	public final static Integer ORDER_TYPE_03 = 20831003;// 指派
	public final static Integer ORDER_TYPE_04 = 20831004;// 天津爆炸港资源池

	// 车辆锁定状态
	public final static Integer VEHICLE_LOCK_STATUS = 2096;
	public final static Integer VEHICLE_LOCK_STATUS_01 = 20961001; // 正常库存
	public final static Integer VEHICLE_LOCK_STATUS_02 = 20961002; // 预留锁定
	public final static Integer VEHICLE_LOCK_STATUS_03 = 20961003; // 移库锁定
	public final static Integer VEHICLE_LOCK_STATUS_04 = 20961004; // 质损锁定
	public final static Integer VEHICLE_LOCK_STATUS_05 = 20961005; // 退库锁定

	// 销售类型
	public final static Integer SELL_TYPE = 2091;
	public final static Integer SELL_TYPE_01 = 20911001;// 1-Direct Retail Sale
	public final static Integer SELL_TYPE_02 = 20911002;// C-Vehicle Conversion
	public final static Integer SELL_TYPE_03 = 20911003;// L-Retail Lease

	// 头衔
	public final static Integer TITLE = 2092;
	public final static Integer TITLE_01 = 20921001;// 1-Mr.
	public final static Integer TITLE_02 = 20921002;// 2-Mrs.
	public final static Integer TITLE_03 = 20921003;// 3-Miss
	public final static Integer TITLE_04 = 20921003;// 4-Reverend

	// 国家
	public final static Integer COUNTRY = 2093;
	public final static Integer COUNTRY_01 = 20931001;// CHINA
	public final static Integer COUNTRY_02 = 20931002;// INDIA
	public final static Integer COUNTRY_03 = 20931003;// ENGLAND
	public final static Integer COUNTRY_04 = 20931002;// AMERICAN SAMOA

	// 客户语言偏好
	public final static Integer CUSTOMER_LANGUAGE = 2094;
	public final static Integer CUSTOMER_LANGUAGE_01 = 20941001;// ENGLISH
	public final static Integer CUSTOMER_LANGUAGE_02 = 20941002;// CHINESE

	// 是否联系
	public final static Integer IS_CONTACT = 2095;
	public final static Integer IS_CONTACT_01 = 20951001;// YES
	public final static Integer IS_CONTACT_02 = 20951002;// NO

	// 零售交车状态
	public final static Integer VEHICLE_RETAIL_STATUS = 2097;
	public final static Integer VEHICLE_RETAIL_STATUS_02 = 20971001;// 待审核
	public final static Integer VEHICLE_RETAIL_STATUS_03 = 20971002;// 通过
	public final static Integer VEHICLE_RETAIL_STATUS_04 = 20971003;// 无效

	// 交车上报状态
	public final static Integer VEHICLE_REPORT_STATUS = 2098;
	public final static Integer VEHICLE_REPORT_STATUS_02 = 20981001;// 未上报
	public final static Integer VEHICLE_REPORT_STATUS_03 = 20981002;// 已上报

	// 订单编号
	public final static String SALES_ORDER_FUTURES_NO = "SOF";// 销售期货订单标号前缀
	public final static String SALES_ORDER_NOW_NO = "SON";// 销售现货订单标号前缀
	public final static String SALES_ORDER_APPOINT_NO = "SOA";// 销售指派订单标号前缀

	// 车辆用途(进口)
	public final static Integer VEHICLE_USE = 2084;
	public final static Integer VEHICLE_USE_01 = 20841001;// 普通
	public final static Integer VEHICLE_USE_02 = 20841002;// 试乘试驾车
	public final static Integer VEHICLE_USE_03 = 20841003;// 员工购车
	public final static Integer VEHICLE_USE_04 = 20841004;// 公司租赁车
	public final static Integer VEHICLE_USE_05 = 20841005;// 服务用车
	public final static Integer VEHICLE_USE_06 = 20841006;// 其他
	public final static Integer VEHICLE_USE_07 = 20841007;// 媒体置换车
	public final static Integer VEHICLE_USE_08 = 20841008;// 媒体试驾车
	public final static Integer VEHICLE_USE_09 = 20841009;// 大客户购车
	public final static Integer VEHICLE_USE_10 = 20841010;// 瑕疵车
	public final static Integer VEHICLE_USE_11 = 20841011;// 瑕疵车其他

	// 零售上报类型
	public final static Integer RETAIL_REPORT_TYPE = 2085;
	public final static Integer RETAIL_REPORT_TYPE_01 = 20851001; // 快速上报
	public final static Integer RETAIL_REPORT_TYPE_02 = 20851002; // 交车上报
	public final static Integer RETAIL_REPORT_TYPE_03 = 20851003; // 零售补传

	// 关键岗位变更状态
	public final int KEY_CHANGE_TYPE = 3127;
	public final int KEY_CHANGE_TYPE_CURRENT = 31271001; // 当前
	public final int KEY_CHANGE_TYPE_CHANGE = 31271002; // 历史

	// 关键岗位变更操作类型
	public final int KEY_CHANGE_OPTYPE = 3103;
	public final int KEY_CHANGE_OPTYPE_NEW = 31031001; // 新入职
	public final int KEY_CHANGE_OPTYPE_CHANGEPOST = 31031002; // 换岗
	public final int KEY_CHANGE_OPTYPE_LEAVE = 31031003; // 离职

	// 关键岗位人员审批状态
	public static Integer KEY_STATUS_APPROVAL = 3104; // type
	public static Integer KEY_STATUS_APPROVAL_01 = 31041001; // 待审批
	public static Integer KEY_STATUS_APPROVAL_02 = 31041002; // 试用
	public static Integer KEY_STATUS_APPROVAL_03 = 31041003; // 审批通过
	public static Integer KEY_STATUS_APPROVAL_04 = 31041004; // 审批驳回

	// 关键岗位人员岗位状态
	public static Integer STATION_KEY_STATUS_TYPE = 3105;
	public static Integer STATUS_IN_THE_POST = 31051001; // 在职
	public static Integer STATUS_NOT_THE_POST = 31051002; // 离职
	public static Integer STATUS_BEFOREIN_THE_POST = 31051003; // 新入职 add by

	// 职位类型
	public static int POSITION_TYPE = 2099;
	public static int POSITION_TYPE_01 = 20991001; // 销售经理
	public static int POSITION_TYPE_02 = 20991002; // 售后经理

	/*
	 * // 关键岗位状态 public static int STATION_STATUS_TYPE = 3106; public static int
	 * STATION_STATUS_TYPE_LOCK = 31061001; // 待审核 public static int
	 * STATION_STATUS_TYPE_NO_LOCK = 31061002; // 正常
	 */
	// 关键岗位匹配结果
	public static Integer MATCHI_RESULT = 3107;
	public static Integer MATCHI_RESULT_01 = 31071001; // 未匹配
	public static Integer MATCHI_RESULT_02 = 31071002; // 匹配成功
	public static Integer MATCHI_RESULT_03 = 31071003; // 匹配失败
	/**
	 * 销售TC_CODE END
	 */

	/**
	 * 售后TC_CODE START
	 */
	public final static double MAINTAIN_MAX_DAYS = 1461d;// 4年免费保养最大天数
	public final static double MAINTAIN_MAX_MILLEGE = 100000d;// 10万公里限制
	public final static String MAINTENSE_ORDER_PRE = "4PM31Q";// 保养工单前缀
	public final static String WARRANTY_ORDER_PRE = "4FR31Q";// 维修工单前缀
	public final static String BALANCE_ORDER_PRE = "4BA31Q";// 结算工单前缀
	public final static String TECH_ORDER_PRE = "4TL31Q";// 技术连线工单前缀

	/*
	 * public final static Integer REPAIR_TYPE = 4001;//售后维修类型 public final
	 * static Integer REPAIR_TYPE_01 = 40011001; //标准维修 public final static
	 * Integer REPAIR_TYPE_02 = 40011002; //售前维修 public final static Integer
	 * REPAIR_TYPE_03 = 40011003; //返修 public final static Integer
	 * REPAIR_TYPE_04 = 40011004; //服务活动
	 */

	public final static Integer REPAIR_FIXED_TYPE = 4100;// 售后维修类型(固化后)16种
	public final static Integer REPAIR_FIXED_TYPE_01 = 41001001; // 常规保养
	public final static Integer REPAIR_FIXED_TYPE_02 = 41001002; // 普通维修
	public final static Integer REPAIR_FIXED_TYPE_03 = 41001003; // 内部修理
	public final static Integer REPAIR_FIXED_TYPE_04 = 41001004; // 内部返工维修
	public final static Integer REPAIR_FIXED_TYPE_05 = 41001005; // 外部返工维修
	// public final static Integer REPAIR_FIXED_TYPE_06 = 41001006; //售前整备
	public final static Integer REPAIR_FIXED_TYPE_07 = 41001007; // 钣金喷漆
	public final static Integer REPAIR_FIXED_TYPE_08 = 41001008; // 精品装潢
	public final static Integer REPAIR_FIXED_TYPE_09 = 41001009; // 自店活动
	public final static Integer REPAIR_FIXED_TYPE_10 = 41001010; // 其他
	public final static Integer REPAIR_FIXED_TYPE_11 = 41001011; // 首次保养
	public final static Integer REPAIR_FIXED_TYPE_12 = 41001012; // 保修
	public final static Integer REPAIR_FIXED_TYPE_13 = 41001013; // 善意维修
	public final static Integer REPAIR_FIXED_TYPE_14 = 41001014; // PDI检查
	// public final static Integer REPAIR_FIXED_TYPE_15 = 41001015; //召回
	public final static Integer REPAIR_FIXED_TYPE_16 = 41001016; // 服务活动
	public final static Integer REPAIR_FIXED_TYPE_17 = 41001017; // 三包维修
	public final static Integer REPAIR_FIXED_TYPE_18 = 41001018; // 营销活动

	public final static Integer REPAIR_ORD_BALANCE_TYPE = 4002;// 维修工单结算状态
	public final static Integer REPAIR_ORD_BALANCE_TYPE_01 = 40021001; // 未结算
	public final static Integer REPAIR_ORD_BALANCE_TYPE_02 = 40021002; // 已结算

	public final static Integer REPAIR_ORD_DEAL_TYPE = 4003;// 维修工单处理方式
	public final static Integer REPAIR_ORD_DEAL_TYPE_01 = 40031001; // 修理
	public final static Integer REPAIR_ORD_DEAL_TYPE_02 = 40031002; // 更换
	public final static Integer REPAIR_ORD_DEAL_TYPE_03 = 40031003; // 检测
	public final static Integer REPAIR_ORD_DEAL_TYPE_04 = 40031004; // 调整
	public final static Integer REPAIR_ORD_DEAL_TYPE_05 = 40031005; // 清洁
	public final static Integer REPAIR_ORD_DEAL_TYPE_06 = 40031006; // 检测和更换

	/*
	 * 原来的 public final static Integer REPAIR_ORD_TYPE = 4004;//维修工单类型 public
	 * final static Integer REPAIR_ORD_TYPE_01 = 40041001; //保养工单 public final
	 * static Integer REPAIR_ORD_TYPE_02 = 40041002; //维修工单
	 */
	// 固化后
	public final static Integer REPAIR_ORD_TYPE = 4004;// 维修工单类型
	public final static Integer REPAIR_ORD_TYPE_01 = 40041001; // 索赔
	public final static Integer REPAIR_ORD_TYPE_02 = 40041002; // 自费

	public final static Integer TRADE_TERM = 4005;// 贸易条件
	public final static Integer TRADE_TERM_01 = 40051001; // 零售
	public final static Integer TRADE_TERM_02 = 40051002; // DNP
	public final static Integer TRADE_TERM_03 = 40051003; // FOB

	public final static Integer PAY_TERM = 4006;// 付费货币
	public final static Integer PAY_TERM_01 = 40061001; // CNY
	public final static Integer PAY_TERM_02 = 40061002; // USD

	public final static Integer CODE_TPYE = 4007;// 代码类型
	public final static Integer CODE_TPYE_01 = 40071001; // 投诉代码
	public final static Integer CODE_TPYE_02 = 40071002; // 故障代码
	public final static Integer CODE_TPYE_03 = 40071003; // 质损程度
	public final static Integer CODE_TPYE_04 = 40071004; // 质损区域
	public final static Integer CODE_TPYE_05 = 40071005; // 质损类型

	public final static Integer CLAIM_STATUS = 4008;// 索赔状态
	public final static Integer CLAIM_STATUS_01 = 40081001; // 未上报
	public final static Integer CLAIM_STATUS_02 = 40081002; // 已上报
	public final static Integer CLAIM_STATUS_03 = 40081003; // 审核中
	public final static Integer CLAIM_STATUS_04 = 40081004; // 审核驳回
	public final static Integer CLAIM_STATUS_05 = 40081005; // 审核拒绝
	public final static Integer CLAIM_STATUS_06 = 40081006; // 审核通过
	public final static Integer CLAIM_STATUS_07 = 40081007; // 结算上报
	public final static Integer CLAIM_STATUS_08 = 40081008; // 结算通过

	public final static Integer NWS_STATUS = 4009;// NWS状态
	public final static Integer NWS_STATUS_01 = 40091001; // NWS待审核
	public final static Integer NWS_STATUS_02 = 40091002; // NWS审核中
	public final static Integer NWS_STATUS_03 = 40091003; // NWS审核拒绝
	public final static Integer NWS_STATUS_04 = 40091004; // NWS审核通过
	public final static Integer NWS_STATUS_05 = 40091005; // HMCI特批
	public final static Integer NWS_STATUS_06 = 40091006; // NWS未批准

	public final static Integer CLAIM_TYPE = 4010;// 索赔类型
	public final static Integer CLAIM_TYPE_01 = 40101001; // 标准保修
	public final static Integer CLAIM_TYPE_02 = 40101002; // 定期保养
	public final static Integer CLAIM_TYPE_03 = 40101003; // 服务活动
	public final static Integer CLAIM_TYPE_04 = 40101004; // 召回/PUD/PIC
	public final static Integer CLAIM_TYPE_05 = 40101005; // 电瓶有限保修

	public final static Integer CLAIM_TYPE_G = 40281002;// 全球索赔类型
	public final static String CLAIM_TYPE_G_F = "F"; // 首保索赔-Service Contract
														// (F)
	public final static String CLAIM_TYPE_G_S = "S"; // 召回索赔-Safety/Recall (S)
	public final static String CLAIM_TYPE_G_W = "W"; // 整车索赔-Warranty (W)
	public final static String CLAIM_TYPE_G_M = "M"; // 零件索赔-Mopar (M)
	public final static String CLAIM_TYPE_G_T = "T"; // 运输索赔-TCR (T,
														// Transportation)

	public final static Integer CLAIM_TYPE_L = 40281001;// 本地索赔类型
	public final static String CLAIM_TYPE_L_A = "A"; // 服务活动activity claim（A）
	public final static String CLAIM_TYPE_L_V = "V"; // VPC索赔 VPC claim（V）
	public final static String CLAIM_TYPE_L_G = "G"; // 善意索赔goodwill claim(G)
	public final static String CLAIM_TYPE_L_E = "E"; // 延保索赔extended warranty
														// claim(E)

	public final static Integer BILL_TYPE = 4011;// 结算类型
	public final static Integer BILL_TYPE_01 = 40111001; // 定期保养
	public final static Integer BILL_TYPE_02 = 40111002; // 一般维修
	public final static Integer BILL_TYPE_03 = 40111003; // HMCI特批

	public final static Integer BILL_STATUS = 4012;// 结算状态
	public final static Integer BILL_STATUS_01 = 40121001; // HMCI未确认
	public final static Integer BILL_STATUS_02 = 40121002; // HMCI已确认

	public final static Integer INVOICE_STATUS = 4013;// 结算发票状态
	public final static Integer INVOICE_STATUS_01 = 40131001; // 未登记
	public final static Integer INVOICE_STATUS_02 = 40131002; // 已登记

	public final static Integer IS_ARRIVAL = 4014;// 保养单凭证到达状态
	public final static Integer IS_ARRIVAL_01 = 40141001; // 未到达
	public final static Integer IS_ARRIVAL_02 = 40141002; // 已到达

	public final static Integer ACTIVITY_TYPE = 4015;// 活动类别
	public final static Integer ACTIVITY_TYPE_01 = 40151001; // RRT快速反应
	public final static Integer ACTIVITY_TYPE_02 = 40151002; // TSB技术通报
	public final static Integer ACTIVITY_TYPE_03 = 40151003; // CSN满意度改进
	public final static Integer ACTIVITY_TYPE_04 = 40151004; // Recall召回活动
	public final static Integer ACTIVITY_TYPE_05 = 40151005; // 免费维修
	public final static Integer ACTIVITY_TYPE_06 = 40151006; // 免费检查
	public final static Integer ACTIVITY_TYPE_07 = 40151007; // 维修套餐
	public final static Integer ACTIVITY_TYPE_08 = 40151008; // 配件服务活动

	public final static Integer DEAL_TYPE = 4016;// 活动处理方式
	public final static Integer DEAL_TYPE_01 = 40161001; // 更换
	public final static Integer DEAL_TYPE_02 = 40161002; // 调整
	public final static Integer DEAL_TYPE_03 = 40161003; // 更换和调整

	public final static Integer ACTIVITY_STATUS = 4017;// 活动状态
	public final static Integer ACTIVITY_STATUS_01 = 40171001; // 未发布
	public final static Integer ACTIVITY_STATUS_02 = 40171002; // 已发布
	public final static Integer ACTIVITY_STATUS_03 = 40171003; // 已关闭

	// 索赔申请单-授权规则维护-授权项
	public final static Integer CLAIM_AUTH_TYPE = 4021; // 授权项类别
	public final static Integer CLAIM_AUTH_TYPE_01 = 40211001; // 维修操作代码
	public final static Integer CLAIM_AUTH_TYPE_02 = 40211002; // 零件金额
	public final static Integer CLAIM_AUTH_TYPE_03 = 40211003; // 维修总金额
	public final static Integer CLAIM_AUTH_TYPE_04 = 40211004; // 其它项目费用
	public final static Integer CLAIM_AUTH_TYPE_05 = 40211005; // 修理完工天数
	public final static Integer CLAIM_AUTH_TYPE_06 = 40211006; // 索赔类型
	public final static Integer CLAIM_AUTH_TYPE_07 = 40211007; // 索赔申请次数
	public final static Integer CLAIM_AUTH_TYPE_08 = 40211008; // 修理完上报期限（天）
	public final static Integer CLAIM_AUTH_TYPE_09 = 40211009; // 是否特殊质保车辆
	public final static Integer CLAIM_AUTH_TYPE_10 = 40211010; // 经销商代码
	public final static Integer CLAIM_AUTH_TYPE_11 = 40211011; // 车型代码
	public final static Integer CLAIM_AUTH_TYPE_12 = 40211012; // 产地

	// 授权项算术比较符
	public final static Integer COMP_TYPE = 4022;
	public final static Integer COMP_TYPE_01 = 40221001;// =
	public final static Integer COMP_TYPE_02 = 40221002;// <
	public final static Integer COMP_TYPE_03 = 40221003;// <=
	public final static Integer COMP_TYPE_04 = 40221004;// >=
	public final static Integer COMP_TYPE_05 = 40221005;// >
	public final static Integer COMP_TYPE_06 = 40221006;// <>

	// 授权项逻辑比较符
	public final static Integer LOGIC_TYPE = 4023;
	public final static Integer LOGIC_TYPE_01 = 40231001;// Begin
	public final static Integer LOGIC_TYPE_02 = 40231002;// Equal
	public final static Integer LOGIC_TYPE_03 = 40231003;// notBegin
	public final static Integer LOGIC_TYPE_04 = 40231004;// notEqual

	public final static Integer AUTO_CHECK_TYPE = 4024;
	public final static Integer AUTO_CHECK_TYPE_01 = 40241001; // 拒绝规则
	public final static Integer AUTO_CHECK_TYPE_02 = 40241002; // 退回规则
	public final static Integer AUTO_CHECK_TYPE_03 = 40241003; // 人工规则
	public final static Integer CLAIM_RULE_TYPE_04 = 40241004; // 自动通过规则

	public final static Integer CHECK_TYPE = 4025;
	public final static Integer CHECK_TYPE_01 = 40251001; // 自动审核
	public final static Integer CHECK_TYPE_02 = 40251002; // 人工审核

	// 现场报告状态
	public final static Integer FIELD_REPORT_STATUS = 4026;
	public final static Integer FIELD_REPORT_STATUS_01 = 40261001; // 未上报
	public final static Integer FIELD_REPORT_STATUS_02 = 40261002; // 已上报
	public final static Integer FIELD_REPORT_STATUS_03 = 40261003; // 审核驳回
	public final static Integer FIELD_REPORT_STATUS_04 = 40261004; // 审核同意
	public final static Integer FIELD_REPORT_STATUS_05 = 40261005; // 审核拒绝

	// 现场报告关闭状态
	public final static Integer FIELD_REPORT_CLOSE_STATUS = 4027;
	public final static Integer FIELD_REPORT_CLOSE_STATUS_01 = 40271001; // 未关闭
	public final static Integer FIELD_REPORT_CLOSE_STATUS_02 = 40271002; // 已关闭

	public final static Integer Labour_Price = 200; // 工时单价

	public final static Integer OLD_PART_01 = 40000000;// 所有旧件遇到的TC_CODE用OLD_PART_01代替

	// 接口下发标识
	public static final Integer IS_DOWN_00 = 0; // 未下发
	public static final Integer IS_DOWN_01 = 1; // 已下发

	// 索赔类别
	public final static Integer CLAIM_CATEGORY = 4028;
	public final static Integer CLAIM_CATEGORY_01 = 40281001;// 本地索赔
	public final static Integer CLAIM_CATEGORY_02 = 40281002;// 全球索赔

	// 活动索赔类型
	public final static Integer ACTIVITY_CLAIM_TYPE = 4030;
	public final static Integer ACTIVITY_CLAIM_TYPE_01 = 40301001;// 召回索赔
	public final static Integer ACTIVITY_CLAIM_TYPE_02 = 40301002;// 服务活动

	// 活动选择方式
	public final static Integer CHOOSE_WAY = 4031;
	public final static Integer CHOOSE_WAY_01 = 40311001;// 必选
	public final static Integer CHOOSE_WAY_02 = 40311002;// 非必选

	// 车龄类型
	public final static Integer AGE_TYPE = 4032;
	public final static Integer AGE_TYPE_01 = 40321001;// 生产日期
	public final static Integer AGE_TYPE_02 = 40321002;// 购车日期

	// 活动车辆状态
	public final static Integer ACTIVITY_VEHICLE_TYPE = 4033;
	public final static Integer ACTIVITY_VEHICLE_TYPE_01 = 40331001;// 未完成
	public final static Integer ACTIVITY_VEHICLE_TYPE_02 = 40331002;// 已完成

	/**
	 * 售后TC_CODE END
	 */
	/**
	 * 配件固定代码
	 */
	public static String partFormartMart = "_"; // 用于配件显示代码
	/**
	 * 配件TC_CODE START
	 */
	public final static Integer CURRENCY_TYPE = 3001;// 货币名
	public final static Integer CURRENCY_TYPE_01 = 30011001; // 人民币
	public final static Integer CURRENCY_TYPE_02 = 30011002; // 日元
	public final static Integer CURRENCY_TYPE_03 = 30011003; // 美元

	public final static Integer CHNG_TYPE = 3002;// 代码类别
	public final static Integer CHNG_TYPE_01 = 30021001;// 进口许可证
	public final static Integer CHNG_TYPE_02 = 30021002;// 3C认证
	public final static Integer CHNG_TYPE_03 = 30021003;// 零部件类型
	public final static Integer CHNG_TYPE_04 = 30021004;// 原产国信息
	public final static Integer CHNG_TYPE_05 = 30021005;// 分拣区
	public final static Integer CHNG_TYPE_06 = 30021006;// 销售代码
	public final static Integer CHNG_TYPE_07 = 30021007;// 零部件单位
	public final static Integer CHNG_TYPE_08 = 30021008;// 危险品
	public final static Integer CHNG_TYPE_09 = 30021009;// 港口代码
	public final static Integer CHNG_TYPE_10 = 30021010;// 原因代码
	public final static Integer CHNG_TYPE_11 = 30021011;// AGE Code
	public final static Integer CHNG_TYPE_12 = 30021012;// 零部件显示格式
	public final static Integer CHNG_TYPE_13 = 30021013;// 区域

	public final static Integer POINT_HANDLE = 3003;// 小数处理
	public final static Integer POINT_HANDLE_01 = 30031001;// 四舍五入
	public final static Integer POINT_HANDLE_02 = 30031002;// 舍入
	public final static Integer POINT_HANDLE_03 = 30031003;// 舍去

	public final static Integer PART_SALE_ORDER_TYPE = 3004;// 销售订单类型
	public final static Integer PART_SALE_ORDER_TYPE_01 = 30041001;// 保修
	public final static Integer PART_SALE_ORDER_TYPE_02 = 30041002;// 紧急
	public final static Integer PART_SALE_ORDER_TYPE_03 = 30041003;// 紧急汽运
	public final static Integer PART_SALE_ORDER_TYPE_04 = 30041004;// 库存

	public final static Integer PART_PURCHASE_ORDER_TYPE = 3005;// 采购订单类型
	public final static Integer PART_PURCHASE_ORDER_TYPE_01 = 30051001;// 海运
	public final static Integer PART_PURCHASE_ORDER_TYPE_02 = 30051002;// 空运
	public final static Integer PART_PURCHASE_ORDER_TYPE_03 = 30051003;// HOT
																		// LINE

	public final static Integer PART_PURCHASE_ORDER_STATUS = 3006;// 采购订单状态
	public final static Integer PART_PURCHASE_ORDER_STATUS_01 = 30061001;// 已保存
	public final static Integer PART_PURCHASE_ORDER_STATUS_02 = 30061002;// 订单中
	public final static Integer PART_PURCHASE_ORDER_STATUS_03 = 30061003;// 已确认
	public final static Integer PART_PURCHASE_ORDER_STATUS_04 = 30061004;// 浮动
	public final static Integer PART_PURCHASE_ORDER_STATUS_05 = 30061005;// 港口到达
	public final static Integer PART_PURCHASE_ORDER_STATUS_06 = 30061006;// 仓库到达
	public final static Integer PART_PURCHASE_ORDER_STATUS_07 = 30061007;// 入库完成
	public final static Integer PART_PURCHASE_ORDER_STATUS_08 = 30061008;// 订单取消

	public final static Integer SCAN_INTER_STATUS = 3007;// 扫描枪交互状态
	public final static Integer SCAN_INTER_STATUS_01 = 30071001;// 未导出
	public final static Integer SCAN_INTER_STATUS_02 = 30071002;// 已导出
	public final static Integer SCAN_INTER_STATUS_03 = 30071003;// 已导出

	public final static Integer PART_PURCHASE_INVOICE_STATUS = 3008;// 采购发票状态
	public final static Integer PART_PURCHASE_INVOICE_STATUS_01 = 30081001;// 已保存
	public final static Integer PART_PURCHASE_INVOICE_STATUS_02 = 30081002;// 浮动
	public final static Integer PART_PURCHASE_INVOICE_STATUS_03 = 30081003;// 港口到达
	public final static Integer PART_PURCHASE_INVOICE_STATUS_04 = 30081004;// 仓库到达
	// public final static Integer PART_PURCHASE_INVOICE_STATUS_05 =
	// 30081005;//开箱清点
	public final static Integer PART_PURCHASE_INVOICE_STATUS_06 = 30081006;// 入库中
	public final static Integer PART_PURCHASE_INVOICE_STATUS_07 = 30081007;// 入库完成

	public final static Integer PART_SALE_ORDER_STATUS = 3009;// 销售订单状态
	public final static Integer PART_SALE_ORDER_STATUS_01 = 30091001;// 已保存
	public final static Integer PART_SALE_ORDER_STATUS_02 = 30091002;// 已提报
	public final static Integer PART_SALE_ORDER_STATUS_03 = 30091003;// 已确认
	public final static Integer PART_SALE_ORDER_STATUS_04 = 30091004;// 已挑选
	public final static Integer PART_SALE_ORDER_STATUS_05 = 30091005;// 已发货
	public final static Integer PART_SALE_ORDER_STATUS_06 = 30091006;// 已签收
	public final static Integer PART_SALE_ORDER_STATUS_07 = 30091007;// 已取消

	public final static Integer PART_STOCK_OUT_TYPE = 3010;// 出库类型
	public final static Integer PART_STOCK_OUT_TYPE_01 = 30101001;// 销售出库
	public final static Integer PART_STOCK_OUT_TYPE_02 = 30101002;// 例外出库

	public final static Integer PART_STOCK_IN_TYPE = 3011;// 入库类型
	public final static Integer PART_STOCK_IN_TYPE_01 = 30111001;// 销售入库
	public final static Integer PART_STOCK_IN_TYPE_02 = 30111002;// 例外入库

	public final static Integer SRS_STATUS = 3012;// 追溯件状态
	public final static Integer SRS_STATUS_01 = 30121001;// 库存中
	public final static Integer SRS_STATUS_02 = 30121002;// 已出库
	public final static Integer SRS_STATUS_03 = 30121003;// 已使用

	public final static Integer EXCHANGER_RATE_STATUS = 3013;// 汇率状态
	public final static Integer EXCHANGER_RATE_STATUS_01 = 30131001;// 未生效
	public final static Integer EXCHANGER_RATE_STATUS_02 = 30131002;// 已生效
	public final static Integer EXCHANGER_RATE_STATUS_03 = 30131003;// 已失效

	public final static Integer PICK_STATUS = 3014;// 备货单状态
	public final static Integer PICK_STATUS_01 = 30141001;// 备货生成
	public final static Integer PICK_STATUS_02 = 30141002;// 备货完成

	public final static Integer SORT_STATUS = 3015;// 分拣单状态
	public final static Integer SORT_STATUS_01 = 30151001;// 分拣生成
	public final static Integer SORT_STATUS_02 = 30151002;// 分拣完成

	public final static Integer PACK_STATUS = 3016;// 包装单状态
	public final static Integer PACK_STATUS_01 = 30161001;// 包装生成
	public final static Integer PACK_STATUS_02 = 30161002;// 包装完成

	public final static Integer DI_TRANS_TYPE = 3017;// 发货单运输方式
	public final static Integer DI_TRANS_TYPE_01 = 30171001;// 汽运
	public final static Integer DI_TRANS_TYPE_02 = 30171002;// 空运
	public final static Integer DI_TRANS_TYPE_03 = 30171003;// 快递
	public final static Integer DI_TRANS_TYPE_04 = 30171004;// 其他

	public final static Integer DI_PAYOFF_STATUS = 3018;// 发货单结算状态
	public final static Integer DI_PAYOFF_STATUS_01 = 30181001;// 未结算
	public final static Integer DI_PAYOFF_STATUS_02 = 30181002;// 已结算
	public final static Integer DI_PAYOFF_STATUS_03 = 30181003;// 不结算
	public final static Integer DI_PAYOFF_STATUS_04 = 30181004;// 已删除

	public final static Integer DI_PART_STATUS = 3019;// 发货单配件状态
	public final static Integer DI_PART_STATUS_01 = 30191001;// 未删除
	public final static Integer DI_PART_STATUS_02 = 30191002;// 已删除

	public final static Integer DI_STATUS = 3020;// 发货单状态
	public final static Integer DI_STATUS_01 = 30201001;// 发货生成
	public final static Integer DI_STATUS_02 = 30201002;// 发货完成
	public final static Integer DI_STATUS_03 = 30201003;// 部分签收
	public final static Integer DI_STATUS_04 = 30201004;// 签收完成

	public final static Integer WAREHOUSE_OPER_TYPE = 3021;// 出入库类型
	public final static Integer WAREHOUSE_OPER_TYPE_01 = 30211001;// 出库
	public final static Integer WAREHOUSE_OPER_TYPE_02 = 30211002;// 入库

	public final static Integer WAREHOUSE_CHECK_TYPE = 3022;// 盘点类型
	public final static Integer WAREHOUSE_CHECK_TYPE_01 = 30221001;// 全部
	public final static Integer WAREHOUSE_CHECK_TYPE_02 = 30221002;// 部分

	public final static Integer WAREHOUSE_CHECK_STATUS = 3023;// 盘点状态
	public final static Integer WAREHOUSE_CHECK_STATUS_01 = 30231001;// 待盘点
	public final static Integer WAREHOUSE_CHECK_STATUS_02 = 30231002;// 盘点中
	public final static Integer WAREHOUSE_CHECK_STATUS_03 = 30231003;// 盘点结束

	public final static Integer DEALER_PART_LEVEL = 3024;// 特约店级别
	public final static Integer DEALER_PART_LEVEL_01 = 30241001;// A
	public final static Integer DEALER_PART_LEVEL_02 = 30241002;// B
	public final static Integer DEALER_PART_LEVEL_03 = 30241003;// C
	public final static Integer DEALER_PART_LEVEL_04 = 30241002;// D
	public final static Integer DEALER_PART_LEVEL_05 = 30241003;// E

	public final static Integer SUPPLIER_TYPE = 3025;// 供应商类型
	public final static Integer SUPPLIER_TYPE_01 = 30251001;// 克莱斯勒
	public final static Integer SUPPLIER_TYPE_02 = 30251002;// 运输
	public final static Integer SUPPLIER_TYPE_03 = 30251003;// 其他

	public final static Integer SUPPLIER_NATION_TYPE = 3026;// 供应商国别类型
	public final static Integer SUPPLIER_NATION_TYPE_01 = 30261001;// 国外
	public final static Integer SUPPLIER_NATION_TYPE_02 = 30261002;// 国内

	public final static Integer PART_PRICE_TYPE = 3027;// 价格类型
	public final static Integer PART_PRICE_TYPE_01 = 30271001;// 分配价格
	public final static Integer PART_PRICE_TYPE_02 = 30271002;// 订单价格

	public final static Integer PART_TRANS_TYPE = 3028;// 发运方式
	public final static Integer PART_TRANS_TYPE_01 = 30281001;// 汽运
	public final static Integer PART_TRANS_TYPE_02 = 30281002;// 空运
	public final static Integer PART_TRANS_TYPE_03 = 30281003;// 海运

	public final static Integer INVOICE_TYPE = 3029;// 发票类型
	public final static Integer INVOICE_TYPE_01 = 30291001;// GLO发票
	public final static Integer INVOICE_TYPE_02 = 30291002;// 手工发票

	public final static Integer SAVE_DEAL_STATUS = 3030;// 保存、处理控制
	public final static Integer SAVE_DEAL_STATUS_01 = 30301001;// 已保存
	public final static Integer SAVE_DEAL_STATUS_02 = 30301002;// 已处理

	public final static Integer DIFF_STATUS = 3031;// 差异状态
	public final static Integer DIFF_STATUS_01 = 30311001;// 无差异
	public final static Integer DIFF_STATUS_02 = 30311002;// 有差异

	public final static Integer PART_SIGN_STATUS = 3032;// 签收状态
	public final static Integer PART_SIGN_STATUS_01 = 30321001;// 未签收
	public final static Integer PART_SIGN_STATUS_02 = 30321002;// 已签收

	public final static Integer PART_ABC_COUNT = 3033;// ABC数量
	public final static Integer PART_ABC_COUNT_01 = 30331001;// A
	public final static Integer PART_ABC_COUNT_02 = 30331002;// B
	public final static Integer PART_ABC_COUNT_03 = 30331003;// C
	public final static Integer PART_ABC_COUNT_04 = 30331004;// D
	public final static Integer PART_ABC_COUNT_05 = 30331005;// E
	public final static Integer PART_ABC_COUNT_06 = 30331006;// F

	public final static Integer PART_ABC_AMOUNT = 3034;// ABC金额
	public final static Integer PART_ABC_AMOUNT_01 = 30341001;// A
	public final static Integer PART_ABC_AMOUNT_02 = 30341002;// B
	public final static Integer PART_ABC_AMOUNT_03 = 30341003;// C
	public final static Integer PART_ABC_AMOUNT_04 = 30341004;// D
	public final static Integer PART_ABC_AMOUNT_05 = 30341005;// E
	public final static Integer PART_ABC_AMOUNT_06 = 30341006;// F

	public final static Integer PART_ORDER_DIFF_TYPE = 3035;// 配件销售订单类型区分接口GHAS
	public final static Integer PART_ORDER_DIFF_TYPE_01 = 30351001;// 讴歌页面订单
	public final static Integer PART_ORDER_DIFF_TYPE_02 = 30351002;// 讴歌接口订单
	public final static Integer PART_ORDER_DIFF_TYPE_03 = 30351003;// GHAS订单

	public final static Integer PART_CHECK_PART_STATUS = 3036;// 盘点配件状态
	public final static Integer PART_CHECK_PART_STATUS_01 = 30361001;// 未盘点
	public final static Integer PART_CHECK_PART_STATUS_02 = 30361002;// 无差异
	public final static Integer PART_CHECK_PART_STATUS_03 = 30361003;// 有差异

	public final static Integer COMP_SOURCE_TYPE = 4035;// 投诉大类
	public final static Integer COMP_SOURCE_TYPE_01 = 40351001;// 售前投诉
	public final static Integer COMP_SOURCE_TYPE_02 = 40351002;// 售后投诉
	public final static Integer COMP_SOURCE_TYPE_03 = 40351003;// 其他

	public final static Integer COMP_TYPE_TYPE_SQ = 4036;// 投诉小类-售前
	public final static Integer COMP_TYPE_TYPE_SQ_01 = 40361001;// 售前服务态度
	public final static Integer COMP_TYPE_TYPE_SQ_02 = 40361002;// 销售顾问与业水平
	public final static Integer COMP_TYPE_TYPE_SQ_03 = 40361003;// 交车日期
	public final static Integer COMP_TYPE_TYPE_SQ_04 = 40361004;// 其他销售投诉
	public final static Integer COMP_TYPE_TYPE_SQ_05 = 40361005;// 销售价格
	public final static Integer COMP_TYPE_TYPE_SQ_06 = 40361006;// 诚信
	public final static Integer COMP_TYPE_TYPE_SQ_07 = 40361007;// 售前市场活劢
	public final static Integer COMP_TYPE_TYPE_SQ_08 = 40361008;// 车辆相关手续
	public final static Integer COMP_TYPE_TYPE_SQ_09 = 40361009;// 问题反馈处理时间
	public final static Integer COMP_TYPE_TYPE_SQ_10 = 40361010;// 产品-附件
	public final static Integer COMP_TYPE_TYPE_SQ_11 = 40361011;// 产品-其他
	public final static Integer COMP_TYPE_TYPE_SQ_12 = 40361012;// 媒体投诉
	public final static Integer COMP_TYPE_TYPE_SQ_13 = 40361013;// 重大事件投诉
	public final static Integer COMP_TYPE_TYPE_SQ_14 = 40361014;// 产品-质量
	public final static Integer COMP_TYPE_TYPE_SQ_15 = 40361015;// 产品-功能

	public final static Integer COMP_TYPE_TYPE_SH = 4050;// 投诉小类-售后
	public final static Integer COMP_TYPE_TYPE_SH_01 = 40501001;// 售后服务态度
	public final static Integer COMP_TYPE_TYPE_SH_03 = 40501003;// 维修/保养与业水平
	public final static Integer COMP_TYPE_TYPE_SH_02 = 40501002;// 维修/保养等待时间
	public final static Integer COMP_TYPE_TYPE_SH_04 = 40501004;// 维修等待区/车间环境及设施
	public final static Integer COMP_TYPE_TYPE_SH_05 = 40501005;// 维修/保养/配件价格
	public final static Integer COMP_TYPE_TYPE_SH_06 = 40501006;// 更换零部件质量
	public final static Integer COMP_TYPE_TYPE_SH_07 = 40501007;// 零部件到货日期
	public final static Integer COMP_TYPE_TYPE_SH_08 = 40501008;// 问题反馈处理时间
	public final static Integer COMP_TYPE_TYPE_SH_09 = 40501009;// 其他售后投诉
	public final static Integer COMP_TYPE_TYPE_SH_10 = 40501010;// 产品-质量
	public final static Integer COMP_TYPE_TYPE_SH_11 = 40501011;// 产品-功能
	public final static Integer COMP_TYPE_TYPE_SH_12 = 40501012;// 产品-附件
	public final static Integer COMP_TYPE_TYPE_SH_13 = 40501013;// 产品-其他
	public final static Integer COMP_TYPE_TYPE_SH_14 = 40501014;// 媒体投诉
	public final static Integer COMP_TYPE_TYPE_SH_15 = 40501015;// 重大事件投诉
	public final static Integer COMP_TYPE_TYPE_SH_16 = 40501016;// 诚信

	public final static Integer COMP_TYPE_TYPE_QT = 4051;// 投诉小类-其他
	public final static Integer COMP_TYPE_TYPE_QT_01 = 40511001;// 其他
	public final static Integer COMP_TYPE_TYPE_QT_02 = 40511002;// 客服代表服务态度
	public final static Integer COMP_TYPE_TYPE_QT_03 = 40511003;// 无法打通客服中心电话
	public final static Integer COMP_TYPE_TYPE_QT_04 = 40511004;// 客服人员与业水平

	public final static Integer COMP_LEVEL_TYPE = 4037;// 投诉等级
	public final static Integer COMP_LEVEL_TYPE_01 = 40371001;// 一般投诉
	public final static Integer COMP_LEVEL_TYPE_02 = 40371002;// 重要投诉
	public final static Integer COMP_LEVEL_TYPE_03 = 40371003;// 重大投诉

	public final static Integer COMP_STATUS_TYPE = 4038;// 投诉状态
	public final static Integer COMP_STATUS_TYPE_01 = 40381001;// 未分配
	public final static Integer COMP_STATUS_TYPE_02 = 40381002;// 已分配
	public final static Integer COMP_STATUS_TYPE_03 = 40381003;// 处理中
	public final static Integer COMP_STATUS_TYPE_04 = 40381004;// 申请结案
	public final static Integer COMP_STATUS_TYPE_05 = 40381005;// 已结案
	public final static Integer COMP_STATUS_TYPE_06 = 40381006;// 申请撤销

	public final static Integer COMP_TICKLING_STATUS_TYPE = 4039;// 反馈状态
	public final static Integer COMP_TICKLING_STATUS_TYPE_01 = 40391001;// 未反馈
	public final static Integer COMP_TICKLING_STATUS_TYPE_02 = 40391002;// 已反馈

	public final static Integer COMP_NATURE = 4040;// 投诉性质
	public final static Integer COMP_NATURE_01 = 40401001;// 首次投诉
	public final static Integer COMP_NATURE_02 = 40401002;// 重复投诉

	public final static Integer COMP_CUSTOMER_AGE = 4041;// 投诉客户年龄
	public final static Integer COMP_CUSTOMER_AGE_01 = 40411001;// 18-25
	public final static Integer COMP_CUSTOMER_AGE_02 = 40411002;// 25-30
	public final static Integer COMP_CUSTOMER_AGE_03 = 40411003;// 30-35
	public final static Integer COMP_CUSTOMER_AGE_04 = 40411004;// 35-40
	public final static Integer COMP_CUSTOMER_AGE_05 = 40411005;// 40-50
	public final static Integer COMP_CUSTOMER_AGE_06 = 40411006;// 50以上

	public final static Integer COMP_HUMAN_CAR_RELATIONSHIP = 4042;// 人车关系
	public final static Integer COMP_HUMAN_CAR_RELATIONSHIP_01 = 40421001;// 车主
	public final static Integer COMP_HUMAN_CAR_RELATIONSHIP_02 = 40421002;// 司机

	/**
	 * 配件TC_CODE END
	 */

	long MAX_SIZE = 1024 * 1024 * 5;// 文件上传最大5M

	public static String MONTH_BEGIN_DATE = "2012-08";

	public static String UP = "开箱";
	public static String SE = "入库";
	public static String PD = "盘点";
	public static String PI = "备货";
	public static String PS = "分拣";
	public static String PA = "装箱";

	public static String GLO_SEND_ID = "GLOSPP";
	public static String GLO_GET_ID = "HMCISPP";

	// 经销商例外入库 是否管理追溯件 0 --不管 1 --管理
	public static String DEALER_STOCK_IN_SN_FLAG = "0";

	// 经销商例外出库 是否管理追溯件
	public static String DEALER_STOCK_OUT_SN_FLAG = "0";

	// 索赔工时维护 故障代码
	public static String LABOUR_BASE_TYPE = "40071002";

	// 物料组级别 add by luoyonggang
	public final static Integer TM_VHCL_MATERIAL_GROUP_BRAND = 1;// 品牌
	public final static Integer TM_VHCL_MATERIAL_GROUP_CAR = 2;// 车系
	public final static Integer TM_VHCL_MATERIAL_GROUP_MODEL = 3;// 车型
	public final static Integer TM_VHCL_MATERIAL_GROUP_TYPE = 4;// 款式

	// 预测状态
	public final static Integer TT_VS_MONTHLY_FORECAST_SAVE = 20791001;// 已保存
	public final static Integer TT_VS_MONTHLY_FORECAST_SUBMIT = 20791002;// 已提交
	public final static Integer TT_VS_MONTHLY_FORECAST_AUDIT = 20791003;// 已审核
	public final static Integer TT_VS_MONTHLY_FORECAST_REPORT = 20791004;// 经销商已反馈上报

	// 索赔延续标识
	public final static Integer CLAIM_CONTINUE_FLAG = 4034;// 延续标识
	public final static Integer CLAIM_CONTINUE_FLAG_N = 40341001; // N=否
	public final static Integer CLAIM_CONTINUE_FLAG_R = 40341002; // R=Repair
	public final static Integer CLAIM_CONTINUE_FLAG_P = 40341003; // P=Part
	public final static Integer CLAIM_CONTINUE_FLAG_O = 40341004; // O=Other

	// 项目类型
	public final static Integer PROJECT_TYPE = 4029;// 项目类型
	public final static Integer PROJECT_TYPE_PRO = 40291001;// 维修项目
	public final static Integer PROJECT_TYPE_PART = 40291002;// 维修配件
	public final static Integer PROJECT_TYPE_OTHER = 40291003;// 其他费用

	// 预授权状态
	public final static Integer FORE_APPLOVAL_STATUS = 1169;// 预授权状态
	public final static Integer FORE_APPLOVAL_STATUS_01 = 11691001;// 未上报
	public final static Integer FORE_APPLOVAL_STATUS_02 = 11691002;// 审核中
	public final static Integer FORE_APPLOVAL_STATUS_03 = 11691003;// 审核退回
	public final static Integer FORE_APPLOVAL_STATUS_04 = 11691004;// 审核通过
	public final static Integer FORE_APPLOVAL_STATUS_05 = 11691005;// 审核拒绝

	// 两年的天数和5万公里的里程数
	public final static Integer TWO_YEAR_DAYS = 730;// 两年的天数
	public final static Integer TEN_WAN_MILLAGE = 50000;// 5万公里

	// 三包规则，维修方案审核结果
	public final static Integer REPAIR_AUDIT_STATUS = 4043;
	public final static Integer REPAIR_AUDIT_STATUS_01 = 40431001;// 审核通过
	public final static Integer REPAIR_AUDIT_STATUS_02 = 40431002;// 审核调整
	public final static Integer REPAIR_AUDIT_STATUS_03 = 40431003;// 审核拒绝
	public final static Integer REPAIR_AUDIT_STATUS_04 = 40431004;// 待审核

	// DCC意向级别
	public final static Integer DCC_LEVEL = 1310;// 意向级别
	public final static Integer DCC_LEVEL_01 = 13101001;// 'H级'
	public final static Integer DCC_LEVEL_02 = 13101002;// 'A级'
	public final static Integer DCC_LEVEL_03 = 13101003;// 'B级'
	public final static Integer DCC_LEVEL_04 = 13101004;// 'C级'
	public final static Integer DCC_LEVEL_05 = 13101005;// 'N级'

	// 关键人员职位类型
	public final static Integer DEALER_POST_TYPE = 2101;
	public final static Integer DEALER_POST_TYPE_01 = 21011001;// 总经理
	public final static Integer DEALER_POST_TYPE_02 = 21011002;// 销售经理
	public final static Integer DEALER_POST_TYPE_03 = 21011003;// 售后经理
	public final static Integer DEALER_POST_TYPE_04 = 21011004;// 市场经理
	public final static Integer DEALER_POST_TYPE_05 = 21011005;// DCC经理
	public final static Integer DEALER_POST_TYPE_06 = 21011006;// 客户关系经理
	public final static Integer DEALER_POST_TYPE_07 = 21011007;// 技术主管
	public final static Integer DEALER_POST_TYPE_08 = 21011008;// 客户经理

	// 快速零售上报状态
	public final static Integer FAST_RETAIL_STATUS = 1147;
	public final static Integer FAST_RETAIL_STATUS_01 = 11471001;// 启用
	public final static Integer FAST_RETAIL_STATUS_02 = 11471002;// 停用

	public final static String WARN_ITEMNO_500 = "500";// 累积占用维修时间
	public final static String WARN_ITEMNO_400 = "400";// 同一产品质量问题监控
	public final static String WARN_ITEMNO_300 = "300";// 其它总成主要零件监控
	public final static String WARN_ITEMNO_200 = "200";// 发动机和变速器主要零件监控
	public final static String WARN_ITEMNO_100 = "100";// 发动机和变速器总成监控

	// 零件主数据下发中的价格参数
	public final static Double PARTBASEPRICE_TAXRATE = 0.17;// 配件税率
	public final static Double PARTBASEPRICE_FEERATE = 0.08;// 配件管理费率

	public final static String SPECIAL_DEALER_ZHONGJIN = "33250A";// 特殊经销商代码-中进

	// 经销店等级
	public final static Integer COMPANY_LEVEL = 1155;
	public final static Integer COMPANY_LEVEL_2S = 11551001;// 2S
	public final static Integer COMPANY_LEVEL_4S = 11551002;// 4S

	public final static Integer TASK_TYPE_01 = 0;// 零售预测任务
	public final static Integer TASK_TYPE_02 = 1;// N+3预测任务

	public final static Integer DEALER_STATE = 4055; // 经销商状态
	public final static Integer DEALER_STATE_01 = 40551001;// 未上报
	public final static Integer DEALER_STATE_02 = 40551002;// 已上报

	public final static Integer DEALER_AUDIT_STATE = 4056; // 经销商审核状态
	public final static Integer DEALER_AUDIT_STATE_01 = 40561001;// 未上报
	public final static Integer DEALER_AUDIT_STATE_02 = 40561002;// 审核中
	public final static Integer DEALER_AUDIT_STATE_03 = 40561003;// 审核通过
	public final static Integer DEALER_AUDIT_STATE_04 = 40561004;// 审核驳回

	public final static Integer RESOURCE_ALLOT_TYPE = 1120;// 特殊分配大类
	public final static Integer RESOURCE_ALLOT_TYPE_01 = 11201001;// 车展支持
	public final static Integer RESOURCE_ALLOT_TYPE_02 = 11201002;// 大客户分配
	public final static Integer RESOURCE_ALLOT_TYPE_03 = 11201003;// 其他分配

	public final static Integer RESOURCE_ALLOT_DETAIL = 1121;// 特殊分配明细
	public final static Integer RESOURCE_ALLOT_DETAIL_01 = 11211001;// 车展支持
	public final static Integer RESOURCE_ALLOT_DETAIL_02 = 11211002;// 大客户
	public final static Integer RESOURCE_ALLOT_DETAIL_03 = 11211003;// 公司用车
	public final static Integer RESOURCE_ALLOT_DETAIL_04 = 11211004;// 销售部试驾车
	public final static Integer RESOURCE_ALLOT_DETAIL_05 = 11211005;// 媒体置换车
	public final static Integer RESOURCE_ALLOT_DETAIL_06 = 11211006;// 市场部试驾车
	public final static Integer RESOURCE_ALLOT_DETAIL_07 = 11211007;// 员工购车
	public final static Integer RESOURCE_ALLOT_DETAIL_08 = 11211008;// VIN
																	// Problem
	public final static Integer RESOURCE_ALLOT_DETAIL_09 = 11211009;// 其他质损车
	public final static Integer RESOURCE_ALLOT_DETAIL_10 = 11211010;// 其他用途

	// 维修类型
	public final static Integer MAINTAIN_TYPE = 3337;// 维修项目类型
	public final static Integer MAINTAIN_TYPE_01 = 33371001;// 索赔
	public final static Integer MAINTAIN_TYPE_02 = 33371002;// 普通维修
	public final static Integer MAINTAIN_TYPE_03 = 33371003;// 其他

	// 预测任务类型
	public final static Integer PREDICTION_TASK_TYPE = 4057;
	public final static Integer PREDICTION_TASK_TYPE_01 = 40571001; // N=1
	public final static Integer PREDICTION_TASK_TYPE_02 = 40571002; // N+2
	// public final static Integer PREDICTION_TASK_TYPE_03 = 40571003;
	public final static Integer PREDICTION_TASK_TYPE_04 = 40571004; // 当月

	// 订单导入状态
	public final static Integer TMP_IMPORT_STATUS_01 = 1;// 正在导入中
	public final static Integer TMP_IMPORT_STATUS_02 = 2;// 导入完成

	// 付款银行
	public final static Integer PAY_BANK = 3388;// 付款银行
	public final static Integer PAY_BANK_01 = 33881001;// 工商银行
	public final static Integer PAY_BANK_02 = 33881002;// 建设银行
	public final static Integer PAY_BANK_03 = 33881003;// 中国银行
	public final static Integer PAY_BANK_04 = 33881004;// 中信银行
	public final static Integer PAY_BANK_05 = 33881005;// 平安银行
	public final static Integer PAY_BANK_06 = 33881006;// 招商银行
	public final static Integer PAY_BANK_07 = 33881007;// 菲亚特金融
	public final static Integer PAY_BANK_08 = 33881008;// 兴业银行
	public final static Integer PAY_BANK_09 = 33881009;// 农业银行
	public final static Integer PAY_BANK_10 = 33881010;// 农商银行
	public final static Integer PAY_BANK_11 = 33881011;// 光大银行
	public final static Integer PAY_BANK_12 = 33881012;// 民生银行
	public final static Integer PAY_BANK_13 = 33881013;// 华夏银行
	public final static Integer PAY_BANK_14 = 33881014;// 浦发银行
	public final static Integer PAY_BANK_15 = 33881015;// 交通银行
	public final static Integer PAY_BANK_16 = 33881016;// 奇瑞徽银汽车金融股份有限公司
	public final static Integer PAY_BANK_17 = 33881017;// 力蕴汽车咨询服务（上海）有限公司
	public final static Integer PAY_BANK_18 = 33881018;// 农村信用社
	public final static Integer PAY_BANK_19 = 33881019;// 邮政储蓄所
	public final static Integer PAY_BANK_20 = 33881020;// 广汽汇理

	// 分期期数
	public final static Integer INSTALLMENT_NUMBER_TYPE = 3387;// 分期期数
	public final static Integer INSTALLMENT_NUMBER_TYPE_01 = 33871001;// 12(按月)
	public final static Integer INSTALLMENT_NUMBER_TYPE_02 = 33871002;// 18(按月)
	public final static Integer INSTALLMENT_NUMBER_TYPE_03 = 33871003;// 24(按月)
	public final static Integer INSTALLMENT_NUMBER_TYPE_04 = 33871004;// 36(按月)
	public final static Integer INSTALLMENT_NUMBER_TYPE_05 = 33871005;// 48(按月)
	public final static Integer INSTALLMENT_NUMBER_TYPE_06 = 33871006;// 48(按月)

	// 下载日志类型
	public final static Integer DOWNLOAD_TYPE_01 = 1;// 交车信息查询
	public final static Integer DOWNLOAD_TYPE_02 = 2;// 车辆维修历史

	/**
	 * 大客户组织架构权限审批状态
	 */
	public static Integer BIG_CUSTOMER_AUTHORITY_APPROVAL_TYPE = 1600;
	/** 申请待审批 */
	public static Integer BIG_CUSTOMER_AUTHORITY_APPROVAL_TYPE_UNAPPROVED = 16000001;
	/** 审核通过 */
	public static Integer BIG_CUSTOMER_AUTHORITY_APPROVAL_TYPE_TYPE_PASS = 16000002;
	/** 审核拒绝 */
	public static Integer BIG_CUSTOMER_AUTHORITY_APPROVAL_TYPE_TYPE_RUS = 16000003;

	/**
	 * add by huyu 大客户报备区域审核状态
	 */
	public static Integer BIG_CUSTOMER_FILING_AREA_APPROVAL_TYPE = 1599;
	/** 区域未审批 */
	public static Integer BIG_CUSTOMER_FILING_AREA_APPROVAL_TYPE_UNAPPROVED = 15990001;
	/** 区域审核通过 */
	public static Integer BIG_CUSTOMER_FILING_AREA_APPROVAL_TYPE_PASS = 15990002;
	/** 区域审核拒绝 */
	public static Integer BIG_CUSTOMER_FILING_AREA_APPROVAL_TYPE_RUS = 15990003;

	/**
	 * 大客户报备审批状态
	 */
	public static Integer BIG_CUSTOMER_FILING_APPROVAL_TYPE = 1598;
	/** 总部未审批 */
	public static Integer BIG_CUSTOMER_FILING_APPROVAL_TYPE_UNAPPROVED = 15980001;
	/** 总部审核通过 */
	public static Integer BIG_CUSTOMER_FILING_APPROVAL_TYPE_PASS = 15980002;
	/** 总部审核驳回 */
	public static Integer BIG_CUSTOMER_FILING_APPROVAL_TYPE_OVER = 15980003;
	/** 总部审核拒绝 */
	public static Integer BIG_CUSTOMER_FILING_APPROVAL_TYPE_RUS = 15980004;
	/** 总部转拒绝 */
	public static Integer BIG_CUSTOMER_FILING_APPROVAL_TYPE_REPORT = 15980005;

	/**
	 * 大客户返利审批状态
	 */
	public static Integer BIG_CUSTOMER_REBATE_APPROVAL_TYPE = 1595;
	/** 未审批 */
	public static Integer BIG_CUSTOMER_REBATE_APPROVAL_TYPE_UNAPPROVED = 15950001;
	/** 通过 */
	public static Integer BIG_CUSTOMER_REBATE_APPROVAL_TYPE_PASS = 15950002;
	/** 驳回 */
	public static Integer BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER = 15950003;
	/** 拒绝 */
	public static Integer BIG_CUSTOMER_REBATE_APPROVAL_TYPE_RUS = 15950004;
	/** 资料完整待审批 */
	public static Integer BIG_CUSTOMER_REBATE_APPROVAL_TYPE_COMPLETE_INFORMATION = 15950005;
	/** 系统转拒绝 */
	public static Integer BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS = 15950006;
	/** 驳回转拒绝 */
	public static Integer BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS = 15950007;

	/** 系统转拒绝备注 */
	public static String BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS_REMARK = "逾期未回复，系统转拒绝";
	/** 驳回转拒绝备注 */
	public static String BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS_REMARK = "驳回超过两次，驳回转拒绝";
	/** 激活备注 */
	public static String BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_OVER_REMARK = "大客户申请已被激活！";

	/**
	 * 二手车置换返利审批状态
	 */
	public static Integer UC_REBATE_APPROVAL_TYPE = 1596;
	/** 未审批 */
	public static Integer UC_REBATE_APPROVAL_TYPE_UNAPPROVED = 15960001;
	/** 通过 */
	public static Integer UC_REBATE_APPROVAL_TYPE_PASS = 15960002;
	/** 驳回 */
	public static Integer UC_REBATE_APPROVAL_TYPE_OVER = 15960003;

	/**
	 * 大客户报备批售类型
	 */
	public static Integer BIG_CUSTOMER_BATCH_SALE_TYPE = 1597;
	/** 集团销售 */
	public static Integer BIG_CUSTOMER_BATCH_SALE_TYPE_GROUP = 15971001;
	/** 影响力人物 */
	public static Integer BIG_CUSTOMER_BATCH_SALE_TYPE_INFLUENTIAL_PEOPLE = 15971002;
	/** 团购 */
	public static Integer BIG_CUSTOMER_BATCH_SALE_TYPE_GROUP_BUY = 15971003;

	/**
	 * 置换方式
	 */
	public static Integer UC_REPLACE_WAY = 1594;
	/** 本人 */
	public static Integer UC_REPLACE_WAY_ONESELF = 15941001;
	/** 非本人 */
	public static Integer UC_REPLACE_WAY_OTHER_PEOPLE = 15941002;

	// 分配备注类型
	public static Integer REMARK_COMMON = 12321001;// 普通备注
	public static Integer REMARK_SPECIAL = 12321002;// 特殊备注

	// OTD特殊备注
	public static Integer OTD_REMARK = 1251;
	public static Integer OTD_REMARK_01 = 12511001;// OTD备注1
	public static Integer OTD_REMARK_02 = 12511002;// OTD备注2
	public static Integer OTD_REMARK_03 = 12511003;// OTD备注3
	public static Integer OTD_REMARK_04 = 12511004;// OTD备注4
	public static Integer OTD_REMARK_05 = 12511005;// OTD备注5
	public static Integer OTD_REMARK_06 = 12511006;// OTD备注6
	public static Integer OTD_REMARK_07 = 12511007;// OTD备注7
	public static Integer OTD_REMARK_08 = 12511008;// OTD备注8

	// 资源分配-分配状态
	public static Integer ORDER_ALLOT_STATIS_01 = 0;// 未分配
	public static Integer ORDER_ALLOT_STATIS_02 = 1;// 已分配

	// 资源分配-审核类型
	public static Integer ORDER_ADJUST_TYPE_01 = 12301;// OTD审核
	public static Integer ORDER_ADJUST_TYPE_02 = 12302;// 大区审核
	public static Integer ORDER_ADJUST_TYPE_03 = 12303;// 小区审核
	public static Integer ORDER_ADJUST_TYPE_04 = 12304;// 销售总监审核

	// 资源分配-审核状态
	public static Integer ORDER_ADJUST_STATIS_01 = 1;// 待审核
	public static Integer ORDER_ADJUST_STATIS_02 = 2;// 通过
	public static Integer ORDER_ADJUST_STATIS_03 = 3;// 驳回
	public static Integer ORDER_ADJUST_STATIS_04 = 4;// 生成订单
	// // 港口优先级
	public static String PORT_LEVEL = "1393";
	public static String PORT_LEVELTJ1 = "13931001";// 港口优先级（天津1
	public static String PORT_LEVELTJ2 = "13931002";// 港口优先级（天津2

	public static String PORT_LEVELSH1 = "13931003";// 港口优先级（上海）1
	public static String PORT_LEVELSH2 = "13931004";// 港口优先级（上海）2
	// 天津VPC港口所属
	public static String VPC_PORT = "1392";
	public static String VPC_PORT_01 = "13921001";// 天津13922
	public static String VPC_PORT_02 = "13921002";// 上海20992
	public static String VPC_PORT_03 = "13921003";// 天津 78015
	public static String VPC_PORT_04 = "13921004";// 上海 78014

	// 付款方式变更申请查询 审核状态
	public static Integer CANCEL_ORDER_APPLY_STATUS = 1252;
	public static Integer CANCEL_ORDER_APPLY_STATUS_01 = 12521001;// 小区待审核
	// public static Integer CANCEL_ORDER_APPLY_STATUS_02 = 12521002;//小区审核通过
	public static Integer CANCEL_ORDER_APPLY_STATUS_02 = 12521002;// 小区审核驳回
	public static Integer CANCEL_ORDER_APPLY_STATUS_03 = 12521003;// OTD待审核
	public static Integer CANCEL_ORDER_APPLY_STATUS_04 = 12521004;// OTD审核通过
	public static Integer CANCEL_ORDER_APPLY_STATUS_05 = 12521005;// OTD审核驳回

	// 是否可以可以申请
	public static Integer AUDIT_TYPE_01 = 0;// 不可以申请
	public static Integer AUDIT_TYPE_02 = 1;// 可以申请

	// 索赔申请单小区审核状态
	public static Integer SMALL_AREA_APPROVAL_STATUS = 9001;
	public static Integer SMALL_AREA_APPROVAL_STATUS_01 = 90011001;// 待审核
	public static Integer SMALL_AREA_APPROVAL_STATUS_02 = 90011002;// 审核通过
	public static Integer SMALL_AREA_APPROVAL_STATUS_03 = 90011003;// 审核驳回

	// 索赔申请单大区审核状态
	public static Integer BIG_AREA_APPROVAL_STATUS = 9002;
	public static Integer BIG_AREA_APPROVAL_STATUS_01 = 90021001;// 待审核
	public static Integer BIG_AREA_APPROVAL_STATUS_02 = 90021002;// 审核通过
	public static Integer BIG_AREA_APPROVAL_STATUS_03 = 90021003;// 审核驳回

	// 索赔申请单索赔组审核状态
	public static Integer CLAIM_GROUP_APPROVAL_STATUS = 9003;
	public static Integer CLAIM_GROUP_APPROVAL_STATUS_01 = 90031001;// 待审核
	public static Integer CLAIM_GROUP_APPROVAL_STATUS_02 = 90031002;// 审核通过
	public static Integer CLAIM_GROUP_APPROVAL_STATUS_03 = 90031003;// 审核驳回

	// 索赔申请单选择分类
	public static Integer SELECT_CATEGORY = 9004;
	public static Integer SELECT_CATEGORY_01 = 90041001;// CAIR --失火
	public static Integer SELECT_CATEGORY_02 = 90041002;// 客户关怀 --人员伤亡
	public static Integer SELECT_CATEGORY_03 = 90041003;// NVDR
	public static Integer SELECT_CATEGORY_04 = 90041004;// 服务活动
	public static Integer SELECT_CATEGORY_05 = 90041005;// 其他
	// 索赔申请单选择方式
	public static Integer SELECT_MODEL = 9005;
	public static Integer SELECT_MODEL_01 = 90051001;// 维修
	public static Integer SELECT_MODEL_02 = 90051002;// 退换车
	public static Integer SELECT_MODEL_03 = 90051003;// 延长保修
	public static Integer SELECT_MODEL_04 = 90051004;// 赠送保养
	public static Integer SELECT_MODEL_05 = 90051005;// 维修基金
	public static Integer SELECT_MODEL_06 = 90051006;// 现金
	public static Integer SELECT_MODEL_07 = 90051007;// 其他

	// 意向客户级别
	public static Integer GRADE = 1310;
	public static Integer GRADE_01 = 13101001;// H级
	public static Integer GRADE_02 = 13101002;// A级
	public static Integer GRADE_03 = 13101003;// B级
	public static Integer GRADE_04 = 13101004;// C级
	public static Integer GRADE_05 = 13101005;// N级
	public static Integer GRADE_06 = 13101006;// F0级
	public static Integer GRADE_07 = 13101007;// F级
	public static Integer GRADE_08 = 13101008;// O级

	// 政策类型
	public static Integer POLICY_TYPE = 6120;
	public static Integer POLICY_TYPE_01 = 61201001;// 政府/军警
	public static Integer POLICY_TYPE_02 = 61201001;// 知名企业
	public static Integer POLICY_TYPE_03 = 61201001;// 酒店/航空
	public static Integer POLICY_TYPE_04 = 61201001;// 租赁企业
	public static Integer POLICY_TYPE_05 = 61201001;// 一般大客户
	public static Integer POLICY_TYPE_06 = 61201001;// 影响力人物
	public static Integer POLICY_TYPE_07 = 61201001;// 企业员工团购
	public static Integer POLICY_TYPE_08 = 61201001;// 集团销售

	// 预计购买时间段
	public static Integer INTENT_BUY_TIME = 1354;
	public static Integer INTENT_BUY_TIME_01 = 13541001;// 0-3个月
	public static Integer INTENT_BUY_TIME_02 = 13541002;// 4-6个月
	public static Integer INTENT_BUY_TIME_03 = 13541003;// 7-12个月
	public static Integer INTENT_BUY_TIME_04 = 13541004;// 还未决定是否购车
	public static Integer INTENT_BUY_TIME_05 = 13541005;// 预计购买时间段
	public static Integer INTENT_BUY_TIME_06 = 13541006;// 有购车计划但不知道何时

	// 生产期货预定录入保存
	public final static Integer TT_VS_MONTHLY_FORECAST_DETAIL_COLOR_SAVE = 21112001;// 已保存
	public final static Integer TT_VS_MONTHLY_FORECAST_DETAIL_COLOR_SUBMIT = 21112002;// 已提交
	public final static Integer TT_VS_MONTHLY_FORECAST_DETAIL_COLOR_AUDIT = 21112003;// 已审核
	public final static Integer TT_VS_MONTHLY_FORECAST_DETAIL_COLOR_REPORT = 21112004;// 已反馈上报

	// 生产期货序列号状态
	public final static Integer PRO_ORDER_SERIAL = 2111;// 已反馈上报
	public final static Integer PRO_ORDER_SERIAL_REPORT_OTD = 21112100;// OTD已经审核
	public final static Integer PRO_ORDER_SERIAL_REPORT = 21112101;// 已反馈上报需要
	public final static Integer ZHONGJIN_ORDER_CONFIRM = 21112102;// 中进定金确认
	public final static Integer ZHONGJIN_TIMEOUT_CANCEL = 21112103;// 逾期未付定金撤销
	public final static Integer PRO_ORDER_SERIAL_REPORT_UN = 21112104;// 已反馈上报
																		// 不需要//add
																		// by
																		// lsy
																		// 2015-5-15

	// 索赔开票状态
	public final static Integer CLAIMS_INVOICE_STATUS = 5020;
	public final static Integer CLAIMS_INVOICE_STATUS_01 = 50201001;// 未开票
	public final static Integer CLAIMS_INVOICE_STATUS_02 = 50201002;// 已开票

	// 问卷制作状态
	public final static Integer VALID_STATUS = 1007;
	public final static Integer VALID_STATUS_01 = 10071001;// 启用
	public final static Integer VALID_STATUS_02 = 10071002;// 停用

	// 员工类型(大客户团购销售)
	public final static Integer EMPLOYEE_TYPE = 1099;
	public final static Integer EMPLOYEE_TYPE_01 = 10991001;// 一般大客户
	public final static Integer EMPLOYEE_TYPE_02 = 10991002;// 知名企业
	public final static Integer EMPLOYEE_TYPE_03 = 10991003;// 平安集团
	public final static Integer EMPLOYEE_TYPE_04 = 10991004;// 公务员
	public final static Integer EMPLOYEE_TYPE_05 = 10991005;// 知名院校、医院
	public final static Integer EMPLOYEE_TYPE_06 = 10991006;// 合作银行
	// 员工类型(集团销售)
	public final static Integer EMPLOYEE_TYPE1 = 1098;
	public final static Integer EMPLOYEE_TYPE1_01 = 10981001;// 知名企业
	public final static Integer EMPLOYEE_TYPE1_02 = 10981002;// 酒店/航空
	public final static Integer EMPLOYEE_TYPE1_03 = 10981003;// 租赁
	public final static Integer EMPLOYEE_TYPE1_04 = 10981004;// 一般企业
	public final static Integer EMPLOYEE_TYPE1_05 = 10981005;// 政府

	// add by huyu start
	// 客户细分类别
	public final static Integer EMPLOYEE_TYPE2 = 1097;
	public final static Integer EMPLOYEE_TYPE2_01 = 10971001;// 腾讯集团
	public final static Integer EMPLOYEE_TYPE2_02 = 10971002;// 中石油中石化
	public final static Integer EMPLOYEE_TYPE2_03 = 10971003;// 平安集团
	public final static Integer EMPLOYEE_TYPE2_04 = 10971004;// 其他
	public final static Integer EMPLOYEE_TYPE2_05 = 10971005;// 建设银行
	public final static Integer EMPLOYEE_TYPE2_06 = 10971006;// 中国银行
	public final static Integer EMPLOYEE_TYPE2_07 = 10971007;// 工商银行
	public final static Integer EMPLOYEE_TYPE2_08 = 10971008;// 中信银行
	public final static Integer EMPLOYEE_TYPE2_09 = 10971009;// 世界五百强
	public final static Integer EMPLOYEE_TYPE2_10 = 10971010;// 中国五百强
	public final static Integer EMPLOYEE_TYPE2_11 = 10971011;// 万科
	public final static Integer EMPLOYEE_TYPE2_12 = 10971012;// 华为
	public final static Integer EMPLOYEE_TYPE2_13 = 10971013;// 海尔
	public final static Integer EMPLOYEE_TYPE2_14 = 10971014;// 蓝色光标
	public final static Integer EMPLOYEE_TYPE2_15 = 10971015;// 中欧商学院
	public final static Integer EMPLOYEE_TYPE2_16 = 10971016;// 长江商学院
	public final static Integer EMPLOYEE_TYPE2_17 = 10971017;// 211院校
	public final static Integer EMPLOYEE_TYPE2_18 = 10971018;// 三甲医院
	// end

	// 证件类型
	public final static Integer ID_CART_TYPE = 2135;//
	public final static Integer ID_CART_TYPE_01 = 21351001;// 身份证
	public final static Integer ID_CART_TYPE_02 = 21351002;// 台胞证

	// 问卷类型
	public final static Integer QUESTIONNAIRE_TYPE = 1008;
	public final static Integer QUESTIONNAIRE_TYPE_01 = 10081001;// 回访问卷

	// 问题类型
	public final static Integer QUESTION_TYPE = 1009;
	public final static Integer QUESTION_TYPE_01 = 10091001;// 试乘试驾
	public final static Integer QUESTION_TYPE_02 = 10091002;// 售后满意度

	// 预约类型
	public final static Integer RESEVER_TYPE = 5100;
	public final static Integer RESEVER_TYPE_01 = 51001001;// 保养类型预约

	// 微信预约状态
	public final static Integer WX_RESERVER_STATUS = 1249;
	public final static Integer WX_RESERVER_STATUS_01 = 12491001;// 预约成功
	public final static Integer WX_RESERVER_STATUS_02 = 12491002;// 服务完成
	public final static Integer WX_RESERVER_STATUS_03 = 12491003;// 已取消
	public final static Integer WX_RESERVER_STATUS_04 = 12491004;// 已过期

	// 微信可预约天数
	public final static Integer WX_RESERVER_DAY = 14;

	public static Integer IS_INPUT_DMS_01 = 10011001; // 录入

	public static Integer IS_INPUT_DMS_02 = 10011002; // 未录入

	public static Integer IS_INPUT_DMS_03 = 10011003; // 未反馈

	// 财务报表类型
	public final static Integer REPORT_TYPE_01 = 20012001; // 年度
	public final static Integer REPORT_TYPE_02 = 20012002; // 季度

	public final static long FZ_BUS_TYPE_01 = 1L; // 其他业务利润
	public final static long FZ_BUS_TYPE_02 = 2L; // 主营业务收入明细

	public final static String MAIL_TO_LIST = "9006"; // 定时邮件收件人
	public final static String COPY_TO_LIST = "9007"; // 定时邮件抄送人

	// TT_VS_MATCH_CHECK CANCEL_TYPE
	public static Integer CANCEL_TYPE_01 = 1001; // 重新分配
	public static Integer CANCEL_TYPE_02 = 1002; // 撤单

	public final static String CEILING_PRICE_SCOPE = "1005";
	public final static String CEILING_PRICE_SCOPE_01 = "10051001"; // 指定经销商
	public final static String CEILING_PRICE_SCOPE_02 = "10051002"; // 全网经销商

	public final static String GROUP_TYPE = "9008";
	public final static String GROUP_TYPE_DOMESTIC = "90081001"; // 国产
	public final static String GROUP_TYPE_IMPORT = "90081002"; // 进口

	// 业务参数类型（国产车）
	public final static Integer PARAM_TYPE = 9015;
	public final static Integer PARAM_TYPE_01 = 90151001; // 销售预测比例下限
	public final static Integer PARAM_TYPE_02 = 90151002; // 销售预测时间设置
	public final static Integer PARAM_TYPE_03 = 90151003; // 配额分配时间设置
	public final static Integer PARAM_TYPE_04 = 90151004; // 常规订单调整比例
	public final static Integer PARAM_TYPE_05 = 90151005; // 常规订单提报时间
	public final static Integer PARAM_TYPE_06 = 90151006; // 配额转换率下限
	public final static Integer PARAM_TYPE_07 = 90151007; // 订单价格比例

	// 时间窗口类型
	public final static Integer DATE_WINDOW_PARAM_TYPE = 7029;
	public final static Integer DATE_WINDOW_PARAM_TYPE_1 = 70291001;// 区域分配时间窗口
	public final static Integer DATE_WINDOW_PARAM_TYPE_2 = 70291002;// 常规订单提交时间窗口

	// 订单类型（国产车）
	public final static Integer ORDER_TYPE_DOMESTIC = 9016;
	public final static Integer ORDER_TYPE_DOMESTIC_01 = 90161001; // 指派订单
	public final static Integer ORDER_TYPE_DOMESTIC_02 = 90161002; // 紧急订单
	public final static Integer ORDER_TYPE_DOMESTIC_03 = 90161003; // 直销订单
	public final static Integer ORDER_TYPE_DOMESTIC_04 = 90161004; // 常规订单
	public final static Integer ORDER_TYPE_DOMESTIC_05 = 90161005; // 官网订单

	// 付款方式（国产车）
	public final static Integer K4_PAYMENT = 9018;
	public final static Integer K4_PAYMENT_01 = 90181001; // 现金
	public final static Integer K4_PAYMENT_02 = 90181002; // 广汽汇理汽车金融有限公司
	public final static Integer K4_PAYMENT_03 = 90181003; // 菲亚特汽车金融有限责任公司
	public final static Integer K4_PAYMENT_04 = 90181004; // 兴业银行
	public final static Integer K4_PAYMENT_05 = 90181005; // 交通银行
	public final static Integer K4_PAYMENT_06 = 90181006; // 中国银行上海静安支行
	public final static Integer K4_PAYMENT_07 = 90181007; // 建行融资
	public final static Integer K4_PAYMENT_08 = 90181008; // 中信银行承兑汇票

	// 生产计划分周结果（国产车）
	public final static Integer MATE_TYPE = 9019;
	public final static Integer MATE_TYPE_01 = 90191001; // 未匹配
	public final static Integer MATE_TYPE_02 = 90191002; // 已匹配

	// 账户操作类型（国产车）
	public final static Integer ACCOUNT_OPPER_TYPE = 9020;
	public final static Integer ACCOUNT_OPPER_TYPE_01 = 90201001; // 打款
	public final static Integer ACCOUNT_OPPER_TYPE_02 = 90201002; // 配车预扣款
	public final static Integer ACCOUNT_OPPER_TYPE_03 = 90201003; // SO扣款
	public final static Integer ACCOUNT_OPPER_TYPE_04 = 90201004; // 开票过账
	public final static Integer ACCOUNT_OPPER_TYPE_05 = 90201005; // 发票冲红

	// 常规订单调整反馈（国产车）
	public final static Integer K4_ORDER_ADJUST = 9021;
	public final static Integer K4_ORDER_ADJUST_01 = 90211001; // 已提交
	public final static Integer K4_ORDER_ADJUST_02 = 90211002; // 已反馈
	public final static Integer K4_ORDER_ADJUST_03 = 90211003; // 不可修改

	// 返利类型（国产车）
	public final static Integer REBATE_TYPE = 9022;
	public final static Integer REBATE_TYPE_01 = 90221001; // 年度返利
	public final static Integer REBATE_TYPE_02 = 90221002; // 季度返利
	public final static Integer REBATE_TYPE_03 = 90221003; // 建店补贴
	public final static Integer REBATE_TYPE_04 = 90221004; // 形象升级返利

	// 返利使用类型（国产车）
	public final static Integer REBATE_USED_TYPE = 9023;
	public final static Integer REBATE_USED_TYPE_01 = 90231001; // 订单车辆使用
	public final static Integer REBATE_USED_TYPE_02 = 90231002; // 开红票使用

	// 融资请款状态
	public final static Integer FINANCING_STATUS = 9024;
	public final static Integer FINANCING_STATUS_01 = 90241001; // 融资请款
	public final static Integer FINANCING_STATUS_02 = 90241002; // 融资成功
	public final static Integer FINANCING_STATUS_03 = 90241003; // 融资失败
	public final static Integer FINANCING_STATUS_04 = 90241004; // 财务审批中
	public final static Integer FINANCING_STATUS_05 = 90241005; // 财务审批通过

	// 返利方向（国产车）
	public final static Integer REBATE_DIRACT = 9025;
	public final static Integer REBATE_DIRACT_01 = 90251001; // 发放
	public final static Integer REBATE_DIRACT_02 = 90251002; // 使用

	// 财务报表提报状态山册同
	// 资产负债表，损益表，经销商运营统计报表
	public final static Integer REPORT_AUDIT = 9027;
	public final static Integer REPORT_AUDIT_01 = 90271001;// 未提报
	public final static Integer REPORT_AUDIT_02 = 90271002;// 已提报
	// 经销商建店等级 by lsy
	public final static Integer RANGE = 9028;
	public final static Integer RANGE_A = 90281001;
	public final static Integer RANGE_B = 90281002;
	public final static Integer RANGE_C = 90281003;
	public final static Integer RANGE_D = 90281004;
	public final static Integer RANGE_D1 = 90281005;
	public final static Integer RANGE_D2 = 90281006;
	public final static Integer RANGE_M = 90281007;

	// 借贷方向
	public final static String BORROW_LEND_H = "H";// 借
	public final static String BORROW_LEND_S = "S";// 贷

	// 车辆节点
	public final static Integer VEHICLE_NODE = 1151;
	public final static Integer VEHICLE_NODE_01 = 11511001; // ZFSC-工厂订单冻结
	public final static Integer VEHICLE_NODE_02 = 11511002; // ZVDU-工厂订单车辆数据更新
	public final static Integer VEHICLE_NODE_03 = 11511003; // ZSHP-海运在途
	public final static Integer VEHICLE_NODE_04 = 11511004; // ZGOR-车辆到港
	public final static Integer VEHICLE_NODE_05 = 11511005; // ZCBC-车辆清关
	public final static Integer VEHICLE_NODE_06 = 11511006; // ZVP8-入VPC仓库
	public final static Integer VEHICLE_NODE_07 = 11511007; // ZPDP-PDI检查通过
	public final static Integer VEHICLE_NODE_08 = 11511008; // ZBIL-一次开票
	public final static Integer VEHICLE_NODE_09 = 11511009; // ZRL2-订单SAP审核通过
	public final static Integer VEHICLE_NODE_10 = 11511010; // ZDRL-中进车款确认
	public final static Integer VEHICLE_NODE_11 = 11511011; // ZDRQ-换车入库
	public final static Integer VEHICLE_NODE_12 = 11511012; // ZPDU-发车出库
	public final static Integer VEHICLE_NODE_13 = 11511013; // ZPDF-PDI检查失败
	public final static Integer VEHICLE_NODE_14 = 11511014; // ZTPR-退车入库
	public final static Integer VEHICLE_NODE_15 = 11511015; // ZDLD-经销商验收
	public final static Integer VEHICLE_NODE_16 = 11511016; // 已实销
	public final static Integer VEHICLE_NODE_17 = 11511017; // ZFSN-工厂订单冻结
	public final static Integer VEHICLE_NODE_18 = 11511018; // ZRL1-资源已分配
	public final static Integer VEHICLE_NODE_19 = 11511019; // ZDRR-经销商订单确认
	public final static Integer VEHICLE_NODE_20 = 11511020; // ZDRV-中进车款取消
	// new add
	public final static Integer VEHICLE_NODE_22 = 11511022; // YORD-Purchase
															// Order Create

	// 车辆生命状态
	public final static Integer LIF_CYCLE = 1152;
	public final static Integer LIF_CYCLE_01 = 11521001; // 生产线上
	public final static Integer LIF_CYCLE_02 = 11521002; // 车厂在库
	public final static Integer LIF_CYCLE_03 = 11521003; // 经销商在途
	public final static Integer LIF_CYCLE_04 = 11521004; // 经销商在库
	public final static Integer LIF_CYCLE_05 = 11521005; // 已实销
	public final static Integer LIF_CYCLE_06 = 11521006; // 无效

	// K4 车辆节点状态（国产车）
	public final static Integer K4_VEHICLE_NODE = 7001;
	public final static Integer K4_VEHICLE_NODE_01 = 70011001; // 总装上线
	public final static Integer K4_VEHICLE_NODE_02 = 70011002; // 总装下线
	public final static Integer K4_VEHICLE_NODE_03 = 70011003; // 质检完成
	public final static Integer K4_VEHICLE_NODE_04 = 70011004; // 工厂在库
	public final static Integer K4_VEHICLE_NODE_05 = 70011005; // 内部结算失败
	public final static Integer K4_VEHICLE_NODE_06 = 70011006; // 内部结算成功
	public final static Integer K4_VEHICLE_NODE_07 = 70011007; // 工厂质量扣留
	public final static Integer K4_VEHICLE_NODE_08 = 70011008; // 工厂质量扣留解除
	public final static Integer K4_VEHICLE_NODE_09 = 70011009; // 销售公司质量扣留
	public final static Integer K4_VEHICLE_NODE_10 = 70011010; // 销售公司质量扣留解除
	public final static Integer K4_VEHICLE_NODE_11 = 70011011; // 执行扣款
	public final static Integer K4_VEHICLE_NODE_12 = 70011012; // 退换车入库
	public final static Integer K4_VEHICLE_NODE_13 = 70011013; // 发票取消
	public final static Integer K4_VEHICLE_NODE_14 = 70011014; // 已开票
	public final static Integer K4_VEHICLE_NODE_15 = 70011015; // 延迟发运
	public final static Integer K4_VEHICLE_NODE_16 = 70011016; // 取消延迟发运
	public final static Integer K4_VEHICLE_NODE_17 = 70011017; // 已发运
	public final static Integer K4_VEHICLE_NODE_18 = 70011018; // 物流到店
	public final static Integer K4_VEHICLE_NODE_19 = 70011019; // 已验收
	public final static Integer K4_VEHICLE_NODE_20 = 70011020; // 已实销
	public final static Integer K4_VEHICLE_NODE_21 = 70011021; // 报废

	// 常规订单调整时间（国产车）
	public final static Integer GENGRAL_ORDER_TIME_TYPE = 7002;
	public final static Integer GENGRAL_ORDER_TIME_TYPE_01 = 70021001;// 常规订单调整时间

	// 销售订单状态（国产车）
	public final static Integer SALE_ORDER_TYPE = 7003;
	public final static Integer SALE_ORDER_TYPE_00 = 70031000; // 已保存
	public final static Integer SALE_ORDER_TYPE_01 = 70031001; // 已提交
	public final static Integer SALE_ORDER_TYPE_02 = 70031002; // 已分周
	public final static Integer SALE_ORDER_TYPE_03 = 70031003; // 小区审核通过
	public final static Integer SALE_ORDER_TYPE_04 = 70031004; // OTD审核通过
	public final static Integer SALE_ORDER_TYPE_05 = 70031005; // 已指派
	public final static Integer SALE_ORDER_TYPE_06 = 70031006; // 已确认
	public final static Integer SALE_ORDER_TYPE_07 = 70031007; // 扣款成功
	public final static Integer SALE_ORDER_TYPE_08 = 70031008; // 已生成发货单
	public final static Integer SALE_ORDER_TYPE_09 = 70031009; // 已开票
	public final static Integer SALE_ORDER_TYPE_10 = 70031010; // 已发运
	public final static Integer SALE_ORDER_TYPE_11 = 70031011; // 已到店
	public final static Integer SALE_ORDER_TYPE_12 = 70031012; // 已收车
	public final static Integer SALE_ORDER_TYPE_13 = 70031013; // 已取消
	public final static Integer SALE_ORDER_TYPE_14 = 70031014; // 小区审核驳回
	public final static Integer SALE_ORDER_TYPE_15 = 70031015; // OTD审核驳回
	public final static Integer SALE_ORDER_TYPE_16 = 70031016; // 发票取消

	// 发票类型（国产车）
	public final static Integer K4_INVOICE_TYPE = 7004;
	public final static Integer K4_INVOICE_TYPE_01 = 70041001; // 国产车发票
	public final static Integer K4_INVOICE_TYPE_02 = 70041002; // 国产车取消发票
	public final static Integer K4_INVOICE_TYPE_03 = 70041003; // 国产车退货发票
	public final static Integer K4_INVOICE_TYPE_04 = 70041004; // 取消贷项凭证

	// 配额导入对象类型（国产车）
	public final static Integer QUOTA_IMPORT_OBJECT = 7005;
	public final static Integer QUOTA_IMPORT_OBJECT_01 = 70051001; // 区域
	public final static Integer QUOTA_IMPORT_OBJECT_02 = 70051002; // 经销商

	// 配额导入类型（国产车）
	public final static Integer QUOTA_IMPORT_TYPE = 7006;
	public final static Integer QUOTA_IMPORT_TYPE_01 = 70061001; // 首次导入
	public final static Integer QUOTA_IMPORT_TYPE_02 = 70061002; // 增量导入

	// 配额状态（国产车）
	public final static Integer QUOTA_STATUS = 7007;
	public final static Integer QUOTA_STATUS_01 = 70071001; // 未分配
	public final static Integer QUOTA_STATUS_02 = 70071002; // 已保存
	public final static Integer QUOTA_STATUS_03 = 70071003; // 已下发

	// 配额包状态
	public final static Integer QUOTA_PACKAGE_STATUS = 7028;
	public final static Integer QUOTA_PACKAGE_STATUS_01 = 70281001;// 未下发
	public final static Integer QUOTA_PACKAGE_STATUS_02 = 70281002;// 已下发
	public final static Integer QUOTA_PACKAGE_STATUS_03 = 70281003;// 已关闭

	// 车辆节点主要状态（国产车）
	public final static Integer MAIN_STATUS = 7008;
	public final static Integer MAIN_STATUS_01 = 70081001; // 总装上线
	public final static Integer MAIN_STATUS_02 = 70081002; // 总装下线
	public final static Integer MAIN_STATUS_03 = 70081003; // 质检完成
	public final static Integer MAIN_STATUS_04 = 70081004; // 车辆在工厂销售库
	public final static Integer MAIN_STATUS_05 = 70081005; // Vehicle Blocked
															// From MFG
	public final static Integer MAIN_STATUS_06 = 70081006; // 车辆销售公司质量扣留 - 冻结
	public final static Integer MAIN_STATUS_07 = 70081007; // 延迟发运
	public final static Integer MAIN_STATUS_08 = 70081008; // 向经销商发货单过账
	public final static Integer MAIN_STATUS_09 = 70081009; // MSAP向SSAP发货单过账
	public final static Integer MAIN_STATUS_10 = 70081010; // MSAP向SSAP开票
	public final static Integer MAIN_STATUS_11 = 70081011; // SSAP向MSAP采购订单创建
	public final static Integer MAIN_STATUS_12 = 70081012; // SSAP向MSAP采购收货
	public final static Integer MAIN_STATUS_13 = 70081013; // SSAP向MSAP发票校验
	public final static Integer MAIN_STATUS_14 = 70081014; // 向经销商发货单创建

	// 车辆用途（国产）
	public final static Integer VEHICLE_USAGE_TYPE = 7010;
	public final static Integer VEHICLE_USAGE_TYPE_68 = 70101068; // 普通
	public final static Integer VEHICLE_USAGE_TYPE_69 = 70101069; // 试乘试驾车
	public final static Integer VEHICLE_USAGE_TYPE_70 = 70101070; // 员工购车
	public final static Integer VEHICLE_USAGE_TYPE_71 = 70101071; // 公司租赁车
	public final static Integer VEHICLE_USAGE_TYPE_72 = 70101072; // 售后服务车
	public final static Integer VEHICLE_USAGE_TYPE_73 = 70101073; // 其他
	public final static Integer VEHICLE_USAGE_TYPE_74 = 70101074; // 媒体置换车
	public final static Integer VEHICLE_USAGE_TYPE_75 = 70101075; // 媒体试驾车-申固
	public final static Integer VEHICLE_USAGE_TYPE_76 = 70101076; // 大客户购车
	public final static Integer VEHICLE_USAGE_TYPE_77 = 70101077; // 瑕疵车
	public final static Integer VEHICLE_USAGE_TYPE_78 = 70101078; // 瑕疵车其他
	public final static Integer VEHICLE_USAGE_TYPE_79 = 70101079; // 媒体优惠购车
	public final static Integer VEHICLE_USAGE_TYPE_80 = 70101080; // 长库龄车辆
	public final static Integer VEHICLE_USAGE_TYPE_81 = 70101081; // 81折销售部试驾车辆
	public final static Integer VEHICLE_USAGE_TYPE_82 = 70101082; // 售后培训用车
	public final static Integer VEHICLE_USAGE_TYPE_83 = 70101083; // 媒体试驾车-非申固

	// 运输类型（国产车）
	public final static Integer K4_TRAFFIC_TYPE = 7011;
	public final static Integer K4_TRAFFIC_TYPE_01 = 70111001; // 公路
	public final static Integer K4_TRAFFIC_TYPE_02 = 70111002; // 邮件
	public final static Integer K4_TRAFFIC_TYPE_03 = 70111003; // 铁路
	public final static Integer K4_TRAFFIC_TYPE_04 = 70111004; // 海运
	public final static Integer K4_TRAFFIC_TYPE_05 = 70111005; // 空运

	// 版本控制
	public final static Integer NO_CHANGE_VERSIONS = 0;
	public final static Integer CHANGE_VERSIONS = 1;

	// 批零预测上报状态（国产车）
	public final static Integer PAYPLREPROT_TYPE = 7012;// 7012
	public final static Integer PAYPLREPROT_TYPE_01 = 70121001;// 7012
																// 批零预测上报状态（国产车）
																// 区域已保存
	public final static Integer PAYPLREPROT_TYPE_02 = 70121002;// 7012
																// 批零预测上报状态（国产车）
																// 经销商已保存
	public final static Integer PAYPLREPROT_TYPE_03 = 70121003;// 7012
																// 批零预测上报状态（国产车）
																// 区域已提交
	public final static Integer PAYPLREPROT_TYPE_04 = 70121004;// 7012
																// 批零预测上报状态（国产车）
																// 经销商已提交

	/**
	 * 旧件模块
	 */
	// 旧件规则类型
	public final static Integer OP_RULE_TYPE = 9109;
	public final static Integer OP_RULE_TYPE_01 = 91091001; // 旧件标记规则
	public final static Integer OP_RULE_TYPE_02 = 91091002; // 旧件返回规则

	// 旧件规则导入剔除类型
	public final static Integer OP_RULE_IMP_TYPE = 9110;
	public final static Integer OP_RULE_IMP_TYPE_01 = 91101001; // 回运件剔除
	public final static Integer OP_RULE_IMP_TYPE_02 = 91101002; // 销毁件剔除
	public final static Integer OP_RULE_IMP_TYPE_03 = 91101003; // 紧急回运

	// 旧件类型：自销毁件，无保修状态，返还件
	public final static Integer OP_TYPE = 9111;
	public final static Integer OP_TYPE_NO_ORDER = 91111001; // 保修件
	public final static Integer OP_TYPE_DESTROY = 91111002; // 自销毁件
	public final static Integer OP_TYPE_RETURN = 91111003; // 返还件
	// 旧件状态：1 未发运状态,2 已发运状态,3. 清点接收,4. 已入库,5. 拒绝
	public final static Integer OP_STATUS = 9112;
	public final static Integer OP_STATUS_NO_DESPATCH = 91121001; // 未发运状态
	public final static Integer OP_STATUS_DESPATCH = 91121002; // 已发运状态
	public final static Integer OP_STATUS_RECEIVE = 91121003; // 清点接收
	public final static Integer OP_STATUS_REGECT = 91121004; // 拒绝
	public final static Integer OP_STATUS_IN_STORE = 91121005; // 审核入库
	// 旧件回运类型：1 常规回运 2 紧急回运
	public final static Integer OP_RETURN_TYPE = 9113;
	public final static Integer OP_RETURN_TYPE_NORMAL = 91131001; // 常规回运
	public final static Integer OP_RETURN_TYPE_URGENCY = 91131002; // 紧急回运
	// 旧件入库类型：扫描入库，手工选择入库
	public final static Integer OP_IN_STORE_TYPE = 9114;
	public final static Integer OP_IN_STORE_TYPE_SCAN = 91141001; // 扫描入库
	public final static Integer OP_IN_STORE_TYPE_MANUAL = 91141002; // 手工选择入库
	// 旧件库存状态：审核入库，出库
	public final static Integer OP_STORE_STATUS = 9115;
	public final static Integer OP_STORE_STATUS_IN = 91151001; // 在库
	public final static Integer OP_STORE_STATUS_OUT = 91151002; // 出库
	// 回运单状态：未发运，已发运，已清点，已审核
	public final static Integer OP_RETURN_STATUS = 9116;
	public final static Integer OP_RETURN_STATUS_NO_DESPATCH = 91161001; // 未发运
	public final static Integer OP_RETURN_STATUS_DESPATCH = 91161002; // 已发运
	/*
	 * public final static Integer OP_RETURN_STATUS_RECEIVE = 91161003; //已清点
	 * public final static Integer OP_RETURN_STATUS_AUDIT = 91161004; //
	 */

	// 扫描登录权限
	public final static Integer APP_LOGIN_AUTHORITY = 1900;
	public final static Integer APP_LOGIN_AUTHORITY_RECEIVE = 19000001; // 清点接收
	/**
	 * 旧件模块 END
	 */

	/**
	 * 微信保养套餐-燃油类型
	 */
	public final static Integer OILE_TYPE = 5055;
	public final static Integer OILE_TYPE_01 = 50551001; // 汽油
	public final static Integer OILE_TYPE_02 = 50551002; // 柴油

	/**
	 * 菲亚特配件类别
	 */
	public final static Integer PART_TYPE = 8001;
	public final static Integer PART_TYPE_01 = 80011001;// 常规零部件
	public final static Integer PART_TYPE_02 = 80011002;// 第三方
	public final static Integer PART_TYPE_03 = 80011003;// 养护品
	public final static Integer PART_TYPE_04 = 80011004;// 电瓶
	public final static Integer PART_TYPE_05 = 80011005;// 用品
	public final static Integer PART_TYPE_06 = 80011006;// 底盘号相关

	/**
	 * 菲亚特配件属性 property
	 */
	public final static Integer PART_PROPERTY = 8002;
	public final static Integer PART_PROPERTY_01 = 80021001;// A
	public final static Integer PART_PROPERTY_02 = 80021002;// B
	public final static Integer PART_PROPERTY_03 = 80021003;// C

	/**
	 * 菲亚特配件提报方式
	 */
	public final static Integer PART_REPORT_METHOD = 8003;
	public final static Integer PART_REPORT_METHOD_01 = 80031001;// 常规零部件
	public final static Integer PART_REPORT_METHOD_02 = 80031002;// 菲跃零部件

	/**
	 * 数据标记
	 */
	public final static Integer DATA_FLAG = 9335;
	public final static Integer DATA_FLAG_01 = 93351001;// 新增
	public final static Integer DATA_FLAG_02 = 93351002;// 修改
	public final static Integer DATA_FLAG_03 = 93351003;// 删除

	/**
	 * 日期类型
	 */
	public final static Integer DATE_TYPE = 9334;
	public final static Integer DATE_TYPE_01 = 93341001;// 出库日期
	public final static Integer DATE_TYPE_02 = 93341002;// 入库日期

	// 往来类型
	// public final static String REBATE_TYPE_1 = "0";// 来款
	// public final static String REBATE_TYPE_2 = "1";// 扣款
	public final static Integer REBATE_TYPE_0 = 9333;// 来款
	public final static Integer REBATE_TYPE_1 = 93331001;// 来款 "0"
	public final static Integer REBATE_TYPE_2 = 93331002;// 扣款 "1"

	/**
	 * 菲亚特配件订单类型
	 */
	public final static Integer PART_ORDER_TYPE = 8004;
	public final static Integer PART_ORDER_TYPE_01 = 80041001;// E-CODE 2
	public final static Integer PART_ORDER_TYPE_02 = 80041002;// E.O.
	public final static Integer PART_ORDER_TYPE_03 = 80041003;// S.O.
	public final static Integer PART_ORDER_TYPE_04 = 80041004;// V.O.R
	public final static Integer PART_ORDER_TYPE_05 = 80041005;// T.O.
	public final static Integer PART_ORDER_TYPE_06 = 80041006;// W.O.
	public final static Integer PART_ORDER_TYPE_07 = 80041007;// F.O.
	public final static Integer PART_ORDER_TYPE_08 = 80041008;// S-CODE 2
	// 精品/附件电商类别
	public final static Integer PART_ORDER_TYPE_09 = 80041009;// 车主直销
	public final static Integer PART_ORDER_TYPE_10 = 80041010;// 经销商批售

	/**
	 * 菲亚特配件订单状态
	 */
	public final static Integer PART_ORDER_STATUS = 8005;
	public final static Integer PART_ORDER_STATUS_01 = 80051001;// 待审核
	public final static Integer PART_ORDER_STATUS_02 = 80051002;// 已驳回
	public final static Integer PART_ORDER_STATUS_03 = 80051003;// 已拒绝
	public final static Integer PART_ORDER_STATUS_04 = 80051004;// 已通过
	public final static Integer PART_ORDER_STATUS_05 = 80051005;// 待提交

	/**
	 * 菲亚特配件索赔要求
	 */
	public final static Integer PART_CLAIM_REQUIREMENT = 8006;
	public final static Integer PART_CLAIM_REQUIREMENT_01 = 80061001;// 换货
	public final static Integer PART_CLAIM_REQUIREMENT_02 = 80061002;// 补发
	public final static Integer PART_CLAIM_REQUIREMENT_03 = 80061003;// 退货

	/**
	 * 菲亚特配件索赔性质
	 */
	public final static Integer PART_CLAIM_NATURE = 8007;
	public final static Integer PART_CLAIM_NATURE_01 = 80071001;// 零件破损（变形、划花、破裂）
	public final static Integer PART_CLAIM_NATURE_02 = 80071002;// 实物与标签不符合
	public final static Integer PART_CLAIM_NATURE_03 = 80071003;// 不完全组件
	public final static Integer PART_CLAIM_NATURE_04 = 80071004;// 不足或过量
	public final static Integer PART_CLAIM_NATURE_05 = 80071005;// 系统原因导致错误
	public final static Integer PART_CLAIM_NATURE_06 = 80071006;// 零件生锈
	public final static Integer PART_CLAIM_NATURE_07 = 80071007;// 钥匙配置错误
	public final static Integer PART_CLAIM_NATURE_08 = 80071008;// 特别批准
	public final static Integer PART_CLAIM_NATURE_09 = 80071009;// 其他
	/**
	 * 菲亚特配件索赔责任商
	 */
	public final static Integer PART_CLAIM_BUSINESS = 8008;
	public final static Integer PART_CLAIM_BUSINESS_01 = 80081001;// 供应商
	public final static Integer PART_CLAIM_BUSINESS_02 = 80081002;// 运营商
	public final static Integer PART_CLAIM_BUSINESS_03 = 80081003;// 广菲克
	public final static Integer PART_CLAIM_BUSINESS_04 = 80081004;// 仓库运营方

	/**
	 * 菲亚特配件索赔处理意见
	 */
	public final static Integer PART_CLAIM_TREAT = 8009;
	public final static Integer PART_CLAIM_TREAT_01 = 80091001;// 返厂更换
	public final static Integer PART_CLAIM_TREAT_02 = 80091002;// 店方自行维修
	public final static Integer PART_CLAIM_TREAT_03 = 80091003;// 封存待集中处理
	public final static Integer PART_CLAIM_TREAT_04 = 80091004;// 退货
	public final static Integer PART_CLAIM_TREAT_05 = 80091005;// 补发

	/**
	 * 菲亚特配件到货索赔附件类型
	 */
	public final static Integer PART_CLAIM_ATT_TYPE = 8010;
	public final static Integer PART_CLAIM_ATT_TYPE_01 = 80101001;// 异常品标签照片
	public final static Integer PART_CLAIM_ATT_TYPE_02 = 80101002;// 异常品照片
	public final static Integer PART_CLAIM_ATT_TYPE_03 = 80101003;// 合格品（到货零件）标签照片
	public final static Integer PART_CLAIM_ATT_TYPE_04 = 80101004;// 合格品照片
	public final static Integer PART_CLAIM_ATT_TYPE_05 = 80101005;// 合格品与异常品对比照片
	public final static Integer PART_CLAIM_ATT_TYPE_06 = 80101006;// 外包装标签照片（近景）
	public final static Integer PART_CLAIM_ATT_TYPE_07 = 80101007;// 外包装标签照片（远景）
	public final static Integer PART_CLAIM_ATT_TYPE_08 = 80101008;// 外包装受损照片（近景）
	public final static Integer PART_CLAIM_ATT_TYPE_09 = 80101009;// 内包装受损照片（近景）
	public final static Integer PART_CLAIM_ATT_TYPE_10 = 80101010;// 零件破损照片
	public final static Integer PART_CLAIM_ATT_TYPE_11 = 80101011;// 系统截图照片
	public final static Integer PART_CLAIM_ATT_TYPE_12 = 80101012;// 运输交接单签收照片
	/**
	 * 菲亚特配件到货索赔单据状态
	 */
	public final static Integer PART_CLAIM_STATUS = 8011;
	public final static Integer PART_CLAIM_STATUS_01 = 80111001;// 作废
	public final static Integer PART_CLAIM_STATUS_02 = 80111002;// 新建
	public final static Integer PART_CLAIM_STATUS_03 = 80111003;// 待审核
	public final static Integer PART_CLAIM_STATUS_04 = 80111004;// 审核通过
	public final static Integer PART_CLAIM_STATUS_05 = 80111005;// 审核驳回
	public final static Integer PART_CLAIM_STATUS_06 = 80111006;// 审核拒绝
	public final static Integer PART_CLAIM_STATUS_07 = 80111007;// 补发确认到货
	public final static Integer PART_CLAIM_STATUS_08 = 80111008;// 补发货运单录入

	/**
	 * 配件 DCS访问SAP的wsdl的用户名和密码
	 */
	// public final static String PART_SERVLET_USERNAME = "yangl2";
	// public final static String PART_SERVLET_PWD = "mis999mis";
	public final static String PART_SERVLET_USERNAME = "WS_DMS";
	public final static String PART_SERVLET_PWD = "dmstest";

	/**
	 * Iwerks 3210 Imarca 99 ICreditConArea GF01
	 */
	public final static String IWERKS = "3210";
	public final static String IMARCA = "99";
	public final static String ICreditConArea = "GFFC";// GF01
	public final static String SIGN = "I";
	public final static String OPTION = "BT";
	public final static String Iztype = "Z1";
	public final static String Mandt = "400";// 测试环境400 生产环境800

	/**
	 * 取消备注维护
	 * 
	 */
	public final static Integer CANCEL_REMARK_SYSTEM = 70131001;
	public final static Integer CANCEL_REMARK_INPUT = 70131002;

	/**
	 * 返利核算管理 上传方式
	 */
	public final static Integer UPDATE_WAY = 9117;
	public final static Integer UPDATE_WAY_01 = 91171001;// 全量覆盖
	public final static Integer UPDATE_WAY_02 = 91171002;// 增量保存

	/**
	 * 返利核算管理 商务政策类型
	 */
	public final static Integer BUSINESS_POLICY_TYPE = 9118;
	public final static Integer BUSINESS_POLICY_TYPE_01 = 91178001;// 销售
	public final static Integer BUSINESS_POLICY_TYPE_02 = 91178002;// 售后
	public final static Integer BUSINESS_POLICY_TYPE_03 = 91178003;// 网络

	/**
	 * 发送状态
	 */
	public final static Integer DELIVER_STATUS = 1213;
	public final static Integer DELIVER_STATUS_01 = 12131001;// 未发运
	public final static Integer DELIVER_STATUS_02 = 12131002;// 发运指令下达
	public final static Integer DELIVER_STATUS_03 = 12131003;// 发运出库
	public final static Integer DELIVER_STATUS_04 = 12131004;// 已签收

	/**
	 * 保修系统工时级别
	 */
	public final static Integer WARRANTY_WORK_LEVEL = 8030;
	public final static Integer WARRANTY_WORK_LEVEL_01 = 80301001;// A
	public final static Integer WARRANTY_WORK_LEVEL_02 = 80301002;// B
	public final static Integer WARRANTY_WORK_LEVEL_03 = 80301003;// C

	/**
	 * 保修系统保修类型
	 */
	public final static Integer WARRANTY_TYPE = 8031;
	public final static Integer WARRANTY_TYPE_01 = 80311001;// 整车保修
	public final static Integer WARRANTY_TYPE_02 = 80311002;// 配件维修
	public final static Integer WARRANTY_TYPE_03 = 80311003;// 活动补充

	/**
	 * 保修系统MVS范围
	 */
	public final static Integer WARRANTY_MVS_RANGE = 8032;
	public final static Integer WARRANTY_MVS_RANGE_01 = 80321001;// 品牌
	public final static Integer WARRANTY_MVS_RANGE_02 = 80321002;// 车系
	public final static Integer WARRANTY_MVS_RANGE_03 = 80321003;// 车型

	/**
	 * 保修系统活动类型
	 */
	public final static Integer WARRANTY_ACTIVITY_TYPE = 8033;
	public final static Integer WARRANTY_ACTIVITY_TYPE_01 = 80331001;// 服务活动
	public final static Integer WARRANTY_ACTIVITY_TYPE_02 = 80331002;// 召回活动

	/**
	 * 保修系统保修单状态
	 */
	public final static Integer WARRANTY_REPAIR_STATUS = 8034;
	public final static Integer WARRANTY_REPAIR_STATUS_01 = 80341001;// 待提交
	public final static Integer WARRANTY_REPAIR_STATUS_02 = 80341002;// 已提交
	public final static Integer WARRANTY_REPAIR_STATUS_03 = 80341003;// 作废
	public final static Integer WARRANTY_REPAIR_STATUS_04 = 80341004;// ESIGI作废
	public final static Integer WARRANTY_REPAIR_STATUS_05 = 80341005;// 审核作废

	/**
	 * 保修系统保修单ESIGI审核状态
	 */
	public final static Integer WARRANTY_ESIGI_STATUS = 8035;
	public final static Integer WARRANTY_ESIGI_STATUS_01 = 80351001;// 0-
	public final static Integer WARRANTY_ESIGI_STATUS_02 = 80351002;// 20-通过后被删除的保修单
	public final static Integer WARRANTY_ESIGI_STATUS_03 = 80351003;// 21-通过的保修单
	public final static Integer WARRANTY_ESIGI_STATUS_04 = 80351004;// 22-需经销商修订的保修单
	public final static Integer WARRANTY_ESIGI_STATUS_05 = 80351005;// 23-需KEYUSER确认的保修单
	public final static Integer WARRANTY_ESIGI_STATUS_06 = 80351006;// 24-需核对授权书的保修单
	public final static Integer WARRANTY_ESIGI_STATUS_07 = 80351007;// 29-被系统拒绝的保修单

	// 菲亚特品牌代码
	// public final static String FIAT_GROUP_CODE ="Fiat";//菲亚特品牌
	public final static Integer FIAT_GROUP_CODE = 91191001;// 菲亚特品牌

	/**
	 * 保修系统召回标记
	 */
	public final static Integer WARRANTY_TAG = 8036;
	public final static Integer WARRANTY_TAG_01 = 80361001;// A
	public final static Integer WARRANTY_TAG_02 = 80361002;// B
	public final static Integer WARRANTY_TAG_03 = 80361003;// C
	public final static Integer WARRANTY_TAG_04 = 80361004;// Z
	public final static Integer WARRANTY_TAG_05 = 80361005;// D

	/**
	 * Fiat保修导入上限
	 */
	public final static Integer MAX_IMPORT_NUMBER = 4000;

	/**
	 * BSUV 官网订单状态
	 */
	public final static Integer BSUV_EC_OEDER_STATUS = 1619;
	public final static Integer BSUV_EC_OEDER_STATUS_01 = 16191001; // 有效
	public final static Integer BSUV_EC_OEDER_STATUS_02 = 16181002; // 期货
	public final static Integer BSUV_EC_OEDER_STATUS_03 = 16191002; // 现货
	public final static Integer BSUV_EC_OEDER_STATUS_04 = 16191003; // 撤单

	/**
	 * 销售分析历史数据查询报表统计类型
	 */
	public final static Integer SALES_TOTAL_TYPE = 7025;
	public final static Integer SALES_TOTAL_TYPE_MONTH = 70251001; // 待提交
	public final static Integer SALES_TOTAL_TYPE_WEEK = 70251002; // 已提交

	/**
	 * PDI检查结果
	 */
	public final static Integer PDI_RESULT = 7016;
	public final static Integer PDI_RESULT_01 = 70161001; // 通过
	public final static Integer PDI_RESULT_02 = 70161002; // 不通过

	/**
	 * 菲亚特配件货运单状态
	 */
	public final static Integer PART_DELIVER_STATUS = 8012;
	public final static Integer PART_DELIVER_STATUS_01 = 80121001;// 待收货
	public final static Integer PART_DELIVER_STATUS_02 = 80121002;// 已收货

	/**
	 * 车辆综合查询【资源池类型】
	 */
	public final static Integer RESOURCE_TYPE = 7030;
	public final static Integer RESOURCE_TYPE_01 = 70301001;// 全国
	public final static Integer RESOURCE_TYPE_02 = 70301002;// 大区总
	public final static Integer RESOURCE_TYPE_03 = 70301003;// 区域
	/**
	 * 质损部位
	 */
	public final static Integer MASS_LOSS_LOCATION = 7031;
	public final static Integer MASS_LOSS_LOCATION_01 = 70311001;// 车头
	public final static Integer MASS_LOSS_LOCATION_02 = 70311002;// 车灯
	public final static Integer MASS_LOSS_LOCATION_03 = 70311003;// 车门

	/**
	 * 质损程度
	 */
	public final static Integer MASS_LOSS_DEGREE = 7032;
	public final static Integer MASS_LOSS_DEGREE_01 = 70321001;// 轻微
	public final static Integer MASS_LOSS_DEGREE_02 = 70321002;// 一般
	public final static Integer MASS_LOSS_DEGREE_03 = 70321003;// 严重

	/**
	 * 质损性质
	 */
	public final static Integer MASS_LOSS_NATURE = 7033;
	public final static Integer MASS_LOSS_NATURE_01 = 70331001;// 刮伤
	public final static Integer MASS_LOSS_NATURE_02 = 70331002;// 变形
	public final static Integer MASS_LOSS_NATURE_03 = 70331003;// 功能失灵
	public final static Integer MASS_LOSS_NATURE_04 = 70331004;// 其他
	/**
	 * 质损状态
	 */
	public final static Integer MASS_STATUS = 7036;
	public final static Integer MASS_STATUS_01 = 70361001;// 正常
	public final static Integer MASS_STATUS_02 = 70361002;// 质损

	/**
	 * 配件发布状态
	 */
	public final static Integer PART_OBSOLETE_RELESE_STATUS = 7034;
	public final static Integer PART_OBSOLETE_RELESE_STATUS_01 = 70341001;// 上架
	public final static Integer PART_OBSOLETE_RELESE_STATUS_02 = 70341002;// 下架

	/**
	 * 呆滞件申请状态
	 */
	public final static Integer PART_OBSOLETE_APPLY_STATUS = 7035;
	public final static Integer PART_OBSOLETE_APPLY_STATUS_01 = 70351001;// 待确认
	public final static Integer PART_OBSOLETE_APPLY_STATUS_02 = 70351002;// 已确认
	public final static Integer PART_OBSOLETE_APPLY_STATUS_03 = 70351003;// 出库
	public final static Integer PART_OBSOLETE_APPLY_STATUS_04 = 70351004;// 入库
	public final static Integer PART_OBSOLETE_APPLY_STATUS_05 = 70351005;// 驳回
	public final static Integer PART_OBSOLETE_APPLY_STATUS_06 = 70351006;// 作废

	/**
	 * 确认状态
	 */
	public final static Integer EC_CONFIRM_STATUS = 7038;
	public final static Integer EC_CONFIRM_STATUS_01 = 70381001;// 已确认
	public final static Integer EC_CONFIRM_STATUS_02 = 70381002;// 未确认

	/**
	 * 电商订单状态
	 */
	public final static Integer EC_ORDER_STATUS = 7037;
	public final static Integer EC_ORDER_STATUS_01 = 70371001;// 成功
	public final static Integer EC_ORDER_STATUS_02 = 70371002;// 失败
	public final static Integer EC_ORDER_STATUS_03 = 70371003;// 默认值

	/**
	 * 维修工时参数CJD,FIAT
	 */
	public final static Integer BRAND_GROUP_TYPE = 7039;
	public final static Integer BRAND_GROUP_TYPE_01 = 70391001;// CJD
	public final static Integer BRAND_GROUP_TYPE_02 = 70391002;// FIAT

	/**
	 * 开票类型
	 */
	public final static Integer Direct_INVOICE_TYPE = 7040;
	public final static Integer Direct_INVOICE_TYPE_01 = 70401001;// 增票
	public final static Integer Direct_INVOICE_TYPE_02 = 70401002;// 普票

	/**
	 * 验证码session常量
	 */
	public final static String USER_CHECK_CODE = "usercheckcode";

	/**
	 * 微信：卡券类型
	 */
	public final static Integer CARD_VOUCHER_TYPE = 5021;
	public final static Integer CARD_VOUCHER_TYPE_01 = 50211001;// 折扣卷
	public final static Integer CARD_VOUCHER_TYPE_02 = 50211002;// 抵用卷
	public final static Integer CARD_VOUCHER_TYPE_03 = 50211003;// 代金卷

	/**
	 * 微信：卡券使用状态
	 */
	public final static Integer CARD_VOUCHER_STATUS = 5022;
	public final static Integer CARD_VOUCHER_STATUS_01 = 50221001;// 未使用
	public final static Integer CARD_VOUCHER_STATUS_02 = 50221002;// 使用中
	public final static Integer CARD_VOUCHER_STATUS_03 = 50221003;// 已使用
	public final static Integer CARD_VOUCHER_STATUS_04 = 50221004;// 已作废

	/**
	 * VIP车辆DCID请求文件和接收文件存放地址
	 */
	public final static String DCID_VIP_INPUT = "D:\\DCID\\VIP\\input\\";// INPUT地址
	public final static String DCID_VIP_OUTPUT = "D://DCID//VIP//output//";// OUTPUT地址

	/**
	 * 投保单：投保类型
	 */
	public final static Integer INSURE_ORDER_TYPE = 9120;
	public final static Integer INSURE_ORDER_TYPE_01 = 91201001;// 新保
	public final static Integer INSURE_ORDER_TYPE_02 = 91201002;// 本店续保
	public final static Integer INSURE_ORDER_TYPE_03 = 91201003;// 它店投保

	/**
	 * 投保单：单据状态
	 */
	public final static Integer FORM_STATUS = 9121;
	public final static Integer FORM_STATUS_01 = 91211001;// 完成
	public final static Integer FORM_STATUS_02 = 91211002;// 作废

	/**
	 * 投保单：投保渠道
	 */
	public final static Integer INS_CHANNELS = 9122;
	public final static Integer INS_CHANNELS_01 = 91221001;// 电话营销
	public final static Integer INS_CHANNELS_02 = 91221002;// 传统
	public final static Integer INS_CHANNELS_03 = 91221003;// 其他
	public final static Integer INS_CHANNELS_04 = 91221004;// 信贷

	/**
	 * 投保单：是否本地投保
	 */
	public final static Integer IS_INS_LOCAL = 9123;
	public final static Integer IS_INS_LOCAL_01 = 91231001;// 是
	public final static Integer IS_INS_LOCAL_02 = 91231002;// 否

	/**
	 * 卡券：卡券分类
	 */
	public final static Integer VOUCHER_TYPE = 9124;
	public final static Integer VOUCHER_TYPE_01 = 91241001;// 现金抵用

	/**
	 * 卡券：使用范围
	 */
	public final static Integer USE_RANGE = 9125;
	public final static Integer USE_RANGE_01 = 91251001;// 发券经销商
	public final static Integer USE_RANGE_02 = 91251002;// 全网经销商

	/**
	 * 卡券：发券标准
	 */
	public final static Integer VOUCHER_STANDARD = 9126;
	public final static Integer VOUCHER_STANDARD_01 = 91261001;// 本店新保
	public final static Integer VOUCHER_STANDARD_02 = 91261002;// 本店续保
	public final static Integer VOUCHER_STANDARD_03 = 91261003;// 全部
	/**
	 * 营销活动：发布状态
	 */
	public final static Integer RELEASE_STATUS = 9220;
	public final static Integer RELEASE_STATUS_01 = 92201001;// 待发布
	public final static Integer RELEASE_STATUS_02 = 92201002;// 已发布
	public final static Integer RELEASE_STATUS_03 = 92201003;// 已终止

	/**
	 * 全境界产品分类
	 */
	public final static Integer PRODUCT_TYPE = 9221;
	public final static Integer PRODUCT_TYPE_01 = 92211001;// 车险
	public final static Integer PRODUCT_TYPE_02 = 92211002;// 全境界延保服务

	/**
	 * 全境界产品状态
	 */
	public final static Integer PRODUCT_STATUS = 9222;
	public final static Integer PRODUCT_STATUS_01 = 92221001;// 待发布
	public final static Integer PRODUCT_STATUS_02 = 92221002;// 已发布
	public final static Integer PRODUCT_STATUS_03 = 92221003;// 已终止

	/**
	 * 保障期限
	 */
	public final static Integer GUARANTEE_PERIOD = 9223;
	public final static Integer GUARANTEE_PERIOD_01 = 92231001;// 6个月
	public final static Integer GUARANTEE_PERIOD_02 = 92231002;// 12个月
	public final static Integer GUARANTEE_PERIOD_03 = 92231003;// 18个月
	public final static Integer GUARANTEE_PERIOD_04 = 92231004;// 24个月
	public final static Integer GUARANTEE_PERIOD_05 = 92231005;// 36个月

	/**
	 * 全境界产品销售状态：销售状态
	 */
	public final static Integer SALES_STATUS = 9224;
	public final static Integer SALES_STATUS_01 = 92241001;// 未提交
	public final static Integer SALES_STATUS_02 = 92241002;// 已提交
	/**
	 * 召回类别
	 */
	public final static Integer RECALL_THEME = 7041;
	public final static Integer RECALL_THEME_01 = 70411001;// RT快速反应
	public final static Integer RECALL_THEME_02 = 70411002;// Recall召回

	/**
	 * 召回参与方式
	 */
	public final static Integer RECALL_TYPE = 7042;
	public final static Integer RECALL_TYPE_01 = 70421001;// 必选
	public final static Integer RECALL_TYPE_02 = 70421002;// 非必选

	/**
	 * 召回车辆导入类型
	 */
	public final static Integer INPORT_TYPE = 7043;
	public final static Integer INPORT_TYPE_01 = 70431001;// 全量覆盖
	public final static Integer INPORT_TYPE_02 = 70431002;// 增量导入

	/**
	 * 召回车辆导入功能
	 */
	public final static Integer INPORT_CLASS = 7044;
	public final static Integer INPORT_CLASS_01 = 70441001;// 召回VIN导入
	public final static Integer INPORT_CLASS_02 = 70441002;// GCS完成状态导入

	/**
	 * 召回状态
	 */
	public final static Integer RECALL_STATUS = 7045;
	public final static Integer RECALL_STATUS_01 = 70451001;// 未发布
	public final static Integer RECALL_STATUS_02 = 70451002;// 已发布
	public final static Integer RECALL_STATUS_03 = 70451003;// 已关闭

	/**
	 * 更换配件类型
	 */
	public final static Integer RECALL_CHECK_STATUS = 7046;
	public final static Integer RECALL_CHECK_STATUS_01 = 70461001;// 标配
	public final static Integer RECALL_CHECK_STATUS_02 = 70461002;// 选配

	/**
	 * 验证码用
	 */
	public final static String GT_RESULT = "gtresult";

	/*
	 * TM_REGION 表 REGION_TYPE
	 */
	public final static int REGION_TYPE_COUNTRY = 10091001; // 国家
	public final static int REGION_TYPE_PROVINCE = 10091002; // 省份
	public final static int REGION_TYPE_CITY = 10091003; // 城市
	public final static int REGION_TYPE_COUNTY = 10091004; // 区县
	/**
	 * 待工原因
	 */
	public final static Integer WAIT_WORK_REASON = 1819;
	public final static Integer WAIT_WORK_REASON_01 = 18191001;// 维修方案确认中
	public final static Integer WAIT_WORK_REASON_02 = 18191002;// 施工中
	public final static Integer WAIT_WORK_REASON_03 = 18191003;// 其他

	/**
	 * 数据显示类别
	 */
	public final static Integer DISPALY_DATA_TEYP = 1254;
	public final static Integer DISPALY_DATA_TEYP1 = 12541001;// 全部数据
	public final static Integer DISPALY_DATA_TEYP2 = 12541002;// 导入成功数据
	public final static Integer DISPALY_DATA_TEYP3 = 12541003;// 导入失败数据

	
	/**
	 * JdPower跟踪性质
	 */
	public final static Integer TRACING_PROPERTY=9201;
	public final static Integer TRACING_PROPERTY_01=92011001;//首次回访
	public final static Integer TRACING_PROPERTY_02=92011002;//多次回访
	
	/**
	 * JdPower渠道来源
	 */
	public final static Integer DITCH_SOURCE=9202;
	public final static Integer DITCH_SOURCE_01=92021001;//JDpower
	public final static Integer DITCH_SOURCE_02=92021002;//NPS
	
	/**
	 * JdPower跟踪状态
	 */
	public final static Integer TAIL_AFTER_STATUS=9203;
	public final static Integer TAIL_AFTER_STATUS_01=92031001;//待处理
	public final static Integer TAIL_AFTER_STATUS_02=92031002;//已结案
	
	/**
	 * JdPower处理结果
	 */
	public final static Integer DISPOSE_RESULT=9204;
	public final static Integer DISPOSE_RESULT_01=92041001;//满意
	public final static Integer DISPOSE_RESULT_02=92041002;//不满意
	
}
