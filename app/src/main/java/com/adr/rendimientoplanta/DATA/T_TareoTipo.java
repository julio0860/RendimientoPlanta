package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/19.
 */
public class T_TareoTipo {
    public static final String TARTIPID ="TarTip_Id";
    public static  final String EMPID ="Emp_Id";
    public static final String ESTID = "Est_Id";
    public static final String TARTIPCODIGO = "TarTip_Codigo";
    public static final String TARTIPDESCRIPCION = "TarTip_Descripcion";



    public static final String N_TABLA = "TareoTipo";

    public static final String CREATE_T_TAREO_TIPO ="CREATE TABLE " + N_TABLA+"("+
            TARTIPID +" INTEGER PRIMARY KEY NOT NULL, "+
            EMPID +" INTEGER NOT NULL, "+
            ESTID +" INTEGER NOT NULL, "+
            TARTIPCODIGO +" TEXT NOT NULL, "+
            TARTIPDESCRIPCION +" TEXT NOT NULL "+
            ");";

    public static final String DROP_T_TAREO_TIPO ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int ID,int EMID,int ESID,String CODIGO,
                                 String DESCRIPCION)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+TARTIPID+","+EMPID+","+ESTID+","+TARTIPCODIGO+
                ","+TARTIPDESCRIPCION+
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
    public static String _SELECT_TAREO_TIPO(int EID)
    {
        String _SELECT;
        _SELECT = "SELECT "+TARTIPID+" AS '_id',"+EMPID+","+ESTID+","+TARTIPCODIGO+
                ","+TARTIPDESCRIPCION+" FROM "+N_TABLA;
        if (EID!=-1)
        {
            _SELECT=_SELECT+" WHERE "+EMPID+"='"+EID+"';";
        }
        return _SELECT;
    }

}
