package com.cz.spring.shop.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@TableName("e_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = -284644051341858603L;


    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer goodsId;


    private String goodsSn;
    

    private Integer publishId;
    

    private String goodsName;

    private String goodsImg;


    private String goodsDetailsImgs;


    private BigDecimal shopPrice;


    private BigDecimal shopRePrice;


    private Integer goodsType;


    private String goodsColors;


    private String goodsSizes;

    private String goodsUnit;


    private Integer isSale;


    private String goodsDesc;


    private Integer saleNum;


    private Integer visitNum;


    private Integer appraiseNum;


    private Date createTime;
    
    @TableField(exist = false)
    private String nickName;  

    @TableField(exist = false)
    private String keyword;

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	
	public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

	
	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public String getGoodsDetailsImgs() {
		return goodsDetailsImgs;
	}

	public void setGoodsDetailsImgs(String goodsDetailsImgs) {
		this.goodsDetailsImgs = goodsDetailsImgs;
	}

	public BigDecimal getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(BigDecimal shopPrice) {
		this.shopPrice = shopPrice;
	}

	public BigDecimal getShopRePrice() {
		return shopRePrice;
	}

	public void setShopRePrice(BigDecimal shopRePrice) {
		this.shopRePrice = shopRePrice;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsColors() {
		return goodsColors;
	}

	public void setGoodsColors(String goodsColors) {
		this.goodsColors = goodsColors;
	}

	public String getGoodsSizes() {
		return goodsSizes;
	}

	public void setGoodsSizes(String goodsSizes) {
		this.goodsSizes = goodsSizes;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public Integer getIsSale() {
		return isSale;
	}

	public void setIsSale(Integer isSale) {
		this.isSale = isSale;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public Integer getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(Integer visitNum) {
		this.visitNum = visitNum;
	}

	public Integer getAppraiseNum() {
		return appraiseNum;
	}

	public void setAppraiseNum(Integer appraiseNum) {
		this.appraiseNum = appraiseNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
    
	public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
}
