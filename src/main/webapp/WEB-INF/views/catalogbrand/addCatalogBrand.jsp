<%@ page session="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add New Catalog Brand</title>
</head>

<body>
<h1>Add New Catalog Brand</h1>
<form action="/catalogbrands/save" method="post">
    Brand Name : <input value="" type="text" name="brand" /><br />
    <input type="submit" value="save" />
</form>
</body>
</html>