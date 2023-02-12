@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // Code to validate the user
        // ...
        storyService.addLoggedInUser(user.getEmail());  
        return user.getEmail();
    }


    @PostMapping("/logout")
    public void logout(@RequestBody User user) {
        storyService.removeLoggedInUser(user.getEmail());
    }

    @GetMapping("/logged-in")
    public List<User> getLoggedInUsers() {
        return userService.getLoggedInUsers();
    }
}
