<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Add/Edit Student</title></head>
<body>
<h2>${student != null ? "Edit" : "Add"} Student</h2>

<form method="post" action="?action=${student != null ? "update" : "save"}">
  <c:if test="${student != null}">
    <input type="hidden" name="id" value="${student.studentId}" />
  </c:if>
  Code: <input type="text" name="studentCode" value="${student.studentCode}" /><br/>
  Name: <input type="text" name="fullName" value="${student.fullName}" /><br/>
  Address: <input type="text" name="address" value="${student.address}" /><br/>
  <input type="submit" value="Save" />
</form>
</body>
</html>
