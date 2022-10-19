<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="normalize.css">
    <link rel="stylesheet" href="main.css">
    <title>Overview</title>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="page" value="overview"/>
</jsp:include>
<main>
    <article>
        <h2>Overview</h2>

        <div id="overview-container">
            <c:forEach var="smoker" items="${smokerDbObject.smokers}">
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
            <div class="card add-card">
                <a href="Controller?command=get_add">
                    <div class="add-button">
                        <span>+</span>
                    </div>
                </a>
            </div>
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