package com.ssafy.algoarium.WebSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller  // 이 클래스가 Spring의 Controller 클래스임을 선언합니다.
public class UrlController {

    // 클라이언트가 "/send/url" 경로로 메시지를 보내면 이 메서드가 호출됩니다.
    @MessageMapping("/send/url/{channelId}")
    // 이 메서드의 반환값은 "/topic/url" 경로에 publish 됩니다. 그러면 해당 topic을 구독하고 있는 클라이언트들이 그 값을 받게 됩니다.
    @SendTo("/topic/url/{channelId}")
    public Url url(UrlMessage message) throws Exception {
        System.out.println(message);

        // HtmlUtils.htmlEscape()는 HTML 태그를 안전하게 escape하여 XSS 공격을 방지합니다.
        return new Url(HtmlUtils.htmlEscape(message.getUrl()));  // escape 처리된 URL 객체를 생성하여 반환합니다.
    }
}
