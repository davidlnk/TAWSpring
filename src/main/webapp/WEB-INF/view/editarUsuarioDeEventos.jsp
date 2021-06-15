<%-- 
    Document   : editarUsuarioDeEventos
    Created on : 06-may-2021, 19:39:33
    Author     : Ivan
--%>

<%@page import="es.taw.sampletaw.dto.UsuarioDeEventosDTO"%>
<%@page import="es.taw.sampletaw.dto.UsuarioDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <link rel="stylesheet" href="crearEditarUsuarioDeEventosCSS.css">
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
        UsuarioDeEventosDTO usuarioEventoEditar = (UsuarioDeEventosDTO)request.getAttribute("usuarioEventoEditar");
        String strError = (String)request.getAttribute("errorRegistro");
            
            
            String strNick = usuarioEditar.getNickname();
            String strContrasena = usuarioEditar.getContrasena();
            String strTipoUsuario = usuarioEditar.getTipoUsuario();
            String strId = usuarioEditar.getId().toString();
            String strCorreo = usuarioEventoEditar.getCorreo();
            String strNombre = usuarioEventoEditar.getNombre();
            String strApellidos = usuarioEventoEditar.getApellidos();
            String strCiudad = usuarioEventoEditar.getCiudad();
            String strSexo = usuarioEventoEditar.getSexo();
            String strFecha = new SimpleDateFormat("yyyy-MM-dd").format(usuarioEventoEditar.getFechaNacimiento());
        if (strError == null) strError = "";
    %>
    <%   
        String strErrorNick = (String)request.getAttribute("errorNick");
        String strErrorCorreo = (String)request.getAttribute("errorCorreo");
        String strErrorFormato = (String)request.getAttribute("errorFormato");
        String strErrorConfirmar = (String)request.getAttribute("errorConfirmar");
        String strErrorFecha = (String)request.getAttribute("errorFecha");
        if (strErrorNick == null) strErrorNick = "";
        if (strErrorCorreo == null) strErrorCorreo = "";
        if (strErrorFormato == null) strErrorFormato = "";
        if (strErrorConfirmar == null) strErrorConfirmar = "";
        if (strErrorFecha == null) strErrorFecha = "";
    %>
    <body>

    <img src="Imagenes/tawevents-logo.png" class="imagen-corporativa">
    <jsp:include page="menu.jsp" />
    <div class="signup-form">
        <form action="ServletUsuarioEventosGuardar">
            <div class="form-group">
                <h2>Datos del usuario</h2>
                <input type="hidden" name="id" value="<%= strId %>" />
                <div class="form-group-1">
                    <b>Nick</b>
                    <input type="text" class="form-control" value="<%= strNick %>" name="nick" maxlength="20" required="required">
                    <p align="center" style="color:#FF0000"><%= strErrorNick %></p>
                </div>
                <div class="form-group-1">
                    <b>Correo electrónico</b>
                    <input type="text" class="form-control" value="<%= strCorreo %>" name="correoElectronico" required="required">
                    <p align="center" style="color:#FF0000"><%= strErrorCorreo %></p>
                    <p align="center" style="color:#FF0000"><%= strErrorFormato %></p>
                </div>
                <div class="form-group-1">
                    <b>Contraseña</b>
                    <input type="password" class="form-control" value="<%= strContrasena %>" name="contrasena" minlength="6" maxlength="30" required="required">
                    <div class="restriccion-contrasena">
                        La contraseña debe tener 6 carácteres como mínimo
                    </div>
                </div>
                <p align="center" style="color:#FF0000"><%= strErrorConfirmar %></p>
                <div class="form-group-1">
                    <b>Confirma la contraseña</b>
                    <input type="password" class="form-control" value="<%= strContrasena %>" name="confirmarContrasena" maxlength="30" required="required">
                    
                </div>
            </div>

            <div class="line-split"></div>

            <div class="form-group">
                <div class="form-group-2">
                    <b>Nombre</b>
                    <input type="text" class="form-control" value="<%= strNombre %>" name="nombre" maxlength="50" required="required">
                </div>
                <div class="form-group-2">
                    <b>Apellidos</b>
                    <input type="text" class="form-control" value="<%= strApellidos %>" name="apellidos" maxlength="50" required="required">
                </div>
                <div class="form-group-2">
                    <b>Ciudad</b>
                    <input type="text" class="form-control" value="<%= strCiudad %>" name="ciudad" maxlength="50" required="required">
                </div>
                <div class="form-group-2">
                    <b>Sexo</b>
                    <br>
                    <select class="combo-box-sexo" name="sexo" required="required">
                        <%
                        if(strSexo.equals("mujer")){
                        %>
                        <option selected value="mujer">Mujer</option>
                        <%
                        }else{
                        %>
                        <option value="mujer">Mujer</option>
                        <%
                        }
                        if(strSexo.equals("hombre")){
                        %>
                        <option selected value="hombre">Hombre</option>
                        <%
                        }else{
                        %>
                        <option value="hombre">Hombre</option>
                        <%
                        }
                        if(strSexo.equals("otro")){
                        %>
                        <option selected value="otro">Otro</option>
                        <%
                        }else{
                        %>
                        <option value="otro">Otro</option>
                        <%
                        }
                        %>
                    </select>
                </div>
                <div class="form-group-2">
                    <b>Fecha de nacimiento</b>
                    <br>
                    <input type="date" class="calendario" value="<%=strFecha%>" name="fechaNacimiento" required="required">
                    <p align="center" style="color:#FF0000"><%= strErrorFecha %></p>
                </div>
            </div>
            <div class="form-submit">
                <button type="submit" class="btn btn-success btn-lg btn-block">Guardar datos</button>
            </div>

        </form>
    </div>
    </body>
</html>
