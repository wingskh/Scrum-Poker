package com.example.demo.dao;

import com.example.demo.dto.StoryDTO;
import java.util.List;

public interface StoryDao {
	List<StoryDTO> getAllStories();
	List<StoryDTO> getStoryDetailByID(String id);
	boolean updateStoryStatus(String id, String newStatus);
	List<StoryDTO> getStoriesByStatus(String status);
}
