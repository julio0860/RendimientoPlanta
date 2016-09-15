package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/19.
 */
public class T_EmpresaUsuario {
    public static final String EMPUSUID ="Emp_Usu_Id";
    public static final String USUID ="Usu_Id";
    public static final String EMPID ="Emp_Id";
    public static final String EMPUSUPERMITIDO = "Emp_Usu_Permitido";


    public static final String N_TABLA = "Empresa_Usuario";

    public static final String CREATE_T_EMPRESAUSUARIO ="CREATE TABLE " + N_TABLA+"("+
            EMPUSUID +" INTEGER PRIMARY KEY NOT NULL, "+
            USUID +" INTEGER NOT NULL, "+
            EMPID +" INTEGER NOT NULL, "+
            EMPUSUPERMITIDO +" INTEGER NOT NULL "+
            ");";

    public static final String DROP_T_EMPRESAUSUARIO ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int ID,int UID,int EID,int PERMITIDO)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+EMPUSUID+","+USUID+","+EMPID+","+EMPUSUPERMITIDO+
                ")VALUES('"+
                ID+"','"+UID+"','"+EID+"','"+PERMITIDO+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_EMPUSU(int UID)
    {
        String _SELECT;
        _SELECT = "SELECT "+EMPUSUID+","+USUID+","+EMPID+","+EMPUSUPERMITIDO
                +" FROM "+N_TABLA;

        if (UID!=-1)
        {
            _SELECT=_SELECT+" WHERE "+USUID+"='"+UID+"';";
        }
        return _SELECT;
    }
}
