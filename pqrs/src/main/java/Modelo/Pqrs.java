package Modelo;

import Conexion.Conectar;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Part;

/**
 *
 * @author Juan Goyes
 */
public class Pqrs {

    /**
     * Constantes para enviar correo
     */
    private final String CORREO_ENVIA = "ppqrs78@gmail.com";
    private final String CONTRASENIA_ENVIA = "tluzkzqwdhthjrmn";

    /**
     * Atributos de una pqrs
     */
    private String idPqrs;
    private String nombreUsuario;
    private String correoUsuario;
    private String mensaje;
    private String nombreArchivo;
    private byte[] archivoAdjunto;
    private int tipoPqrs;
    private String tipoPqrsS;
    private String estado;
    private String Respuesta;

    /**
     * Metodo constructor vacio de pqrs
     */
    public Pqrs() {

    }

    /**
     * Metdos getters y setters para accerder a los atributos de una pqrs
     */
    //=============================================================================
    public void setTipoPqrs(int tipoPqrs) {
        this.tipoPqrs = tipoPqrs;
    }

    public int getTipoPqrs() {
        return tipoPqrs;
    }

    public String getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(String Respuesta) {
        this.Respuesta = Respuesta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoPqrsS() {
        return tipoPqrsS;
    }

    public void setTipoPqrsS(String tipoPqrsS) {
        this.tipoPqrsS = tipoPqrsS;
    }

    public String getIdPqrs() {
        return idPqrs;
    }

    public void setIdPqrs(String idPqrs) {
        this.idPqrs = idPqrs;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public byte[] getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(byte[] archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }

    /**
     * Método para agregar una nueva PQRS a la base de datos asociada al usuario
     * en sesión.
     *
     * @param nombreUsuario Nombre del usuario que realiza la PQRS
     * @param correoElectronico Correo electrónico del usuario que realiza la
     * PQRS
     * @param tipoPqrs Tipo de PQRS (1: Peticion, 2: Queja, 3: Reclamo, 4:
     * Sugerencia)
     * @param mensaje Mensaje de la PQRS
     * @param archivoAdjunto Archivo adjunto (PDF) en forma de array de bytes
     * @param nombreArchivo Nombre del archivo adjunto
     * @param idUsuarioEnSesion ID del usuario que está en sesión
     * @return true si la PQRS se agregó correctamente, false en caso contrario
     */
    public boolean agregarPqrs(String nombreUsuario, String correoElectronico, int tipoPqrs, String mensaje, byte[] archivoAdjunto, String nombreArchivo, int idUsuarioEnSesion) {
        System.out.println("EL nombre del archivo es: " + nombreArchivo);
        boolean pqrsAgregada = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.Conectar.getConexion();

            // Convertir el tipo de PQRS de número a nombre
            String nombreTipoPqrs = null;
            switch (tipoPqrs) {
                case 1:
                    nombreTipoPqrs = "Peticion";
                    break;
                case 2:
                    nombreTipoPqrs = "Queja";
                    break;
                case 3:
                    nombreTipoPqrs = "Reclamo";
                    break;
                case 4:
                    nombreTipoPqrs = "Sugerencia";
                    break;
                default:
                    System.out.println("Tipo de PQRS inválido: " + tipoPqrs);
                    return false;
            }

            // Insertar la PQRS en la base de datos
            String query = "INSERT INTO pqrs (nombreUsuario, correoUsuario, mensaje, archivoAdjunto, nombreArchivo, idTipoPqrs, idUsuario) VALUES (?, ?, ?, ?, ?, (SELECT idTipoPqrs FROM tipo_pqrs WHERE nombre = ?), ?)";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombreUsuario);
            ps.setString(2, correoElectronico);
            ps.setString(3, mensaje);
            ps.setBytes(4, archivoAdjunto);
            ps.setString(5, nombreArchivo);
            ps.setString(6, nombreTipoPqrs);
            ps.setInt(7, idUsuarioEnSesion);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                // Obtener el ID generado de la PQRS
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    System.out.println("PQRS agregada con ID: " + generatedKeys.getInt(1));
                }
                pqrsAgregada = true;
                enviarGmail(correoElectronico, mensaje, nombreTipoPqrs, "Nuestro equipo revisará su solicitud y se pondrá en contacto con usted lo antes posible. \n " + "**Por favor, no responda a este correo, este correo ha sido generado automáticamente.** \n Gracias por comunicarse con nosotros. \n Atentemente, \n Equipo de JG ");
            }

        } catch (SQLException e) {
            System.out.println("Error al agregar la PQRS: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }

        return pqrsAgregada;
    }

    /**
     * Metodo para mostrar todas las pqrs creadas
     *
     * @return
     */
    public ArrayList<Pqrs> mostrarPqrss() {
        /**
         * Método para obtener todas las PQRS almacenadas en la base de datos.
         *
         * @return una lista de todas las PQRS almacenadas en la base de datos
         */
        ArrayList<Pqrs> listaPqrss = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.Conectar.getConexion();
            String query = "SELECT pq.idPQRS, pq.nombreUsuario, pq.correoUsuario, tp.nombre AS tipoPqrs, pq.mensaje, pq.nombreArchivo, pq.archivoAdjunto, pq.estado FROM pqrs pq JOIN tipo_pqrs tp ON pq.idTipoPqrs = tp.idTipoPqrs";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Pqrs pqrs = new Pqrs();
                pqrs.setIdPqrs(rs.getString("idPQRS"));
                pqrs.setNombreUsuario(rs.getString("nombreUsuario"));
                pqrs.setCorreoUsuario(rs.getString("correoUsuario"));
                pqrs.setTipoPqrsS(rs.getString("tipoPqrs")); // Setear el nombre del tipo de PQRS
                pqrs.setMensaje(rs.getString("mensaje"));
                pqrs.setNombreArchivo(rs.getString("nombreArchivo"));
                pqrs.setEstado(rs.getString("estado")); // Obtener el estado de la PQRS

                // Leer el archivo adjunto como un arreglo de bytes
                InputStream inputStream = rs.getBinaryStream("archivoAdjunto");
                if (inputStream != null) {
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();
                    pqrs.setArchivoAdjunto(buffer);
                }

                listaPqrss.add(pqrs);
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error al obtener las PQRS: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }

        return listaPqrss;
    }

    /**
     * Metodo para enviar correos
     *
     * @param correo
     * @param mensajePqrs
     * @param tipoPqrs
     * @param informacion
     */
    public void enviarGmail(String correo, String mensajePqrs, String tipoPqrs, String informacion) {

        Properties objectPEC = new Properties();

        objectPEC.put("mail.smtp.host", "smtp.gmail.com");
        objectPEC.setProperty("mail.smtp.starttls.enable", "true");
        objectPEC.put("mail.smtp.port", "587");
        objectPEC.setProperty("mail.smtp.user", CORREO_ENVIA);
        objectPEC.setProperty("mail.smtp.auth", "true");

        Session sesion = Session.getDefaultInstance(objectPEC);
        MimeMessage mail = new MimeMessage(sesion);

        try {
            mail.setFrom(new InternetAddress(CORREO_ENVIA));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            mail.setSubject(tipoPqrs);
            mail.setText(informacion);
            mail.setText(mensajePqrs + "\n\n"  + informacion);

            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(CORREO_ENVIA, CONTRASENIA_ENVIA);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();

            System.out.println("El correo se envio Correctamente");

        } catch (Exception e) {
            System.out.println("No se pudo enviar el correo");

        }
    }

    /**
     * Método para obtener toda la información de la PQRS mediante el id de la
     * PQRS
     *
     * @param id el ID de la PQRS que se desea obtener
     * @return un objeto Pqrs con toda la información de la PQRS, o null si no
     * se encuentra
     */
    public Pqrs pqrsInfo(String id) {
        Pqrs pqrs = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.Conectar.getConexion();
            String query = "SELECT pq.idPQRS, pq.nombreUsuario, pq.correoUsuario, pq.mensaje, pq.nombreArchivo, pq.archivoAdjunto, pq.estado, tp.nombre AS tipoPqrsS "
                    + "FROM pqrs pq "
                    + "INNER JOIN tipo_pqrs tp ON pq.idTipoPqrs = tp.idTipoPqrs "
                    + "WHERE pq.idPQRS = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, id.trim());
            rs = ps.executeQuery();

            if (rs.next()) {
                pqrs = new Pqrs();
                pqrs.setIdPqrs(rs.getString("idPQRS"));
                pqrs.setNombreUsuario(rs.getString("nombreUsuario"));
                pqrs.setCorreoUsuario(rs.getString("correoUsuario"));
                pqrs.setMensaje(rs.getString("mensaje"));
                pqrs.setNombreArchivo(rs.getString("nombreArchivo"));
                pqrs.setEstado(rs.getString("estado"));
                pqrs.setTipoPqrsS(rs.getString("tipoPqrsS")); // Nombre del tipo de PQRS

                // Obtener archivo adjunto como un arreglo de bytes
                pqrs.setArchivoAdjunto(rs.getBytes("archivoAdjunto"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la PQRS: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
        return pqrs;
    }

    /**
     * Metodo para dar respuesta a una PQRS
     *
     * @param correoRemitente
     * @param respuesta el texto de la respuesta
     * @return true si se pudo dar respuesta correctamente, false en caso
     * contrario
     */
    public boolean darRespuesta(String correoRemitente, String respuesta) {
        boolean respuestaE = false;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.Conectar.getConexion();
            String query = "UPDATE pqrs SET respuesta = ?, estado = 'Respondida' WHERE correoUsuario = ? AND estado = 'No leída'";
            ps = con.prepareStatement(query);
            ps.setString(1, respuesta);
            ps.setString(2, correoRemitente);

            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas > 0) {
                respuestaE = true;
                // Envía un correo electrónico al usuario notificándole la respuesta
                enviarGmail(correoRemitente, respuesta, "Respuesta a su PQRS ", "\n Esperamos que esta respuesta haya sido de ayuda, si tiene alguna otra pregunta o necesita más asistencia, no dude en contactarnos nuevamente. \n **Por favor, no responda a este correo, este correo ha sido generado automáticamente.** \n Gracias por su paciencia y comprensión. \n Atentamente, \n El equipo de JG");
            }
        } catch (SQLException e) {
            System.out.println("Error al dar respuesta a la PQRS: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
        return respuestaE;
    }

    /**
     * Metodo para ejecutar el archivo pdf
     *
     * @param id
     */
    public void ejecutarPdf(String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conexion = null;

        try {
            conexion = Conexion.Conectar.getConexion();

            // Consulta para obtener el archivo PDF según el ID
            String consulta = "SELECT archivoAdjunto FROM pqrs WHERE idPQRS = ?";
            ps = conexion.prepareStatement(consulta);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Obtener los bytes del archivo PDF
                byte[] bytesObtenidos = rs.getBytes("archivoAdjunto");
                // Crear un InputStream a partir de los bytes obtenidos
                InputStream inputStream = new ByteArrayInputStream(bytesObtenidos);

                // Especifica la ubicación donde se guardará el archivo PDF
                File file = new File("nuevoPdf.pdf");
                OutputStream outputStream = new FileOutputStream(file);

                // Copiar los bytes del InputStream al OutputStream
                int read;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

                // Cerrar flujos
                outputStream.close();
                inputStream.close();

                // Abrir el archivo PDF con la aplicación predeterminada
                if (Desktop.isDesktopSupported() && file.exists()) {
                    Desktop.getDesktop().open(file);
                } else {
                    System.out.println("El archivo no existe o no se puede abrir automáticamente.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar PreparedStatement y ResultSet
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo para eliminar una pqrs
     */
    public boolean eliminarPqrs(String idPqrs) {
        boolean eliminacionPqrs = false;
        Connection conexion = null;
        PreparedStatement pst = null;

        try {
            // Establecer conexión
            conexion = Conexion.Conectar.getConexion();

            // Consulta SQL para eliminar la PQRS
            String sql = "DELETE FROM pqrs WHERE idPQRS = ?";

            // Preparar la consulta
            pst = conexion.prepareStatement(sql);
            pst.setString(1, idPqrs);

            // Ejecutar la consulta
            int filasAfectadas = pst.executeUpdate();

            // Comprobar si se eliminó correctamente
            if (filasAfectadas > 0) {
                eliminacionPqrs = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar conexión y liberar recursos
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return eliminacionPqrs;
    }

    /**
     * Metodo para mostrar todas las PQRS realizadas por un usuario
     *
     * @param idUsuario El ID del usuario del cual se desean obtener las PQRS
     * @return ArrayList de objetos Pqrs
     */
    public ArrayList<Pqrs> obtenerPqrsUsuario(int idUsuario) {
        ArrayList<Pqrs> pqrsUsuario = new ArrayList<>();

        try {
            Connection con = Conexion.Conectar.getConexion();
            String query = "SELECT p.idPQRS, p.nombreUsuario, p.correoUsuario, p.mensaje, "
                    + "p.nombreArchivo, p.archivoAdjunto, t.nombre AS tipoPqrsS, "
                    + "p.estado, p.respuesta "
                    + "FROM pqrs p "
                    + "JOIN tipo_pqrs t ON p.idTipoPqrs = t.idTipoPqrs "
                    + "WHERE p.idUsuario = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idUsuario);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pqrs pqrs = new Pqrs();
                pqrs.setIdPqrs(rs.getString("idPQRS"));
                pqrs.setNombreUsuario(rs.getString("nombreUsuario"));
                pqrs.setCorreoUsuario(rs.getString("correoUsuario"));
                pqrs.setMensaje(rs.getString("mensaje"));
                pqrs.setNombreArchivo(rs.getString("nombreArchivo"));
                pqrs.setArchivoAdjunto(rs.getBytes("archivoAdjunto"));
                pqrs.setTipoPqrsS(rs.getString("tipoPqrsS"));
                pqrs.setEstado(rs.getString("estado"));
                pqrs.setRespuesta(rs.getString("respuesta"));

                pqrsUsuario.add(pqrs);
            }

            con.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener PQRS del usuario: " + e);
        }

        return pqrsUsuario;
    }
    

}
