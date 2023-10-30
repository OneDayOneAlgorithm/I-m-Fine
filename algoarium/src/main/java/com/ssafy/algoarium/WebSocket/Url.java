package com.ssafy.algoarium.WebSocket;

// Url 클래스는 URL 정보를 담기 위한 클래스입니다.
public class Url {
    // URL의 내용을 저장하는 멤버 변수입니다.
    private String content;

    // 기본 생성자입니다. 객체를 생성할 때 특별한 초기화 없이 사용됩니다.
    public Url() {
    }

    // 매개변수가 있는 생성자입니다. 객체를 생성하면서 동시에 content 멤버 변수의 값을 초기화합니다.
    public Url(String content) {
        this.content = content;
    }

    // content 멤버 변수의 값을 반환하는 getter 메서드입니다.
    // 이 메서드를 통해 private로 선언된 content의 값을 외부에서 읽을 수 있습니다.
    public String getContent() {
        return content;
    }
}
