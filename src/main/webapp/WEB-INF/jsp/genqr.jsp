<%--
  Created by IntelliJ IDEA.
  User: prathibha_w
  Date: 5/31/2021
  Time: 12:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>BOC SmartPay - WEB QR</title>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/messages.css">
</head>

<body>
<div class="content-wrapper" style="display: none">
    <div class="header">
        <div class="merchant-logo">
            <%--            <img src="${pageContext.request.contextPath}/images/icta_2.png">--%>
            <div class="title"><span id="merchantName">${merchantData.legalName}</span></div>
        </div>

        <img src="${pageContext.request.contextPath}/images/boc_full.svg" class="bank-logo">
    </div>
    <div class="content">
        <div class="form-container">
            <div class="title">Scan QR</div>
            <div class="qr-content" id="qr-content-block">
                <div class="lanka-qr-logo">
                    <img src="${pageContext.request.contextPath}/images/lankaqr-logo.png" alt="">
                </div>
                <div class="qr-wrapper">
                    <div id="output"></div>
                </div>
                <div class="store-details"><span id="storeName">${merchantData.legalName}</span><br><span
                        id="merchantId">${data.mid}</span></div>
                <div class="scan-instruction">
                    Use any of your JustPay or Lanka QR enabled Mobile App to scan this QR Code
                </div>
            </div>
        </div>

        <div class="responseMessage" style="display: none">
            <div class="ui-success" style="display: none">
                <svg viewBox="0 0 87 87" version="1.1" xmlns="http://www.w3.org/2000/svg">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                        <g id="Group-3" transform="translate(2.000000, 2.000000)">
                            <circle id="Oval-2" stroke="rgba(165, 220, 134, 0.2)" stroke-width="4" cx="41.5"
                                    cy="41.5" r="41.5"></circle>
                            <circle class="ui-success-circle" id="Oval-2" stroke="#A5DC86" stroke-width="4"
                                    cx="41.5" cy="41.5" r="41.5"></circle>
                            <polyline class="ui-success-path" id="Path-2" stroke="#A5DC86" stroke-width="4"
                                      points="19 38.8036813 31.1020744 54.8046875 63.299221 28"></polyline>
                        </g>
                    </g>
                </svg>
            </div>
            <div class="ui-error" style="display: none">
                <svg viewBox="0 0 87 87" version="1.1" xmlns="http://www.w3.org/2000/svg">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                        <g id="Group-2" transform="translate(2.000000, 2.000000)">
                            <circle id="Oval-2" stroke="rgba(252, 191, 191, .5)" stroke-width="4" cx="41.5"
                                    cy="41.5" r="41.5"></circle>
                            <circle class="ui-error-circle" stroke="#F74444" stroke-width="4" cx="41.5"
                                    cy="41.5" r="41.5"></circle>
                            <path class="ui-error-line1" d="M22.244224,22 L60.4279902,60.1837662" id="Line"
                                  stroke="#F74444" stroke-width="3" stroke-linecap="square"></path>
                            <path class="ui-error-line2" d="M60.755776,21 L23.244224,59.8443492" id="Line"
                                  stroke="#F74444" stroke-width="3" stroke-linecap="square"></path>
                        </g>
                    </g>
                </svg>
            </div>
        </div>
        <div class="msg-content" id="success-msg" style="display: none">
            <div class="redirect-details">Transaction Completed<br>
                <span>You will redirected to the merchant site...</span>
            </div>
        </div>
        <div class="msg-content" id="error-msg" style="display: none">
            <div class="redirect-details">Transaction Failed<br>
                <span>You will redirected to the merchant site...</span>
            </div>
        </div>
    </div>
    <div class="footer">
        <img src="${pageContext.request.contextPath}/images/boc_logo.svg" class="bank-logo">
        <img src="${pageContext.request.contextPath}/images/smartpaylogo.svg" alt="" class="epicpay-logo">
    </div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/stomp.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app-execute.js"
        mid="${data.mid}" tid="${data.tid}" amount="${data.amount}"
        currency="${data.currency}" successUrl="${data.successUrl}"
        errorUrl="${data.errorUrl}" returnUrl="${data.returnUrl}"></script>
</html>
