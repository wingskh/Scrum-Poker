package com.example.demo.service.impl;

import com.example.demo.controller.StoryController;
import com.example.demo.dao.StoryDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.GeneralResponseBodyDTO;
import com.example.demo.dto.StoryDTO;
import com.example.demo.service.StoryService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;


@Slf4j
@Service
public class StoryServiceImpl implements StoryService {
	private ObjectMapper objectMapper = new ObjectMapper();
	public  static final String WAITING = "Waiting";
	public static  final String ACTIVE = "Active";
	public static final String COMPLETED = "Completed";

	@Autowired
	StoryDao storyDao;

	@Autowired
	UserDao userDao;

	@Autowired
	UserService userService;

	// Method to get the list of stories
	@Override
	public GeneralResponseBodyDTO getAllStories() {
		GeneralResponseBodyDTO response = new GeneralResponseBodyDTO();

		response.setCode("FAIL");

		try {
			List<StoryDTO> stories = storyDao.getAllStories();
			JSONObject storiesJson = new JSONObject();
			if (ObjectUtils.isEmpty(stories)) {
				response.setMessage("Cannot find any stories");
				storiesJson.put("stories", new JSONArray());
				return response;
			}

			response.setCode("SUCCESS");
			response.setMessage("Get Data Successful");
			storiesJson.put("stories", stories);
			response.setPayload(storiesJson);

			return response;
		} catch (Exception e) {
			log.error("Fail to retrieve data from DB with error: ", e);
			response.setMessage("Error: " + e.getMessage());
		}
		return response;
	}

	// Method to get the list of stories
	@Override
	public GeneralResponseBodyDTO updateStoryStatus(String email, String selectedID, String updatedStatus){
		GeneralResponseBodyDTO response = new GeneralResponseBodyDTO();
		response.setCode("FAIL");

		try {
			if (!StringUtils.hasText(email)) {
				response.setMessage("Missing email");
				return response;
			}

			if (updatedStatus.equals(ACTIVE)){
				String userRole = userDao.getUserRole(email);
				if (!userRole.equals("Tech Lead")) {
					response.setMessage("Error: Not a Tech Lead Account");
					return response;
				}

				List<StoryDTO> stories = storyDao.getStoriesByStatus(updatedStatus);
				if (stories.size() > 0) {
					response.setMessage("Error: Already Have Active Story");
					return response;
				}
			}

			boolean updateStatusSuccess = storyDao.updateStoryStatus(selectedID, updatedStatus);
			if (!updateStatusSuccess) {
				response.setMessage("Error: Cannot Find Matched Story");
				return response;
			}

			response.setCode("SUCCESS");
			if (updatedStatus.equals(ACTIVE)){
				userService.clearAllUserPoint();
			}
			response.setMessage("Update Successful");

			return response;
		} catch (Exception e) {
			log.error("Fail to retrieve data from DB with error: ", e);
			response.setMessage("Error: " + e.getMessage());
		}
		return response;
	}

	@Override
	public GeneralResponseBodyDTO getStoryDetailByID(String storyID) {
		GeneralResponseBodyDTO response = new GeneralResponseBodyDTO();
		response.setCode("FAIL");

		try {
			List<StoryDTO> story = storyDao.getStoryDetailByID(storyID);
			JSONObject storiesJson = new JSONObject();

			if (CollectionUtils.isEmpty(story)) {
				response.setMessage("Cannot find matched story");
				storiesJson.put("stories", new JSONArray());
				return response;
			}
			String storyJsonStr = objectMapper.writeValueAsString(story.get(0));
			JSONObject storyJson = new JSONObject(storyJsonStr);
			response.setCode("SUCCESS");
			response.setMessage("Get Story Successful");
			storiesJson.put("story", storyJson);
			response.setPayload(storiesJson);

			return response;
		} catch (Exception e) {
			log.error("Fail to retrieve data from DB with error: ", e);
			response.setMessage("Error: " + e.getMessage());
		}
		return response;
	}

	@Override
	public GeneralResponseBodyDTO storyPointEstimate(String email, String point) {
		GeneralResponseBodyDTO response = userService.userVote(email, point);
		if (!response.getMessage().equals("FAIL")) {
			try {
				boolean allUserVoted = userService.allUserVoted();
				JSONObject payloadJson = new JSONObject();
				payloadJson.put("allUserVoted", allUserVoted);
				response.setPayload(payloadJson);
				return response;
			} catch (Exception e) {
				log.error("Fail to check voted status: ", e);
				response.setMessage("Error: " + e.getMessage());
			}
		}
		return response;
	}

	@Override
	public GeneralResponseBodyDTO getPointResult(String storyID) {
		GeneralResponseBodyDTO response = new GeneralResponseBodyDTO();
		response.setCode("FAIL");
		try {
			HashMap<String, String> userPointHistory = userService.getAllUserPoint();
			response.setPayload(new JSONObject(userPointHistory));
			response.setMessage("Get Point Record Successfully");
			response.setCode("SUCCESS");

			return response;

		} catch (Exception e) {
			log.error("Fail to check voted status: ", e);
			response.setMessage("Error: " + e.getMessage());
		}
		return response;
	}
}