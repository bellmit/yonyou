package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

@Table("tm_entity_relationship")
@CompositePK({"PARENT_ENTITY","CHILD_ENTITY","BIZ_CODE"})
public class EntityRelationshipPO extends BaseModel   {

}
