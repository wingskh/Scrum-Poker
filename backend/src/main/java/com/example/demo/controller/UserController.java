package com.example.demo.controller;


import com.example.demo.dto.GeneralResponseBodyDTO;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	private JSONObject constructResponseObject(GeneralResponseBodyDTO responseResult) {
		JSONObject response = new JSONObject();
		JSONObject statusCode = new JSONObject();
		statusCode.put("code", responseResult.getCode());
		statusCode.put("payload", responseResult.getPayload());
		statusCode.put("message", responseResult.getMessage());
		response.put("status", statusCode);
		return response;
	}

	@PostMapping(value = "/login", produces = "application/json")
	public ResponseEntity<String> userLogin(@RequestParam("email") String email, @RequestParam("password") String password)
			throws Exception {
		GeneralResponseBodyDTO responseResult = userService.userLogin(email, password);
		JSONObject response = constructResponseObject(responseResult);
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}

}
