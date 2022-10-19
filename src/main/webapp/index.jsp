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
    <jsp:param name="page" value="home"/>
</jsp:include>
<main>
    <article>
        <h2>Welcome!</h2>
        <p>
            Welcome to this little web app to keep an overview of your streak without cigarettes. Add your date of quitting to the overview or check how well others are doing.
            Had a slip up or decided to take on the pesky habit once more? No problem! Got to the overview or search your entry and delete it.
        </p>
        <br>
        <p>
            <c:if test="${cookie.hidden != null && cookie.hidden.value == 'false'}">
                On average, people on this app quit for <b>${averageDaysElapsed}</b> days.
            </c:if>
            <br>
            Click <a href="Controller?command=toggle_hidden">here</a> to show/hide the average elapsed days.
        </p>
        <br>
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