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
@WebServlet(name = "SvIngreso", urlPatterns = {"/SvIngreso"})
public class SvIngreso extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los parámetros del formulario de inicio de sesión
        String correoIngresar = request.getParameter("correoIngresar");
        String contraseniaIngresar = request.getParameter("contraseniaIngresar");
        // Variable para controlar la alerta de inicio de sesión
        String alertaInicioSesion = "false";

        // Verificar si se proporcionaron las credenciales de inicio de sesión
        if (correoIngresar != null && contraseniaIngresar != null) {
            // Crear un objeto de controlador de usuario
            Usuario controladorUsuario = new Usuario();
            // Verificar si las credenciales de inicio de sesión son válidas
            boolean inicioSesion = controladorUsuario.inicioSesionUsuario(correoIngresar, contraseniaIngresar);
            if (inicioSesion) {
                // Obtener la sesión actual o crear una nueva
                HttpSession miSesion = request.getSession();
                // Obtener los detalles del usuario que ha iniciado sesión
                Usuario usuarioEnSesion = controladorUsuario.conseguirUsuario(correoIngresar);
                // Establecer el usuario en sesión como un atributo de sesión
                miSesion.setAttribute("usuarioEnSesion", usuarioEnSesion);

                // Verificar si el usuario es administrador
                if (controladorUsuario.esAdministrador(correoIngresar)) {
                    // Redireccionar a la interfaz del administrador
                    response.sendRedirect("AdministradorInterfaz.jsp");
                    return;
                } else {
                    // Redireccionar a la interfaz principal del usuario
                    response.sendRedirect("UsuarioInterfazPrincipal.jsp");
                    return;
                }
            } else {
                // Si las credenciales son incorrectas, configurar la alerta de inicio de sesión
                alertaInicioSesion = "true";
            }
        }

        // Si no se proporcionaron credenciales o si las credenciales son incorrectas,
        // configurar la alerta de inicio de sesión y redireccionar a la página de inicio de sesión
        HttpSession miSesion = request.getSession();
        miSesion.setAttribute("alertaInicioSesion", alertaInicioSesion);
        response.sendRedirect("index.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar el inicio de sesión de usuarios";
    }
}
