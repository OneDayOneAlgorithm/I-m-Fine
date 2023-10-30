// StompJs.Client 객체를 생성하고 웹소켓 서버의 URL을 설정합니다.
const stompClient = new StompJs.Client({
//    brokerURL: 'ws://192.168.100.169:8080/websocket'
    brokerURL: 'ws://j9d204.p.ssafy.io:8090/websocket'
});

// 현재 채널 ID
let currentChannelId = null;

// 웹소켓이 연결되었을 때 실행될 콜백 함수를 설정합니다.
stompClient.onConnect = (frame) => {
    // 연결 상태를 표시하고
    setConnected(true);
    // 연결 정보를 콘솔에 출력합니다.
    console.log('Connected: ' + frame);

//    // '/topic/url' 주제에 대한 구독을 시작하고,
//    // 메시지가 도착할 때마다 showUrl 함수를 호출하여 메시지 내용을 화면에 표시합니다.
//    stompClient.subscribe('/topic/url', (greeting) => {
//        showUrl(JSON.parse(greeting.body).content);
//    });
};

// 웹소켓 오류가 발생했을 때 실행될 콜백 함수를 설정합니다.
stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

// STOMP 프로토콜 오류가 발생했을 때 실행될 콜백 함수를 설정합니다.
stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

// 연결 상태에 따라 UI 요소의 활성화 상태와 보이기/숨기기 상태를 변경하는 함수입니다.
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
            $("#conversation").show();
            // 기존 구독을 해제하고, 현재 선택된 채널에 대해 구독 시작
            if(currentChannelId !== null){
                stompClient.unsubscribe('/topic/url/' + currentChannelId);
            }
            currentChannelId = $("#channel_id").val();
            stompClient.subscribe('/topic/url/' + currentChannelId, (greeting) => {
                showUrl(JSON.parse(greeting.body).content);
            });

         } else {
             $("#conversation").hide();
         }

         $("#greetings").html("");
    }

// 웹소켓 연결을 시작하는 함수입니다.
function connect() {
    stompClient.activate();
}

// 웹소켓 연결을 종료하는 함수입니다.
function disconnect() {
  if(currentChannelId !== null){
      stompClient.unsubscribe('/topic/url/' + currentChannelId);
  }
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}

// 사용자가 입력한 URL 데이터를 서버로 전송하는 함수입니다.
function sendName() {
   // 선택된 channelId로 메시지 보내기
   stompClient.publish({
       destination: "/app/send/url/" + currentChannelId,
       body: JSON.stringify({'url': $("#url").val()})
   });
}

// 받은 URL 데이터를 화면에 추가하는 함수입니다.
function showUrl(message) {
    $("#urls").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});