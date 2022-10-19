<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="normalize.css">
    <link rel="stylesheet" href="main.css">
    <title>Quit Smoking!</title>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="page" value="logbook"/>
</jsp:include>
<main>
    <article>
        <h2>Logs</h2>
        <ul class="log-container">
            <c:forEach items="${log}" var="action">
                <li>
                    <div class="log-item">
                        <c:out value="${action}"></c:out>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <p>
            <a href="Controller?command=clear_logbook">
                <input type="button" value="Clear">
            </a>
        </p>
    </article>
</main>
<footer>
    <p>Webontwikkeling 2 &copy; 2021-2022 Yassine Sayadi</p>
</footer>
</body>
</html>