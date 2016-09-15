package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/16.
 */
public class T_Empresa {
    public static final String EMPID ="Emp_Id";
    public static  final String ESTID ="Est_Id";
    public static final String EMPCODIGO = "Emp_Codigo";
    public static final String EMPDESCRIPCION = "Emp_Descripcion";
    public static final String EMPABREV = "Emp_Abrev";
    public static final String EMPCONEXION = "Emp_Conexion";


    public static final String N_TABLA = "Empresa";

    public static final String CREATE_T_EMPRESA ="CREATE TABLE " + N_TABLA+"("+
            EMPID +" INTEGER PRIMARY KEY NOT NULL, "+
            ESTID +" INTEGER NOT NULL, "+
            EMPCODIGO +" TEXT NOT NULL, "+
            EMPDESCRIPCION +" TEXT NOT NULL, "+
            EMPABREV +" TEXT NOT NULL, "+
            EMPCONEXION+" TEXT NOT NULL "+
            ");";

    public static final String DROP_T_EMPRESA ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int ID,int ESID,String CODIGO,String DESCRIPCION,
                                 String ABREV,String CONEXION)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+EMPID+","+ESTID+","+EMPCODIGO+","+EMPDESCRIPCION+
                ","+EMPABREV+","+EMPCONEXION+
                ")VALUES('"+
                ID+"','"+ESID+"','"+CODIGO+"','"+DESCRIPCION+"','"+ABREV+"','"+CONEXION+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_EMP(int ID)
    {
        String _SELECT;
        _SELECT = "SELECT "+EMPID+" AS '_id',"+ESTID+","+EMPCODIGO+","+EMPDESCRIPCION+
                ","+EMPABREV+","+EMPCONEXION+" FROM "+N_TABLA;
        if (ID!=-1)
        {
            _SELECT=_SELECT+" WHERE "+EMPID+"='"+ID+"';";
        }
        return _SELECT;
    }

}
