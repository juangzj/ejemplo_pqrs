<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Usuario"%>
<%@include file="Templates/HeaderAdministrador.jsp" %>
<!-- Tabla donde esta la informacion de los usuarios -->
<div class="container col-md-8 my-3">
    <table class="table table-bordered table-light my-3">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Celular</th>
                <th>Cedula</th>

                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                //Llamado al metodo cosntructor de Usuario para acceder a sus metodos
                Usuario controladorUsuario = new Usuario();

                ArrayList<Usuario> usuariosRegistrados = controladorUsuario.obtenerUsuarios();

                if (usuariosRegistrados != null && !usuariosRegistrados.isEmpty()) {
                    for (Usuario usuario : usuariosRegistrados) {


            %>

            <tr>
                <td><%=usuario.getIdUsuario()%></td>
                <td><%= usuario.getNombreCompleto()%> </td>
                <td><%= usuario.getCorreo()%>  </td>
                <td><%= usuario.getNumCelular()%> </td>
                <td><%= usuario.getCedula()%>  </td>

                <td>
                    <!---------------------------Iconos----------------------------------------->

                    <!-- Editar  -->
                    <a type="button" class="btn btn-info editar-btn"  data-id="<%=usuario.getIdUsuario()%>" data-bs-toggle="modal" data-bs-target="#staticBackdropEditarUsuario">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                            <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                            <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/>
                        </svg> 
                    </a>

                    <!-- Eliminar -->
                    <a type="button" class="btn btn-danger eliminar-btn" data-id="<%= usuario.getCorreo()%> " data-bs-toggle="modal" data-bs-target="#staticBackdropEliminarUsuario">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                            <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5M8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5m3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0"/>
                        </svg>
                    </a>
                </td>
            </tr>
            <%                    }
            } else {
            %>
            <div class="container col-md-8 my-3">
                <div class="alert alert-warning" role="alert">
                    No hay usuarios registrados.
                </div>
            </div>

            <%
                }
            %>

        </tbody>
    </table>
    <!-- Modal para eliminar un usuario -->
    <div class="modal fade" id="staticBackdropEliminarUsuario" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Eliminar Usuario</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="SvEliminarUsuario" method="POST">
                        <p>¿Estás seguro de que deseas eliminar el usuario con con correo: <span id="usuarioCorreoEliminar"></span>?</p>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" id="boton_eliminar"  value="eliminarUsuario" class="btn btn-primary eliminarUsuario">Eliminar Usuario</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal para editar un usaurio -->
    <div class="modal fade" id="staticBackdropEditarUsuario" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Editar Usuario</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="SvEditarUsuario" method="POST">
                        <p>Editar usuario con ID: <span id="usuarioEditar"></span>
                        </p>
                         <!-- Entrada para el nombre del usuario -->
                            <h5>Nombre Completo</h5>
                            <input class="form-control form-control-sm" name="nombreCompletoEditar" type="text" placeholder="Nombre del usuario" aria-label=".form-control-sm example" required>
                            <!-- Entrada para el número de teléfono del usuario -->
                            <h5>Número de celular</h5>
                            <input class="form-control form-control-sm" name="numeroTelefonoEditar" type="tel" placeholder="Número de celular" aria-label=".form-control-sm example" required>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" id="boton_editar"  value="editarUsuario" class="btn btn-primary editarUsuario">Editar Usuario</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="Templates/Foother.jsp" %>
<!-- Script para recoger el correo del usuario que se va a eliminar y despues enviarla por ajaxx al servlet -->
<script>
    // Capturar clic en botón de eliminar
    $('.eliminar-btn').on('click', function () {
        // Obtener el correo del usuario
        const correoEliminar = $(this).data('id');
        // Mostrar el correo en el modal de eliminación
        $('#usuarioCorreoEliminar').text(correoEliminar);

        // Envío del correo al servlet a través de AJAX (método POST)
        $.ajax({
            url: 'SvEliminarUsuario', // Url donde se enviarán los datos (en este caso el correo)
            method: 'POST', // Método de solicitud por donde llegarán los datos al servlet
            data: {correoEliminar: correoEliminar}, // Datos a enviar (en este caso, el correo)
            success: function (response) {
                // Manejar la respuesta del servidor si es necesario
            },
            error: function (xhr, status, error) {
                console.error('Error al enviar la solicitud:', error);
            }
        });
    });
</script>
<!-- Script para enviar la confimacion de la eliminación de un usuario -->
<script>
    $('.eliminarUsuario').on('click', function () {
        // Obtener el ID de la membresía
        const eliminarUsuario = $('#boton_eliminar').val();

        // Envío de ID al servlet a través de AJAX (método POST)
        $.ajax({
            url: 'SvEliminarUsuario', // Url donde se enviara los datos(en este caso la verificacion de la eliminación de la memrbesia)
            method: 'POST', // Método de solicitud
            data: {boton_eliminar: eliminarUsuario}, // Datos a enviar (verificación para la eliminación de la memrbesia)
            success: function (response) {
                // Manejar la respuesta del servidor si es necesario
            },
            error: function (xhr, status, error) {
                console.error('Error al enviar la solicitud:', error);
            }
        });
    });
</script>
<!-- Script para recoger el id del usuario que se va a editar y despues enviarla por ajaxx al servlet -->
<script>
    // Capturar clic en botón de eliminar
    $('.editar-btn').on('click', function () {
        // Obtener el correo del usuario
        const idEditar = $(this).data('id');
        // Mostrar el correo en el modal de eliminación
        $('#usuarioEditar').text(idEditar);

        // Envío del correo al servlet a través de AJAX (método POST)
        $.ajax({
            url: 'SvEditarUsuario', // Url donde se enviarán los datos (en este caso el correo)
            method: 'POST', // Método de solicitud por donde llegarán los datos al servlet
            data: {idEditar: idEditar}, // Datos a enviar (en este caso, el correo)
            success: function (response) {
                // Manejar la respuesta del servidor si es necesario
            },
            error: function (xhr, status, error) {
                console.error('Error al enviar la solicitud:', error);
            }
        });
    });
</script>
<!-- Script para enviar la confimacion de la edicion de un usuario -->
<script>
    $('.editarUsuario').on('click', function () {
        // Obtener el ID de la membresía
        const editarUsuario = $('#boton_editar').val();

        // Envío de ID al servlet a través de AJAX (método POST)
        $.ajax({
            url: 'SvEditarUsuario', // Url donde se enviara los datos(en este caso la verificacion de la eliminación de la memrbesia)
            method: 'POST', // Método de solicitud
            data: {editarConf: editarUsuario}, // Datos a enviar
            success: function (response) {
                // Manejar la respuesta del servidor si es necesario
            },
            error: function (xhr, status, error) {
                console.error('Error al enviar la solicitud:', error);
            }
        });
    });
</script>