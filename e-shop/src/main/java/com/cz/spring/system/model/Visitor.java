package com.cz.spring.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("sys_visitor")
public class Visitor implements Serializable {

    private static final long serialVersionUID = 5347507588757889197L;


    @TableId(value = "visitor_id", type = IdType.AUTO)
    private Integer visitorId;


    private String visitorIp;


    private String visitorAddress;


    private Date visitorTime;

	public Integer getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(Integer visitorId) {
		this.visitorId = visitorId;
	}

	public String getVisitorIp() {
		return visitorIp;
	}

	public void setVisitorIp(String visitorIp) {
		this.visitorIp = visitorIp;
	}

	public String getVisitorAddress() {
		return visitorAddress;
	}

	public void setVisitorAddress(String visitorAddress) {
		this.visitorAddress = visitorAddress;
	}

	public Date getVisitorTime() {
		return visitorTime;
	}

	public void setVisitorTime(Date visitorTime) {
		this.visitorTime = visitorTime;
	}
    
    

}
