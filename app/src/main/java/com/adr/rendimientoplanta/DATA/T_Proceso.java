package com.adr.rendimientoplanta.DATA;

/**
 * Created by jcasas on 15/09/2016.
 */
public class T_Proceso {
    public static final String PROID ="Pro_Id";
    public static final String PROCODIGO = "Pro_Codigo";
    public static final String PRODESCRIPCION = "Pro_Descripcion";



    public static final String N_TABLA = "Proceso";

    public static final String CREATE_T_PROCESO ="CREATE TABLE " + N_TABLA+"("+
            PROID +" INTEGER PRIMARY KEY NOT NULL, "+
            PROCODIGO +" TEXT NOT NULL, "+
            PRODESCRIPCION +" TEXT NOT NULL, "+
            ");";

    public static final String DROP_T_PROCESO ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int ID,String CODIGO,String DESCRIPCION)

    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+PROID+","+PROCODIGO+","+PRODESCRIPCION+","+
                ")VALUES('"+
                ID+"','"+CODIGO+"','"+DESCRIPCION+"');";
        return _INSERT;
    }

    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
}
