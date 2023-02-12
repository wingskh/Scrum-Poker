package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class StoryDTO
{
	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("status")
	private String status;

	@JsonProperty("point")
	private String point;
}

