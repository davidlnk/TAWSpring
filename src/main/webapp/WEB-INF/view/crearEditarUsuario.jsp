<%-- 
    Document   : registro
    Created on : 21-abr-2021, 19:00:19
    Author     : Ivan
--%>


<%@page import="es.taw.sampletaw.dto.UsuarioDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro</title>
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
        if (usuario == null) {
    %>        
    <jsp:forward page="inicioSesion.jsp" />
        
    <%
        }
        UsuarioDTO usuarioEditar = (UsuarioDTO)request.getAttribute("usuarioEditar");
        String strNick = "", strContrasena = "", strTipoUsuario = "", strId = "";
        String strError = (String)request.getAttribute("errorRegistro");
        String strErrorContra = (String)request.getAttribute("errorContra");
        String strErrorNick = (String)request.getAttribute("errorNick");
        if(usuarioEditar != null){
            strNick = usuarioEditar.getNickname();
            strContrasena = usuarioEditar.getContrasena();
            strTipoUsuario = usuarioEditar.getTipoUsuario();
            strId = usuarioEditar.getId().toString();
        }
        if (strError == null) strError = "";
        if (strErrorContra == null) strErrorContra = "";
        if (strErrorNick == null) strErrorNick = "";
    %>
    <body>
        <img src="/img/tawevents-logo.png" class="imagen-corporativa">
            
        <jsp:include page="menu.jsp" />
        <div class="signup-form">
            <form action="/admin/guardarusuario">
                <h2>Datos del Usuario</h2>
                <input type="hidden" name="id" value="<%= strId %>" />
                <p align="center" style="color:#FF0000"><%= strError %> </p>
                    <b>Nick</b>
                <div class="form-group">
                    <input type="text" class="form-control" value="<%= strNick %>" name="nick" required="required">
                    <p align="center" style="color:#FF0000"><%= strErrorNick %> </p>
                </div>
                    <b>Contraseña</b>
                <div class="form-group">
                    <input type="password" class="form-control" value="<%= strContrasena %>" name="contrasena" minlength="6" required="required">
                    <div class="restriccion-contrasena">
                        La contraseña debe tener 6 carácteres como mínimo
                        <p align="center" style="color:#FF0000"><%= strErrorContra %> </p>
                    </div>
                </div>
                    <b>Confirma la contraseña</b>
                <div class="form-group">
                    <input type="password" class="form-control" value="<%= strContrasena %>" name="confirmarContrasena" minlength="6" required="required">
                    <div class="restriccion-contrasena">
                    </div>
                </div>
                    <b>Tipo de Usuario</b>
                <div class="form-group">
                    <select class="tipo-usuario" name="tipoUsuario">
                        <%
                            if(strTipoUsuario.equals("administrador")){
                        %>
                        <option selected value="administrador">Administrador</option>
                        <%
                            
                            }else{
                        %>
                        <option value="administrador">Administrador</option>
                        <%
                           }
                        %>
                        <%
                            if(strTipoUsuario.equals("creadordeeventos")){
                        %>
                        <option selected value="creadordeeventos">Creador de Eventos</option>
                        <%
                            
                            }else{
                        %>
                        <option value="creadordeeventos">Creador de Eventos</option>
                        <%
                           }
                        %>
                        <%
                            if(strTipoUsuario.equals("teleoperador")){
                        %>
                        <option selected value="teleoperador">Teleoperador</option>
                        <%
                            
                            }else{
                        %>
                        <option value="teleoperador">Teleoperador</option>
                        <%
                           }
                        %>
                        <%
                            if(strTipoUsuario.equals("analistadeeventos")){
                        %>
                        <option selected value="analistadeeventos">Analista de Eventos</option>
                        <%
                            
                            }else{
                        %>
                        <option value="analistadeeventos">Analista de Eventos</option>
                        <%
                           }
                        %>
                        
                    </select>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-success btn-lg btn-block">Guardar datos</button>
                </div>
            </form>
            
        </div>
    </body>
</html>
