<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Add Score</title>
</head>
<body>
<h2>Insert Score for Student</h2>
<form action="score" method="post">
  <label>Student ID:</label><input type="text" name="studentId"/><br/>
  <label>Subject ID:</label><input type="text" name="subjectId"/><br/>
  <label>Score 1:</label><input type="text" name="score1"/><br/>
  <label>Score 2:</label><input type="text" name="score2"/><br/>
  <input type="submit" value="Save Score"/>
</form>
</body>
</html>