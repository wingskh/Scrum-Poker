@Service
public class StoryService {
    private Map<String, Integer> storyPoints = new HashMap<>();

    @Autowired
    private LoginService loginService;
    
    public void assignStoryPoint(String email, int point) {
        storyPoints.put(email, point);
        if (storyPoints.size() == getNumberOfLoggedInUsers()) {
            revealStoryPoints();
        }
    }

    private int getNumberOfLoggedInUsers() {
        // logic to get the number of logged-in users
        return loginService.getNumberOfLoggedInUsers();
    }

    private void revealStoryPoints() {
        // logic to reveal the story points
    }

    public Map<String, Integer> getStoryPoints() {
        return storyPoints;
    }
}
