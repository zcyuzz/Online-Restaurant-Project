package com.cz.spring.WebSocket.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("mes_info")
public class MesInfo implements Serializable {

    private static final long serialVersionUID = -5474464168570871449L;

    @TableId(value = "mes_id", type = IdType.AUTO)
    private Integer mesId;

    private String id;

    private String username;

    private String content;

    private String type;

    private String avatar;

    private String collectUserId;

    private Date mesTime;

    private Integer isAlreadyRead;

    @TableField(exist = false)
    private boolean mine=false;

	public Integer getMesId() {
		return mesId;
	}

	public void setMesId(Integer mesId) {
		this.mesId = mesId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCollectUserId() {
		return collectUserId;
	}

	public void setCollectUserId(String collectUserId) {
		this.collectUserId = collectUserId;
	}

	public Date getMesTime() {
		return mesTime;
	}

	public void setMesTime(Date mesTime) {
		this.mesTime = mesTime;
	}

	public Integer getIsAlreadyRead() {
		return isAlreadyRead;
	}

	public void setIsAlreadyRead(Integer isAlreadyRead) {
		this.isAlreadyRead = isAlreadyRead;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}
    
    
}
