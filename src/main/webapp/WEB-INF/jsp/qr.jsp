<%--
  Created by IntelliJ IDEA.
  User: prathibha_w
  Date: 5/6/2021
  Time: 11:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demo page</title>
</head>
<body>

<div id="output"></div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/stomp.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app-execute.js"
        mid="${data.mid}" tid="${data.tid}" amount="${data.amount}"
        currency="${data.currency}" successUrl="${data.successUrl}"
        errorUrl="${data.errorUrl}" returnUrl="${data.returnUrl}" ></script>
<%--<script >--%>
<%--    const stompWebSocket = (data) => {--%>
<%--        // request params--%>
<%--        const token = btoa('admin:admin');--%>
<%--        // URL for websocket--%>
<%--        let url = "ws://localhost:8080/WEBQR/websocket-qr";--%>

<%--        let stompClient = Stomp.client(url);--%>
<%--        stompClient.connect({}, function (frame) {--%>
<%--            data.referenceNo = frame.headers['user-name'];--%>
<%--            let grStringPromise = getQRString(data, token);--%>
<%--            grStringPromise.then((message) => {--%>

<%--                // generate QR from frontend--%>
<%--                $('#output').qrcode(message.content.qrString);--%>

<%--                stompClient.subscribe('/user/topic/user', function (response) {--%>
<%--                    console.log(JSON.parse(response.body));--%>
<%--                    stompClient.disconnect();--%>
<%--                    if (JSON.parse(response.body).code === '00') {--%>
<%--                        window.location.replace('https://www.google.com/');--%>
<%--                    }--%>

<%--                });--%>
<%--            }).catch((message) => {--%>
<%--                console.error('Exception ' + message);--%>
<%--                stompClient.disconnect();--%>
<%--            })--%>
<%--        });--%>
<%--    }--%>

<%--    // const getQRString = async (refId, merchantId, terminalId1, token) => {--%>
<%--    const getQRString = async (data, token) => {--%>
<%--        console.log(data);--%>
<%--        return await fetch('/WEBQR/api/v1/swt/getqrstring', {--%>
<%--            method: 'post',--%>
<%--            headers: {--%>
<%--                'Accept': 'application/json',--%>
<%--                'Authorization': 'Basic ' + token,--%>
<%--                'Content-Type': 'application/json'--%>
<%--            },--%>
<%--            body: JSON.stringify(data)--%>
<%--        }).then(response => {--%>
<%--            if (!response.ok) {--%>
<%--                throw Error(response.statusText);--%>
<%--            } else {--%>
<%--                return response.json();--%>
<%--            }--%>
<%--        }).then((data) => {--%>
<%--            return data;--%>
<%--        }).catch((error) => {--%>
<%--            return error;--%>
<%--        });--%>
<%--    }--%>
<%--    // let data = {};--%>
<%--    //--%>
<%--    // data.mid = $('script').last().attr('mid');--%>
<%--    // data.tid = $('script').last().attr('tid');--%>
<%--    // data.amount = $('script').last().attr('amount');--%>
<%--    // data.currency = $('script').last().attr('currency');--%>
<%--    // data.successUrl = $('script').last().attr('successUrl');--%>
<%--    // data.errorUrl = $('script').last().attr('errorUrl');--%>
<%--    // data.returnUrl = $('script').last().attr('returnUrl');--%>

<%--    let data = {};--%>

<%--    data.mid = '${data.mid}';--%>
<%--    data.tid = '${data.tid}';--%>
<%--    data.amount = '${data.amount}';--%>
<%--    data.currency = '${data.currency}';--%>
<%--    data.successUrl = '${data.successUrl}';--%>
<%--    data.errorUrl = '${data.errorUrl}';--%>
<%--    data.returnUrl = '${data.returnUrl}';--%>

<%--    console.log(data);--%>
<%--    --%>
<%--    stompWebSocket(data);--%>

<%--</script>--%>
</html>
