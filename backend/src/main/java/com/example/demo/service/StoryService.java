package com.example.demo.service;

import com.example.demo.dto.GeneralResponseBodyDTO;


public interface StoryService {
	String WAITING = "Waiting";
	String ACTIVE = "Active";
	String COMPLETED = "Completed";
	GeneralResponseBodyDTO getAllStories();
	GeneralResponseBodyDTO updateStoryStatus(String email, String selectedID, String updatedStatus);
	GeneralResponseBodyDTO getStoryDetailByID(String storyID);
	GeneralResponseBodyDTO storyPointEstimate(String email, String point);
	GeneralResponseBodyDTO getPointResult(String storyID);

}
