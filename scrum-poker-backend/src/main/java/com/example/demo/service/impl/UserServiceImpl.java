package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.GeneralResponseBodyDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
	private final String REDISSON_KEY = "userLoginRecord";

	@Autowired
	UserDao userDao;

	@Autowired
	RedissonClient redissonClient;

	@Override
	public GeneralResponseBodyDTO userLogin(String email, String password) {
		GeneralResponseBodyDTO response = new GeneralResponseBodyDTO();
		response.setCode("FAIL");
		RList<HashMap<String, String>> loginRedissonRecord = redissonClient.getList(REDISSON_KEY);

//		String tempStringKey = "tempKey";
//		RList<HashMap> tempString = redissonClient.getList(tempStringKey);
//		loginRecord.add(userLoginRecord);
//		tempString.clear();

		if (CollectionUtils.isEmpty(loginRedissonRecord)) {
			loginRedissonRecord.add(new HashMap<>());
		}

		try {
			if (!StringUtils.hasText(email)) {
				response.setMessage("Missing email");
				return response;
			}

			if (!StringUtils.hasText(password)) {
				response.setMessage("Missing password");
				return response;
			}

			List<UserDTO> users = userDao.loginValidation(email, password);
			if (ObjectUtils.isEmpty(users)) {
				response.setMessage("Incorrect email or password");
				return response;
			}

			response.setCode("SUCCESS");
			response.setMessage("Login Success");
			JSONObject userJson = new JSONObject();
			userJson.put("email", users.get(0).getEmail());
			userJson.put("username", users.get(0).getUsername());
			response.setPayload(userJson);

			HashMap<String, String> userLoginRecord = loginRedissonRecord.get(0);
			userLoginRecord.put(users.get(0).getEmail(), "");
			loginRedissonRecord.clear();
			loginRedissonRecord.add(userLoginRecord);

			log.info("userLoginRecord: " + loginRedissonRecord.get(0));
			return response;
		} catch (Exception e) {
			log.error("Fail to retrieve data from DB with error: ", e);
			response.setMessage("Error: " + e.getMessage());
		}
		return response;
	}

	@Override
	public GeneralResponseBodyDTO userVote(String email, String point) {
		GeneralResponseBodyDTO response = new GeneralResponseBodyDTO();
		response.setCode("FAIL");
		try {
			RList<HashMap<String, String>> loginRedissonRecord = redissonClient.getList(REDISSON_KEY);
			HashMap<String, String> userLoginRecord = loginRedissonRecord.get(0);
			userLoginRecord.put(email, point);
			loginRedissonRecord.clear();
			loginRedissonRecord.add(userLoginRecord);
			response.setCode("SUCCESS");
			response.setMessage("Update Point Successfully");
			return response;
		} catch (Exception e) {
			log.error("Fail to update point with error: ", e);
			response.setMessage("Error: " + e.getMessage());
		}
		return response;
	}

	@Override
	public boolean allUserVoted() {
		RList<HashMap<String, String>> loginRedissonRecord = redissonClient.getList(REDISSON_KEY);
		return !CollectionUtils.isEmpty(loginRedissonRecord) & !loginRedissonRecord.get(0).containsValue("");
	}

	@Override
	public HashMap<String, String> getAllUserPoint() {
		RList<HashMap<String, String>> loginRedissonRecord = redissonClient.getList(REDISSON_KEY);
		return loginRedissonRecord.get(0);
	}

	@Override
	public void clearAllUserPoint() {
		RList<HashMap<String, String>> loginRedissonRecord = redissonClient.getList(REDISSON_KEY);
		HashMap<String, String> userLoginRecord = loginRedissonRecord.get(0);
		log.info("Clear all point record");
		for (Map.Entry<String, String> entry : userLoginRecord.entrySet()) {
			entry.setValue("");
		}
		loginRedissonRecord.clear();
		loginRedissonRecord.add(userLoginRecord);
	}

}
