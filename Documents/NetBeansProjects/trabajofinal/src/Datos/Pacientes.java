/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author author
 */
import com.mysql.jdbc.Statement;
import Entidades.Paciente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import static trabajofinal.FrmSistema.fe;

public class Pacientes {

    //Información necesaria para realizar la conexion
    static final String DRIVER = "com.mysql.jdbc.Driver"; // Nombre del driver de MySQL 
    static final String servidor = "localhost"; // Servidor donde se ejecuta el servicio de MySQL. Puede ser un nombre o un numero de IP
    static final String DB = "clinica"; // Nombre de la base de datos donde se va a conectar
    static final String puerto = "3306"; // Puerto de escucha en el servidor de base de datos
    static final String usuario = "root"; // Usuario que tiene acceso a la base de datos
    static final String pws = ""; // Contraseña de usuario
    static final String URL = "jdbc:mysql://" + servidor + ":" + puerto + "/" + DB + "?" + "user=" + usuario + "&password=" + pws; // Direccion que se para
    // al driver de conexion para crear una nueva conexion
    public static final String TABLA = "pacientes"; // Nombre de la tabla dentro de la base de datos
    private Connection cn; // Objeto conexion que guarda toda la informacion necesaria para conectarse con la base de datos

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }

    /**
     * Metodo que devuelve verdadero si se puede crear un Objeto Connection
     *
     * @return
     */
    public boolean isOkConexion() {
        Connection cn = null;
        boolean isOk = false;
        try {
            Class.forName(DRIVER).newInstance(); // para saber si el driver esta y se puede cargar
            cn = DriverManager.getConnection(URL); // Genera un objeto connexion
            setCn(cn); // se setea guarda la un Objeto connexion en la clase para luego ser usado
            isOk = true; // bandera para poner en true y el metodo isOkConexion() me retorne verdadero
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return isOk;
    }

    /**
     * Metodo para deconectar la base de datos
     *
     * @return
     */
    public boolean isCloseConexion() {
        boolean isOk = false;
        try {
            if (getCn() != null && !getCn().isClosed()) {
                getCn().close();
                setCn(null);
                isOk = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return isOk;
        }
    }

    public boolean isCancelConexion() {
        boolean isOk = false;
        try {
            if (getCn() != null && !getCn().isClosed()) {
                getCn().close();
                setCn(null);
                isOk = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return isOk;
        }
    }

    /**
     * Metodo que recibe como parametro un Objeto tipo Client y lo REGISTRA en
     * la base de datos en la tabla clients
     *
     * @param c
     * @return true=registra, false=presencia de error
     */
    public boolean isNew(Paciente c) {
        boolean isOk = false;
        try {
            //Creo un Objeto tipo Statament (st), que es necesario para procesar sentencias SQL
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //Del Objeto Statement genero otro Objeto tipo ResultSet que almacena los resultados
            //de la consulta sql.
            //En este caso resultados en blanco porque en la consulta no hay registros con id=-1
            //Esto se hace para luego crear un nuevo registro en blanco
            ResultSet rs = st.executeQuery("SELECT * FROM " + TABLA + " WHERE id=-1");

            //Creo un nuevo registro en blanco para luego ir actualizando cada campo del registro
            rs.moveToInsertRow();

            //rs.updateInt("id", p.getId()); // como el campo de id es autoincremental no se hace mención
            //Actualizo los campos del nuevo registro con los datos que recibo del Objeto Client (c)
            rs.updateString("apellido", c.getLastName());
            rs.updateString("nombre", c.getName());
            rs.updateInt("dni_paciente", c.getDocument());
            rs.updateString("emails", c.getEmail());
            rs.updateString("telefonos", c.getMovils());
            //Pregunto si la información de fecha de nacimiento es null actualizo el campo de fecha con null
            //y uso el metodo updateDate, sino pasa la información de tiempo a un valor String y uso el metodo
            //updateString.
            if (c.getFecha() == null) {
                rs.updateDate("fecha", null);
            } else {
                rs.updateString("fecha", fe.getFechaMySQL(c.getFecha()));
            }
            

            //Por ultimo para que se termine de crear un registro en la base de datos/tabla, ejecuto el metodo insertRow
            rs.insertRow();

            //Cierro ResultSet (rs) y Statament (st), es indispensable NO Olvidarse de cerrar
            //estos objetos. De no ser asi, NO se registrara que se creo un registro
            rs.close();
            st.close();

            //Por Ultimo pongo a la bandera del metodo en true para indicar que no ha ocurrido ningun error
            //y el metodo tubo exito
            //Nota: es importante definir a lo ultimo del codigo esta bandera indicando que todos los pasos
            //fueron realizados con exito
            isOk = true;

        } catch (SQLException e) {
            //Si hay alguna excepción en algun momento relacionado con algun metodo mal escrito o mal ejecutado, o bien
            //problemas en la red para ejecutar los metodos se sucede esta seccion de codigo.
            System.out.println(e.getMessage());
        } finally {
            return isOk;
        }
    }

    /**
     * Metodo que recibe como parametro un Objeto tipo Client y lo ACTUALIZA en
     * la base de datos en la tabla clients
     *
     * @param c
     * @return true=registra, false=presencia de error
     */
    public boolean isUpdate(Paciente c) {
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + TABLA + " WHERE id=" + c.getId());

            //Metodo next() que indica si puedo ir al nuevo resultado del ResultSet, indicando que tengo un registro.
            //Pueden investigar si funciona tambien con el metodo rs.first()
            if (rs.next()) {
                //Me ubico en el resultado y actualizo los campos
                rs.updateString("apellido", c.getLastName());
                rs.updateString("nombre", c.getName());
                rs.updateInt("dni_paciente", c.getDocument());
                rs.updateString("emails", c.getEmail());
                rs.updateString("telefonos", c.getMovils());
                rs.updateInt("Id_obrasocial", c.getId_obrasocial());
                //Pregunto si la información de fecha de nacimiento es null actualizo el campo de fecha con null
                //y uso el metodo updateDate, sino pasa la información de tiempo a un valor String y uso el metodo
                //updateString.
                if (c.getFecha() == null) {
                    rs.updateDate("fecha", null);
                } else {
                    rs.updateString("fecha", fe.getFechaMySQL(c.getFecha()));
                }
              

                //Metodo para atualizar resultado(registro) en la base de datos/tabla
                rs.updateRow();
                isOk = true;
            }
            rs.close();
            st.close();

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        } finally {
            return isOk;
        }
    }

    /**
     * Metodo que recibe como parametro un Objeto tipo Client y lo BORRA en la
     * base de datos en la tabla cliens
     *
     * @param c
     * @return true=registra, false=presencia de error
     */
    public boolean isDelete(Paciente c) {
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + TABLA + " WHERE id=" + c.getId());

            if (rs.next()) {
                //Al ejecutar el metodo deleteRow() confirmo los campos en la base de datos/tabla
                rs.deleteRow();
                isOk = true;
            }
            rs.close();
            st.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return isOk;
        }
    }

    /**
     * Metodo que BORRA TODOS paciente de la base de datos
     *
     * @return true=registra, false=presencia de error
     */
    public boolean isDeleteAll() {
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            isOk = st.executeUpdate("DELETE FROM " + TABLA) > 0;

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return isOk;
        }
    }/*

    /**
     * Metodo que recibo una consulta SQL en String y retorna un ArrayList de
     * paciente
     *
     * @param query
     * @return ArrayList
     */
    public ArrayList<Paciente> listPaciente(String query) {
        ArrayList<Paciente> list = new ArrayList<Paciente>();
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Paciente paciente = new Paciente(rs.getInt("id"),
                        rs.getString("apellido"),
                        rs.getString("nombre"),
                        rs.getInt("dni_paciente"),
                        rs.getInt("id_obrasocial"),
                        rs.getString("telefonos"),
                        rs.getString("emails"),
                        fe.getFechaACalendar(rs.getDate("fecha"))
                        //fe.getCalendar(rs.getTimestamp("lastupdate"))
                );

                list.add(paciente);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return list;
        }
    }

    /**
     * Metodo que recibo codigo de client y retorna un Objeto de tipo Client,
     * sino encuentra el client el Objeto que retorna es nulo (null)
     *
     * @param id
     * @return Client
     */
    public Paciente getPaciente(int id) {
        Paciente c = null;
        //Saber si me puedo Conectar
        if (isOkConexion()) {
            //Genero consulta SQL
            String query = "SELECT * FROM " + TABLA + " WHERE id=" + id;
            //Reutilizo metodo de listClients con SQL en busqueda de client
            ArrayList<Paciente> pacienteList = listPaciente(query);
            isCloseConexion();//Cierro Conexion

            //Pregunto si el ArrayList en respuesta del metodo listarPacinetes(***) tiene un Objeto
            if (pacienteList.size() == 1) {
                //Si tiene acceso a la primer posicion
                Paciente cList = pacienteList.get(0);
                //hago una nueva instancia de c(Client) con el valor de la posicion 0
                c = new Paciente(
                        cList.getId(),
                        cList.getLastName(),
                        cList.getName(),
                        cList.getDocument(),
                        cList.getId_obrasocial(),
                        cList.getEmail(),
                        cList.getMovils(),
                        cList.getFecha()
                        //cList.getLastupdate()
                );
            }
        }
        //Retorno el valor del Objeto c
        return c;
    }
}
