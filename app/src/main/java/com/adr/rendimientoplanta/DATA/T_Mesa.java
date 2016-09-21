package com.adr.rendimientoplanta.DATA;

/**
 * Created by jcasas on 21/09/2016.
 */
public class T_Mesa {
    public static final String POSICION = "Posicion";
    public static final String DESCRIPCION = "Descripcion";
    public static final String N_TABLA = "Mesa";

    public static final String CREATE_T_MESA = "CREATE TABLE " + N_TABLA + "(" +

            POSICION + " INTEGER NOT NULL, " +
            DESCRIPCION + " TEXT NOT NULL " +
            ");";

    public static final String DROP_T_MESA = "DROP TABLE IF EXISTS " + N_TABLA;

    public static String _INSERT(int Posicion, String Descripcion)

    {
        String _INSERT;
        _INSERT = "INSERT INTO " + N_TABLA + "(" + POSICION + "," +
                "," + DESCRIPCION +
                ")VALUES('" +
                Posicion + "','" + Descripcion + "');";
        return _INSERT;
    }

    public static String _DELETE() {
        String _DELETE;
        _DELETE = "DELETE FROM " + N_TABLA + ";";
        return _DELETE;
    }

    public static String _SELECT_MESA()
    {
        String _SELECT;
        _SELECT = "SELECT "+POSICION+" AS '_id',"+
                ","+DESCRIPCION
                +" FROM "+N_TABLA;

        return _SELECT;
    }
}
