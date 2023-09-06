@RestController
@RequestMapping("/api/stories")
public class StoryController {

  private final StoryService storyService;

  public StoryController(StoryService storyService) {
    this.storyService = storyService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Story> getStory(@PathVariable Long id) {
    return storyService.getStory(id)
        .map(story -> ResponseEntity.ok().body(story))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public List<Story> getStories() {
    return storyService.getStories();
  }

  @PostMapping
  public Story createStory(@RequestBody Story story) {
    return storyService.createStory(story);
  }

  @PutMapping("/{id}")
  public Story updateStory(@PathVariable Long id, @RequestBody Story story) {
    return storyService.updateStory(id, story);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteStory(@PathVariable Long id) {
    storyService.deleteStory(id);
    return ResponseEntity.ok().build();
  }
}
