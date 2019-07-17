package com.sun.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BindProductDto {

	private Integer parentId;
	
	private String parentProductId;
	
	private float productLeftbackweight;
	
	private Integer productchildId;
	
	private float productChildTargetweight;
}
