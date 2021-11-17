<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>Show CatalogBrands</title>

</head>
<body>
<h1>CatalogBrand</h1>
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}" />

<c:if test="${fn:contains(requestPath, '/catalogbrands/show/')}">
    <h2>Showing CatalogBrand</h2>
    <div>
        <div><c:out value="${catalogbrand.id}" /></div>
        <div>
            <span><c:out value="${catalogbrand.brand}" /></span>
        </div>
    </div>
</c:if>

<c:if test="${fn:contains(requestPath, '/catalogbrands/edit/')}">
    <h2>Editing CatalogBrand</h2>
    <form action="/catalogbrands/save" method="post">
        <input value="${catalogbrand.id}" type="hidden" name="id" />
        Brand Name : <input value="${catalogbrand.brand}" type="text" name="brand" /><br />
        <input type="submit" value="save" />
    </form>
</c:if>

</body>
</html>

