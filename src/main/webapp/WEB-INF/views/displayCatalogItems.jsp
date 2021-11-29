<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page session="true" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="generator" content="Hugo 0.88.1">
    <title>Browse : CatalogItems</title>
    <link rel="stylesheet" href='<c:url value="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" />'>
    <link rel="stylesheet" href='<c:url value="/webjars/font-awesome/5.15.4/css/all.css" />'>

    <style>
        .row {
            display: flex;
            flex-wrap: wrap;
        }

        .row > div[class*='col-'] {
            display: flex;
        }

        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }

        .item-img {
            width: 304px;
            height: 304px;
        }
    </style>

</head>
<script type="text/javascript">
  window.onload = (event) => {
    const urlParams = new URLSearchParams(window.location.search);
    const categoryTypeId = urlParams.get('categoryTypeId');
    if (categoryTypeId) {
      document.getElementById("comboBoxCatalogType").value = categoryTypeId;
    }

    const catalogBrandId = urlParams.get('catalogBrandId');
    if (catalogBrandId) {
      document.getElementById("comboBoxCatalogType").value = categoryTypeId;
    }
  };

  function changeLocation() {
    const cboCatalogBrand = document.getElementById("comboBoxCatalogBrand");
    const catalogBrandId = cboCatalogBrand.options[cboCatalogBrand.selectedIndex].value;

    const cboCatalogType = document.getElementById("comboBoxCatalogType");
    const categoryTypeId = cboCatalogType.options[cboCatalogType.selectedIndex].value;
    let url = "/catalogitems/display";
    if (catalogBrandId !== "") {
      url += "?catalogBrandId=" + catalogBrandId;
    }
    if (categoryTypeId !== "") {
      url = !url.includes("?") ? url + "?categoryTypeId=" : url + "&categoryTypeId=";
      url += categoryTypeId;
    }
    console.log(url);
    window.location.href = url;
  }
</script>

<body>
<div class="container py-4">
    <header class="pb-3 mb-4 border-bottom">
        <a href="/" class="d-flex align-items-center text-dark text-decoration-none">
            <svg class="bi me-2" width="40" height="32">
                <use xlink:href="#bootstrap" />
            </svg>
            <span style="color: black; padding-right: 5px;">
                  <i class="fas fa-glasses fa-2x"></i>
                </span>
            <span class="fs-4">Geekshop</span>
        </a>
    </header>

    <div class="p-5 mb-4 bg-light rounded-3">
        <div class="container-fluid py-5">
            <h1 class="display-5 fw-bold">A one stop for all geek items</h1>
            <p class="col-md-8 fs-4">Geekshop is a definite place to find all the trendiest merchandise with different techologies.</p>
            <button class="btn btn-primary btn-lg" type="button">20% Off all Spring items!</button>
        </div>
    </div>

    <div style="display: flex; justify-content: flex-start; align-items:flex-end;">
        <div style="padding-right: 50px;">
            Catalog Brand
            <div class="dropdown">
                <select class="combobox" id="comboBoxCatalogBrand">
                    <option value="">All</option>
                    <c:forEach items="${catalogBrandList}" var="catalogBrand">
                        <option value="<c:out value='${catalogBrand.id}'/>"><c:out value='${catalogBrand.brand}' /></option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div style="padding-right: 50px;">
            Catalog Type
            <div class="dropdown">
                <select class="combobox" id="comboBoxCatalogType">
                    <option value="">All</option>
                    <c:forEach items="${catalogTypeList}" var="catalogType">
                        <option value="<c:out value='${catalogType.id}'/>"><c:out value='${catalogType.type}' /></option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div>
            <button type="button" class="btn btn-outline-dark" onclick="changeLocation()">Search
            </button>
        </div>
    </div>

    <div class="row">
        <div class="mt-3">
            <c:if test="${empty catalogItemList}">
                <div>
                    <img src="<c:url value='/images/No-Results-Found.png'/>">
                </div>
            </c:if>
        </div>

        <c:if test="${!empty catalogItemList}">
            <c:forEach items="${catalogItemList}" var="catalogItem">
                <div class="col-sm-3">
                    <div class="card mb-3 mt-3">
                        <img class="item-img" src="/catalogitems/image/<c:out value='${catalogItem.pictureUrl}'/>">
                        <div class="card-body">
                            <h5 class="card-title"><c:out value="${catalogItem.name}" /></h5>
                            <p class="card-text"><c:out value="${catalogItem.description}" /></p>

                            <div style="display:flex; align-items:center; justify-content: space-between;">
                            <span>
                              From $<c:out value="${catalogItem.displayPrice}" />
                            </span>

                                <button type="button" class="btn btn-sm btn-outline-light">
                                <span style="color:black; float: right; display: inline-block; " class="fa-stack fa-lg">
                                  <i style="color:#f2f2f6;" class="fa fa-circle-thin fa-stack-2x"></i>
                                  <i class="fa fa-cart-arrow-down fa-stack-1x"></i>
                                </span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <br />
</div>
</body>
</html>