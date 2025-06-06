<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Scores</title></head>
<body>
<h1>Scores</h1>
<a href="?action=add">+ Score</a>
<table border="1">
    <tr><th>Student</th><th>Subject</th><th>Score1</th><th>Score2</th><th>Actions</th></tr>
    <c:forEach var="score" items="${scores}">
        <tr>
            <td>${score.student.fullName}</td>
            <td>${score.subject.subjectName}</td>
            <td>${score.score1}</td>
            <td>${score.score2}</td>
            <td>
                <a href="?action=edit&id=${score.studentScoreId}">Edit</a>
                <a href="?action=delete&id=${score.studentScoreId}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
