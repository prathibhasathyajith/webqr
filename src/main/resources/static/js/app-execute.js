let stompClient;
let returnUrl;
const stompWebSocket = (data) => {
    // request params
    const token = btoa('admin:admin');
    // URL for websocket
    let url = "ws://localhost:8080/WEBQR/websocket-qr";

    stompClient = Stomp.client(url);
    stompClient.connect({}, function (frame) {
        data.referenceNo = frame.headers['user-name'];
        let grStringPromise = getQRString(data, token);
        grStringPromise.then((message) => {

            // generate QR from frontend
            $('.content-wrapper').show();
            $('#output').qrcode(message.content.qrString);

            stompClient.subscribe('/user/topic/user', function (response) {
                console.log(JSON.parse(response.body));
                stompClient.disconnect();
                localStorage.setItem("createWebsocket", "");
                localStorage.removeItem("createWebsocket");
                if (JSON.parse(response.body).code === '00') {
                    $('.responseMessage').show();
                    $('.ui-success').show();
                    $('#qr-content-block').hide();
                    $('#success-msg').show();
                    setTimeout(()=>{
                        window.location.replace(JSON.parse(response.body).switchBean.successUrl);
                    },5000);
                } else if (JSON.parse(response.body).code === '03'){
                    $('.responseMessage').show();
                    $('.ui-error').show();
                    $('#qr-content-block').hide();
                    $('#error-msg').show();
                    setTimeout(()=>{
                        window.location.replace(JSON.parse(response.body).requestBean.errorUrl);
                    },5000);
                }

            });
        }).catch((message) => {
            console.error('Exception ------ ' + message);
            localStorage.setItem("createWebsocket", "");
            localStorage.removeItem("createWebsocket");
            stompClient.disconnect();
            $('.responseMessage').show();
            $('.ui-error').show();
            $('#qr-content-block').hide();
            $('#error-msg').show();
            setTimeout(()=>{
                window.location.replace(returnUrl);
            },5000);
        })
    });
}

// const getQRString = async (refId, merchantId, terminalId1, token) => {
const getQRString = async (data, token) => {
    return await fetch('/WEBQR/api/v1/swt/getqrstring', {
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Basic ' + token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(response => {
        if (!response.ok) {
            throw Error(response.statusText);
        } else {
            return response.json();
        }
    }).then((data) => {
        return data;
    }).catch((error) => {
        returnUrl = data.errorUrl;
        return error;
    });
}
let data = {};

data.mid = $('script').last().attr('mid');
data.tid = $('script').last().attr('tid');
data.amount = $('script').last().attr('amount');
data.currency = $('script').last().attr('currency');
data.successUrl = $('script').last().attr('successUrl');
data.errorUrl = $('script').last().attr('errorUrl');
data.returnUrl = $('script').last().attr('returnUrl');

// to handle refresh
let websocket = localStorage.getItem("createWebsocket");

if(!websocket){
    localStorage.setItem("createWebsocket", "boc");
    stompWebSocket(data);
}else{
    localStorage.setItem("createWebsocket", "");
    localStorage.removeItem("createWebsocket");
    alert("Sorry! You will be redirected to the history page")
    window.history.back();
}




