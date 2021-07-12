<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List, br.com.zup.gerenciador.model.Empresa"   %>
    
<%
List<Empresa> empresas =(List<Empresa>) request.getAttribute("empresas");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista empresas</title>
</head>
<body>
	<ul>
		<h3>Empresas cadastradas:</h3>
		<% 	for (Empresa empresa : empresas) { %>
			<li> <%= empresa.getNome() %> </li>
		<% } %>	
	</ul>
</body>
</html>