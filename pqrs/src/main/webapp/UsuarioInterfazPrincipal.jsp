<%@include file="Templates/HeaderUsuario.jsp" %>
<!-- Imagen banner de PQRS -->
<div>
    <img src="Imagenes/pqrs_img.png" class="img-fluid" alt="">
</div>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6 border p-4">
            <h2 class="text-center mb-4">Formulario de PQRS</h2>
            <form action="SvPqrs" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="nombreUsuario">Nombre:</label>
                    <input type="text" class="form-control"  name="nombreUsuario" required>
                </div>
                <div class="form-group">
                    <label for="correoUsuario">Correo electrónico:</label>
                    <input type="email" class="form-control"  name="correoUsuario" required>
                </div>
                <div class="form-group">
                    <label for="tipoPQRS">Tipo de PQRS:</label>
                    <select class="form-control"  name="tipoPqrs" required>
                        <option value="">Seleccione una opción</option>
                        <option value="1">Petición</option>
                        <option value="2">Queja</option>
                        <option value="3">Reclamo</option>
                        <option value="4">Sugerencia</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="mensaje">Mensaje:</label>
                    <textarea class="form-control"  name="mensaje" rows="4" ></textarea>
                </div>
                <div class="mb-3">
                    <label for="formFile" class="form-label">Adjuntar archivo</label>
                    <input class="form-control" type="file" name="archivoAdjunto">
                </div>
                <div class="container mt-3 text-center">
                    <div class="row justify-content-center">
                        <div class="col-md-6">
                            <button type="submit" class="btn btn-primary btn-block">Enviar PQRS</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%
    /**
     * Mostrar mensaje emergente de "SvPqrs" donde se agrega una nueva pqrs,
     * los mensajes emergentes son para determinar si se agrego correctamente
     * o hubo un error en el proceso
     */
    String alertaPqrsAg = (String) request.getSession().getAttribute("alertaPqrsAg");

    if (alertaPqrsAg != null) {
        if (alertaPqrsAg.trim().equals("true")) {
%>
<script>
    Swal.fire({
        title: "Envío exitoso",
        text: "Se envió la PQRS correctamente",
        icon: "success"
    });
</script>
<%
} else if (alertaPqrsAg.equals("false")) {
%>
<script>
    Swal.fire({
        title: "Error",
        text: "No se pudo enviar la PQRS",
        icon: "error"
    });
</script>
<%
        }
    }
    // Remueve el atributo de la sesión después de mostrar la alerta
    request.getSession().removeAttribute("alertaPqrsAg");
%>



<%@include file="Templates/Foother.jsp" %>
