package com.sun.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchOrderParam {

	private String keyword;
	
	private String fromTime;
	
	private String toTime;
	
	private String search_status;
}
