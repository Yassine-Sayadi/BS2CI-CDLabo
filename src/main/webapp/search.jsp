<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="normalize.css">
    <link rel="stylesheet" href="main.css">
    <title>Search</title>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="page" value="search"/>
</jsp:include>
<main>
    <article>
        <h2>Search</h2>
        <form method="GET" action="Controller">
            <input type="text" name="command" value="get_search" hidden>
            <input type="text" name="query">
            <input type="submit" value="Search">
        </form>

        <div class="search-result-container">

        <c:if test="${param.query != null && result != null}">
                <c:forEach var="smoker" items="${result}">
                    <div class="card">
                        <div class="card-content">
                            <div class="card-header">
                                <h2><c:out value="${smoker.fName}"/> <c:out value="${smoker.lName}"/></h2>
                            </div>
                            <div class="card-body">
                                <div class="card-body-col">
                                    <p class="card-body-paragraph">Date of quitting</p>
                                    <c:out value="${smoker.startDate.toString()}"/>
                                </div>
                                <div class="card-body-col">
                                    <p class="card-body-paragraph">Days elapsed</p>
                                    <c:out value="${smoker.calculateDaysElapsed()}"/>
                                </div>
                            </div>
                            <div class="card-footer">
                                <a href="Controller?command=get_confirm_delete&id=${smoker.id}">
                                    <input class="card-delete-button" type="Button" value="Delete">
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
        </c:if>

        <c:if test="${param.query != null && result == null}">
            <div id="error-banner">
                <p>No results found.</p>
            </div>
        </c:if>
        </div>
    </article>
</main>
<footer>
    <p>Webontwikkeling 2 &copy; 2021-2022 Yassine Sayadi</p>
    <p>
        To see your local activity, click <a href="Controller?command=get_logbook">here</a>.
    </p>
</footer>
</body>
</html>