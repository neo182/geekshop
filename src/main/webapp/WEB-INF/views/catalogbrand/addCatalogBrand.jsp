<%@ page session="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
  <head>
    <title>Spittr</title>

  </head>
  <body>
    <h1>Add New Catalog Brand</h1>
    <form action="/catalogbrands/save" method="post">
      Brand Name : <input value="" type="text" name="brand" /><br/>
      <input type="submit" value="save" />
    </form>
  </body>
</html>