package com.adr.rendimientoplanta.DATA;

/**
 * Created by jcasas on 15/09/2016.
 */
public class T_Subproceso {

    public static final String SUBID ="Sub_Id";
    public static final String PROID = "Pro_Id";
    public static final String SUBDESCRIPCION = "Sub_Descripcion";




    public static final String N_TABLA = "Subproceso";

    public static final String CREATE_T_SUBPROCESO ="CREATE TABLE " + N_TABLA+"("+
            SUBID +" INTEGER PRIMARY KEY NOT NULL, "+
            PROID +" INTEGER NOT NULL, "+
            SUBDESCRIPCION +" TEXT NOT NULL "+

            ");";

    public static final String DROP_T_SUBPROCESO ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int ID,int Pro_Id,String DESCRIPCION)

    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+SUBID+","+PROID+","+SUBDESCRIPCION+
                ")VALUES('"+
                ID+"','"+Pro_Id+"','"+DESCRIPCION+"');";
        return _INSERT;
    }

    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_SUBPROCESO(int ID) {
        String _SELECT;
        _SELECT = "SELECT " + SUBID + " AS '_id'," + PROID + "," + SUBDESCRIPCION

                + " FROM " + N_TABLA;
        if (ID != -1) {
            _SELECT = _SELECT + " WHERE " + PROID + "='" + ID + "'";
        }
        _SELECT = _SELECT + " ORDER BY " + SUBDESCRIPCION + " ASC;";
        return _SELECT;
    }
}
