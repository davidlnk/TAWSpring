<%-- 
    Document   : editarEventoAdmin
    Created on : 07-may-2021, 13:47:55
    Author     : Ivan
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="es.taw.sampletaw.dto.UsuarioDTO" %>
<%@ page import="es.taw.sampletaw.dto.EventoDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar evento</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <link rel="stylesheet" href="../css/crearEditarUsuarioDeEventosCSS.css">
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
        EventoDTO eventoEditar = (EventoDTO)request.getAttribute("eventoEditar");
        String etiquetas = (String)request.getAttribute("etiquetas");
        String strError = (String)request.getAttribute("errorRegistro");
          
            String strId = eventoEditar.getId().toString();
            String strTitulo = eventoEditar.getTitulo();
            String strPrecio = String.valueOf(eventoEditar.getPrecioEntrada());
            String strDescripcion = eventoEditar.getDescripcion();
            String strFecha = new SimpleDateFormat("yyyy-MM-dd").format(eventoEditar.getFecha());
            String strFechaLim = new SimpleDateFormat("yyyy-MM-dd").format(eventoEditar.getFechaLimEntradas());
            String strAforo = String.valueOf(eventoEditar.getAforoMax());
            String strMaxEntradas = String.valueOf(eventoEditar.getMaxEntradasPorUsuario());
            boolean strAsientosAsignados = eventoEditar.getAsientosAsignados();
            String strImagen = eventoEditar.getImagen();
            String strEtiquetas = etiquetas;
            String strNumFilas = "";
            if(eventoEditar.getNumFilas() != null){
                strNumFilas = eventoEditar.getNumFilas().toString();
            }
            String strAsientosFila = "";
            if(eventoEditar.getAsientosPorFila() != null){
                strAsientosFila = eventoEditar.getAsientosPorFila().toString();
            }    
            if (strError == null) strError = "";
    %>
    <body>
        
        <img src="../img/tawevents-logo.png" class="imagen-corporativa">
    <jsp:include page="menu.jsp" />
    <div class="signup-form">
        
        <form action="/admin/guardarevento">
            <div class="form-group">
                <h2>Datos del evento</h2>
                <input type="hidden" name="id" value="<%= strId %>" />
                
                <div class="form-group-1">
                    <b>Título</b>
                    <input type="text" class="form-control" value="<%= strTitulo %>" name="titulo" required="required">
                </div>
                <div class="form-group-1">
                    <b>Descripción</b>
                    <textarea name="descripcion" rows="4" cols="20" class="form-control" required="required"><%=strDescripcion%></textarea>
                </div>
                <div class="form-group-1">
                    <b>Precio de entrada</b>
                    <input type="number" class="form-control" value="<%= strPrecio %>" name="precio" required="required" min="0">
                </div>
                <div class="form-group-1">
                    <b>Etiquetas (separadas por un espacio)</b>
                    <textarea class="form-control" name="etiquetas" ><%=strEtiquetas%></textarea>
                </div>
                <div class="form-group-1">
                    <b>Imagen</b>
                    <textarea type="text" class="form-control"  name="imagen"><%=strImagen%></textarea>
                </div>
                <%
                    if (eventoEditar != null && !strImagen.isEmpty()) {
                        if(strImagen.contains("http")){
                %>  
                    <img class="img-thumbnail"  width="400" height="200" src="<%=strImagen%>"/>
                <%
                    }
                }
                %>
                <div class="form-group-1">
                    <b>Fecha</b>
                    <br>
                    <input type="date" class="calendario" value="<%=strFecha%>" name="fecha" required="required">
                </div>
                <p align="center" style="color:#FF0000"><%= strError %> </p>
                <div class="form-group-1">
                    <b>Fecha límite de entradas</b>
                    <br>
                    <input type="date" class="calendario" value="<%=strFechaLim%>" name="fecha_limite_entradas" required="required">
                </div>
                <div class="form-group-1">
                    <b>Aforo máximo</b>
                    <input type="number" class="form-control" value="<%=strAforo%>" name="aforo_maximo" required="required" min="1">
                </div>
                <div class="form-group-1">
                    <b>Máximo de entradas por usuario</b>
                    <input type="number" class="form-control" value="<%= strMaxEntradas %>" name="maximo_entradas_usuario" required="required" min="1">
                </div>
                <div class="form-group-2">
                    <b>Asientos fijos asignados</b>
                    <br>
                    <%
                        if(strAsientosAsignados){
                    %>
                        <select id="fijo1" onchange="myFunction1()" class="combo-box-sexo" name="asientos_asignados">
                        <option selected value="Si">Si</option>
                        <option value="No">No</option>
                        </select>
                        <br>
                        <div id="filas" class="form-group-1">
                        <b>Número de filas</b>
                        <input id="nf1" type="number" class="form-control" value="<%=strNumFilas%>" name="numero_filas" min="1" max="10">
                        </div>
                        <div id="asientos" class="form-group-1">
                        <b>Asientos por fila</b>
                        <input id="af1" type="number" class="form-control" value="<%=strAsientosFila%>" name="asientos_por_fila" min="1" max="10">
                        </div>
                    <%
                    }else{
                    %>
                        <select id="fijo2" onchange="myFunction2()" class="combo-box-sexo" name="asientos_asignados">
                        <option value="Si">Si</option>
                        <option selected value="No">No</option>
                        </select>
                        <br>
                        <div id="filas" class="form-group-1" style="display:none">
                        <b>Número de filas</b>
                        <input id="nf2" type="number" class="form-control" value="<%=strNumFilas%>" name="numero_filas" min="1" max="10">
                        </div>
                        <div id="asientos" class="form-group-1" style="display:none">
                        <b>Asientos por fila</b>
                        <input id="af2" type="number" class="form-control" value="<%=strAsientosFila%>" name="asientos_por_fila" min="1" max="10">
                        </div>
                        <%
                        }
                        %>   
                </div>
                <br>
                <div class="form-submit">
                    <button type="submit" class="btn btn-success btn-lg btn-block">Guardar datos</button>
                </div>
            </div>   
        </form>
        </div>
        <script>
        function myFunction1() {
          var x = document.getElementById("fijo1").value;
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
        
        function myFunction2() {
          var x = document.getElementById("fijo2").value;
          if(x === "Si"){
              document.getElementById("filas").style.display = "initial";
              document.getElementById("asientos").style.display = "initial";
              document.getElementById("af2").required = true;
              document.getElementById("nf2").required = true;
          }else{
              document.getElementById("filas").style.display = "none";
              document.getElementById("asientos").style.display = "none";
              document.getElementById("af2").required = false;
              document.getElementById("nf2").required = false;
          }
        }
        </script>
    </body>
</html>
