package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/19.
 */
public class T_Actividad {
    public static final String ACTID ="Act_Id";
    public static final String ESTID = "Est_Id";
    public static  final String ACTCODIGO ="Act_Codigo";
    public static final String ACTDESCRIPCION = "Act_Descripcion";
    public static final String EMPID = "Emp_Id";



    public static final String N_TABLA = "Actividad";

    public static final String CREATE_T_ACTIVIDAD ="CREATE TABLE " + N_TABLA+"("+
            ACTID +" INTEGER PRIMARY KEY NOT NULL, "+
            ESTID +" INTEGER NOT NULL, "+
            ACTCODIGO +" TEXT NOT NULL, "+
            ACTDESCRIPCION +" TEXT NOT NULL, "+
            EMPID +" INTEGER NOT NULL "+
            ");";

    public static final String DROP_T_ACTIVIDAD ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int ID,int ESID,String CODIGO,String DESCRIPCION,
                                 int EMID)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+ACTID+","+ESTID+","+ACTCODIGO+","+ACTDESCRIPCION+
                ","+EMPID+
                ")VALUES('"+
                ID+"','"+ESID+"','"+CODIGO+"','"+DESCRIPCION+"','"+EMID+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_ACTIVIDAD(int ID)
    {
        String _SELECT;
        _SELECT = "SELECT "+ACTID+" AS '_id',"+ESTID+","+ACTCODIGO+","+ACTDESCRIPCION+
                ","+EMPID
                +" FROM "+N_TABLA;
        if (ID!=-1)
        {
            _SELECT=_SELECT+" WHERE "+ACTID+"='"+ID+"'";
        }
        _SELECT = _SELECT +" ORDER BY "+ACTDESCRIPCION + " ASC;";
        return _SELECT;
    }
    public static String _SELECT_ACTIVIDAD_TAREO(String CODIGO)
    {
        String _SELECT;
        _SELECT = "SELECT "+ACTID+" AS '_id',"+ESTID+","+ACTCODIGO+","+ACTDESCRIPCION+
                ","+EMPID
                +" FROM "+N_TABLA;
        if (CODIGO.length()>0)
        {
            _SELECT=_SELECT+" WHERE "+ACTCODIGO+"='"+CODIGO+"'";
        }
        _SELECT = _SELECT +" ORDER BY "+ACTDESCRIPCION + " ASC;";
        return _SELECT;
    }
}
