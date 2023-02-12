package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class UserDTO
{
	@JsonProperty("username")
	private String username;

	@JsonProperty("email")
	private String email;
}

