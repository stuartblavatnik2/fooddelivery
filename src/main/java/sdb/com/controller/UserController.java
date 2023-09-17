package sdb.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sdb.com.common.model.ApiResponse;
import sdb.com.common.model.UserRegistrationRequest;


@Slf4j
@RestController
@RequestMapping(value = "/api")

public class UserController {
    private final RabbitTemplate rabbitTemplate;

    public UserController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/test")
    public ApiResponse getUser() {
        log.info("In get");
        return new ApiResponse("Ok");
    }

    @PostMapping("/user")
    public ApiResponse createUser(@RequestBody UserRegistrationRequest request) {
        log.info("Received request to create user: {}", request);
        rabbitTemplate.convertAndSend("", "q.user-registration", request);
        return new ApiResponse("Ok");
    }
}
