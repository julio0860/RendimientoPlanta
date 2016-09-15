package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/13.
 */
public class T_Estado {

    public static final String ESTID ="Est_Id";
    public static  final String ESTDESCRIPCION ="Est_Descripcion";
    public static final String ESTABREV = "Est_Abreviatura";


    public static final String N_TABLA = "Estado";

    public static final String CREATE_T_ESTADO ="CREATE TABLE " + N_TABLA+"("+
            ESTID +" INTEGER PRIMARY KEY NOT NULL, "+
            ESTDESCRIPCION +" TEXT NOT NULL, "+
            ESTABREV+" TEXT NOT NULL "+
            ");";

    public static final String DROP_T_ESTADO ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(Integer ESID,String DESCRIPCION,String ABREV)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+ESTID+","+ESTDESCRIPCION+","+ESTABREV+
                ")VALUES('"+
                ESID+"','"+DESCRIPCION+"','"+ABREV+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_EST(String ABREV)
    {
        String _SELECT;
        _SELECT = "SELECT "+ESTID+","+ESTDESCRIPCION+","+ESTABREV
                +" FROM "+N_TABLA;

        if (ABREV.length()>0)
        {
            _SELECT=_SELECT+" WHERE "+ESTABREV+"='"+ABREV+"';";
        }
        return _SELECT;
    }
}
