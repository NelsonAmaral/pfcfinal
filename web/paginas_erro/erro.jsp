<%-- 
    Document   : erro
    Created on : 01/11/2018, 20:36:03
    Author     : nelson_amaral
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erro Page</title>
    </head>
    <body>
        <h1>Erro!</h1>
        
        <%= ((Exception)request.getAttribute("erro")).getMessage()%>
    </body>
</html>
