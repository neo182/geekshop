<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title>CatalogBrands</title>
</head>
<body>
    <h1>Listing of all Catalog Brands</h1>

    <c:forEach items="${catalogBrandList}" var="brand" >
      <li>
          <c:out value="${brand.brand}" />
          <a href="/catalogbrands/show/<c:url value="${brand.id}" />">Details</a>
          <a href="/catalogbrands/edit/<c:url value="${brand.id}" />">Edit</a>
          <a href="/catalogbrands/delete/<c:url value="${brand.id}" />">Delete</a>
      </li>
    </c:forEach>

    <br/>
    <a href="/catalogbrands/<c:url value="add" />">Add New</a>
</body>
</html>