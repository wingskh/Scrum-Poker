package com.example.demo.controller;


import com.example.demo.dto.GeneralResponseBodyDTO;
import com.example.demo.service.StoryService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/story")
public class StoryController {
	@Autowired
    StoryService storyService;

	private JSONObject constructResponseObject(GeneralResponseBodyDTO responseResult) {
        JSONObject response = new JSONObject();
        JSONObject statusCode = new JSONObject();
        statusCode.put("code", responseResult.getCode());
        statusCode.put("payload", responseResult.getPayload());
        statusCode.put("message", responseResult.getMessage());
        response.put("status", statusCode);
        return response;
    }

    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<String> getAllStories()
            throws Exception {
        GeneralResponseBodyDTO responseResult = storyService.getAllStories();
        JSONObject response = constructResponseObject(responseResult);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

    @PostMapping(value = "/set-active", produces = "application/json")
    public ResponseEntity<String> setStoryActive(@RequestParam("email") String email, @RequestParam("selectedID") String selectedID) {
        GeneralResponseBodyDTO responseResult = storyService.updateStoryStatus(email, selectedID, StoryService.ACTIVE);
        JSONObject response = constructResponseObject(responseResult);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

    @GetMapping(value = "/detail", produces = "application/json")
    public ResponseEntity<String> getStoryDetailByID(@RequestParam("storyID") String storyID) {
        GeneralResponseBodyDTO responseResult = storyService.getStoryDetailByID(storyID);
        JSONObject response = constructResponseObject(responseResult);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

    @GetMapping(value = "/result", produces = "application/json")
    public ResponseEntity<String> getPointResult(@RequestParam("storyID") String storyID) {
        GeneralResponseBodyDTO responseResult = storyService.getPointResult(storyID);
        JSONObject response = constructResponseObject(responseResult);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }
}
