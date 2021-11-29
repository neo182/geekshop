<%@page session="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Catalog Item</title>
</head>

<body>
<h1>Add New Catalog Item</h1>
<form:form action="/catalogitems/save" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td><form:label path="name">Item Name</form:label></td>
            <td><form:input path="name" /></td>
        </tr>
        <tr>
            <td><form:label path="description">Description</form:label></td>
            <td><form:textarea path="description" rows="5" cols="30" /></td>
        </tr>
        <tr>
            <td><form:label path="displayPrice">Display Price</form:label></td>
            <td><form:input path="displayPrice" /></td>
        </tr>
        <tr>
            <td><form:label path="catalogType">Catalog Type</form:label></td>
            <td>
                <form:select path="catalogType"
                        items="${catalogTypeList}" itemValue="id" itemLabel="type" />
            </td>
        </tr>
        <tr>
            <td><form:label path="catalogBrand">Catalog Brand</form:label></td>
            <td>
                <form:select path="catalogBrand"
                        items="${catalogBrandList}" itemValue="id" itemLabel="brand" />
            </td>
        </tr>
        <tr>
            <td><form:label path="availableStock">Display Price</form:label></td>
            <td><form:input path="availableStock" value="10" /></td>
        </tr>
        <tr>
            <td><form:label path="restockThreshold">Display Price</form:label></td>
            <td><form:input path="restockThreshold" value="10" /></td>
        </tr>
        <tr>
            <td><form:label path="maxStockThreshold">Display Price</form:label></td>
            <td><form:input path="maxStockThreshold" value="10" /></td>
        </tr>
        <tr>
            <td><form:label path="picture">Item Picture</form:label></td>
            <td><input type="file" name="picture" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Submit" />
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>