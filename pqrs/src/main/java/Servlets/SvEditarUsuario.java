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
@WebServlet(name = "SvEditarUsuario", urlPatterns = {"/SvEditarUsuario"})
public class SvEditarUsuario extends HttpServlet {

    //Llamado al metodo construcotr de usuario para acceder a sus metodos
    Usuario controladorUsuario = new Usuario();
    //Variable donde se guardara el id temporalmente
    String idEditarU;

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
        String idEditar = request.getParameter("idEditar");
        String editarConf = request.getParameter("editarConf");
        String nombreEditar = request.getParameter("nombreCompletoEditar");
        String celularEdiatr = request.getParameter("numeroTelefonoEditar");
        System.out.println("Nombre: " + nombreEditar);
        System.out.println("numero cel: " + celularEdiatr);
        System.out.println("la confirmacion es: " + editarConf);
        if (idEditar != null) {
            idEditarU = idEditar;
            System.out.println("EL ide que se va a editar es: " + idEditar);
        }
        if (editarConf == null && nombreEditar != null && celularEdiatr != null) {

            boolean usuarioEditado = controladorUsuario.editarUsuario(nombreEditar, celularEdiatr, idEditarU);
            System.out.println("el usuario fue editado ? " + usuarioEditado);
        }
        request.getRequestDispatcher("UsuariosRegistrados.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
