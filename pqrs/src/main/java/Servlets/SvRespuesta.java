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
@WebServlet(name = "SvRespuesta", urlPatterns = {"/SvRespuesta"})
public class SvRespuesta extends HttpServlet {

    //Llamado al metodo constructor de pqrs
    Pqrs controladorPqrs = new Pqrs();
    //Variable temporal de correoRemitente
    String correoRe = "";

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

        //Obtenemos el id de la pqrs que se va a responder
        String idPqrs = request.getParameter("idPqrsRespuesta");

        //Enviamos la pqrs a la pagina "repuesta.jsp"
        if (idPqrs != null) {
            HttpSession sesion = request.getSession();//Obtenemos la sesion
            sesion.setAttribute("idPqrs", idPqrs);//Enviamos el servlet a traves de la sesion
        }

        // obtenemos la respuesta de la pqrs
        String respuesta = request.getParameter("respuesta");
        String correoRemitente = request.getParameter("correoRemitente");

        if (correoRemitente != null) {
            correoRe = correoRemitente.trim();
        }
        if (!respuesta.equals("")) {
            //System.out.println("DENTRO: " + respuesta + " el correoRemitente es: " + correoRe);
            // Aquí verificamos si la respuesta se envió correctamente
            boolean respuestaAg = controladorPqrs.darRespuesta(correoRe, respuesta);
        }

        // Redirecciona a la página principal del usuario
        response.sendRedirect("respuesta.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
