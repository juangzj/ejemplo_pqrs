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
@WebServlet(name = "SvEliminarUsuario", urlPatterns = {"/SvEliminarUsuario"})
public class SvEliminarUsuario extends HttpServlet {

    //Llamado al método constructor de Usuario para acceder a sus métodos
    Usuario controladorUsuario = new Usuario();
    //Variable para guardar el correo que se va a eliminar temporalmente
    String correoE = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Obtenemos el correo del usuario que se va a eliminar
        String correoEliminar = request.getParameter("correoEliminar");
        String eliminarCom = request.getParameter("boton_eliminar");

        if (correoEliminar != null && !correoEliminar.isEmpty()) {
            correoE = correoEliminar.trim();
            System.out.println("entro al primer if");
        }
        if (eliminarCom != null && !eliminarCom.isEmpty()) {

            System.out.println("Se elimino el usuario con correo: " + correoE);
            controladorUsuario.eliminarUsuario(correoE);

            System.out.println("Se elimino el usuario con correo: " + correoE);
        }

        request.getRequestDispatcher("UsuariosRegistrados.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
