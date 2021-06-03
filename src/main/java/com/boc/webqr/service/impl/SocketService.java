package com.boc.webqr.service.impl;

import com.boc.webqr.bean.RequestBean;
import com.boc.webqr.bean.SwitchBean;
import com.boc.webqr.bean.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;

@Service
public class SocketService {

    private static final String SIMP_SESSION_ID = "simpSessionId";
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/topic/user";
    private List<String> userNames = new ArrayList<>();

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Handles WebSocket connection events
     */
    @EventListener(SessionConnectEvent.class)
    public void handleWebsocketConnectListener(SessionConnectEvent event) {
        //log.info(String.format("WebSocket connection established for sessionID %s",getSessionIdFromMessageHeaders(event)));
    }

    /**
     * Handles WebSocket disconnection events
     */
    @EventListener(SessionDisconnectEvent.class)
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
        //log.info(String.format("WebSocket connection closed for sessionID %s",getSessionIdFromMessageHeaders(event)));
    }

    public void sendMessages() {
        for (String userName : userNames) {
            simpMessagingTemplate.convertAndSendToUser(userName, WS_MESSAGE_TRANSFER_DESTINATION,
                    new UserResponse(userName + " at " + new Date().toString())
            );
            System.out.println(userName);
        }
    }

    public void notifyPayment(String statusCode, String statusMsg, SwitchBean switchBean) {
        simpMessagingTemplate.convertAndSendToUser(switchBean.getReferenceId(), WS_MESSAGE_TRANSFER_DESTINATION,
//                new UserResponse("Payment Received to : " + refID + " at " + new Date().toString())
                new UserResponse(statusMsg,statusCode,switchBean)
        );
    }

    public void notifyPaymentTimeOut(String statusCode, String statusMsg, RequestBean requestBean) {
        simpMessagingTemplate.convertAndSendToUser(requestBean.getReferenceNo(), WS_MESSAGE_TRANSFER_DESTINATION,
                new UserResponse(statusMsg,statusCode,requestBean)
        );
    }

    public void addUserName(String username) {
        userNames.add(username);
    }

    private String getSessionIdFromMessageHeaders(SessionDisconnectEvent event) {
        Map<String, Object> headers = event.getMessage().getHeaders();
        return Objects.requireNonNull(headers.get(SIMP_SESSION_ID)).toString();
    }

    private String getSessionIdFromMessageHeaders(SessionConnectEvent event) {
        Map<String, Object> headers = event.getMessage().getHeaders();
        return Objects.requireNonNull(headers.get(SIMP_SESSION_ID)).toString();
    }

}
