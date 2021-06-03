package com.boc.webqr.config.app;

import com.boc.webqr.bean.SessionBean;
import com.boc.webqr.bean.StompPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Created by bhanuka_t on 10/22/2020.
 * Set anonymous user (Principal) in WebSocket messages by using UUID
 * This is necessary to avoid broadcasting messages but sending them to specific user sessions
 */
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Autowired
    SessionBean sessionBean;

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        // generate user name by UUID

//        System.out.println(request.getHeaders().get("Sec-WebSocket-Key"));
//        Base64.Decoder ss = Base64.getDecoder();
//        byte[] bytes = ss.decode(request.getHeaders().get("Sec-WebSocket-Key").get(0));
//
//        StringBuilder buffer = new StringBuilder();
//        for(int i=0; i<bytes.length; i++) {
//            buffer.append(String.format("%02x", bytes[i]));
//        }
//
//        System.out.println(buffer.toString());

        return new StompPrincipal(this.generateUID());
    }

    public String generateUID() {
        try {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMsSSSSSSS");
            String datetime = ft.format(dNow);
            Random generator = new Random();
            int num = generator.nextInt(1000);
            if (num >= 0 && num <= 899) {
                num += 100;
            }
            return datetime + num;
        }catch (Exception ex){
            return "12345678901234567890";
        }
    }
}
