<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Home - Student Info System</title></head>
<body>
<h1>Welcome to Student Information System</h1>
<p>Total Students: ${totalStudents}</p>
<p>Total Scores: ${totalScores}</p>

<h2>Students and Scores</h2>
<c:forEach var="student" items="${students}">
    <p>${student.fullName} - ${student.address}</p>
</c:forEach>
</body>
</html>
