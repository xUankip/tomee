<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>View Student</title></head>
<body>
<h2>Student Detail</h2>
<p>Name: ${student.fullName}</p>
<p>Address: ${student.address}</p>

<h3>Scores</h3>
<table border="1">
  <tr><th>Subject</th><th>Score1</th><th>Score2</th><th>Grade</th></tr>
  <c:forEach var="s" items="${studentScores}">
    <tr>
      <td>${s.subject.subjectName}</td>
      <td>${s.score1}</td>
      <td>${s.score2}</td>
      <td>
        <c:set var="grade" value="${0.3 * s.score1 + 0.7 * s.score2}" />
        <c:choose>
          <c:when test="${grade >= 8.0}">A</c:when>
          <c:when test="${grade >= 6.0}">B</c:when>
          <c:when test="${grade >= 4.0}">D</c:when>
          <c:otherwise>F</c:otherwise>
        </c:choose>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
