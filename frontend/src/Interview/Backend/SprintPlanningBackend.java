import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SprintPlanningBackend {
  
  private static Map<String, Integer> storyPoints = new HashMap<>();
  private static String activeStory = "";

  public static void main(String[] args) {
    SpringApplication.run(SprintPlanningBackend.class, args);
  }

  public static void setActiveStory(String newActiveStory) {
    activeStory = newActiveStory;
  }

  public static String getActiveStory() {
    return activeStory;
  }

  public static void addStoryPoint(String email, int point) {
    storyPoints.put(email, point);
  }

  public static Map<String, Integer> getStoryPoints() {
    return storyPoints;
  }

}
