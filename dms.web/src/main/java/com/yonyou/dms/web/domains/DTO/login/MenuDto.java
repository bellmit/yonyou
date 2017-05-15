/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : MenuDto.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月19日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.web.domains.DTO.login;

import java.util.Map;

public class MenuDto {
    private String menuId;
    private String menuName;
    private String menuUrl;
    private String menuIcon;
    private String parentId;
    private String menuType;
    private Integer menuRank;
    private Map<String,MenuDto> children;
    
    public String getMenuId() {
        return menuId;
    }
    
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    
    public String getMenuName() {
        return menuName;
    }
    
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    public String getMenuUrl() {
        return menuUrl;
    }
    
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
    
    public String getMenuIcon() {
        return menuIcon;
    }
    
    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
    
    public String getParentId() {
        return parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    
    public Map<String,MenuDto> getChildren() {
        return children;
    }

    
    public void setChildren(Map<String,MenuDto> children) {
        this.children = children;
    }

    
    public String getMenuType() {
        return menuType;
    }

    
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    
    public Integer getMenuRank() {
        return menuRank;
    }

    
    public void setMenuRank(Integer menuRank) {
        this.menuRank = menuRank;
    }
   
    
}
