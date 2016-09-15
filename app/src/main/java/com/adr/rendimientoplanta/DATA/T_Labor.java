package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/19.
 */
public class T_Labor {
    public static final String LABID ="Lab_Id";
    public static final String ACTID ="Act_Id";
    public static final String ESTID = "Est_Id";
    public static  final String LABCODIGO ="Lab_Codigo";
    public static final String LABDESCRIPCION = "Lab_Descripcion";
    public static final String EMPID = "Emp_Id";



    public static final String N_TABLA = "Labor";

    public static final String CREATE_T_LABOR ="CREATE TABLE " + N_TABLA+"("+
            LABID +" INTEGER PRIMARY KEY NOT NULL, "+
            ACTID +" INTEGER NOT NULL, "+
            ESTID +" INTEGER NOT NULL, "+
            LABCODIGO +" TEXT NOT NULL, "+
            LABDESCRIPCION +" TEXT NOT NULL, "+
            EMPID +" INTEGER NOT NULL "+
            ");";

    public static final String DROP_T_LABOR ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int ID,int AID,int ESID,String CODIGO,String DESCRIPCION,
                                 int EMID)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+LABID+","+ACTID+","+ESTID+","+LABCODIGO+","+LABDESCRIPCION+
                ","+EMPID+
                ")VALUES('"+
                ID+"','"+AID+"','"+ESID+"','"+CODIGO+"','"+DESCRIPCION+"','"+EMID+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_LABOR(int ID)
    {
        String _SELECT;
        _SELECT = "SELECT "+LABID+" AS '_id',"+ACTID+","+ESTID+","+LABCODIGO+","+LABDESCRIPCION+
                ","+EMPID
                +" FROM "+N_TABLA;
        if (ID!=-1)
        {
            _SELECT=_SELECT+" WHERE "+ACTID+"='"+ID+"'";
        }
        _SELECT = _SELECT +" ORDER BY "+LABDESCRIPCION + " ASC;";
        return _SELECT;
    }
}
