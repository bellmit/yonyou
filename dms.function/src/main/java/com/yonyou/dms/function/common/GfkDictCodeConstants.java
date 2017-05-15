package com.yonyou.dms.function.common;

public class GfkDictCodeConstants {
	/**
	 * 服务活动类型
	 */
	public static final int ACTIVITY_TYPE=22041001;  //本地定义活动

	public static final int DELIVERY_STATUS_ALREADY = 13061004;//已签收
	
	/**
	 * 发布状态
	 */
	public static final int RELEASE_TAG_NOT=22051001;  //未发布
	
	public static final int RELEASE_TAG_YES=22051002;  //已发布
	
	public static final int RELEASE_TAG_CANCEL=22051003;  //已取消
	
	/**
	 * 购车类型
	 */
	public static final int BUY_CAR_TYPE = 14211001;
	
	//数据来源 （本地）
    public static final int ACTIVITY_FROM_THIS=22061001;
    
    /**
     * 三包锁定状态
     */
    //锁定
    public static final int LOCK_STATUS_YES=10041001;
    
    //未锁
    public static final int LOCK_STATUS_NO=10041002;
    
    //服务经理解锁
   // public static final int LOCK_STATUS_SERVER=10041002;
    
    //服务经理
    public static final int EMP_ROLE_SERVER=10061015;
}
