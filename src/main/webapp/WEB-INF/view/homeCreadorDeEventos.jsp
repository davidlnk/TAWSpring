<%-- 
    Document   : homeCreadorDeEventos
    Created on : 05-may-2021, 10:56:50
    Author     : rafar
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@ page import="es.taw.sampletaw.dto.EventoDTO" %>
<%@ page import="es.taw.sampletaw.dto.EtiquetaDTO" %>
<%@ page import="es.taw.sampletaw.dto.UsuarioDTO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
    List<EventoDTO> listaEventos = (List) request.getAttribute("listaEventos");
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    UsuarioDTO usuario = (UsuarioDTO) request.getAttribute("usuario");
    String filtro = request.getParameter("filtro");
    List<EtiquetaDTO> todasLasEtiquetas = (List) request.getAttribute("todasLasEtiquetas");
    if (filtro == null) {
        filtro = "";
    }
%>

<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="homeCreadorDeEventosCSS.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Home - Creador de eventos</title>
</head>

<body>
<br>
<div class="container me-0 d-flex">
    <div class="me-3">
        <h1><%=usuario.getNickname()%></h1>
    </div>
    <div>
        <form action="/salir">
            <button type="submit" class="btn btn-light">Cerrar sesión</button>
        </form>
    </div>
</div>
<img src="Imagenes/tawevents-logo.png" class="imagen-corporativa">
<div class="container">
    <div class="row">
        <div class="col">
            <h1 class="text-center">TUS EVENTOS</h1>
        </div>
    </div>
</div>
<br>

<form action="/creador/listar" method="POST">
    <div class="container d-flex justify-content-around">
        <div class="row">
            <div class="col-10">
                <input type="text" name="filtro" placeholder="Filtra por título o etiqueta" class="form-control" value="<%=filtro%>" />
            </div>
            <div class="col">
                <button type="submit" class="btn btn-light">Filtrar</button>
            </div>
        </div>
    </div><br>
</form>

<table border="1" class="table table-striped text-center table-sm">
    <tr>
        <th>Título</th>
        <th>Imagen</th>
        <th>Fecha</th>
        <th>Precio</th>
        <th>Aforo</th>
        <th>Fecha límite entradas</th>
        <th>Max entradas</th>
        <th>Etiquetas</th>
        <th>Asientos asignados</th>
        <th>Filas</th>
        <th>Asientos por fila</th>
        <th>Editar</th>
        <th>Borrar</th>
    </tr>
    <%
        for (EventoDTO evento : listaEventos) {
    %>
    <tr>
        <td><%= evento.getTitulo()%></td>
        <%
            if (evento != null && !evento.getImagen().isEmpty()) {
        %>
        <td><img class="rounded" src="<%=evento.getImagen()%>"  width="130" height="65"/></td>
        <%
        } else {
        %>
        <td>Sin imagen</td>
        <%
            }
        %>
        <td><%= format.format(evento.getFecha())%></td>
        <td><%= evento.getPrecioEntrada()%></td>
        <td><%= evento.getAforoMax()%></td>
        <td><%= format.format(evento.getFechaLimEntradas())%></td>
        <td><%= evento.getMaxEntradasPorUsuario()%></td>
        <td>
            <%
                List<EtiquetaDTO> etiquetasDelEvento = new ArrayList();
                for(int id : evento.getEtiquetaList()){
                    for(EtiquetaDTO etiqueta : todasLasEtiquetas){
                        if(id == etiqueta.getId()){
                            etiquetasDelEvento.add(etiqueta);
                        }
                    }
                }

                for(EtiquetaDTO e : etiquetasDelEvento){
            %>
            <%=e.getNombre() %>
            <%
                }
            %>
        </td>
        <% if (evento.getAsientosAsignados() == true) {%>
        <td>Si</td>
        <td><%= evento.getNumFilas()%></td>
        <td><%= evento.getAsientosPorFila()%></td>
        <%} else { %>
        <td>No</td>
        <td>-</td>
        <td>-</td>
        <%}%>

        <td>
            <form action="editar/<%=evento.getId()%>" method = "GET">
                <button type="submit" class="btn btn-secondary">Editar</button>
            </form><br>
        </td>
        <%-- <td><a href="ServletCreadorDeEventosEditarEvento?idE=<%=evento.getId() %>">Editar</a></td>--%>

        <td>
            <form action="borrar/<%=evento.getId()%>" method = "GET">
                <a> <button onclick="return confirm('¿Estás seguro de eliminar este evento?')" class="btn btn-danger">Borrar </button> </a>
            </form><br>
        </td>
        <%-- <td><a href="ServletCreadorDeEventosBorrar?idE=<%=evento.getId() %>">Borrar</a></td>--%>

    </tr>
    <%
        }
    %>
</table>
<div class="container text-center">
    <div class="row">
        <div class="col">
            <form method="GET" action="/creador/crear">
                <br><input type="submit" class="btn btn-success btn-lg" value="Crear evento" />
            </form>
        </div>
    </div>
</div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
</body>
</html>