<%-- 
    Document   : BusquedaReservasUsuarioDeEventos
    Created on : 08-may-2021, 20:19:57
    Author     : David
--%>

<%@page import="java.util.Map"%>
<%@page import="es.taw.sampletaw.dto.PublicoDTO"%>
<%@page import="es.taw.sampletaw.dto.EventoDTO"%>
<%@page import="es.taw.sampletaw.dto.UsuarioDTO"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <!-- Popper JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
        <link rel="stylesheet" href="/css/busquedaHomeUsuarioDeEventosCSS.css">
        <link rel="stylesheet" href="/css/menuEventosCSS.css">

        <title>Explorar</title>


    </head>

    <%
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        if (usuario == null) {
    %>        
    <jsp:forward page="inicioSesion.jsp" />

    <%
        }
        Map<EventoDTO, List<PublicoDTO>> listaEventosPagina = (Map<EventoDTO, List<PublicoDTO>>) request.getAttribute("listaEventosPagina");
        Integer pagina = (Integer) request.getAttribute("pagina");
        Boolean paginaFinal = (Boolean) request.getAttribute("pagfinal");
        String busquedaAnterior = (String) request.getAttribute("ultimaBusqueda");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        formatter.format(date);
    %>
    <body>

        <%// No usamos un jsp como menu para esta navbar puesto que necesitamos saber el nombre del usuario%>
        <ul class="topnav">
            <li class="elem-nav" style="margin-top: 25px;"><a role="button" class="pag-prin btn btn-outline-success" href="usuario/home">Home</a></li>
            <li class="elem-nav" style="margin-top: 25px;"><a role="button" class="explorar btn btn-outline-success" href="/usuario/explorar/1">Explorar</a></li>
            <li><img src="/img/tawevents-logo.png" class="imagen-corporativa"></li>
            <li class="right elem-nav" style="margin-top: 25px;"><a role="button" class="nav-cerrar-sesion btn btn-outline-success" href="/salir">Cerrar sesión</a></li>
            <li class="right elem-nav" style="margin-top: 25px;"><span class="navbar-text">
                Has iniciado sesión como: <%=usuario.getNickname()%>
            </span></li>
        </ul>

        <nav class="navbar navbar-expand-sm navbar">
            <form class="form-inline" action="/usuario/explorar/reservas/1">
                <input class="form-control mr-sm-2" type="text" placeholder="Introduzca un evento..." name="busqueda" value="<%if (busquedaAnterior != null) {%> <%=busquedaAnterior%> <%}%>">
                <button class="btn btn-success" type="submit">Buscar</button>
            </form>
        </nav>

        <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
            <input type="radio" class="btn-check" name="btn-todos" id="btnradio1" onclick="window.location = '/usuario/explorar/1';" autocomplete="off">
            <label class="btn btn-outline-primary" for="btnradio1">Todos</label>

            <input type="radio" class="btn-check" name="btn-reservas" id="btnradio2" onclick="window.location = '/usuario/explorar/reservas/1';" autocomplete="off" checked>
            <label class="btn btn-outline-primary" for="btnradio2">Mis reservas</label>

            <input type="radio" class="btn-check" name="btn-historial" id="btnradio3" onclick="window.location = '/usuario/explorar/historial/1';" autocomplete="off">
            <label class="btn btn-outline-primary" for="btnradio3">Mi historial</label>
        </div>



        <div class="row row-cols-1 row-cols-md-3">
            <!--TODO: Bucle de cada elemento-->
            <%
                for (Map.Entry<EventoDTO, List<PublicoDTO>> entry : listaEventosPagina.entrySet()) {
            %>
            <div class="col mb-4">
                <div class="card h-100">
                    <img src="<%=entry.getKey().getImagen()%>" class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title"><%=entry.getKey().getTitulo()%></h5>
                        <p class="card-text"><%=entry.getKey().getDescripcion()%></p>
                        <%
                            int i = 0;
                            boolean estaDentro = false;
                            while (i < entry.getValue().size() && !estaDentro) {
                                if (entry.getValue().get(i).getUsuarioDeEventos() == (usuario.getUsuarioDeEventos())) {
                                    estaDentro = true;
                                }
                                i++;
                            }

                            if (!estaDentro) {
                                if (date.before(entry.getKey().getFechaLimEntradas())) {
                                    if (entry.getKey().getAforoMax() > entry.getValue().size()) {
                        %>

                        <a href="/usuario/reserva/<%=entry.getKey().getId()%>" class="btn btn-primary">Reservar »</a>

                        <%
                        } else {
                        %>

                        <div class="estado-evento">Entradas agotadas</div>

                        <%
                            }
                        } else if (date.after(entry.getKey().getFechaLimEntradas())) {
                        %>

                        <div class="estado-evento">Fecha expirada</div>

                        <%
                            }
                        } else {
                            if (date.before(entry.getKey().getFechaLimEntradas())) {
                        %>

                        <a href="/usuario/reserva/<%=entry.getKey().getId()%>" class="btn btn-primary">Editar reserva</a>

                        <%
                        } else if (date.after(entry.getKey().getFechaLimEntradas())) {
                        %>

                        <a href="/usuario/reserva/<%=entry.getKey().getId()%>" class="btn btn-primary">Ver reserva</a>

                        <%
                                }
                            }
                        %>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>

        <nav aria-label="bottom-pag">
            <ul class="pagination">
                <li class="page-item <%if (pagina == 1) {%>disabled<%}%>">
                    <a class="page-link" href="/usuario/explorar/reservas/<%=pagina - 1%>" tabindex="-1">Anterior</a>
                </li>
                <%
                    if (pagina == 1) {
                %>
                <li class="page-item active">
                    <a class="page-link" href="/usuario/explorar/reservas/1">1 <span class="sr-only">(current)</span></a>
                </li>
                <%
                    if (!paginaFinal) {
                %>
                <li class="page-item"><a class="page-link" href="/usuario/explorar/reservas/2">2</a></li>
                    <%
                        }
                    %>
                    <%
                    } else {
                    %>
                <li class="page-item"><a class="page-link" href="/usuario/explorar/reservas/<%=pagina - 1%>"><%=pagina - 1%></a></li>
                <li class="page-item active">
                    <a class="page-link" href="/usuario/explorar/reservas/<%=pagina%>"><%=pagina%><span class="sr-only">(current)</span></a>
                </li>
                <%
                    if (!paginaFinal) {
                %>
                <li class="page-item"><a class="page-link" href="/usuario/explorar/reservas/<%=pagina + 1%>"><%=pagina + 1%></a></li>
                    <%
                        }
                    %>
                    <%
                        }
                    %>
                <li class="page-item <%if (paginaFinal) {%>disabled<%}%>">
                    <a class="page-link" href="/usuario/explorar/reservas/<%=pagina + 1%>">Siguiente</a>
                </li>
            </ul>
        </nav>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>  
    </body>
</html>
