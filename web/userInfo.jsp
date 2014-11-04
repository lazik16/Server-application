<%-- 
    Document   : userInfo
    Created on : 28.10.2014, 14:00:05
    Author     : Dominik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User information</title>
    </head>
    <form action="./UserServlet" method="POST">
        <body>
            <h1>User information</h1>
            <table>
                <tr>
                    <td>Student ID</td>
                    <td><input type="text" name="userId" value="${user.idUser}"/></td>
                </tr>
                <tr>
                    <td>Username</td>
                    <td><input type="text" name="username" value="${user.userName}"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" name="action" value="Add"/>
                        <input type="submit" name="action" value="Edit"/>
                        <input type="submit" name="action" value="Delete"/>
                        <input type="submit" name="action" value="Search"/>
                    </td>
                </tr>
            </table>
    </form>
                
    <br>
    <table border="1">
        <thead> ID </thead>
        <thead> Username </thead>
        <c:forEach items="${allUsers}" var="user">
            <tr>
                <td>${user.idUser}</td>
                <td>${user.userName}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
