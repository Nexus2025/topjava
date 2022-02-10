<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create/Update Meal</title>
</head>
<body>
    <h1>Edit Meal</h1>
    <br>
    <c:set var="meal" value='${requestScope["meal"]}' />
    <form method="post">
        <input type="hidden" name="id" value="${meal.getId()}">
        <p>DateTime: <input type="datetime-local" name="dateTime" value="${not empty meal ?  meal.getDateTime(): ''}"></p>
        <p>Description: <input name="description" value="${not empty meal ?  meal.getDescription(): ''}"></p>
        <p>Calories: <input type="number" name="calories" value="${not empty meal ?  meal.getCalories(): ''}"></p>
        <p><button type="submit">Save</button>
        <button type="button" onclick="location.href='meals';">Cancel</button></p>
    </form>
</body>
</html>
