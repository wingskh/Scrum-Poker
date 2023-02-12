package com.example.demo.service;

import com.example.demo.dto.GeneralResponseBodyDTO;


public interface WebSocketService {
	GeneralResponseBodyDTO findActiveStory();
	GeneralResponseBodyDTO storyPointEstimate(String email, String storyID, String point);
}
