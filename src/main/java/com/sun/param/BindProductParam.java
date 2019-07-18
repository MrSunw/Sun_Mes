package com.sun.param;

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
public class BindProductParam {

	private Integer parentId;
	
	private float productLeftbackweight;
	
	private Integer productchildId;
	
	private float productChildTargetweight;
}
