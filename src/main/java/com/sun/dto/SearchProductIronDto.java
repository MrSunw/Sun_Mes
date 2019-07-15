package com.sun.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchProductIronDto {

	private String keyword;
	
	private Integer search_status;
}
