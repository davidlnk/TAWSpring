<%-- 
    Document   : registro
    Created on : 22-abr-2021, 19:45:07
    Author     : David
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de TAWEVENTS</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
    <link rel="stylesheet" href="crearEditarUsuarioDeEventosCSS.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>

    <%   
        String strErrorNick = (String)request.getAttribute("errorNick");
        String strErrorCorreo = (String)request.getAttribute("errorCorreo");
        String strErrorFormato = (String)request.getAttribute("errorFormato");
        String strErrorConfirmar = (String)request.getAttribute("errorConfirmar");
        String strErrorNacimiento = (String)request.getAttribute("errorNacimiento");
        if (strErrorNick == null) strErrorNick = "";
        if (strErrorCorreo == null) strErrorCorreo = "";
        if (strErrorFormato == null) strErrorFormato = "";
        if (strErrorConfirmar == null) strErrorConfirmar = "";
        if (strErrorNacimiento == null) strErrorNacimiento = "";
    %>

<body>

    <img src="Imagenes/tawevents-logo.png" class="imagen-corporativa">
    <div class="signup-form">
        <form action="ServletCrearUsuarioDeEventos">
            <div class="form-group">
                <h2>Crear cuenta</h2>
                <div class="form-group-1">
                    <b>Nick</b>
                    <input type="text" class="form-control" name="nick" maxlength="20" required="required">
                    <div class="restriccion-nick"><%= strErrorNick %></div>
                </div>
                <div class="form-group-1">
                    <b>Correo electrónico</b>
                    <input type="text" class="form-control" name="correoElectronico" required="required">
                    <div class="restriccion-correo"><%= strErrorCorreo %></div>
                    <div class="restriccion-correo"><%= strErrorFormato %></div>
                </div>
                <div class="form-group-1">
                    <b>Contraseña</b>
                    <input type="password" class="form-control" name="contrasena" minlength="6" maxlength="30" required="required">
                    <div class="restriccion-contrasena">
                        La contraseña debe tener 6 caracteres como mínimo.
                    </div>
                </div>
                <div class="form-group-1">
                    <b>Confirmar tu contraseña</b>
                    <input type="password" class="form-control" name="confirmarContrasena" maxlength="30" required="required">
                    <div class="restriccion-confirmar-contrasena"><%= strErrorConfirmar %></div>
                </div>
            </div>

            <div class="line-split"></div>

            <div class="form-group">
                <div class="form-group-2">
                    <b>Nombre</b>
                    <input type="text" class="form-control" name="nombre" maxlength="50" required="required">
                </div>
                <div class="form-group-2">
                    <b>Apellidos</b>
                    <input type="text" class="form-control" name="apellidos" maxlength="50" required="required">
                </div>
                <div class="form-group-2">
                    <b>Ciudad</b>
                    <input type="text" class="form-control" name="ciudad" maxlength="50" required="required">
                </div>
                <div class="form-group-2">
                    <b>Sexo</b>
                    <br>
                    <select class="combo-box-sexo" name="sexo" required="required">
                        <option value="mujer">Mujer</option>
                        <option value="hombre">Hombre</option>
                        <option value="otro">Otro</option>
                    </select>
                </div>
                <div class="form-group-2">
                    <b>Fecha de nacimiento</b>
                    <br>
                    <input type="date" class="calendario" name="fechaNacimiento" required="required">
                    <div class="restriccion-nacimiento"><%= strErrorNacimiento %></div>
                </div>
            </div>
            <div class="form-submit">
                <button type="submit" class="btn btn-success btn-lg btn-block">Crea tu cuenta de TAWEVENTS®</button>
            </div>

        </form>
    </div>
</body>
</html>
