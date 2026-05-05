package in.ashokit.rest;

import in.ashokit.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
public class DemoRestController {

    @GetMapping("/")
    public String test(){
        return "Test msg";
    }

    @GetMapping("/welcome")
    public ResponseEntity<ApiResponse<String>> welcome(){

        String payload = "Welcome to MyMart..!!";

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setMessage("Request processed successfully");
        response.setData(payload);

        return ResponseEntity.ok(response);

     }
}
