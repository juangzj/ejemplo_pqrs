<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Pqrs"%>
<%@page import="Modelo.Usuario"%>
<%@include file="Templates/HeaderUsuario.jsp" %>

<!-- Imagen banner de PQRS -->
<div>
    <img src="Imagenes/pqrs_img.png" class="img-fluid" alt="">
</div>

<div class="container col-md-8 my-3">
    <table class="table table-bordered table-light my-3">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Pqrs</th>
                <th>Pdf</th>
                <th>Estado</th>
                <th>Mensaje</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                //LLamado al metodo constructor de usuario para acceder a sus metodos
                Usuario controladorUsuario = new Usuario();
                //Llamado al metodo constructor de pqrs para acceder a sus metodos
                Pqrs controladorPqrs = new Pqrs();

                //Obtenemos el usuario en sesión
                Usuario usuarioEnSesion = (Usuario) request.getSession().getAttribute("usuarioEnSesion");

                //Conseguimos el id del usuario en sesión 
                int idUsuario = usuarioEnSesion.getIdUsuario();

                //Obtenemos las pqrs de un usuario
                ArrayList<Pqrs> listaPqrs = controladorPqrs.obtenerPqrsUsuario(idUsuario);

                if (listaPqrs != null && !listaPqrs.isEmpty()) {
                    for (Pqrs usuarioPqrs : listaPqrs) {

            %>

            <tr>
                <td><%= usuarioPqrs.getIdPqrs()%></td>
                <td><%= usuarioPqrs.getNombreUsuario()%></td>
                <td> <%= usuarioPqrs.getCorreoUsuario()%></td>
                <td><%= usuarioPqrs.getTipoPqrsS()%></td>
                <%
                    /**
                     * Verificar si la pqrs tiene un archivo adjunto
                     */
                    String nombreArchivo = "";
                    if (usuarioPqrs.getNombreArchivo() == null || usuarioPqrs.getNombreArchivo().isEmpty()) {
                        nombreArchivo = "NO";
                %>
                <td><%= nombreArchivo%></td>
                <%
                } else {
                %>
                <td><%=usuarioPqrs.getNombreArchivo()%></td>
                <%
                    }
                %>
                <%
                    /**
                     * Verificar el estado de la pqrs para mostrar los botones
                     * de eliminar y editar
                     */
                    String estadoPqrs = usuarioPqrs.getEstado();

                %>

                <td><%= estadoPqrs%></td>
                <td>
                    <button type="button" class="btn btn-success mensaje-btn" data-id="<%= usuarioPqrs.getMensaje()%>" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                        Mensaje
                    </button>
                </td>

                <td>

                    <%
                        if (!estadoPqrs.equals("Respondida")) {

                    %>    
                    <!---------------------------Iconos----------------------------------------->
                    <!-- Eliminar -->
                    <a type="button" class="btn btn-danger eliminarPqrs-btn" data-id="<%= usuarioPqrs.getIdPqrs()%>" data-bs-toggle="modal" data-bs-target="#staticBackdropEliminarPqrs">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                        <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5M8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5m3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0"/>
                        </svg>
                    </a>
                    <%                        }

                    %> 
                    <%                        /**
                         * Verificar si el archivo tiene nombre o no para
                         * mostrar el boton de mostrar pdf
                         */
                        if (!nombreArchivo.equals("NO")) {
                    %>
                    <!--Ver-->
                    <a type="button" class="btn btn-primary pdf" data-id="<%= usuarioPqrs.getIdPqrs()%>">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-pdf" viewBox="0 0 16 16">
                        <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
                        <path d="M4.603 14.087a.8.8 0 0 1-.438-.42c-.195-.388-.13-.776.08-1.102.198-.307.526-.568.897-.787a7.7 7.7 0 0 1 1.482-.645 20 20 0 0 0 1.062-2.227 7.3 7.3 0 0 1-.43-1.295c-.086-.4-.119-.796-.046-1.136.075-.354.274-.672.65-.823.192-.077.4-.12.602-.077a.7.7 0 0 1 .477.365c.088.164.12.356.127.538.007.188-.012.396-.047.614-.084.51-.27 1.134-.52 1.794a11 11 0 0 0 .98 1.686 5.8 5.8 0 0 1 1.334.05c.364.066.734.195.96.465.12.144.193.32.2.518.007.192-.047.382-.138.563a1.04 1.04 0 0 1-.354.416.86.86 0 0 1-.51.138c-.331-.014-.654-.196-.933-.417a5.7 5.7 0 0 1-.911-.95 11.7 11.7 0 0 0-1.997.406 11.3 11.3 0 0 1-1.02 1.51c-.292.35-.609.656-.927.787a.8.8 0 0 1-.58.029m1.379-1.901q-.25.115-.459.238c-.328.194-.541.383-.647.547-.094.145-.096.25-.04.361q.016.032.026.044l.035-.012c.137-.056.355-.235.635-.572a8 8 0 0 0 .45-.606m1.64-1.33a13 13 0 0 1 1.01-.193 12 12 0 0 1-.51-.858 21 21 0 0 1-.5 1.05zm2.446.45q.226.245.435.41c.24.19.407.253.498.256a.1.1 0 0 0 .07-.015.3.3 0 0 0 .094-.125.44.44 0 0 0 .059-.2.1.1 0 0 0-.026-.063c-.052-.062-.2-.152-.518-.209a4 4 0 0 0-.612-.053zM8.078 7.8a7 7 0 0 0 .2-.828q.046-.282.038-.465a.6.6 0 0 0-.032-.198.5.5 0 0 0-.145.04c-.087.035-.158.106-.196.283-.04.192-.03.469.046.822q.036.167.09.346z"/>
                        </svg>
                    </a>

                </td>
                <%
                    }
                %>
                </td>

            </tr>

            <%
                }

            } else {
            %>

        <div class="container col-md-8 my-3">
            <div class="alert alert-warning" role="alert">
                No hay ninguna PQRS registrada.
            </div>
        </div>

        <%
            }
        %>


        </tbody>
    </table>
    <!-- Modal para mostrar el mensaje -->
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Mensaje</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <span id="mensajePqrs"></span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div> 

    <!-- Modal para eliminar una pqrs -->
    <div class="modal fade" id="staticBackdropEliminarPqrs" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Eliminar Pqrs</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="SvEliminarPqrs" method="POST">

                        <p>¿Estás seguro de que deseas eliminar la pqrs con Id: <span id="pqrsIdEliminar"></span>?</p>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-primary">Eliminar Pqrs</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <%@include file="Templates/Foother.jsp" %>
        <!-- Script para recoger el id de la pqrs para ver el pdf y despues enviarla por ajaxx al servlet -->
        <script>
            // Capturar clic en botón de ver pdf
            $('.pdf').on('click', function () {
                // Obtener el id del pdf
                const idPqrsPdf = $(this).data('id');


                // Envío del id del pdf al servlet a través de AJAX (método POST)
                $.ajax({
                    url: 'SvMostrarPdf', // Url donde se enviarán los datos (Id del pdf)
                    method: 'POST', // Método de solicitud por donde llegarán los datos al servlet
                    data: {idPqrsPdf: idPqrsPdf}, // Datos a enviar (en este caso, id pdf)
                    success: function (response) {
                        // Manejar la respuesta del servidor si es necesario
                    },
                    error: function (xhr, status, error) {
                        console.error('Error al enviar la solicitud:', error);
                    }
                });
            });
        </script>
        <!-- Script para recoger la id del usuario que se va a eliminar y despues enviarla por ajaxx al servlet -->
        <script>
            // Capturar clic y mandar el numero de identificacion para la eliminacion del usuario
            $('.eliminarPqrs-btn').on('click', function () {
                // Obtener el ID 
                const eliminarId = $(this).data('id');
                // Mostrar el ID en el modal
                $('#pqrsIdEliminar').text(eliminarId);

                // Envío de ID al servlet a través de AJAX (método POST)
                $.ajax({
                    url: 'SvEliminarPqrs', // Url donde se enviara los datos(en este caso el id)
                    method: 'POST', // Método de solicitud por donde llegara la información al servlet
                    data: {eliminarPqrsId: eliminarId}, // Datos a enviar (en este caso, el ID)
                    success: function (response) {
                        // Manejar la respuesta del servidor si es necesario
                    },
                    error: function (xhr, status, error) {
                        console.error('Error al enviar la solicitud:', error);
                    }
                });
            });
        </script>

        <script>

            $('.mensaje-btn').on('click', function () {
                // Obtener el ID 
                const mensaje = $(this).data('id');
                // Mostrar el ID en el modal
                $('#mensajePqrs').text(mensaje);
            });
        </script>