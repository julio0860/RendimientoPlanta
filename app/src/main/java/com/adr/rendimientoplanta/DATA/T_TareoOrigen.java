package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/19.
 */
public class T_TareoOrigen {
    public static final String TARORIID ="TarOri_Id";
    public static  final String EMPID ="Emp_Id";
    public static final String ESTID = "Est_Id";
    public static final String TARORICODIGO = "TarOri_Codigo";
    public static final String TARORIDESCRIPCION = "TarOri_Descripcion";



    public static final String N_TABLA = "TareoOrigen";

    public static final String CREATE_T_TAREO_ORIGEN ="CREATE TABLE " + N_TABLA+"("+
            TARORIID +" INTEGER PRIMARY KEY NOT NULL, "+
            EMPID +" INTEGER NOT NULL, "+
            ESTID +" INTEGER NOT NULL, "+
            TARORICODIGO +" TEXT NOT NULL, "+
            TARORIDESCRIPCION +" TEXT NOT NULL "+
            ");";

    public static final String DROP_T_TAREO_ORIGEN ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int ID,int EMID,int ESID,String CODIGO,
                                 String DESCRIPCION)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+TARORIID+","+EMPID+","+ESTID+","+TARORICODIGO+
                ","+TARORIDESCRIPCION+
                ")VALUES('"+
                ID+"','"+EMID+"','"+ESID+"','"+CODIGO+"','"+DESCRIPCION+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_TAREO_ORIGEN(int EMID)
    {
        String _SELECT;
        _SELECT = "SELECT "+TARORIID+" AS '_id',"+EMPID+","+ESTID+","+TARORICODIGO+
                ","+TARORIDESCRIPCION+" FROM "+N_TABLA;
        if (EMID!=-1)
        {
            _SELECT=_SELECT+" WHERE "+EMPID+"='"+EMID+"';";
        }
        return _SELECT;
    }

}
