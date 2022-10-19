<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="normalize.css">
    <link rel="stylesheet" href="main.css">
    <title>Confirm</title>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="page" value=""/>
</jsp:include>
<main>
    <article>
        <h2>Are you sure?</h2>
        <p>Would you like to delete the following item?</p>

        <c:choose>
            <c:when  test="${item == null}">
                <div id="error-banner">
                    <p>You tried to delete an item that doesn't exist.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="card">
                    <div class="card-content">
                        <div class="card-header">
                            <h2><c:out value="${item.fName}"/> <c:out value="${item.lName}"/></h2>
                        </div>
                        <div class="card-body">
                            <div class="card-body-col">
                                <p class="card-body-paragraph">Date of quitting</p>
                                <c:out value="${item.startDate.toString()}"/>
                            </div>
                            <div class="card-body-col">
                                <p class="card-body-paragraph">Days elapsed</p>
                                <c:out value="${item.calculateDaysElapsed()}"/>
                            </div>
                        </div>
                    </div>
                </div>

                <form method="POST" action="Controller">
                    <input type="text" name="command" value="delete" hidden>
                    <input type="text" name="id" value="${item.id}" hidden>
                    <input type="submit" value="Delete item">
                </form>
            </c:otherwise>
        </c:choose>

    </article>
</main>
<footer>
    <p>Webontwikkeling 2 &copy; 2021-2022 Yassine Sayadi</p>
</footer>
</body>
</html>