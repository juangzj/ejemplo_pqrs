package Servlets;

import Modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan Goyes
 */
@WebServlet(name = "SvCambioRol", urlPatterns = {"/SvCambioRol"})
public class SvCambioRol extends HttpServlet {

    //LLamado al metodo constructor de Usuario para acceder a sus metodos
    Usuario controladorUsuario = new Usuario();
    //varriables donde se guardara el id temporalmente
    String idUA;
    String idAU;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Obtenemos el id de el usaurio que se va a cambiar de rol
        String rolAdministrador = request.getParameter("cambiarRolAdministrador");
        String rolUsuario = request.getParameter("cambiarRolUsuario");
        //Obtenemos la confirmacion en ambos casos
        String confirmacionUA = request.getParameter("usuarioAdministradorConf");
        String confirmacionAU = request.getParameter("administradorAusuarioConf");

        //cambiar de usaurio a adminsitrador
        if (rolAdministrador != null) { //
            //System.out.println("el cambio de rol de usuario es: " + rolAdministrador);
            idUA = rolAdministrador;
        }
        if (confirmacionUA != null && !confirmacionUA.isEmpty()) {
            controladorUsuario.cambiarRolUsuarioAAdministrador(idUA);
            //System.out.println("La confirmacion del cambio UA: " + confirmacionUA);
        }
        //Cambiar de administrador a usuario
        if (rolUsuario != null) {
            System.out.println("el cambio de rol de usuario es: " + rolUsuario);
            idAU = rolUsuario;

        }
        if (confirmacionAU != null && !confirmacionAU.isEmpty()) {
            //System.out.println("La confrimacion del cambio AU: " + confirmacionAU);
            controladorUsuario.cambiarRolAdministradorAUsuario(idAU);
        }

        request.getRequestDispatcher("Roles.jsp").forward(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
