<%@page import="java.util.List"%>
<%@page import="com.student.informationsystem.entity.Student"%>

<html>
<head>
    <title>Danh sách sinh viên</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Danh sách sinh viên</h2>
    <table class="table">
        <thead>
        <tr>
            <th>Mã SV</th>
            <th>Họ Tên</th>
            <th>Địa chỉ</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${students}" var="student">
            <tr>
                <td>${student.studentCode}</td>
                <td>${student.fullName}</td>
                <td>${student.address}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
