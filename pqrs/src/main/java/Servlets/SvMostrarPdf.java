package Servlets;

import Modelo.Pqrs;
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
@WebServlet(name = "SvMostrarPdf", urlPatterns = {"/SvMostrarPdf"})
public class SvMostrarPdf extends HttpServlet {

    //LLamado al metodo constructor de pqrs para acceder a sus metodos
    Pqrs controladorPqrs = new Pqrs();

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
        String idPqrs = request.getParameter("idPqrsPdf");

        //Llamado al metodo para mostrar pdf
        controladorPqrs.ejecutarPdf(idPqrs.trim());

       // System.out.println("la id de la pqrs es: " + idPqrs);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
