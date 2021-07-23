<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url value="/removeEmpresa" var="urlRemove"></c:url>
<c:url value="/mostraEmpresa" var="urlEdita"></c:url>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista empresas</title>
</head>
<body>
	Empresas cadastradas:
	<ul>
		<c:forEach items="${empresas}" var="empresa">
			<li>
				${empresa.nome} - <fmt:formatDate value="${empresa.dataAbertura}" pattern="dd/MM/yyyy"/>
				<a href="${urlEdita}?id=${empresa.id}">editar</a>
				<a href="${urlRemove}?id=${empresa.id}">remover</a>
			</li>
		</c:forEach>
	</ul>
</body>
</html>