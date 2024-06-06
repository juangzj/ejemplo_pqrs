package Modelo;

import Conexion.Conectar;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Juan Goyes
 */
public class Usuario {

    /**
     * Atributos de un usuario
     */
    private int idUsuario;
    private String nombreCompleto;
    private String numCelular;
    private String correo;
    private String contrasenia;
    private String cedula;
    private String tipoUsuario;

    /**
     * Contructor vacio de un usuario
     */
    public Usuario() {
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Metodos getters y setters
     */
    //==========================================================================
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNumCelular() {
        return numCelular;
    }

    public void setNumCelular(String numCelular) {
        this.numCelular = numCelular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    //==========================================================================

    /**
     * Metodo para verififcar si un usuario existe dentro de la base de datos
     *
     * @param correo
     * @param cedula
     * @return
     */
    public boolean usuarioExiste(String correo, String cedula) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.Conectar.getConexion();
            String sql = "SELECT * FROM usuario WHERE correo = ? OR cedula = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, cedula);
            rs = stmt.executeQuery();

            if (rs.next()) {
                existe = true; // Usuario encontrado en la base de datos
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return existe;
    }

    /**
     * Metodo para registrar un usuario
     *
     * @param nombreCompleto
     * @param numeroTelefono
     * @param correo
     * @param contrasenia
     * @param cedula
     * @return
     */
    public boolean registraUsuario(String nombreCompleto, String numeroTelefono, String correo, String contrasenia, String cedula) {
        boolean registroExitoso = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.Conectar.getConexion();
            String sql = "INSERT INTO usuario (nombreCompleto, numeroTelefono, correo, contrasenia, cedula, tipoUsuario) VALUES (?, ?, ?, ?, ?, 1)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nombreCompleto);
            stmt.setString(2, numeroTelefono);
            stmt.setString(3, correo);
            stmt.setString(4, contrasenia);
            stmt.setString(5, cedula);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                registroExitoso = true;
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    System.out.println("Usuario registrado con ID: " + idGenerado);
                } else {
                    System.err.println("No se pudo obtener el ID generado");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return registroExitoso;
    }

    /**
     * Metodo para verificar el inicio de sesion un usuario
     *
     * @param correo
     * @param contrasenia
     * @return
     */
    public boolean inicioSesionUsuario(String correo, String contrasenia) {
        boolean comprobacionSesion = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.Conectar.getConexion();
            String sql = "SELECT * FROM usuario WHERE correo = ? AND contrasenia = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, contrasenia);
            rs = stmt.executeQuery();

            if (rs.next()) {
                comprobacionSesion = true; // Usuario y contraseña válidos
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return comprobacionSesion;
    }

    /**
     * Metodo para obtener los Usuarios desde la base de datos
     *
     * @return
     */
    /**
     * Metodo para obtener los Usuarios desde la base de datos
     *
     * @return ArrayList de objetos Usuario
     */
    public ArrayList<Usuario> obtenerUsuarios() {
        ArrayList<Usuario> usuariosRegistrados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.Conectar.getConexion();
            String sql = "SELECT u.*, tu.nombre AS tipoUsuario FROM usuario u JOIN tipo_usuario tu ON u.tipoUsuario = tu.idTipoUsuario";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombreCompleto(rs.getString("nombreCompleto"));
                usuario.setNumCelular(rs.getString("numeroTelefono"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContrasenia(rs.getString("contrasenia"));
                usuario.setCedula(rs.getString("cedula"));
                usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                usuariosRegistrados.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuariosRegistrados;
    }

    /**
     * Metodo para eliminar un usuario
     *
     * @param correo
     * @return
     */
    public boolean eliminarUsuario(String correo) {
        boolean eliminacion = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.Conectar.getConexion();
            String sql = "DELETE FROM usuario WHERE correo = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                eliminacion = true; // Usuario eliminado exitosamente
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return eliminacion;
    }

    /**
     * Metodo para conseguir el usuario en sesion
     *
     * @param correo
     * @return
     */
    public Usuario conseguirUsuario(String correo) {
        Usuario usuarioEnSesion = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.Conectar.getConexion();
            String sql = "SELECT * FROM usuario WHERE correo = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                usuarioEnSesion = new Usuario();
                usuarioEnSesion.setIdUsuario(rs.getInt("idUsuario"));
                usuarioEnSesion.setNombreCompleto(rs.getString("nombreCompleto"));
                usuarioEnSesion.setCorreo(rs.getString("correo"));
                usuarioEnSesion.setNumCelular(rs.getString("numeroTelefono"));
                usuarioEnSesion.setCedula(rs.getString("cedula"));
                // Obtén más atributos si es necesario
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuarioEnSesion;
    }

    /**
     * Metodo para verificar si el usuario es administrador o usaurio normal de
     * acuerdo a su correo
     *
     * @param correo
     * @return
     */
    public boolean esAdministrador(String correo) {
        boolean esAdmin = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.Conectar.getConexion();
            String query = "SELECT COUNT(*) AS isAdmin FROM usuario WHERE correo = ? AND tipoUsuario = 2";
            ps = con.prepareStatement(query);
            ps.setString(1, correo);
            rs = ps.executeQuery();

            if (rs.next()) {
                int isAdmin = rs.getInt("isAdmin");
                if (isAdmin > 0) {
                    esAdmin = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }

        return esAdmin;
    }

    /**
     * Metodo para obtener el id del usuario a partir de su correo
     *
     * @param usuario Objeto Usuario con el correo del usuario
     * @return id del usuario o -1 si no se encuentra
     */
    public int obtenerIdUsuario(Usuario usuario) {
        int idUsuario = -1;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.Conectar.getConexion();
            String query = "SELECT idUsuario FROM usuario WHERE correo = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, usuario.getCorreo());
            rs = ps.executeQuery();

            if (rs.next()) {
                idUsuario = rs.getInt("idUsuario");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el ID del usuario: " + e.getMessage());
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

        return idUsuario;
    }

    /**
     * Metodo para obtener el usuario de acuerdo al id de la pqrs
     *
     * @param idPqrs el ID de la PQRS de la que se desea obtener el usuario
     * @return un objeto Usuario que representa al usuario asociado a la PQRS, o
     * null si no se encuentra
     */
    public Usuario usuarioPqrs(String idPqrs) {
        Usuario usuario = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.Conectar.getConexion();
            String query = "SELECT u.idUsuario, u.nombreCompleto, u.numeroTelefono, u.correo, u.contrasenia, u.cedula, tu.nombre AS tipoUsuario "
                    + "FROM usuario u "
                    + "INNER JOIN pqrs p ON u.idUsuario = p.idUsuario "
                    + "INNER JOIN tipo_usuario tu ON u.tipoUsuario = tu.idTipoUsuario "
                    + "WHERE p.idPQRS = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, idPqrs.trim());
            rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombreCompleto(rs.getString("nombreCompleto"));
                usuario.setNumCelular(rs.getString("numeroTelefono"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContrasenia(rs.getString("contrasenia"));
                usuario.setCedula(rs.getString("cedula"));
                usuario.setTipoUsuario(rs.getString("tipoUsuario"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el usuario: " + e.getMessage());
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
        return usuario;
    }

    /**
     * Método para cambiar un usuario normal a administrador
     *
     * @param idUsuario el ID del usuario a cambiar
     * @return true si se realizó el cambio correctamente, false en caso
     * contrario
     */
    public boolean cambiarRolUsuarioAAdministrador(String idUsuario) {
        boolean cambioUsuarioAAdministrador = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = Conexion.Conectar.getConexion();

            String sql = "UPDATE usuario SET tipoUsuario = ? WHERE idUsuario = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, 2); // Tipo de usuario Administrador
            pstmt.setInt(2, Integer.parseInt(idUsuario.trim()));

            int filasActualizadas = pstmt.executeUpdate();

            if (filasActualizadas > 0) {
                cambioUsuarioAAdministrador = true;
                System.out.println("El usuario ha sido cambiado a administrador.");
            } else {
                System.out.println("No se pudo cambiar el rol del usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cambiar el rol del usuario: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return cambioUsuarioAAdministrador;
    }

    /**
     * Método para cambiar un administrador a usuario normal
     *
     * @param id el ID del usuario a cambiar
     * @return true si se realizó el cambio correctamente, false en caso
     * contrario
     */
    public boolean cambiarRolAdministradorAUsuario(String id) {
        boolean cambioAdministradorAUsuario = false;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = Conexion.Conectar.getConexion();

            String sql = "UPDATE usuario SET tipoUsuario = ? WHERE idUsuario = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, 1); // Tipo de usuario Normal
            pstmt.setInt(2, Integer.parseInt(id));

            int filasActualizadas = pstmt.executeUpdate();

            if (filasActualizadas > 0) {
                cambioAdministradorAUsuario = true;
                System.out.println("El administrador ha sido cambiado a usuario normal.");
            } else {
                System.out.println("No se pudo cambiar el rol del administrador.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cambiar el rol del administrador: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return cambioAdministradorAUsuario;
    }

    /**
     * Metodo para editar un usuario
     *
     * @param nombreCompleto
     * @param numeroTelefono
     * @param id
     * @return
     */
    public boolean editarUsuario(String nombreCompleto, String numeroTelefono, String id) {
        boolean usuarioEditado = false;

        try {
            Connection con = Conexion.Conectar.getConexion();
            String query = "UPDATE usuario SET nombreCompleto = ?, numeroTelefono = ? WHERE idUsuario = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nombreCompleto);
            ps.setString(2, numeroTelefono);
            ps.setString(3, id.trim());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                usuarioEditado = true;
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Error al editar usuario: " + e);
        }
        return usuarioEditado;
    }

}
