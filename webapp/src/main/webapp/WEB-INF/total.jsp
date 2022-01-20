<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Total Order</title>
</head>
<body>
<div align="center">
<h1>Dear <c:out value="${username}" />, your order:</h1>
${chosenProducts}
${totalPrice}
<p>Do you want to make another order?</p>
<form name="anotherOrderForm" method="get" action="/assortment" id="anotherOrder" align="center">
<input type="submit" name="btn" value="Yes" form="anotherOrder" align="center">
</form>
</div>
</body>
</html>