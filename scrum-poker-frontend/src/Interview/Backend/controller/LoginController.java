import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestBody String email) {
        // Perform the login logic here
        // For example, you can check if the email exists in the database
        // If it does, return a success message, otherwise return an error message
        return "Login successful for email: " + email;
    }
}

// This is a basic implementation of a login controller that allows the 
// frontend to send an email address to the backend, which then performs 
// the login logic. The @RestController annotation indicates that this 
// class is a REST controller, and the @PostMapping("/login") annotation 
// indicates that this method is accessible through a POST request to 
// the /login endpoint. The @RequestBody annotation is used to extract 
// the email from the request body and store it in the email variable.