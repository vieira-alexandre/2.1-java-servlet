<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Empresa criada</title>
</head>
<body>
	<c:if test="${not empty nomeEmpresa }">
		${nomeEmpresa} cadastrada com sucesso.
	</c:if>

	<c:if test="${empty nomeEmpresa }">
		Dados inválidos.
	</c:if>
</body>
</html>