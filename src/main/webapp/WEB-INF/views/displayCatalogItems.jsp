<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="generator" content="Hugo 0.88.1">
    <title>Browse : CatalogItems</title>
    <link rel="stylesheet" href='<c:url value="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" />'>
    <link rel="stylesheet" href='<c:url value="/webjars/font-awesome/5.15.4/css/font-awesome.min.css" />'>
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
        img {
          width:304px;
          height:304px;
        }
    </style>
</head>

<body>
    <main>
        <div class="container py-4">
              <header class="pb-3 mb-4 border-bottom">
                <a href="/" class="d-flex align-items-center text-dark text-decoration-none">
                 <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"/></svg>
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


    <div class="row">
    <c:forEach items="${catalogItemList}" var="catalogItem" >
       <div class="col-sm-3">
          <div class="card mb-3  mt-3" >
            <img src="/catalogitems/image/<c:out value='${catalogItem.pictureUrl}'/>">
             <div class="card-body">
              <h5 class="card-title"><c:out value="${catalogItem.name}" /></h5>
              <p class="card-text"><c:out value="${catalogItem.description}" /></p>

              <div style="display:flex; align-items:center; justify-content: space-between;">
                <span>
                  From <c:out value="${catalogItem.displayPrice}" />
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

    </div>

    <br/>
    </main>
</body>
</html>