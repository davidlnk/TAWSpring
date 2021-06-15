<%-- 
    Document   : editarEventoCreadorDeEventos
    Created on : 08-may-2021, 15:06:43
    Author     : rafar
--%>

<%@page import="es.taw.sampletaw.dto.EventoDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="es.taw.sampletaw.entity.Evento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        EventoDTO evento = (EventoDTO) request.getAttribute("evento");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    %>
    <head>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="crearEventoCSS.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

        <title>Editar evento</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <a href="ServletCreadorDeEventosListar" title="Home">
    <img src="Imagenes/tawevents-logo.png" class="imagen-corporativa">
    </a>
</head>
<body>
    <div class="container">


        <div class="card">
            <div class="card-header">
                EDITAR EVENTO
            </div>
            <div class="card-body"> 
                <div class="text-center">
                    <% if (request.getAttribute("mensajeError") != null) {
                    %> 

                    <div class="alert alert-warning" role="alert">
                        <%=request.getAttribute("mensajeError")%>
                    </div>
                    <% }%>
                    
                     <%
                    if (evento != null && !evento.getImagen().isEmpty()) {
                %>  
                    <img class="img-thumbnail"  width="400" height="200" src="<%=evento.getImagen()%>"/>
                    <% }%>
                </div>
                <form action="ServletEventoGuardar?idEventoEditar=<%=evento.getId().toString()%>" method="POST">

                    <table>

                        <tr>  
                            <td>Titulo:</td> 
                            <td><input type="text" name="titulo" class="form-control" value="<%=evento.getTitulo()%>" /></td>
                        </tr>

                        <tr>
                            <td> Descripción: </td>
                            <td> <textarea name="descripcion" class="form-control" value="" rows="4" cols="20"><%=evento.getDescripcion()%>
                                </textarea>
                            </td>
                        </tr>

                        <tr>  
                            <td>URL de la imagen:</td> 
                            <td><input type="text" name="imagen" class="form-control" value="<%=evento.getImagen()%>" /></td>
                        </tr>

                        <tr>  
                            <td>Precio de la entrada:</td> 
                            <td><input type="text" class="form-control" name="precio" value="<%=evento.getPrecioEntrada()%>" /></td>
                        </tr>

                        <tr>  
                            <td>Fecha:</td> 
                            <td><input type="date"  class="form-control" name="fecha" required="required" value="<%=format.format(evento.getFecha())%>"></td>
                        </tr>

                        <tr>  
                            <td>Fecha límite de entradas:</td> 
                            <td><input type="date"  class="form-control" name="fecha_limite_entradas" required="required" value="<%=format.format(evento.getFechaLimEntradas())%>"></td>
                        </tr>

                        <tr>  
                            <td>Aforo máximo:</td> 
                            <td><input type="text" class="form-control" name="aforo_maximo" value="<%=evento.getAforoMax()%>" /></td>
                        </tr>

                        <tr>  
                            <td>Máximo de entradas por usuario:</td> 
                            <td><input type="text" class="form-control" name="maximo_entradas_usuario" value="<%=evento.getMaxEntradasPorUsuario()%>" /></td>
                        </tr>
                        
                        <tr>
                            <td> <textarea name="etiquetas" class="form-control "rows="4" cols="20" style="display:none;">
                                </textarea>
                            </td>
                        </tr>
                        
                        <%
                            if (evento.getAsientosAsignados() == true) {
                        %>

                        <tr>  
                            <td>Asientos asignados:</td> 
                            <td><select name="asientos_asignados" class="form-control">
                                    <option>Si</option>
                                    <option>No</option>
                                </select></td>
                        </tr>
                        <tr>  
                            <td>Número de filas (máximo 10):</td> 
                            <td><input type="text" name="numero_filas" class="form-control" value="<%=evento.getNumFilas()%>" /></td>
                        </tr>

                        <tr>  
                            <td>Asientos por fila (máximo 10):</td> 
                            <td><input type="text" name="asientos_por_fila" class="form-control" value="<%=evento.getAsientosPorFila()%>" /></td>
                        </tr>
                        <br>

                        <% } else { %>  
                        <tr>  
                            <td>Asientos asignados:</td> 
                            <td><select name="asientos_asignados" class="form-control">
                                    <option>Si</option>
                                    <option value="No" selected>No</option>
                                </select></td>
                        </tr>
                        <tr>  
                            <td>Número de filas (máximo 10):</td> 
                            <td><input type="text" name="numero_filas" value="" class="form-control" /></td>
                        </tr>

                        <tr>  
                            <td>Asientos por fila (máximo 10):</td> 
                            <td><input type="text" name="asientos_por_fila" value="" class="form-control" /></td>
                        </tr>
                        <% }%>
                        <br>
                        <td> <br><input type="submit" class="btn btn-success btn-lg" value="Guardar cambios" name="guardarCambios" /><td> <br>

                    </table>
                    <br>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
</body>
</html>

