package com.example.demo.dao.impl;


import com.example.demo.dao.StoryDao;
import com.example.demo.dto.StoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


@Slf4j
@SpringBootApplication
public class StoryDaoImpl implements StoryDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<StoryDTO> getAllStories(){
		String sql = "SELECT * FROM `story`";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<StoryDTO>(StoryDTO.class));
	}

	@Override
	public List<StoryDTO> getStoryDetailByID(String id){
		String sql = "SELECT * FROM `story` where `id` = ? limit 1";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<StoryDTO>(StoryDTO.class), id);
	}

	@Override
	public List<StoryDTO> getStoriesByStatus(String status){
		String sql = "SELECT * FROM `story` where `status` = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<StoryDTO>(StoryDTO.class), status);
	}

	@Override
	public boolean updateStoryStatus(String id, String newStatus){
		String sql = "UPDATE `story` SET `status` =  ? WHERE `id` = ?";
		int affectedRowCount = jdbcTemplate.update(sql, newStatus, id);
		return affectedRowCount == 1;
	}
}
