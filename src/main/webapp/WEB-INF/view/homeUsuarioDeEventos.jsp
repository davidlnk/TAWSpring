<%-- 
    Document   : homeUsuarioDeEventos
    Created on : 26-abr-2021, 17:38:54
    Author     : David
--%>

<%@page import="es.taw.sampletaw.dto.EventoDTO"%>
<%@page import="es.taw.sampletaw.dto.UsuarioDTO"%>
<%@page import="es.taw.sampletaw.entity.Evento"%>
<%@page import="java.util.List"%>
<%@page import="es.taw.sampletaw.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">


        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <!-- Popper JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flickity/1.0.0/flickity.css">

        <link rel="stylesheet" href="homeUsuarioDeEventosCSS.css">
        <link rel="stylesheet" href="menuEventosCSS.css">

        <title>Página principal</title>
    </head>

    <%
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        if (usuario == null) {
    %>        
    <jsp:forward page="inicioSesion.jsp" />

    <%
        }
        String ciudad = (String) request.getAttribute("ciudad");
        List<EventoDTO> listaEnCiudad = (List) request.getAttribute("listaEnCiudad");
        List<EventoDTO> listaPopulares = (List) request.getAttribute("listaPopulares");
        List<EventoDTO> listaProximos = (List) request.getAttribute("listaProximos");
    %>

    <body>

        <%// No usamos un jsp como menu para esta navbar puesto que necesitamos saber el nombre del usuario%>
        <ul class="topnav">
            <li class="elem-nav" style="margin-top: 25px;"><a role="button" class="pag-prin btn btn-outline-success" href="ServletHomeUsuarioDeEventos">Home</a></li>
            <li class="elem-nav" style="margin-top: 25px;"><a role="button" class="explorar btn btn-outline-success" href="ServletPaginacionEventos?pagina=1">Explorar</a></li>
            <li><img src="Imagenes/tawevents-logo.png" class="imagen-corporativa"></li>
            <li class="right elem-nav" style="margin-top: 25px;"><a role="button" class="nav-cerrar-sesion btn btn-outline-success" href="ServletCerrarSesion">Cerrar sesión</a></li>
            <li class="right elem-nav" style="margin-top: 25px;"><span class="navbar-text">
                Has iniciado sesión como: <%=usuario.getNickname()%>
            </span></li>
        </ul>

        <!------ Include the above in your HEAD tag ---------->

        <nav class="navbar navbar-expand-sm navbar">
            <form class="form-inline" action="ServletPaginacionEventos">
                <input class="form-control mr-sm-2" type="text" placeholder="Introduzca un evento..." name="busqueda">
                <button class="btn btn-success" type="submit">Buscar</button>
            </form>
        </nav>

        <div class="caruseles">
            <!-- Eventos de la ciudad del usuario -->

            <%
                if (listaEnCiudad != null && !listaEnCiudad.isEmpty()) {
            %>
            <div class="background-ciudad">

                <div class="texto-categoria" style="font-family: sans-serif;font-size: 50px;padding-left: 2em;padding-top: 1em;">
                    En <%=ciudad%>
                </div>

                <div class="gallery js-flickity" data-flickity-options='{ "wrapAround": true, "freeScroll": true, "freeScrollFriction": 0.5, "pageDots": false }'>
                    <%
                        for (EventoDTO evento : listaEnCiudad) {
                    %>
                    <div class="gallery-cell">
                        <img src="<%=evento.getImagen()%>">
                        <div class="texto-interior">
                            <a style="color:white;" href="ServletUnirseEvento?id_evento=<%=evento.getId()%>"><%=evento.getTitulo()%></a>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>

            </div>

            <%
                }
            %>

            <!-- Eventos de con más usuarios inscritos -->

            <%
                if (listaPopulares != null && !listaPopulares.isEmpty()) {
            %>

            <div class="background-populares">

                <div class="texto-categoria" style="font-family: sans-serif;font-size: 50px;padding-left: 2em;padding-top: 1em;">
                    Los más populares
                </div>

                <div class="gallery js-flickity" data-flickity-options='{ "wrapAround": true, "freeScroll": true, "freeScrollFriction": 0.5, "pageDots": false }'>
                    <%
                        for (EventoDTO evento : listaPopulares) {
                    %>
                    <div class="gallery-cell">
                        <img src="<%=evento.getImagen()%>">
                        <div class="texto-interior">
                            <a style="color:white;" href="ServletUnirseEvento?id_evento=<%=evento.getId()%>"><%=evento.getTitulo()%></a>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div> 

            </div>

            <%
                }
            %>

            <!-- Eventos de música -->

            <%
                if (listaProximos != null && !listaProximos.isEmpty()) {
            %>

            <div class="background-proximos">

                <div class="texto-categoria" style="font-family: sans-serif;font-size: 50px;padding-left: 2em;padding-top: 1em;">
                    A punto de comenzar
                </div>

                <div class="gallery js-flickity" data-flickity-options='{ "wrapAround": true, "freeScroll": true, "freeScrollFriction": 0.5, "pageDots": false }'>
                    <%
                        for (EventoDTO evento : listaProximos) {
                    %>
                    <div class="gallery-cell">
                        <img src="<%=evento.getImagen()%>">
                        <div class="texto-interior">
                            <a style="color:white;" href="ServletUnirseEvento?id_evento=<%=evento.getId()%>"><%=evento.getTitulo()%></a>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>

            </div>


            <%
                }
            %>
        </div>
    </body>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/flickity/1.0.0/flickity.pkgd.js"></script>
</html>
