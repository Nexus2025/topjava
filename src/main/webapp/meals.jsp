<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h1>Meals</h1>
    <p><a href="meals?action=create">Add Meal</a></p>
    <table border="1" cellpadding="5">
        <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th colspan="2">Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${meals}" var="meal">
                <tr <c:out value="${meal.isExcess() ?  'style=\"color: red\"' : 'style=\"color: green\"'}" escapeXml="false"/>>
                    <fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                    <th><fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${parsedDateTime}"/></th>
                    <th><c:out value="${meal.getDescription()}" /></th>
                    <th><c:out value="${meal.getCalories()}" /></th>
                    <td><a href="meals?action=update&mealId=<c:out value="${meal.getId()}"/>">Update</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${meal.getId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
