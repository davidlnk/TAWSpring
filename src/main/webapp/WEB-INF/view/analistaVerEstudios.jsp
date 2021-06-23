<%--
    Document   : analistaVerEstudios
    Created on : 24-abr-2021, 14:56:55
    Author     : daniel
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="es.taw.sampletaw.dto.UsuarioDTO" %>
<%@page import="es.taw.sampletaw.dto.EstudioDTO" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/css/analistaCSS.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title> Analista de Eventos - Ver Estudios </title>
</head>
<%
    UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
    if (usuario == null || !usuario.getTipoUsuario().equals("analistadeeventos")) {
%>
<jsp:forward page="inicioSesion.jsp"/>
<%
    }

    String strID = (String) request.getAttribute("strID"); // id del estudio seleccionado para eliminarse
    String confirmacion = (String) request.getAttribute("confirmacion"); // Si es idk se tiene que confirmar si se borra o no

    List<EstudioDTO> lista = (List<EstudioDTO>) request.getAttribute("lista");
%>
<body>
<div class="head">
    <img src="/img/tawevents-logo.png" class="imagen-corporativa" align="center">
    <ul class="menuSuperior">
        <li>
            <a href="/analista/" class="active"> Ver todos mis estudios </a>
        </li>
        <li>
            <a href="/analista/ver/crear/nulo"> Crear nuevo </a>
        </li>
        <li style="float:right">
            <a href="/salir"> Cerrar sesión </a>
        </li>
    </ul>
</div>
<div class="container">
    <br/>
    <form:form method="post" action="/analista/listar" modelAttribute="filtro">
        <h4>Filtrar estudios ... </h4>
        <div class="container row">
            <div class="col-md-auto">
                Desde: <form:input type="date" path="desdeFecha"/> &nbsp; hasta: <form:input type="date"
                                                                                             path="hastaFecha"/>
                <br/> <br/>
            </div>
            <div class="col-md-auto">
                Descripción: <form:input type="text" path="descripcion"/> <br/> <br/>
            </div>
            <div class="col-md-auto">
                Ordenar por fecha:
                &emsp; <form:radiobutton path="ordenporfecha" value="a"/> Ascendente
                &emsp; <form:radiobutton path="ordenporfecha" value="r"/> Descendente
            </div>
            <div class="col-md-auto">
                <input class="btn-default" align="right" type="submit" value="Filtrar Estudios" name="filtrar"/> <br/> <br/>
            </div>
        </div>
    </form:form>
    <%
        if (confirmacion != null && confirmacion.equals("idk") && strID != null) {
    %>
    <div class="row alert alert-danger confirmarEliminar" role="alert" id="eliminarEstudio" align="center">
        <strong> ¿Estás seguro de que quieres eliminar el estudio con id=<%=strID%>? </strong>
        &emsp; &emsp; &emsp; &emsp; &emsp;
        <a href="/analista/eliminar/y/<%=strID%>"> <input type="submit"
                                                          value="Si, estoy seguro"/></a>
        <a href="/analista/eliminar/n/<%=strID%>"> <input type="submit"
                                                          value="No eliminar"/></a>
    </div>
    <%
        }
    %>

    <div class="table-responsive">
        <table class="table table-striped center scroll" align="center" border="2" cellpadding="3">
            <thead>
            <tr>
                <th class="text-nowrap"> ID</th>
                <th class="text-nowrap"> Fecha</th>
                <th> Descripción</th>
                <th class="text-nowrap"></th>
                <th class="text-nowrap"></th>
            </tr>
            </thead>
            <tbody>
            <%
                if (lista != null) {
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
                    for (EstudioDTO estudio : lista) {
            %>
            <tr>
                <td class="text-nowrap"><%= estudio.getId()%>
                </td>
                <td class="text-nowrap"><%= formatoFecha.format(estudio.getFecha())%>
                </td>
                <td><%= estudio.getDescripcion()%>
                </td>
                <td class="text-nowrap">
                    <a href="/analista/ver/ver/<%=estudio.getId()%>"> Resultados</a>
                </td>
                <td class="text-nowrap" id="eliminar">
                    <a href="/analista/eliminar/idk/<%=estudio.getId()%>"> Eliminar </a>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
        <br/>
    </div>
</div>
</body>
</html>