<%-- 
    Document   : crearEvento
    Created on : 03-may-2021, 14:05:17
    Author     : rafar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="../../../resources/static/css/crearEventoCSS.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

        <title>Crear evento</title>
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
                NUEVO EVENTO
            </div>
            <div class="card-body">
                <% if (request.getAttribute("mensajeError") != null) {
                %> 

                <div class="alert alert-warning" role="alert">
                    <%=request.getAttribute("mensajeError")%>
                </div>
                <% }%>
                <form action="ServletEventoGuardar">

                    <table>

                        <tr>  
                            <td>Titulo:</td> 
                            <td><input type="text" name="titulo" class="form-control" value="" /></td>
                        </tr>

                        <tr>
                            <td> Descripción: </td>
                            <td> <textarea name="descripcion" class="form-control "rows="4" cols="20">
                                </textarea>
                            </td>
                        </tr>

                        <tr>  
                            <td>URL de la imagen:</td> 
                            <td><input type="text" name="imagen" class="form-control" value="" /></td>
                        </tr>

                        <tr>  
                            <td>Precio de la entrada:</td> 
                            <td><input type="text" class="form-control" name="precio" value="" /></td>
                        </tr>

                        <tr>  
                            <td>Fecha:</td> 
                            <td><input type="date" class="form-control"  name="fecha" required="required"></td>
                        </tr>

                        <tr>  
                            <td>Fecha límite de entradas:</td> 
                            <td><input type="date" class="form-control" name="fecha_limite_entradas"  required="required"></td>
                        </tr>

                        <tr>  
                            <td>Aforo máximo:</td> 
                            <td><input type="text" class="form-control" name="aforo_maximo" value="" /></td>
                        </tr>

                        <tr>  
                            <td>Máximo de entradas por usuario:</td> 
                            <td><input type="text" class="form-control" name="maximo_entradas_usuario" value="" /></td>
                        </tr>

                        <tr>
                            <td> Etiquetas (separadas por un espacio): </td>
                            <td> <textarea name="etiquetas" class="form-control "rows="4" cols="20">
                                </textarea>
                            </td>
                        </tr>

                        <tr>  
                            <td>Asientos asignados:</td> 
                            <td><select name="asientos_asignados" class="form-control">
                                    <option>Si</option>
                                    <option>No</option>
                                </select></td>
                        </tr>

                        <tr>  
                            <td>Número de filas (máximo 10): </td> 
                            <td><input type="text" name="numero_filas" value="" class="form-control" /></td>
                        </tr>

                        <tr>  
                            <td>Asientos por fila (máximo 10): </td> 
                            <td><input type="text" name="asientos_por_fila" value="" class="form-control" /></td>
                        </tr>
                        <td> <br><input type="submit" class="btn btn-success btn-lg" value="Crear evento" name="crear_evento" /><td> <br>
                    </table>
                    <br>
                </form>
            </div>
        </div>
    </div>
</body>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>

</body>
</html>
