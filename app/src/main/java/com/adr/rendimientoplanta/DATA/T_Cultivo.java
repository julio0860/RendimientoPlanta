package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/13.
 */
public class T_Cultivo {
    public static final String CULID = "Cul_Id";
    public static final String ESTID ="Est_Id";
    public static final String CULCODIGO = "Cul_Codigo";
    public static  final String CULDESCRIPCION ="Cul_Descripcion";


    public static final String N_TABLA = "Cultivo";

    public static final String CREATE_T_CULTIVO ="CREATE TABLE " + N_TABLA+"("+
            CULID +" INTEGER PRIMARY KEY NOT NULL, "+
            ESTID +" INTEGER NOT NULL, "+
            CULCODIGO+" TEXT NOT NULL, "+
            CULDESCRIPCION+" TEXT NOT NULL "+
            ");";

    public static final String DROP_T_CULTIVO ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(Integer CUID,Integer EST,String CODIGO,String DESCRIPCION)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+CULID+","+ESTID+","+CULCODIGO+","+CULDESCRIPCION+
                ")VALUES('"+
                CUID+"','"+EST+"','"+CODIGO+"','"+DESCRIPCION+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_CULT(Integer CUID,Integer EST)
    {
        String _SELECT;
        _SELECT = "SELECT "+CULID+" AS '_id',"+ESTID+","+CULCODIGO+","+CULDESCRIPCION
                +" FROM "+N_TABLA;

        if (CUID!=-1||EST!=-1)
        {
            _SELECT=_SELECT+" WHERE ";
        }
        if (CUID!=-1)
        {
            _SELECT = _SELECT+CULID+"='"+CUID+"'";
        }
        if (EST!=-1&&CUID!=-1)
        {
            _SELECT = _SELECT+" AND ";
        }
        if (EST!=-1)
        {
            _SELECT = _SELECT+ESTID+ "='"+EST+"'";
        }
        return _SELECT;
    }
}
