package Servlets;

import Modelo.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Juan Goyes
 */
@WebServlet(name = "SvRegistrarUsuario", urlPatterns = {"/SvRegistrarUsuario"})
public class SvRegistrarUsuario extends HttpServlet {

    // Instanciar el controlador de Usuario para acceder a sus métodos
    Usuario controladorUsuario = new Usuario();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Método para procesar la solicitud
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        // Variables para el mensaje emergente de registro
        String alertaRegistro = "";

        // Obtener los datos del formulario de registro
        String nombreCompleto = request.getParameter("nombreCompletoRegistar");
        String numeroTelefono = request.getParameter("numeroTelefonoRegistar");
        String correoRegistrar = request.getParameter("correoRegistrar");
        String contraseniaRegistrar = request.getParameter("contraseniaRegistrar");
        String cedulaRegistrar = request.getParameter("cedulaRegistar");

        // Verificar si se proporcionaron credenciales
        if (correoRegistrar != null && contraseniaRegistrar != null && cedulaRegistrar != null) {
            // Comprobar si el usuario ya está registrado
            boolean comprobacionUsuarioExiste = controladorUsuario.usuarioExiste(correoRegistrar, contraseniaRegistrar);

            if (!comprobacionUsuarioExiste) {
                // Registrar al usuario si no está registrado
                boolean registroExitoso = controladorUsuario.registraUsuario(nombreCompleto, numeroTelefono, correoRegistrar, contraseniaRegistrar, cedulaRegistrar);
                alertaRegistro = "true"; // Para mostrar un mensaje emergente que diga que el usuario se registró de forma exitosa
            } else {
                // Mostrar mensaje de error si el usuario ya está registrado
                alertaRegistro = "false"; // Para mostrar un mensaje emergente que diga que las credenciales registradas ya están en uso
            }
        }

        // Establecer la alerta de registro en la sesión
        HttpSession miSesion = request.getSession();
        miSesion.setAttribute("alertaRegistro", alertaRegistro);

        // Redireccionar a la página "index.jsp"
        response.sendRedirect("index.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Servlet para registrar usuarios";
    }
}
