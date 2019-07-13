package com.sun.param;

import java.util.Date;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MesProductVo {
	
	//接收批量生成材料的个数
	@Min(1)
	private Integer counts=1;
	
    private Integer id;

    private Integer pId;

    private String productId;

    private Integer productOrderid;

    private Integer productPlanid;

    private Float productTargetweight;//工艺重量

    private Float productRealweight;//投料重量

    private Float productLeftweight;//剩余重量

    private Float productBakweight;

    private String productIrontype;

    private Float productIrontypeweight;

    private String productMaterialname;

    private String productImgid;

    private String productMaterialsource;

    private Integer productStatus;

    private String productRemark;

    private String productOperator;

    private Date productOperateTime;

    private String productOperateIp;

   
}