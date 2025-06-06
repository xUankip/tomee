<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Add Student</title>
</head>
<body>
<h2>Insert Student</h2>
<form action="student" method="post">
  <label>Student Code:</label><input type="text" name="studentCode"/><br/>
  <label>Full Name:</label><input type="text" name="fullName"/><br/>
  <label>Address:</label><input type="text" name="address"/><br/>
  <input type="submit" value="Save Student"/>
</form>
</body>
</html>