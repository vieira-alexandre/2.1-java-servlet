<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String nomeEmpresa = (String) request.getAttribute("nome");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Empresa criada</title>
</head>
<body>
<%= nomeEmpresa  + " cadastrada com sucesso." %>
</body>
</html>