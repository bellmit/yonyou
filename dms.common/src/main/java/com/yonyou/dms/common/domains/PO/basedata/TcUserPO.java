package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.OemBaseModel;

/**
 * DCS用户表
 * @author 夏威
 * 2017-2-6
 */
@Table("TC_USER")
@IdName("USER_ID")
public class TcUserPO extends OemBaseModel {

}
