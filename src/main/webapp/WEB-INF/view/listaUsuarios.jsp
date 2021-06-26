<%-- 
    Document   : listaUsuarios
    Created on : 17-abr-2021, 18:20:29
    Author     : Ivan
--%>

<%@page import="es.taw.sampletaw.dto.UsuarioDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <link rel="stylesheet" href="/css/listaUsuariosCSS.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
        <title>Usuarios</title>
    </head>
    <%
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        if (usuario == null) {
    %>        
    <jsp:forward page="inicioSesion.jsp" />
        
    <%
        }
        List<UsuarioDTO> lista = (List)request.getAttribute("lista");
    %>
    <body onload="myFunction()">
        <img src="/img/tawevents-logo.png" class="imagen-corporativa">
            
        <jsp:include page="menu.jsp" />
        
        <div class="signup-form">

            
            <h2>Listado de usuarios</h2>
            <div class="form-group">
                <form action="/admin/usuarios">
                    <div class="form-group">
                    <select id="mySelect" onchange="myFunction()" class="tipo-filtro" name="tipoFiltro">
                        <option selected disabled>Selecciona filtro</option>
                        <option value="fNick">Nick</option>
                        <option value="fContrasena">Contraseña</option>
                        <option value="fTipoUsuario">Tipo de Usuario</option>
                    </select>
                    </div>    
                    <input id="texto" type="text" class="form-control" name="filtro">
                    
                    <button id="boton" type="submit" class="btn btn-success btn-lg btn-block">Filtrar</button>
                    <br>
                    <button id="botonL" type="submit" class="btn btn-success btn-lg btn-block">Limpiar filtro</button>
                </form>
                
                <div class="table-responsive">
                    <table class="table table-striped center scroll" align="center">
                        <thead>
                            <tr>
                                
                                <th>Nick</th>
                                <th>Contraseña</th>
                                <th>Tipo de Usuario</th>
                                <th></th>            
                                <th></th> 
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (UsuarioDTO us:lista) {
                            %>   
                            <tr>
                                <td><%= us.getNickname() %></td>
                                <td><%= us.getContrasena() %></td>
                                <%
                                if (us.getTipoUsuario().equals("administrador")) {
                                %>
                                <td>Administrador</td>
                                <%
                                }else if (us.getTipoUsuario().equals("creadordeeventos")) {
                                %>
                                <td>Creador de Eventos</td>
                                <%
                                }else if (us.getTipoUsuario().equals("teleoperador")) {
                                %>
                                <td>Teleoperador</td>
                                <%
                                }else if(us.getTipoUsuario().equals("analistadeeventos")){
                                %>
                                <td>Analista de Eventos</td>
                                <%
                                }else{
                                %>
                                <td>Usuario de Eventos</td>
                                <%
                                }
                                %>
                                <td><a href="/admin/borrarusuario/<%= us.getId() %>">
                                    <button onclick="return confirm('¿Estás seguro de eliminar al usuario?')" class="btn btn-success btn-lg btn-block">Borrar </button> </a>
                                <td><a href="/admin/editarusuario/<%= us.getId() %>">
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
                    <a href="/admin/crearusuario">
                        <button  class="btn btn-success btn-lg btn-block"> Crear nuevo usuario</button>
                    </a>
        </div>
        </div> 
        <script>
        function myFunction() {
          var x = document.getElementById("mySelect").value;
          if(x === "fNick" || x === "fContrasena" || x === "fTipoUsuario"){
              document.getElementById("texto").style.display = "initial";
              document.getElementById("boton").style.display = "initial";
              document.getElementById("botonL").style.display = "none";
          }else{
              document.getElementById("texto").style.display = "none";
              document.getElementById("boton").style.display = "none";
              document.getElementById("botonL").style.display = "initial";
          }
        }
        </script>
    </body>
</html>
