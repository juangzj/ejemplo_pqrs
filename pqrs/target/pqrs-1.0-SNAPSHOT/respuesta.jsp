<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Pqrs"%>
<%@include file="Templates/HeaderAdministrador.jsp" %>
<style>
    .btn-nav {
        background-color: transparent;
        color: #007bff;
        border: 2px solid #007bff;
        transition: background-color 0.3s, color 0.3s;
    }

    .btn-nav:hover {
        background-color: #007bff;
        color: #fff;
    }
</style>
<div class="container">
    <div class="row mb-3">
        <div class="col-md-12 text-center">
            <a href="#" class="btn btn-nav mr-2" onclick="mostrarSeccion('leer')">Leer PQRS</a>
            <a href="#" class="btn btn-nav" onclick="mostrarSeccion('responder')">Responder PQRS</a>
        </div>
    </div>

    <div class="scrollable-content">
        <section id="leer">
            <div class="row">
                <div class="col-md-12">
                    <h2>Leer PQRS</h2>
                    <div class="pqrs">
                        <div class="card mb-3">
                            <%
                                // Llamado al metodo contructor de pqrs para acceder a sus metodos
                                Pqrs controladorPqrs = new Pqrs();
                                //Pedimos la idPqrs mediante la sesion
                                String idPqrs = (String) request.getSession().getAttribute("idPqrs");
                                if (idPqrs != null && !idPqrs.isEmpty()) {

                                    Pqrs pqrs = controladorPqrs.pqrsInfo(idPqrs);//Obtenemos la pqrs mediante el id
%>
                            <div class="card-body">
                                <h5 class="card-title">ID Pqrs: <%= pqrs.getIdPqrs()%></h5>
                                <p class="card-text"><strong>Remitente :</strong> <%= pqrs.getNombreUsuario()%> </p>  
                                <p class="card-text"><strong>Correo Remitente :</strong> <%= pqrs.getCorreoUsuario()%> </p>  
                                <p class="card-text"><strong>Tipo: </strong> <%= pqrs.getTipoPqrsS()%> </p>
                                <p class="card-text"><strong>Descripción: </strong> <%= pqrs.getMensaje()%></p>

                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <%
                //LLamado al metodo constructor de Usuario para acceder a sus metodos
                Usuario controladorUsuario = new Usuario();

                Usuario usuario = controladorUsuario.usuarioPqrs(idPqrs);

                if (usuario != null) {
            %>
            <div class="row">
                <div class="col-md-12">
                    <h2>Infromación del usuario</h2>
                    <div class="pqrs">
                        <div class="card mb-3">

                            <div class="card-body">
                                <h5 class="card-title">ID Usuario: <%= usuario.getIdUsuario()%> </h5>
                                <p class="card-text"><strong>Usuario :</strong> <%= usuario.getNombreCompleto()%>  </p>  
                                <p class="card-text"><strong>Celular: </strong> <%= usuario.getNumCelular()%>  </p>
                                <p class="card-text"><strong>Correo: </strong> <%= usuario.getCorreo()%>  </p>
                                <p class="card-text"><strong>Cedula: </strong> <%= usuario.getCedula()%>  </p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section id="responder" style="display: none;">
            <div class="row">
                <div class="col-md-12">
                    <h2>Responder PQRS</h2>
                    <form action="SvRespuesta" method="POST">

                        <div class="form-group">
                            <label for="respuesta">Respuesta:</label>
                            <textarea class="form-control" id="respuesta" name="respuesta" rows="4"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary correoR " data-id="<%=pqrs.getCorreoUsuario()%>">Responder</button>
                    </form>
                </div>
            </div>
        </section>
    </div>
    <%                                    }
        }
    %>
</div>
<%@include file="Templates/Foother.jsp" %>

<script>
    function mostrarSeccion(idSeccion) {
        // Ocultar todas las secciones
        document.querySelectorAll('section').forEach(function (seccion) {
            seccion.style.display = 'none';
        });

        // Mostrar la sección especificada por idSeccion
        document.getElementById(idSeccion).style.display = 'block';
    }
</script>
<!-- Script para recoger el correo del usuario al que se le enviara una respuesta y despues enviarla por ajaxx al servlet -->
<script>
    // Capturar clic en botón de eliminar
    $('.correoR').on('click', function () {
        // Obtener el correo del usuario
        const correoRemitente = $(this).data('id');


        // Envío del correo al servlet a través de AJAX (método POST)
        $.ajax({
            url: 'SvRespuesta', // Url donde se enviarán los datos (en este caso el correo)
            method: 'POST', // Método de solicitud por donde llegarán los datos al servlet
            data: {correoRemitente: correoRemitente}, // Datos a enviar (en este caso, el correo)
            success: function (response) {
                // Manejar la respuesta del servidor si es necesario
            },
            error: function (xhr, status, error) {
                console.error('Error al enviar la solicitud:', error);
            }
        });
    });
</script>
