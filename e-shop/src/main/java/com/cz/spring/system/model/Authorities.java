package com.cz.spring.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;
import java.io.Serializable;


@TableName("sys_authorities")
public class Authorities implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "authority_id", type = IdType.AUTO)
    private Integer authorityId;


    private String authorityName;


    private String authority;


    private String menuUrl;


    private Integer parentId;


    private Integer isMenu;


    private Integer orderNumber;


    private String menuIcon;

    private Date createTime;

    private Date updateTime;

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                ", authorityId=" + authorityId +
                ", authorityName=" + authorityName +
                ", authority=" + authority +
                ", menuUrl=" + menuUrl +
                ", parentId=" + parentId +
                ", isMenu=" + isMenu +
                ", orderNumber=" + orderNumber +
                ", menuIcon=" + menuIcon +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
