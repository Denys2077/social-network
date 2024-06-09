<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.util.Base64"%>
<%
    String fileId = request.getParameter("fileId");
    byte[] array = (byte[]) request.getSession().getAttribute(fileId);
    String str = new String(array);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Content</title>
</head>
<body>
    <p><%= str %></p>
</body>
</html>