import java.util.HashMap;
import java.util.Map;

public class User {
  private String email;
  private Map<String, Integer> storyPointEstimates;

  public User(String email) {
    this.email = email;
    this.storyPointEstimates = new HashMap<>();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Map<String, Integer> getStoryPointEstimates() {
    return storyPointEstimates;
  }

  public void setStoryPointEstimates(Map<String, Integer> storyPointEstimates) {
    this.storyPointEstimates = storyPointEstimates;
  }

  public void addStoryPointEstimate(String storyTitle, Integer estimate) {
    this.storyPointEstimates.put(storyTitle, estimate);
  }
}
