<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CatalogItems</title>
</head>

<body>
<h1>Listing of all Catalog Brands</h1>

<c:forEach items="${catalogItemList}" var="item">
    <li>
        <c:out value="${item.name}" />
        <a href="/catalogitems/view/<c:url value="${item.id}" />">Details</a>
        <a href="/catalogitems/edit/<c:url value="${item.id}" />">Edit</a>
        <a href="/catalogitems/delete/<c:url value="${item.id}" />">Delete</a>
    </li>
</c:forEach>

<br />
<a href="/catalogitems/<c:url value="add" />">Add New Item</a>
</body>
</html>