<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Student List</title></head>
<body>
<h1>Student List</h1>
<a href="?action=add">+ Student</a>
<form method="get">
  <input type="text" name="searchName" placeholder="Search by name" value="${searchName}">
  <button type="submit">Search</button>
</form>

<table border="1">
  <tr><th>ID</th><th>Code</th><th>Name</th><th>Address</th><th>Actions</th></tr>
  <c:forEach var="student" items="${students}">
    <tr>
      <td>${student.studentId}</td>
      <td>${student.studentCode}</td>
      <td>${student.fullName}</td>
      <td>${student.address}</td>
      <td>
        <a href="?action=view&id=${student.studentId}">View</a>
        <a href="?action=edit&id=${student.studentId}">Edit</a>
        <a href="?action=delete&id=${student.studentId}">Delete</a>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
