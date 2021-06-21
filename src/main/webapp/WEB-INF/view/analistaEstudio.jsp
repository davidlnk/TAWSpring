<%--
    Document   : analistaEstudios
    Created on : 24-abr-2021, 21:45:37
    Author     : daniel
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="es.taw.sampletaw.dto.UsuarioDeEventosDTO" %>
<%@page import="es.taw.sampletaw.dto.EstudioDTO" %>
<%@page import="es.taw.sampletaw.dto.UsuarioDTO" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.List" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="../css/analistaCSS.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title> Analista de Eventos - Ver Estudios </title>
</head>
<%
    UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
    if (usuario == null || !usuario.getTipoUsuario().equals("analistadeeventos")) {
%>
<jsp:forward page="inicioSesion.jsp"/>
<%
    }

    boolean ver = false;
    boolean crear = false;
    String disabled = "false";
    String fechaEstudio = "";

    String modo = (String) request.getAttribute("modo");
    EstudioDTO estudio = (EstudioDTO) request.getAttribute("estudio");
    List<String> datos = (List<String>) request.getAttribute("datos");

    // Recoger resultados
    Object[] resultados = (Object[]) request.getAttribute("resultados");
    List<UsuarioDTO> listaUsuarios = null;
    List<UsuarioDeEventosDTO> listaUdE = null;
    if (resultados != null) {
        listaUsuarios = (List<UsuarioDTO>) resultados[0];
        listaUdE = (List<UsuarioDeEventosDTO>) resultados[1];
    }
    Set<String> ciudades = (Set<String>) request.getAttribute("ciudades");

    if (modo.equals("crear")) {
        crear = true;
    } else if (estudio != null) {
        ver = true;
        fechaEstudio = new SimpleDateFormat("yyyy-MM-dd").format(estudio.getFecha());
        disabled = "true";
    }
%>
<body>
<div class="head">
    <img src="../img/tawevents-logo.png" class="imagen-corporativa" align="center"/>
    <ul class="menuSuperior">
        <li>
            <a href="/analista/"> Ver todos mis estudios </a>
        </li>
        <li>
            <a href="/analista/verEstudio/crear/null" <%=crear ? "class=\"active\"" : ""%>> Crear nuevo </a>
        </li>
        <%
            if (ver) {
        %>
        <li>
            <a href="" class="active"> Ver resultados </a>
        </li>
        <li>
            <a href="/analista/estudio/crear/<%=estudio.getId()%>"> Crear desde </a>
        </li>
        <%
            }
        %>
        <li style="float:right">
            <a href="/salir"> Cerrar sesión </a>
        </li>
    </ul>
</div>

<div class="container">
    <form method="post" action="/analista/guardar">
        <br/>
        <%
            if (modo.equals("ver")) {
        %>
        <h1> Ver análisis y resultados </h1>
        <p> Fecha de creación: << <%=fechaEstudio%> >> </p>
        <h3> Datos del estudio: </h3>
        <%
        } else {
        %>
        <h3> Rellenar datos del nuevo estudio: </h3>
        <%
            }
        %>
        <div class="container-fluid">
            Descripción: <br/>
            <div id="descripcion-estudio" class="form-group">
                <input type="text" name="descripcion" title="Describa el análisis" maxlength="100" value="<%=(estudio == null) ? "" : estudio.getDescripcion()%>"
                       class="form-control" size="30" <%=disabled%>  required="true"/>
            </div>

            <div class="row">
                <div class="col">
                    <h4> Filtros de Evento </h4>
                    Titulo: <input type="text" name="titulo"
                                   title="Título del evento al que han asistido o asistiran los usuarios analizados"
                                   value="<%=datos.get(0)%>" size="30" <%=disabled%> /> <br/> <br/>

                    Fecha <br/>
                    Desde: <input name="desdeFecha" type="date" value="<%=datos.get(1)%>"
                                  min="2000-01-01" max="2030-01-01" <%=disabled%>/> <br/>
                    Hasta: <input name="hastaFecha" type="date" value="<%=datos.get(2)%>"
                                  min="2000-01-01" max="2030-01-01" <%=disabled%>/> <br/> <br/>

                    Evento finalizado:
                    <input type="checkbox" name="event_fin" value="on"  <%=disabled%> <%=datos.get(3)%>
                           title="Si esta opción está activada se filtrará por Eventos ya celebrados, si no, no se tendrá en cuenta"/>
                    <br/>
                    Asientos Asignados:
                    <input type="checkbox" name="asient_asig" value="on"  <%=disabled%>  <%=datos.get(4)%>
                           title="Si esta opción está activada se filtrará por eventos con asientos asignados, si no, no se tendrá en cuenta"/>
                    <br/>
                    <br/>

                    Aforo máximo entre:
                    &nbsp <input type="number" name="aforo_min" value="<%=datos.get(6)%>" maxlength="5" <%=disabled%>
                                 title="Si no se rellena se supone que es 0"/> &nbsp
                    y &nbsp <input type="number" name="aforo_max" value="<%=datos.get(7)%>" maxlength="5" <%=disabled%>
                                   title="Si no se rellena se supone que no hay un aforo máximo"/> <br/>
                    <br/> <br/>
                </div>
                <div class="col">
                    <h4> Filtros de Usuario </h4>
                    <div class="row">
                        <div class="col">
                            Sexo:
                            <div id="filtrosexo">
                                <input type="radio" name="sexo" value="m" id="mujer" <%=(datos.get(8).equals("m") ? "checked" : "")%> <%=disabled%> /> Mujer <br/>
                                <input type="radio" name="sexo" value="h" id="hombre" <%=(datos.get(8).equals("m") ? "checked" : "")%> <%=disabled%> /> Hombre <br/>
                                <input type="radio" name="sexo" value="-" id="otro" <%=(datos.get(8).isEmpty() ? "checked" : "")%> <%=disabled%> /> Todos <br/>
                            </div>
                            <br/>
                        </div>
                        <div class="col">
                            Ciudad:
                            <select name="ciudad" <%=disabled%>
                                    title="Si se deja en blanco no se filtra por ciudad">
                                <option value="<%=datos.get(9)%>"> <%=datos.get(9)%> </option>
                                <%
                                    if (ciudades != null) {
                                        for (String ciudad : ciudades) {
                                            if (!ciudad.equals(datos.get(9))) {
                                %>
                                <option value="<%= ciudad%>"><%= ciudad%>
                                </option>
                                <%
                                } else {
                                %>
                                <option value=""> Cualquier ciudad</option>
                                <%
                                            }
                                        }
                                    }
                                %>
                            </select> <br/> <br/>
                        </div>
                        <div class="col"></div>
                    </div>
                    Edad entre &nbsp
                    <input type="number" name="edad_min" value="<%=datos.get(10)%>" size="5" <%=disabled%>
                           title="Si no se rellena se supone que es 0"/> &nbsp y &nbsp
                    <input type="number" name="edad_max" value="<%=datos.get(11)%>" size="5" <%=disabled%>
                           title="Si no se rellena se supone que no hay límite de edad"/> <br/> <br/>

                    Nombre: &nbsp; <input type="text" name="nombre" value="<%=datos.get(12)%>" size="30" <%=disabled%> /> <br/>
                    Apellidos: <input type="text" name="apellidos" value="<%=datos.get(13)%>" size="30" <%=disabled%> /> <br/>
                    <br/>

                    Tipo de usuario:
                    <select name="tipousuario" <%=disabled%>
                            title="Solo los usuarios de evento se filtran por filtros de usuario">
                        <%
                            if (ver) {
                                String tipo = datos.get(14);
                        %>
                        <option value="<%=tipo%>"><%=tipo.isEmpty() ? "Cualquier tipo" : tipo%>
                        </option>
                        <%
                        } else {
                        %>
                        <option value=""> Cualquier tipo</option>
                        <option value="usuariodeeventos"> Usuario de eventos</option>
                        <option value="administrador"> Administrador</option>
                        <option value="creadordeeventos"> Creador de eventos</option>
                        <option value="teleoperador"> Teleoperador</option>
                        <option value="analistadeeventos"> Analista de eventos</option>
                        <%
                            }
                        %>
                    </select> <br/> <br/>

                    <%
                        if (crear) {
                    %>
                    <input type="submit" value="Guardar" name="guardar"/> <br/>

                    <%
                    } else if (ver) {
                    %>
                </div>
            </div>
        </div>
        <div class="table-responsive text-nowrap">
            <h2> Resultados </h2>
            <table class="table table-striped center scroll" align="center" border="2" cellpadding="6">
                <thead>
                <tr>
                    <th> ID</th>
                    <th> Nickname</th>
                    <th> Tipo</th>
                    <th> Nombre</th>
                    <th> Apellidos</th>
                    <th> Email</th>
                    <th> Ciudad</th>
                    <th> Fecha de nacimiento</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (listaUsuarios != null) {
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                        int i = 0;
                        for (UsuarioDTO u : listaUsuarios) {
                %>
                <tr>
                    <td><%= u.getId()%>
                    </td>
                    <td><%= u.getNickname()%>
                    </td>
                    <td><%= u.getTipoUsuario()%>
                    </td>
                    <%
                        UsuarioDeEventosDTO ude = listaUdE.get(i);
                        if (ude != null) {
                    %>
                    <td><%= ude.getNombre()%>
                    </td>
                    <td><%= ude.getApellidos()%>
                    </td>
                    <td><%= ude.getCorreo()%>
                    </td>
                    <td><%= ude.getCiudad()%>
                    </td>
                    <td><%= formatoFecha.format(ude.getFechaNacimiento())%>
                    </td>
                    <%
                    } else {
                    %>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <%
                        }
                    %>
                </tr>
                <%
                            i++;
                        }
                    }
                %>
                </tbody>
            </table>
            <%
                }
            %>
            <br/>
        </div>
    </form>
</div>

</body>
</html>

<style>
    body {
        color: black;
        /* color: #fff; */
        /* background: #63738a; */
        background: white;
        font-family: 'Roboto', sans-serif;
        font-size: large;
    }

    .cabeza {
        padding: 0em;
        position: fixed;
        width: 100%;
        background: white;
        margin-top: -155px;
    }

    .cuerpo {
        margin: auto;
        margin-top: 155px;
        width: 76%;
        padding: 20px;
    }

    ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #333;
    }

    li {
        float: left;
        font-size: larger;
    }

    li:first-child {
        margin-left: 16%;
    }

    li:last-child {
        margin-right: 16%;
    }

    li a {
        display: block;
        color: white;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
    }

    li a:hover {
        background-color: #111;
        color: burlywood;
        text-decoration: none;
    }

    .active {
        background-color: #006633;
    }

    .imagen-corporativa {
        display: block;
        margin-left: auto;
        margin-right: auto;
        width: 165px;
        height: 98px;
    }

    .filtroEstudioCol {
        float: left;
        width: 50%;
    }

    .filtroEstudio:after {
        content: "";
        display: table;
        clear: both;
    }

    #eliminar a {
        color: #990000;
    }

    #filtrosexo {
        padding-left: 1em;
    }

    input[type="number"] {
        width: 100px;
    }

    .container {
        min-width: 1280px;
    }
</style>