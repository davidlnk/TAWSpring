<%--
    Document   : inicioSesion
    Created on : 21-abr-2021, 20:51:18
    Author     : Ivan
--%>

<%@page import="es.taw.sampletaw.dto.UsuarioDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio de sesión</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <link rel="stylesheet" href="/css/crearEditarUsuarioCSS.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    </head>
        <%
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        if (usuario != null) {
    %>
    <jsp:forward page="ServletInicioAutenticar" />
    <%  
        }
        String strError = (String)request.getAttribute("error");
        if (strError == null) strError = "";
    %>
    <body>
        
        <img src="/img/tawevents-logo.png" class="imagen-corporativa">
        
        <div class="signup-form">
            
            
            
            <form method="POST" action="/autenticar">
                <h2>Inicio de sesión</h2>
                <p align="center" style="color:#FF0000"><%= strError %> </p>
                    <b>Nick</b>
                <div class="form-group">
                    <input type="text" class="form-control" name="nick" required="required">
                </div>
                    <b>Contraseña</b>
                <div class="form-group">
                    <input type="password" class="form-control" name="contrasena"  required="required">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-success btn-lg btn-block">Iniciar sesión</button>
                </div>
                <a style="text-align:center;display:block;" href="/usuario/registro">Crear cuenta en TAWEvents</a>
            </form>
            
        </div>
    </body>
</html>
