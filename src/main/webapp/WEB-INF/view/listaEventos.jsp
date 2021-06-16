<%-- 
    Document   : listaEventos
    Created on : 26-abr-2021, 17:46:00
    Author     : Ivan
--%>

<%@page import="es.taw.sampletaw.dto.EventoDTO"%>
<%@page import="es.taw.sampletaw.dto.UsuarioDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <link rel="stylesheet" href="../css/listaUsuariosCSS.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
        <title>Eventos</title>
    </head>
    <%
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        if (usuario == null) {
    %>        
    <jsp:forward page="inicioSesion.jsp" />
        
    <%
        }
        List<EventoDTO> lista = (List)request.getAttribute("listaEv");
        List<UsuarioDTO> listaUs = (List)request.getAttribute("listaUs");
        SimpleDateFormat fechaSimple = new SimpleDateFormat("dd/MM/yyyy");
    %>
    <body onload="myFunction()">
        <img src="../img/tawevents-logo.png" class="imagen-corporativa">
            
        <jsp:include page="menu.jsp" />
        
        <div class="signup-form">

            
            <h2>Listado de eventos</h2>
            <div class="form-group">
                <form action="/admin/eventos">
                    <div class="form-group">
                    <select id="mySelect" onchange="myFunction()" class="tipo-filtro" name="tipoFiltro">
                        <option selected disabled value="fSelecciona">Selecciona filtro</option>
                        <option value="fNombre">Título</option>
                        <option value="fDescripcion">Descripción</option>
                        <option value="fFecha">Fecha</option>
                        <option value="fFechaEntrada">Fecha límite de venta de entradas</option>
                        <option value="fEtiqueta">Etiqueta</option>
                        <option value="fPrecio">Precio de entrada</option>
                        <option value="fAforo">Aforo máximo</option>
                        <option value="fImagen">Link de imagen</option>
                        <option value="fMaxEntradasUsuario">Máximo de entradas por usuario</option>
                        <option value="fAsientosAsignados">Asientos fijos asignados</option>
                        <option value="fNumFilas">Número de filas</option>
                        <option value="fAsientosFila">Asientos por fila</option>
                        <option value="fCreador">Creador</option>
                    </select>
                    </div>    
                    <input id="texto" type="text" class="form-control" name="filtroT">
                    <input id="numero" type="number" class="form-control" name="filtroN" min="0">
                    <input id="fecha" type="date" class="form-control" name="filtroF">
                    <select id="select" class="combo-box-sexo" name="filtroC">  
                        <option selected disabled value="nada">Selecciona</option>
                        <option value="No">No</option>
                        <option value="Si">Si</option>
                    </select>
   
                    <button id="boton" type="submit" class="btn btn-success btn-lg btn-block">Filtrar</button>
                    <br>
                    <button id="botonL" type="submit" class="btn btn-success btn-lg btn-block">Limpiar filtro</button>
                </form>
                <div class="table-responsive">
                    <table class="table table-striped center" align="center">
                        <thead>
                            <tr>
                                
                                <th>Título</th>
                                <th>Fecha</th>
                                <th>Aforo</th>
                                <th>Creador</th>
                                <th></th>            
                                <th></th> 
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (EventoDTO ev:lista) {
                            %>   
                            <tr>
                                <td><%= ev.getTitulo() %></td>
                                <td><%= fechaSimple.format(ev.getFecha()) %></td>
                                <td><%= ev.getAforoMax() %></td>
                                <%
                                    String nombre = "";
                                    for(UsuarioDTO u:listaUs){
                                        if (u.getId().equals(ev.getUsuario())){
                                            nombre = u.getNickname();
                                        }
                                    }
                                %>
                                <td><%= nombre %></td>
                                <td><a href="/admin/borrarevento/<%= ev.getId() %>">
                                    <button onclick="return confirm('¿Estás seguro de eliminar el evento?')" class="btn btn-success btn-lg btn-block">Borrar </button> </a>
                                <td><a href="/admin/editarevento/<%= ev.getId() %>">
                                    <button  class="btn btn-success btn-lg btn-block">Editar</button></a>                      
                            </tr>        
                            <%
                                }
                            %>  
                        </tbody>
                    </table>
                </div>
            </div>
        
        <div class="form-group">
                    <a href="/admin/crearevento">
                        <button  class="btn btn-success btn-lg btn-block"> Crear nuevo evento</button>
                    </a>
        </div>  
        </div>  
        <script>
        function myFunction() {
          var x = document.getElementById("mySelect").value;
          if(x === "fNombre" || x === "fDescripcion" || x === "fEtiqueta" || x === "fImagen" || x === "fCreador"){
              document.getElementById("numero").style.display = "none";
              document.getElementById("fecha").style.display = "none";
              document.getElementById("select").style.display = "none";
              document.getElementById("texto").style.display = "initial";
              document.getElementById("boton").style.display = "initial";
              document.getElementById("botonL").style.display = "none";
          }else if(x === "fFecha" || x === "fFechaEntrada"){
              document.getElementById("numero").style.display = "none";
              document.getElementById("texto").style.display = "none";
              document.getElementById("select").style.display = "none";
              document.getElementById("fecha").style.display = "initial";
              document.getElementById("boton").style.display = "initial";
              document.getElementById("botonL").style.display = "none";
          }else if(x === "fPrecio" || x === "fAforo" || x === "fMaxEntradasUsuario" || x === "fNumFilas" || x === "fAsientosFila"){
              document.getElementById("texto").style.display = "none";
              document.getElementById("fecha").style.display = "none";
              document.getElementById("select").style.display = "none";
              document.getElementById("numero").style.display = "initial";
              document.getElementById("boton").style.display = "initial";
              document.getElementById("botonL").style.display = "none";
          }else if(x === "fAsientosAsignados"){
              document.getElementById("texto").style.display = "none";
              document.getElementById("fecha").style.display = "none";
              document.getElementById("numero").style.display = "none";
              document.getElementById("select").style.display = "initial";
              document.getElementById("boton").style.display = "initial";
              document.getElementById("botonL").style.display = "none";
          }else{
              document.getElementById("texto").style.display = "none";
              document.getElementById("fecha").style.display = "none";
              document.getElementById("numero").style.display = "none";
              document.getElementById("select").style.display = "none";
              document.getElementById("boton").style.display = "none";
              document.getElementById("botonL").style.display = "initial";
          }
        }
        </script>                
                        
    </body>
</html>
