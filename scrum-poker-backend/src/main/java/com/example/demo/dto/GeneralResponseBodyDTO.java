package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.json.JSONObject;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GeneralResponseBodyDTO extends GeneralResponseDTO
{
	@JsonProperty("code")
	private String code;

	@JsonProperty("message")
	private String message;

	@JsonProperty("payload")
	private JSONObject payload;
}

