package com.example.demo.service.impl;

import com.example.demo.dao.StoryDao;
import com.example.demo.dto.GeneralResponseBodyDTO;
import com.example.demo.dto.StoryDTO;
import com.example.demo.service.StoryService;
import com.example.demo.service.UserService;
import com.example.demo.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Autowired
    StoryDao storyDao;

    @Autowired
    UserService userService;

    @Autowired
    StoryService storyService;

    @Override
    public GeneralResponseBodyDTO findActiveStory() {
        GeneralResponseBodyDTO response = new GeneralResponseBodyDTO();
        response.setCode("FAIL");

        try {
            List<StoryDTO> stories = storyDao.getStoriesByStatus(storyService.ACTIVE);
            if (stories.size() == 0) {
                response.setMessage("No Active Story");
                return response;
            }

            response.setCode("SUCCESS");
            response.setMessage("Update Successful");
            JSONObject payloadJson = new JSONObject();
            payloadJson.put("id", stories.get(0).getId());
            response.setPayload(payloadJson);

            return response;
        } catch (Exception e) {
            log.error("Fail to retrieve data from DB with error: ", e);
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    @Override
    public GeneralResponseBodyDTO storyPointEstimate(String email, String storyID, String point) {
        GeneralResponseBodyDTO response = new GeneralResponseBodyDTO();
        try {
            response = storyService.storyPointEstimate(email, point);
            boolean allUserVoted = userService.allUserVoted();
            JSONObject payloadJson = new JSONObject();
            payloadJson.put("allUserVoted", allUserVoted);
            response.setPayload(payloadJson);
            if (allUserVoted) {
                storyService.updateStoryStatus(email, storyID, storyService.COMPLETED);
            }
            return response;
        } catch (Exception e) {
            log.error("Fail to check point estimate status: ", e);
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }
}
