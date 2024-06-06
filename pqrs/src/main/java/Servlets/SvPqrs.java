package Servlets;

import Modelo.Pqrs;
import Modelo.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Juan Goyes
 */
@WebServlet(name = "SvPqrs", urlPatterns = {"/SvPqrs"})
@MultipartConfig // Anotación que indica que este servlet puede procesar solicitudes multipartes (con archivos adjuntos)
public class SvPqrs extends HttpServlet {

    Usuario controladorUsuario = new Usuario(); // Controlador de Usuario
    Pqrs controladorPqrs = new Pqrs(); // Controlador de PQRS
    String alertaPqrsAg = ""; //Variable para mostrar mensaje 

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
        HttpSession miSesion = request.getSession(); // Obtiene la sesión actual
        Usuario usuarioEnSesion = (Usuario) miSesion.getAttribute("usuarioEnSesion"); // Obtiene el usuario en sesión

        // Obtención de los parámetros de la solicitud
        String nombreUsuario = request.getParameter("nombreUsuario");
        String correoUsuario = request.getParameter("correoUsuario");
        String tipoPqrsS = request.getParameter("tipoPqrs").trim();
        int tipoPqrs = Integer.parseInt(tipoPqrsS);
        String mensaje = request.getParameter("mensaje");
        Part archivoPart = request.getPart("archivoAdjunto");
        String nombreArchivo = Paths.get(archivoPart.getSubmittedFileName()).getFileName().toString();
        byte[] archivoContenido = null;

        System.out.println("Archivo: " + nombreArchivo); // Imprime el nombre del archivo adjunto

        if (nombreArchivo != null) {
            // Lee el contenido del archivo adjunto
            try (InputStream inputStream = archivoPart.getInputStream()) {
                archivoContenido = inputStream.readAllBytes();
            }
        }

        if (usuarioEnSesion != null && nombreUsuario != null && correoUsuario != null) {
            // Obtiene el ID del usuario en sesión
            int idUsuarioEnSesion = controladorUsuario.obtenerIdUsuario(usuarioEnSesion);
            // Agrega la PQRS a la base de datos
            boolean pqrsAgregada = controladorPqrs.agregarPqrs(nombreUsuario, correoUsuario, tipoPqrs, mensaje, archivoContenido, nombreArchivo, idUsuarioEnSesion);
            // System.out.println("¿Se agregó la PQRS? " + pqrsAgregada); // Imprime si la PQRS se agregó o no

            //Mostrar Mensaje emergente
            if (pqrsAgregada) {
                alertaPqrsAg = "true";//Cambiamos la varibale para mostrar el mensaje emergente
                HttpSession sesion = request.getSession();//obtenemos la sesion 
                sesion.setAttribute("alertaPqrsAg", alertaPqrsAg);//Mandamos la variable boolean de la alerta

            } else if (pqrsAgregada == false) {
                alertaPqrsAg = "false";//Cambiamos la varibale para mostrar el mensaje emergente
                HttpSession sesion = request.getSession();//obtenemos la sesion 
                sesion.setAttribute("alertaPqrsAg", alertaPqrsAg);//Mandamos la variable boolean de la alerta

            }

        }

        // Redirecciona a la página principal del usuario
        request.getRequestDispatcher("UsuarioInterfazPrincipal.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
