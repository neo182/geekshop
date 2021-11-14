<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
    <title>CatalogItem</title>
</head>
<body>
<h1>Catalog Item - ${catalogitem.name}</h1>
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<c:if test="${fn:contains(requestPath, '/catalogitems/view/')}">
   <h2>Details</h2>
   <table>
       <tr>
          <td>Item Name</td>
          <td><c:out value="${catalogitem.name}" /></td>
       </tr>
       <tr>
         <td>Description</td>
         <td><c:out value="${catalogitem.description}" /></td>
      </tr>
      <tr>
         <td>Catalog Type</td>
         <td><c:out value="${catalogitem.catalogType}" /></td>
      </tr>
      <tr>
           <td>Catalog Brand</td>
           <td><c:out value="${catalogitem.catalogBrand}" /></td>
        </tr>
      <tr>
         <td>Display Price</td>
         <td><c:out value="${catalogitem.displayPrice}" /></td>
      </tr>
      <tr>
        <td>Available Stock</td>
        <td><c:out value="${catalogitem.availableStock}" /></td>
     </tr>
     <tr>
        <td>Restock Threshold</td>
        <td><c:out value="${catalogitem.restockThreshold}" /></td>
     </tr>
      <tr>
       <td>MaxStock Threshold</td>
       <td><c:out value="${catalogitem.maxStockThreshold}" /></td>
    </tr>
    </table>
</c:if>

<c:if test="${fn:contains(requestPath, '/catalogitems/edit/')}">
    <h2>Editing ${catalogitem.name}</h2>
     <form:form action="/catalogitems/save" method="post">
        <form:hidden path = "id" value="${catalogitem.id}" />
        <table>
          <tr>
             <td><form:label path = "name">Item Name</form:label></td>
             <td><form:input path = "name" value="${catalogitem.name}" /></td>
          </tr>
          <tr>
             <td><form:label path = "description">Description</form:label></td>
             <td><form:textarea path = "description" rows = "5" cols = "30" value="${catalogitem.description}" /></td>
          </tr>
          <tr>
             <td><form:label path = "displayPrice">Display Price</form:label></td>
             <td><form:input path = "displayPrice" value="${catalogitem.displayPrice}" /></td>
          </tr>
          <tr>
             <td><form:label path = "catalogType">Catalog Type</form:label></td>
             <td>
                  <form:select path="catalogType"
                  items="${catalogTypeList}" itemValue="id" itemLabel="type" />
             </td>
          </tr>
          <tr>
             <td><form:label path = "catalogBrand">Catalog Brand</form:label></td>
             <td>
                <form:select path="catalogBrand"
                 items="${catalogBrandList}" itemValue="id" itemLabel="brand" />
            </td>
          </tr>
          <tr>
             <td><form:label path = "availableStock">Available Stock</form:label></td>
             <td><form:input path = "availableStock" value="${catalogitem.availableStock}" /></td>
          </tr>
          <tr>
             <td><form:label path = "restockThreshold">Restock Threshold</form:label></td>
             <td><form:input path = "restockThreshold" value="${catalogitem.restockThreshold}"/></td>
          </tr>
           <tr>
             <td><form:label path = "maxStockThreshold">Max Stock Threshold</form:label></td>
             <td><form:input path = "maxStockThreshold" value="${catalogitem.maxStockThreshold}"/></td>
          </tr>

          <tr>
             <td colspan = "2">
                <input type = "submit" value = "Submit"/>
             </td>
          </tr>
       </table>
   </form:form>
</c:if>