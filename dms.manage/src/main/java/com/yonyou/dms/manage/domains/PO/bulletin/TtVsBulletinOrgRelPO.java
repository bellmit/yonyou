package com.yonyou.dms.manage.domains.PO.bulletin;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.OemBaseModel;

@Table("TT_VS_BULLETIN_ORG_REL")
@CompositePK({"ORG_ID","BULLETIN_ID"})
public class TtVsBulletinOrgRelPO extends OemBaseModel {

}
