<%-- 
    Document   : reservaEvento
    Created on : 09-may-2021, 1:02:29
    Author     : David
--%>

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
        <link rel="stylesheet" href="../css/reservaEventoCSS.css">
        <link rel="stylesheet" href="../css/menuEventosCSS.css">

        <title>Reserva</title>


    </head>

    <%
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        if (usuario == null) {
    %>        
    <jsp:forward page="inicioSesion.jsp" />

    <%
        }
        EventoDTO evento = (EventoDTO) request.getAttribute("evento");
        List<PublicoDTO> publicoDeUsuario = (List) request.getAttribute("publicosUs");
        List<PublicoDTO> publicoDeEvento = (List) request.getAttribute("publicosEv");
        boolean[][] ocupados = (boolean[][]) request.getAttribute("ocupados");
        SimpleDateFormat formatterVista = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        formatter.format(date);
    %>
    <body>

        <%// No usamos un jsp como menu para esta navbar puesto que necesitamos saber el nombre del usuario%>
        <ul class="topnav">
            <li class="elem-nav" style="margin-top: 25px;"><a role="button" class="pag-prin btn btn-outline-success" href="/usuario/home">Home</a></li>
            <li class="elem-nav" style="margin-top: 25px;"><a role="button" class="explorar btn btn-outline-success" href="/usuario/explorar/1">Explorar</a></li>
            <li><img src="../img/tawevents-logo.png" class="imagen-corporativa"></li>
            <li class="right elem-nav" style="margin-top: 25px;"><a role="button" class="nav-cerrar-sesion btn btn-outline-success" href="/salir">Cerrar sesión</a></li>
            <li class="right elem-nav" style="margin-top: 25px;"><span class="navbar-text">
                Has iniciado sesión como: <%=usuario.getNickname()%>
            </span></li>
        </ul>

        <div class="contenedor row row-cols-1 row-cols-md-3">
            <h1 style="padding-left: 200px;">Selecciona tu reserva</h1>
            <div class="grupo-imagen container-fluid col mb-4">
                <img src="<%=evento.getImagen()%>" class="img-evento card">
            </div>
            <div class="grupo-info card container-fluid  col mb-4">
                <h5 class="titulo-evento"><%=evento.getTitulo()%></h5>
                <p class="descripcion-evento"><%=evento.getDescripcion()%></p>
                <p class="fecha-evento">Fecha: <%=formatterVista.format(evento.getFecha())%>
                    <br> Fecha límite de reserva: <%=formatterVista.format(evento.getFechaLimEntradas())%></p>
                <h2 class="precio-evento"><%=evento.getPrecioEntrada()%>€</h2>
                <p class="plazas-evento">Quedan <%=evento.getAforoMax() - evento.getPublicoList().size()%> entradas 
                    <br>Puedes reservar hasta <%=evento.getMaxEntradasPorUsuario()%> entradas</p>
                <form action="/usuario/solicitud" method="post">
                    <input type="hidden" name="id_evento" value="<%=evento.getId()%>">
                    <%
                        int numeroEntradasReservadasPorUsuario = 0;
                        for (PublicoDTO publico : publicoDeEvento) {
                            if (publico.getUsuarioDeEventos() == usuario.getUsuarioDeEventos()) {
                                numeroEntradasReservadasPorUsuario++;
                            }
                        }

                        if (evento.getAsientosAsignados()) {
                            if (date.before(evento.getFechaLimEntradas()) && evento.getMaxEntradasPorUsuario() > numeroEntradasReservadasPorUsuario
                                    && evento.getAforoMax() - evento.getPublicoList().size() > 0) {
                    %>
                    <div>Fila:<input type="text" class="form-control" name="fila" id="fila" maxlength="50" readonly required="required"></div>
                    <div>Asiento:<input type="text" class="form-control" name="asiento" id="asiento" maxlength="50" readonly required="required"></div>
                    <button type="submit" class="btn btn-primary" id="guardar-seleccion">Guardar selección</button>
                    <%
                        }

                    } else {
                        if (date.before(evento.getFechaLimEntradas()) && evento.getMaxEntradasPorUsuario() > numeroEntradasReservadasPorUsuario
                                && evento.getAforoMax() - evento.getPublicoList().size() > 0) {
                    %>

                    <button type="submit" class="btn btn-primary">Añadir entrada</button>
                    <%
                            }
                        }
                    %>
                </form>
            </div>
            <tbody>
            <div class="scrollit card container-fluid col mb-4">
                <h2>Entradas adquiridas</h2>
                <table class="table table-striped">
                    <tr>
                        <th>Número</th>
                        <th>Fila</th>
                        <th>Asiento</th>
                    </tr>
                    <%
                        int num = 1;
                        for (PublicoDTO pub : publicoDeEvento) {
                            if (publicoDeUsuario.contains(pub)) {
                    %>
                    <tr>
                        <td><%=num%></td>
                        <td><%if (pub.getFila() == null) {%>-<%} else {%><%=pub.getFila() + 1%><%}%></td>
                        <td><%if (pub.getAsiento() == null) {%>-<%} else {%><%=pub.getAsiento() + 1%><%}%></td>
                        <%
                            if (date.before(evento.getFechaLimEntradas())) {
                        %>
                            <td><a href="/usuario/cancelar/<%=pub.getId()%>" class="btn btn-danger">Cancelar</a></td>
                        <%
                            }
                        %>
                    </tr>
                    <%
                            }
                            num++;
                        }
                    %>
                </table>
            </div>
        </tbody>



        <%
            // Crearemos una matriz con asientos siempre que sean asignables, el usuario pueda elegir más y no haya más de 100
            if (evento.getAsientosAsignados() && date.before(evento.getFechaLimEntradas()) && evento.getAforoMax() <= 100) {
        %>
        <div class="container-fluid card col mb-4">
            <img src="../img/theatre-stage.png" class="imagen-escenario">
            <%
                for (int i = 0; i < ocupados.length; i++) {
            %>
            <div class="row g-2">
                <%
                    for (int j = 0; j < ocupados[0].length; j++) {
                        // ==== Si esta ocupado saldrá el boton rojo, si está libre saldrá verde =====
%>

                <div class="col"><button type="button" value="<%=i + "-" + j%>" <%if (ocupados[i][j]) {%> class="seat reserved btn btn-warning" <%} else {%> class="seat btn btn-success" onclick="seatClick(this.value)"<%}%>></button></div>

                <%
                        // ===========================================================================
                    }
                %>
            </div>
            <%
                }
            %>
        </div>

        <%
            }
        %>

    </div>



    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script>
                    function seatClick(seat) {
                        var res = seat.split("-");
                        fila.value = parseInt(res[0]) + 1;
                        asiento.value = parseInt(res[1]) + 1;
                        boton.disabled = false;
                    }

                    var fila = document.getElementById("fila");
                    var asiento = document.getElementById("asiento");
                    var boton = document.getElementById("guardar-seleccion");
                    boton.disabled = true;
    </script>
</body>
</html>
