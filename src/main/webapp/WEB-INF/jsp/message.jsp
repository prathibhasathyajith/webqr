<%--
  Created by IntelliJ IDEA.
  User: prathibha_w
  Date: 5/6/2021
  Time: 10:18 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>


    <spring:url value="/css/main.css" var="springCss" />
    <link href="${springCss}" rel="stylesheet" />

    <c:url value="/css/main.css" var="jstlCss" />
    <link href="${jstlCss}" rel="stylesheet" />

</head>
<body>
<h2>Message: ${message}</h2>
</body>

</html>
