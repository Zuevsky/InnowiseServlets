<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Assortment</title>
</head>
<body>
<div align="center">
<h1 align="center">Hello <c:out value="${username}" />!</h1>
${shoppingCart}
<p align="center">Make your order</p>
<form name="selector" method="post" action="/assortment" id="selectForm" align="center">
${selectForProduct}
<input type="submit" name="addItem" value="Add Item" form="selectForm" align="center">
</form>
${submitButton}
</div>
</body>
</html>
