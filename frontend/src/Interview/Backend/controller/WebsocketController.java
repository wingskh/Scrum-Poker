@Controller
public class WebSocketController {
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    public WebSocketController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/story-point-update")
    @SendTo("/topic/story-point-update")
    public StoryPointUpdateResponse updateStoryPoint(StoryPointUpdateRequest request) {
        // Perform operations to update story points
        // ...
        // Send the updated story point to the frontend
        messagingTemplate.convertAndSend("/topic/story-point-update", new StoryPointUpdateResponse(request));
        return new StoryPointUpdateResponse(request);
    }
}

// <dependency>
//     <groupId>org.springframework.boot</groupId>
//     <artifactId>spring-boot-starter-websocket</artifactId>
// </dependency>