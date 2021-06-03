package com.boc.webqr.controller;

import com.boc.webqr.bean.User;
import com.boc.webqr.bean.UserResponse;
import com.boc.webqr.service.impl.SocketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    SocketService socketService;

    @MessageMapping("/user")
    @SendToUser("/topic/user") // use @SendToUser instead of @SendTo
    public UserResponse socketResponse(User user, Principal principal) throws Exception {
        logger.info("Class: "+getClass()+",Method: socketResponse ,Mapping: /user/topic/user");
        //log.info("Received greeting message {} from {}", message, principal.getName());
        socketService.addUserName(principal.getName()); // store UUID
        //Thread.sleep(1000); // simulated delay
        return new UserResponse(user.getName());
    }

    @MessageMapping("/paymentReceived")
    @SendTo("/topic/user")
    public UserResponse getSocket(String refId) {
        return new UserResponse("Ref ID received " + refId);
    }
}
