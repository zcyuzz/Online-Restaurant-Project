package com.cz.spring.shop.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("e_appraise")
public class Appraise implements Serializable {

    private static final long serialVersionUID = -9133401464345687054L;


    @TableId(value = "appraise_id", type = IdType.AUTO)
    private Integer appraiseId;


    private String goodsSn;

    private String goodsName;


    private Integer userId;


    @TableField(exist = false)
    private String nickName;


    @TableField(exist = false)
    private String avatar;


    private Integer logisticsService;


    private Integer commodityQuality;

 
    private Integer coincide;


    private String comments;

    private Date appraiseTime;
}
