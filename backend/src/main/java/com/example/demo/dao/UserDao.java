package com.example.demo.dao;

import com.example.demo.dto.UserDTO;
import java.util.List;


public interface UserDao {
	List<UserDTO> loginValidation(String email, String password);
	String getUserRole(String email);
}
