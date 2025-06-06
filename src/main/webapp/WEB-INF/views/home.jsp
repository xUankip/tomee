<%--
  Created by IntelliJ IDEA.
  User: truongnx
  Date: 06/06/2025
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Score List</title>
</head>
<body>
<h2>Student Score Information</h2>
<table border="1">
    <tr>
        <th>Student Code</th>
        <th>Full Name</th>
        <th>Address</th>
        <th>Subject</th>
        <th>Score 1</th>
        <th>Score 2</th>
        <th>Grade</th>
    </tr>
    <c:forEach var="item" items="${scoreList}">
        <tr>
            <td>${item.studentCode}</td>
            <td>${item.fullName}</td>
            <td>${item.address}</td>
            <td>${item.subjectName}</td>
            <td>${item.score1}</td>
            <td>${item.score2}</td>
            <td>${item.grade}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
