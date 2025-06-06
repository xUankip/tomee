<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Add/Edit Score</title></head>
<body>
<h2>${score != null ? "Edit" : "Add"} Score</h2>

<form method="post" action="?action=${score != null ? "update" : "save"}">
  <c:if test="${score != null}">
    <input type="hidden" name="id" value="${score.studentScoreId}" />
  </c:if>

  Student:
  <select name="studentId">
    <c:forEach var="s" items="${students}">
      <option value="${s.studentId}"
              <c:if test="${score != null && score.student.studentId == s.studentId}">selected</c:if>>
          ${s.fullName}
      </option>
    </c:forEach>
  </select><br/>

  Subject:
  <select name="subjectId">
    <c:forEach var="subj" items="${subjects}">
      <option value="${subj.subjectId}"
              <c:if test="${score != null && score.subject.subjectId == subj.subjectId}">selected</c:if>>
          ${subj.subjectName}
      </option>
    </c:forEach>
  </select><br/>

  Score1: <input type="text" name="score1" value="${score != null ? score.score1 : score1}" /><br/>
  Score2: <input type="text" name="score2" value="${score != null ? score.score2 : score2}" /><br/>
  <input type="submit" value="Save" />
</form>
</body>
</html>
