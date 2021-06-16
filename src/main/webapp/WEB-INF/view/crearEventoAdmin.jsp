<%-- 
    Document   : crearEventoAdmin
    Created on : 10-may-2021, 23:59:52
    Author     : Ivan
--%>

<%@page import="tawevents.dto.UsuarioDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear evento</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <link rel="stylesheet" href="../../../resources/static/css/crearEditarUsuarioDeEventosCSS.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    </head>
    <%
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        String strError = (String)request.getAttribute("errorRegistro");
        if (usuario == null) {
    %>        
    <jsp:forward page="inicioSesion.jsp" />
        
    <%
        }
        if (strError == null) strError = "";
    %>
    <body onload="myFunction()">
        <img src="Imagenes/tawevents-logo.png" class="imagen-corporativa">
    <jsp:include page="menu.jsp" />
    <div class="signup-form">
        <form action="ServletEventoGuardarAdmin">
            <div class="form-group">
                <h2>Datos del evento</h2>
                
                <div class="form-group-1">
                    <b>Título</b>
                    <input type="text" class="form-control" name="titulo" required="required">
                </div>
                <div class="form-group-1">
                    <b>Descripción</b>
                    <textarea name="descripcion" rows="4" cols="20" class="form-control" required="required"></textarea>
                </div>
                <div class="form-group-1">
                    <b>Precio de entrada</b>
                    <input type="number" class="form-control" name="precio" required="required" min="0">
                </div>
                <div class="form-group-1">
                    <b>Etiquetas (separadas por un espacio)</b>
                    <textarea class="form-control" name="etiquetas" ></textarea>
                </div>
                <div class="form-group-1">
                    <b>Imagen</b>
                    <textarea type="text" class="form-control"  name="imagen"></textarea>
                </div>
                <div class="form-group-1">
                    <b>Fecha</b>
                    <br>
                    <input type="date" class="calendario" name="fecha" required="required">
                </div>
                <p align="center" style="color:#FF0000"><%= strError %> </p>
                <div class="form-group-1">
                    <b>Fecha límite de entradas</b>
                    <br>
                    <input type="date" class="calendario" name="fecha_limite_entradas" required="required">
                </div>
                <div class="form-group-1">
                    <b>Aforo máximo</b>
                    <input type="number" class="form-control" name="aforo_maximo" required="required" min="1">
                </div>
                <div class="form-group-1">
                    <b>Máximo de entradas por usuario</b>
                    <input type="number" class="form-control" name="maximo_entradas_usuario" required="required" min="1">
                </div>
                <div class="form-group-2">
                    <b>Asientos fijos asignados</b>
                    <br>
                    <select id="fijo" onchange="myFunction()" class="combo-box-sexo" required="required" name="asientos_asignados">
                        
                        <option value="No">No</option>
                        <option value="Si">Si</option>
                        
                    </select>
                <br>    
                <div id="filas" class="form-group-1">
                <b>Número de filas</b>
                <input id="nf1" type="number" class="form-control" name="numero_filas" min="1" max="10">
                </div>
                <div id="asientos" class="form-group-1">
                <b>Asientos por fila</b>
                <input id="af1" type="number" class="form-control" name="asientos_por_fila" min="1" max="10">
                </div>
                <br>
                <div class="form-submit">
                    <button type="submit" class="btn btn-success btn-lg btn-block">Guardar datos</button>
                </div>
            </div>
            </div>    
        </form>
    </div>
         <script>
        function myFunction() {
          var x = document.getElementById("fijo").value;
          if(x === "Si"){
              document.getElementById("filas").style.display = "initial";
              document.getElementById("asientos").style.display = "initial";
              document.getElementById("af1").required = true;
              document.getElementById("nf1").required = true;
          }else{
              document.getElementById("filas").style.display = "none";
              document.getElementById("asientos").style.display = "none";
              document.getElementById("af1").required = false;
              document.getElementById("nf1").required = false;
          }
        }
        </script>
    </body>
</html>
