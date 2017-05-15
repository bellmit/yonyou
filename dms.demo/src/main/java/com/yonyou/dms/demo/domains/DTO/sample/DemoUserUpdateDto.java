package com.yonyou.dms.demo.domains.DTO.sample;

import java.util.Date;

/**
 * 
* UserDto
* @author zhangxc
* @date 2016年6月30日
 */
public class DemoUserUpdateDto {

    private String userIds;
    private Integer sex;
    private Date bridthday;
    
    public String getUserIds() {
        return userIds;
    }
    
    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
    
    public Integer getSex() {
        return sex;
    }
    
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    
    public Date getBridthday() {
        return bridthday;
    }
    
    public void setBridthday(Date bridthday) {
        this.bridthday = bridthday;
    }
    
    
}