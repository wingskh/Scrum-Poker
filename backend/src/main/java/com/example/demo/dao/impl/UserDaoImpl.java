package com.example.demo.dao.impl;


import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


@Slf4j
@SpringBootApplication
public class UserDaoImpl implements UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<UserDTO> loginValidation(String email, String password) {
		String sql = "SELECT `email`, `username` FROM `user` WHERE `email` = ? AND `password` = ? limit 1";
		List<UserDTO> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserDTO>(UserDTO.class), email, password);
		return users;
	}

	@Override
	public String getUserRole(String email) {
		String sql = "SELECT `role` FROM `user` WHERE `email` = ? limit 1";
		String userRole = jdbcTemplate.queryForObject(sql, (String.class), email);
		return userRole;
	}
}
