<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url value="/editaEmpresa" var="url"></c:url>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar empresa</title>
</head>
<body>
	<form action="${url}" method="post">
		Nome: <input type="text" name="nome" value="${empresa.nome}" />
		Data abertura: <input type="text" name="data" value="<fmt:formatDate value="${empresa.dataAbertura}" pattern="dd/MM/yyyy"/>" />
		<input type="hidden" name="id" value="${empresa.id}" />
		<input type="submit">
	</form>
</body>
</html>