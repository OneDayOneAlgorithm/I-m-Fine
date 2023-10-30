package com.ssafy.algoarium.WebSocket;

// UrlMessage 클래스는 URL 메시지를 담기 위한 클래스입니다.
public class UrlMessage {

    // URL의 내용을 저장하는 멤버 변수입니다.
    private String url;

    // url 멤버 변수의 값을 반환하는 getter 메서드입니다.
    // 이 메서드를 통해 private로 선언된 url의 값을 외부에서 읽을 수 있습니다.
    public String getUrl() {
        return url;
    }

    // url 멤버 변수에 값을 설정하는 setter 메서드입니다.
    // 이 메서드를 통해 외부에서 private로 선언된 url에 값을 할당할 수 있습니다.
    public void setUrl(String url) {
        this.url = url;
    }
}
