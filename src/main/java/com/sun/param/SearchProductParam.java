package com.sun.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchProductParam {

	private String keyword;
	
	private String search_source;
}
