package com.example.demo.service;

import com.example.demo.dto.GeneralResponseBodyDTO;
import java.util.HashMap;


public interface UserService {
	GeneralResponseBodyDTO userLogin(String email, String password);
	GeneralResponseBodyDTO userVote(String email, String point);
	HashMap<String, String> getAllUserPoint();
	boolean allUserVoted();
	void clearAllUserPoint();
}
