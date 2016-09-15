package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/19.
 */
public class T_Responsable {
    public static final String RESID ="Res_Id";
    public static final String ESTID = "Est_Id";
    public static final String RESCODIGO = "Res_Codigo";
    public static final String RESDESCRIPCION = "Res_Descripcion";
    public static final String RESDNI = "Res_Dni";
    public static final String PERID = "Per_Id";
    public static final String EMPID ="Emp_Id";
    public static final String RESSUPERVISOR = "Res_Supervisor";


    public static final String N_TABLA = "Responsable";

    public static final String CREATE_T_RESPONSABLE ="CREATE TABLE " + N_TABLA+"("+
            RESID +" INTEGER PRIMARY KEY NOT NULL, "+
            ESTID +" INTEGER NOT NULL, "+
            RESCODIGO +" TEXT NOT NULL, "+
            RESDESCRIPCION +" TEXT NOT NULL, "+
            RESDNI +" INTEGER NOT NULL, "+
            PERID +" INTEGER NOT NULL, "+
            EMPID +" INTEGER NOT NULL, "+
            RESSUPERVISOR +" TEXT NOT NULL "+
            ");";

    public static final String DROP_T_RESPONSABLE ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int ID,int ESID,String CODIGO,String DESCRIPCION,String DNI,
                                 int PEID,int EMID,String SUPERVISOR)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+RESID+","+ESTID+","+RESCODIGO+","+RESDESCRIPCION+
                ","+RESDNI+","+PERID+","+EMPID+","+RESSUPERVISOR+
                ")VALUES('"+
                ID+"','"+ESID+"','"+CODIGO+"','"+DESCRIPCION+"','"+DNI+"','"+PEID+
                "','"+EMID+"','"+SUPERVISOR+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_RESPONSABLE(int ID)
    {
        String _SELECT;
        _SELECT = "SELECT "+RESID+","+ESTID+","+RESCODIGO+","+RESDESCRIPCION+
                ","+RESDNI+","+PERID+","+EMPID+","+RESSUPERVISOR
                +" FROM "+N_TABLA;
        if (ID!=-1)
        {
            _SELECT=_SELECT+" WHERE "+RESID+"='"+ID+"';";
        }
        return _SELECT;
    }

}
