package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/04/20.
 */
import android.os.StrictMode;
import android.util.Log;
import net.sourceforge.jtds.jdbc.Driver;
import java.sql.*;

public class ConexionBD {

    private static ConexionBD instance = null;
    //Adicional
    private static final String Servidor = "192.168.1.110";
    private static final String BaseDatos = "ADRSYSTEM";

    //private static final String URL = "jdbc:jtds:sqlserver://192.168.1.110/ADRSYSTEM;";
    private static final String URL = "jdbc:jtds:sqlserver://"+Servidor+"/"+BaseDatos+";";
    private static final String USER = "sa";
    private static final String PASS = "admin2010";
    private static Connection connection = null;
    private static final String TAG = "ConexionBD";
    private ConexionBD() {};

    public static ConexionBD getInstance() {
        if (instance == null)
            instance = new ConexionBD();
        return instance;
    }
    public Connection getConnection() {
        if (connection == null)
            connection = Conectar();
        return connection;
    }
    private Connection Conectar() {
        Connection conn = null;
        try {
            (new Driver()).getClass();
            //Class.forName("net.sourceforge.jtds.jdbc.Driver");
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            Log.e(TAG, "Error SQLException: " + e);
        } catch (Exception e) {
            Log.e(TAG, "Error Exception: " + e);
        }
        return conn;
    }
}
