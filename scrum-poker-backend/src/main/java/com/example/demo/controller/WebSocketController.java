package com.example.demo.controller;

import com.example.demo.dto.GeneralResponseBodyDTO;
import com.example.demo.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WebSocketController {
    @Autowired
    WebSocketService webSocketService;

    private JSONObject constructResponseObject(GeneralResponseBodyDTO responseResult) {
        JSONObject response = new JSONObject();
        JSONObject statusCode = new JSONObject();
        statusCode.put("code", responseResult.getCode());
        statusCode.put("payload", responseResult.getPayload());
        statusCode.put("message", responseResult.getMessage());
        response.put("status", statusCode);
        return response;
    }

    @MessageMapping("/find-active-story")
    @SendTo("/subscribe/find-active-story")
    public ResponseEntity<String> findActiveStory() {
        GeneralResponseBodyDTO responseResult = webSocketService.findActiveStory();
        JSONObject response = constructResponseObject(responseResult);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

    @MessageMapping("/update-user-vote")
    @SendTo("/subscribe/update-user-vote")
    public ResponseEntity<String> storyPointEstimate(String jsonObjectStr) {
        JSONObject jsonObject = new JSONObject(jsonObjectStr);
        String email = (String) jsonObject.get("email");
        String point = (String) jsonObject.get("point");
        String storyID = (String) jsonObject.get("storyID");
        GeneralResponseBodyDTO responseResult = webSocketService.storyPointEstimate(email, storyID, point);
        JSONObject response = constructResponseObject(responseResult);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }


}