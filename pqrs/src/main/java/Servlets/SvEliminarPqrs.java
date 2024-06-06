package Servlets;

import Modelo.Pqrs;
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
@WebServlet(name = "SvEliminarPqrs", urlPatterns = {"/SvEliminarPqrs"})
public class SvEliminarPqrs extends HttpServlet {

    //Llamado al metodo constructor de Pqrs para acceder a sus metodos
    Pqrs controladorPqrs = new Pqrs();
    //Variable para la alerta de eliminacion de una pqrs
    String alertaPqrsEliminacion = "";

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
        processRequest(request, response);
        //Obtenemos el id de la PQRS que se va a eliminar
        String idPqrs = request.getParameter("eliminarPqrsId");

        if (idPqrs != null) {
            //Llamado al metodo para eliminar la PQRS
            boolean eliminacionPqrs = controladorPqrs.eliminarPqrs(idPqrs);

            //Enviar la variable booleana de la alerta de eliminacion de una pqrs
            if (eliminacionPqrs) {
                alertaPqrsEliminacion = "true";
            } else {
                alertaPqrsEliminacion = "false";
            }

            HttpSession sesion = request.getSession();
            sesion.setAttribute("alertaPqrsEliminacion", alertaPqrsEliminacion);
        }

        // Redireccionar al usuario a la página anterior
        String referer = request.getHeader("referer");
        response.sendRedirect(referer);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
