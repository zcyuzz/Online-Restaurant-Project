package com.cz.spring.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_config")
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 3826431687691601081L;


    @TableId(value = "config_id", type = IdType.AUTO)
    private Integer configId;

    //1:mail 2:oss 3:push
    private Integer configType;


    private String configName;

    //smtp address
    private String configPar1;

    //smtp port
    private String configPar2;

    //sender adddress
    private String configPar3;

    //app code
    private String configPar4;

    //sender
    private String configPar5;

    //0:enable 1:dis
    private Integer state;

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Integer getConfigType() {
		return configType;
	}

	public void setConfigType(Integer configType) {
		this.configType = configType;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigPar1() {
		return configPar1;
	}

	public void setConfigPar1(String configPar1) {
		this.configPar1 = configPar1;
	}

	public String getConfigPar2() {
		return configPar2;
	}

	public void setConfigPar2(String configPar2) {
		this.configPar2 = configPar2;
	}

	public String getConfigPar3() {
		return configPar3;
	}

	public void setConfigPar3(String configPar3) {
		this.configPar3 = configPar3;
	}

	public String getConfigPar4() {
		return configPar4;
	}

	public void setConfigPar4(String configPar4) {
		this.configPar4 = configPar4;
	}

	public String getConfigPar5() {
		return configPar5;
	}

	public void setConfigPar5(String configPar5) {
		this.configPar5 = configPar5;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
    
    
}
