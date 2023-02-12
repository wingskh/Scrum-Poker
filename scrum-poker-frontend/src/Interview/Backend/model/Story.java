import java.util.HashMap;
import java.util.Map;

public class Story {
  private String id;
  private String name;
  private Map<String, Integer> storyPoints;

  public Story(String id, String name) {
    this.id = id;
    this.name = name;
    this.storyPoints = new HashMap<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, Integer> getStoryPoints() {
    return storyPoints;
  }

  public void setStoryPoints(Map<String, Integer> storyPoints) {
    this.storyPoints = storyPoints;
  }

  public void addStoryPoint(String userId, int point) {
    storyPoints.put(userId, point);
  }
}
