<%-- 
    Document   : analista_ver_estudios
    Created on : 24-abr-2021, 14:56:55
    Author     : daniel
--%>

<%@page import="es.taw.sampletaw.dto.UsuarioDTO"%>
<%@page import="es.taw.sampletaw.dto.EstudioDTO"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../../resources/static/css/analistaCSS.css">
        <title> Analista de Eventos - Ver Estudios </title>
    </head>
    <%
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        if (usuario == null || !usuario.getTipoUsuario().equals("analistadeeventos")) {
    %>
    <jsp:forward page="inicioSesion.jsp" /> 
    <%
        }
        String confirmacion = request.getParameter("confirmacion");
        String strID = request.getParameter("id"); // id del estudio seleccionado para eliminarse

        String desdeFecha = request.getParameter("desdeFecha");
        desdeFecha = (desdeFecha == null) ? "2021-01-01" : desdeFecha;
        String hastaFecha = request.getParameter("hastaFecha");
        hastaFecha = (hastaFecha == null) ? (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) : hastaFecha;
        List<EstudioDTO> lista = (List<EstudioDTO>) request.getAttribute("lista");
    %>
    <body>
        <div class="head">
            <img src="Imagenes/tawevents-logo.png" class="imagen-corporativa" align="center">
            <ul class="menuSuperior">
                <li>
                    <a href="ServletAnalistaListar" class="active"> Ver todos mis estudios </a>
                </li>
                <li>
                    <a href="ServletAnalistaVerEstudio?modo=crear"> Crear nuevo </a>
                </li>
                <li style="float:right">
                    <a href="ServletCerrarSesion"> Cerrar sesión </a>
                </li>
            </ul>
        </div>
        <div class="container">
            <br/>
            <form name="filtrar_estudios" methor="get" action="ServletAnalistaListar">
                <div class="row">
                    <div class="col">
                        Filtrar estudios ... <br/>
                        Desde: <input name="desdeFecha" type="date" value="<%=desdeFecha%>" /> <br/>
                        Hasta: &thinsp;<input name="hastaFecha" type="date" value="<%=hastaFecha%>" /> <br/> <br/>
                    </div>
                    <div class="col">
                        Ordenar desde ... <br/>
                        &nbsp; <input type="radio" name="ordenporfecha" value="desdeprimeros" checked="checked" /> Los antiguos <br/>
                        &nbsp; <input type="radio" name="ordenporfecha" value="desdeultimos"/> Los recientes <br/>
                        <input class="btn-default" align="right" type="submit" value="Filtrar Estudios" name="filtrar"/>       
                    </div>
                </div>
            </form>
            <%
                if (confirmacion != null && confirmacion.equals("idk") && strID != null) {
            %>
            <div class="alert alert-danger confirmarEliminar" role="alert" id="eliminarEstudio" align="center"> 
                <p>
                    <strong> ¿Estás seguro de que quieres eliminar el estudio con id=<%=strID%>? </strong>
                    <a href="ServletAnalistaEliminar?confirmacion=y&id=<%=strID%>"> <input type="submit" value="Si, estoy seguro" /></a>
                    <a href="ServletAnalistaEliminar?confirmacion=n&id=<%=strID%>"> <input type="submit" value="No eliminar" /></a>
                </p>
            </div>
            <%
                }
            %>

            <div class="table-responsive ">
                <table class="table table-striped center scroll" align="center" border="2" cellpadding="6">
                    <thead>
                        <tr>
                            <th class="text-nowrap"> ID </th>
                            <th class="text-nowrap"> Fecha </th>
                            <th> Descripción </th>
                            <th class="text-nowrap">  </th>
                            <th class="text-nowrap">  </th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (lista != null) {
                                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
                                for (EstudioDTO estudio : lista) {
                        %>   
                        <tr>
                            <td class="text-nowrap"> <%= estudio.getId()%> </td>
                            <td class="text-nowrap"> <%= formatoFecha.format(estudio.getFecha())%> </td>
                            <td> <%= estudio.getDescripcion()%> </td>
                            <td class="text-nowrap"> <a href="ServletAnalistaVerEstudio?modo=ver&id=<%=estudio.getId()%>"> +info </a> </td>
                            <td class="text-nowrap" id="eliminar"> <a href="ServletAnalistaEliminar?confirmacion=idk&id=<%=estudio.getId()%>"> Eliminar </a> </td>
                        </tr>        
                        <%
                                }
                            }
                        %>    
                    </tbody>
                </table> <br/>
            </div>
        </div>
    </body>
</html>