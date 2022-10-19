<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <nav>
        <h1 class="big-title">Quit Smoking - Keep Track!</h1>
        <ul>
            <li <c:if test="${param.page == 'home'}"> class="current-page"</c:if>><a href="Controller?command=get_home">Home</a></li>
            <li <c:if test="${param.page == 'overview'}"> class="current-page"</c:if>><a href="Controller?command=get_overview">Overview</a></li>
            <li <c:if test="${param.page == 'add'}"> class="current-page"</c:if>><a href="Controller?command=get_add">Add</a></li>
            <li <c:if test="${param.page == 'search'}"> class="current-page"</c:if>><a href="Controller?command=get_search">Search</a></li>
        </ul>
    </nav>

</header>