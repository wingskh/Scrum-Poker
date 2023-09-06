@Service
public class UserService {
  private List<User> loggedInUsers = new ArrayList<>();

  public void addLoggedInUser(User user) {
    loggedInUsers.add(user);
  }

  public List<User> getLoggedInUsers() {
    return loggedInUsers.size();
  }
}
