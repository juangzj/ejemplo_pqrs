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
                <th>Usuario</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                //Llamado al metodo constructor de Usuario para acceder a sus metodos
                Usuario controladorUsuario = new Usuario();
                ArrayList<Usuario> usuariosRegistrados = controladorUsuario.obtenerUsuarios();
                if (usuariosRegistrados != null && !usuariosRegistrados.isEmpty()) {
                    for (Usuario usuario : usuariosRegistrados) {
            %>
            <tr>
                <td><%= usuario.getIdUsuario()%> </td>
                <td><%= usuario.getNombreCompleto()%> </td>
                <td><%= usuario.getCorreo()%>  </td>
                <td><%= usuario.getNumCelular()%> </td>
                <td><%= usuario.getCedula()%>  </td>
                <%
                    /**
                     * Convertir el valor del tipo de usuario
                     */
                    String tipoUsuario = "";
                    String valorTipoUsuario = usuario.getTipoUsuario();

                    if (valorTipoUsuario.equals("1")) {
                        tipoUsuario = "Usuario";
                    }
                    if (valorTipoUsuario.equals("2")) {
                        tipoUsuario = "Administrador";
                    }
                %>
                <td><%=tipoUsuario%></td>
                <td>
                    <%
                        if (tipoUsuario.equals("Usuario")) {
                    %>
                    <!-- Convertir en Administrador  -->
                    <button type="button" class="btn btn-info cambioRolAdministrador" data-id="<%= usuario.getIdUsuario()%>" data-bs-toggle="modal" data-bs-target="#staticBackdropUsuarioAAdministrador">
                        Convertir en Administrador
                    </button>
                    <%
                    } else if (tipoUsuario.equals("Administrador")) {
                    %>
                    <!-- Convertir en Usuario  -->
                    <button type="button" class="btn btn-success cambioRolUsuario" data-id="<%= usuario.getIdUsuario()%>" data-bs-toggle="modal" data-bs-target="#staticBackdropAdministradorAUsuario">
                        Convertir en Usuario
                    </button>
                    <%
                        }
                    %>
                </td>
            </tr>
            <%  }
            } else {
            %>
            <tr>
                <td colspan="7">
                    <div class="alert alert-warning" role="alert">
                        No hay usuarios registrados.
                    </div>
                </td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>

<!-- Modal para cambiar a un usuario a administrador -->
<div class="modal fade" id="staticBackdropUsuarioAAdministrador" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Convertir Usuario en Administrador</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>¿Estás seguro de que deseas convertir el usuario con ID: <span id="cambiar-rolAdministrador"></span> en Administrador?</p>
            </div>
            <form action="SvCambioRol" method="POST">
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-primary usuarioAAdministrador" id="confirmacionUA" value="cambioUA" >Convertir</button>
            </form>
        </div>
    </div>
</div>
</div>

<!-- Modal para cambiar a un administrador a usuario -->
<div class="modal fade" id="staticBackdropAdministradorAUsuario" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Convertir Administrador en Usuario</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>¿Estás seguro de que deseas convertir este Administrador con ID: <span id="cambiar-rolUsuario"></span> en Usuario?</p>
            </div>
            <div class="modal-footer">
                <form action="SvCambioRol" method="POST">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-primary administradorAUsuario" id="confirmacionAU" value="cambioAU" >Convertir</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="Templates/Foother.jsp" %>

<!-- Script para recoger el id del usaurio que al que se le va a cambiar de rol y despues enviarla por ajaxx al servlet -->
<script>
    // Captur clic y mandar el id del usaurio que se le cambiara el rol
    $('.cambioRolAdministrador').on('click', function () {
        // Obtener el ID del usuario
        const cambiarRolAdministrador = $(this).data('id');
        // Mostrar el ID en el modal 
        $('#cambiar-rolAdministrador').text(cambiarRolAdministrador);

        // Envío de ID al servlet a través de AJAX (método POST)
        $.ajax({
            url: 'SvCambioRol', // Url donde se enviara los datos(en este caso el id)
            method: 'POST', // Método de solicitud por donde llegara la información al servlet
            data: {cambiarRolAdministrador: cambiarRolAdministrador}, // Datos a enviar (en este caso, el ID)
            success: function (response) {
                // Manejar la respuesta del servidor si es necesario
            },
            error: function (xhr, status, error) {
                console.error('Error al enviar la solicitud:', error);
            }
        });
    });
</script>
<!-- Script para recoger el id del usaurio que al que se le va a cambiar de rol y despues enviarla por ajaxx al servlet -->
<script>
    // Captur clic y mandar el id del usaurio que se le cambiara el rol
    $('.cambioRolUsuario').on('click', function () {
        // Obtener el ID del usuario
        const cambiarRolUsuario = $(this).data('id');
        // Mostrar el ID en el modal 
        $('#cambiar-rolUsuario').text(cambiarRolUsuario);

        // Envío de ID al servlet a través de AJAX (método POST)
        $.ajax({
            url: 'SvCambioRol', // Url donde se enviara los datos(en este caso el id)
            method: 'POST', // Método de solicitud por donde llegara la información al servlet
            data: {cambiarRolUsuario: cambiarRolUsuario}, // Datos a enviar (en este caso, el ID)
            success: function (response) {
                // Manejar la respuesta del servidor si es necesario
            },
            error: function (xhr, status, error) {
                console.error('Error al enviar la solicitud:', error);
            }
        });
    });
</script>
<!-- Script para enviar la confirmacion del cambio de usuario a administrador por ajaxx -->
<script>
    $('.usuarioAAdministrador').on('click', function () {
        // Obtener la identificacion del usuario
        const usuarioAdministradorConf = $('#confirmacionUA').val();

        // Envío de ID al servlet a través de AJAX (método POST)
        $.ajax({
            url: 'SvCambioRol', // Url donde se enviara los datos(en este caso la verificaciondel cambio de usuario a administrador)
            method: 'POST', // Método de solicitud
            data: {usuarioAdministradorConf: usuarioAdministradorConf}, // Datos a enviar ()
            success: function (response) {
                // Manejar la respuesta del servidor si es necesario
            },
            error: function (xhr, status, error) {
                console.error('Error al enviar la solicitud:', error);
            }
        });
    });
</script>
<!-- Script para enviar la confirmacion del cambio de administrador a usuario por ajaxx -->
<script>
    $('.administradorAUsuario').on('click', function () {
        // Obtener la identificacion del usuario
        const administradorAusuarioConf = $('#confirmacionAU').val();

        // Envío de ID al servlet a través de AJAX (método POST)
        $.ajax({
            url: 'SvCambioRol', // Url donde se enviara los datos(en este caso la verificaciondel cambio de administrador a usuario)
            method: 'POST', // Método de solicitud
            data: {administradorAusuarioConf: administradorAusuarioConf}, // Datos a enviar ()
            success: function (response) {
                // Manejar la respuesta del servidor si es necesario
            },
            error: function (xhr, status, error) {
                console.error('Error al enviar la solicitud:', error);
            }
        });
    });
</script>