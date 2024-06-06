<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="sweetalert2.all.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <title>Inicio de sesion</title>
    </head>
    <body>
        <section class="vh-100" style="background: linear-gradient(to bottom, #2C3E50, #34495E, #2C3E50, #000000);">
            <div class="container py-5 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col col-xl-10">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="row g-0">
                                <div class="col-md-6 col-lg-5 d-none d-md-block">
                                    <img src="Imagenes/pqrs.jpg" class="img-fluid rounded-start" alt="Imagen de PQRS" style="height: 100%; object-fit: cover;">
                                </div>
                                <div class="col-md-6 col-lg-7 d-flex align-items-center">
                                    <div class="card-body p-4 p-lg-5 text-black">

                                        <form action="SvIngreso" method="POST">
                                            <div class="d-flex align-items-center mb-3 pb-1">
                                                <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219;"></i>
                                                <span class="h1 fw-bold mb-0">PQRS</span>
                                            </div>

                                            <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Ingrese a su cuenta</h5>

                                            <div data-mdb-input-init class="form-outline mb-4">
                                                <input type="email" name="correoIngresar" class="form-control form-control-lg" required />
                                                <label class="form-label" for="form2Example17">Correo electrónico</label>
                                            </div>

                                            <div data-mdb-input-init class="form-outline mb-4">
                                                <input type="password" name="contraseniaIngresar" class="form-control form-control-lg" required />
                                                <label class="form-label" for="form2Example27">Contraseña</label>
                                            </div>

                                            <div class="pt-1 mb-4">
                                                <button data-mdb-button-init data-mdb-ripple-init class="btn btn-dark btn-lg btn-block" type="submit">Ingresar</button>
                                            </div>
                                        </form>



                                        <p class="mb-5 pb-lg-2" style="color: #393f81;"> ¿Aun no tiene una cuenta? 

                                            <button type="button" class="" data-bs-toggle="modal" data-bs-target="#staticBackdropRegistrarUsuario">
                                                Registrese aquí!
                                            </button><br>

                                            <a href="#!" class="small text-muted">Terms of use.</a>
                                            <a href="#!" class="small text-muted">Privacy policy</a>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Modal para registrar un nuevo usuario -->
        <div class="modal fade" id="staticBackdropRegistrarUsuario" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">Nuevo Usuario</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="SvRegistrarUsuario" method="POST">
                            <!-- Entrada para el nombre del usuario -->
                            <h5>Nombre Completo</h5>
                            <input class="form-control form-control-sm" name="nombreCompletoRegistar" type="text" placeholder="Nombre del usuario" aria-label=".form-control-sm example" required>
                            <!-- Entrada para el número de teléfono del usuario -->
                            <h5>Número de celular</h5>
                            <input class="form-control form-control-sm" name="numeroTelefonoRegistar" type="tel" placeholder="Número de celular" aria-label=".form-control-sm example" required>
                            <!-- Entrada para el correo del usuario -->
                            <h5>Correo electrónico</h5>
                            <input class="form-control form-control-sm" name="correoRegistrar" type="email" placeholder="Correo electrónico" aria-label=".form-control-sm example" required>
                            <!-- Entrada para la contraseña del usuario -->
                            <h5>Contraseña</h5>
                            <input class="form-control form-control-sm" name="contraseniaRegistrar" type="password" placeholder="Contraseña" aria-label=".form-control-sm example" required>
                            <!-- Entrada para el número de cédula del usuario -->
                            <h5>Cédula</h5>
                            <input class="form-control form-control-sm" name="cedulaRegistar" type="text" placeholder="Cédula" aria-label=".form-control-sm example" required>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-primary">Registrar Usuario</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <%
                /**
                 * Mostrar Mensajes emergentes del servlet "SvResgistrarUsuario"
                 */
                String alertaRegistro = (String) request.getSession().getAttribute("alertaRegistro");

                if (alertaRegistro != null && !alertaRegistro.isEmpty()) {//Se comprueba si la alerta es diferente de null o vacia
                    /**
                     * Si la alerta es true, se muestra un mensaje emergente que
                     * diga que se agregó el usaurio de forma corredcta
                     */
                    if (alertaRegistro.equals("true")) {
            %> 
            <script>
                Swal.fire({
                    title: "Usuario registrado",
                    text: "Se registró el usuario de forma exitosa",
                    icon: "success"
                });
            </script>
            <%
                }
                /**
                 * Si la alerta es false, se muestra un mensaje que diga que los
                 * credenciales ingresados ya estan en uso
                 */
                if (alertaRegistro.equals("false")) {
            %>
            <script>
                Swal.fire({
                    title: "Error",
                    text: "Las credenciales ingresadas ya estan en uso ",
                    icon: "error"
                });
            </script>
            <%
                    }
                }
                //Remover el atributo que recibimos por la sesion
                request.getSession().removeAttribute("alertaRegistro");
            %>
            <%
                /**
                 * Metodo para mostrar un mensaje emergente del servlet
                 * "SvIngreso"
                 */
                String alertaInicioSesion = (String) request.getSession().getAttribute("alertaInicioSesion");
                System.out.println("la alerta de inicio de sesion es: " + alertaInicioSesion);
                if (alertaInicioSesion != null && !alertaInicioSesion.isEmpty()) {

                    if (alertaInicioSesion.equals("false")) {
            %>    
            <script>
                Swal.fire({
                    title: "Error",
                    text: "No pudo ingresar a la plataforma, verifique sus credenciales ",
                    icon: "error"
                });
            </script>

            <%
                    }

                }
                //Remover el atributo que recibimos por la sesion
                request.getSession().removeAttribute("alertaInicioSesion");
            %>

            <%@include file="Templates/Foother.jsp" %>
