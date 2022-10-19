<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="normalize.css">
    <link rel="stylesheet" href="main.css">
    <title>Add</title>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="page" value="add"/>
</jsp:include>
<main>
    <article>
        <h2>Add your date of quitting here!</h2>

        <c:if test="${errors != null}">
            <div id="error-banner">
                <p>You entered the wrong format of input... try again.</p>
                <ul>
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>

        </c:if>

        <form method="POST" action="Controller">
            <input type="text" name="command" value="add" hidden>
            <div>
                <label for="firstName">First name: </label>
                <input class="${firstnameHasErrors == true ? "input-error" : ""}" type="text" name="firstname" value="${firstnamePreviousValue}" maxlength="20" required id="firstName" placeholder="Enter your first name here...">
            </div>
            <div>
                <label for="lastName">Last name: </label>
                <input class="${lastnameHasErrors == true ? "input-error" : ""}" type="text" name="lastname" value="${lastnamePreviousValue}" maxlength="20" required id="lastName" placeholder="Enter your last name here...">
            </div>
            <div id="date-of-quitting-container">
                    <label for="date">Date of quitting: </label>
                    <input class="${startdateHasErrors == true ? "input-error" : ""}" type="date" name="startdate" value="${startdatePreviousValue}" required id="date" pattern="\d{4}-\d{2}-\d{2}" placeholder="yyyy-mm-dd">
            </div>
            <input type="submit">
        </form>
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
