<%-- 
    Document   : analista_home
    Created on : 24-abr-2021, 14:25:54
    Author     : daniel
--%>

<%@page import="tawevents.dto.UsuarioDTO"%>
<%@page import="tawevents.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="analistaCSS.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Analista de Eventos - Home </title>
    </head>
    <%
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        if (usuario == null) {
    %>        
    <jsp:forward page="inicioSesion.jsp" />   
    <%
        }
    %>
    <jsp:forward page="ServletAnalistaListar" />   
    <body>
    </body>
</html>