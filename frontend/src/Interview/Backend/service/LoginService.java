import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final Map<String, User> users = new HashMap<>();
 
  public User login(String email) {
      User user = users.get(email);
      if (user == null) {
          user = new User(email);
          users.put(email, user);
      }
      return user;
  }

  public void logout(String email) {
    userMap.put(email, false);
  }

  public boolean isUserAuthenticated(String email) {
    return userMap.getOrDefault(email, false);
  }
}
This service has three methods:

// login - It takes an email address as input and sets its authentication status as true in the HashMap.
// logout - It takes an email address as input and sets its authentication status as false in the HashMap.
// isUserAuthenticated - It takes an email address as input and returns the authentication status of the user.