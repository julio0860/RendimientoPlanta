package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/13.
 */
public class T_Variedad {
    public static final String VARID ="Var_Id";
    public static final String CULID = "Cul_Id";
    public static final String VARCODIGO = "Var_Codigo";
    public static  final String VARDESCRIPCION ="Var_Descripcion";
    public static final String ESTID ="Est_Id";

    public static final String N_TABLA = "Variedad";

    public static final String CREATE_T_VARIEDAD ="CREATE TABLE " + N_TABLA+"("+
            VARID +" INTEGER PRIMARY KEY NOT NULL, "+
            CULID +" INTEGER NOT NULL, "+
            VARCODIGO+" TEXT NOT NULL, "+
            VARDESCRIPCION+" TEXT NOT NULL, "+
            ESTID+" INTEGER NOT NULL" +
            ");";

    public static final String DROP_T_VARIEDAD ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(Integer VID,Integer CUID,String CODIGO,String DESCRIPCION,Integer EST)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+VARID+","+CULID+","+VARCODIGO+","+VARDESCRIPCION+","+ESTID+
                ")VALUES('"+
                VID+"','"+CUID+"','"+CODIGO+"','"+DESCRIPCION+"','"+EST+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_VAR(Integer CUID,Integer EST)
    {
        String _SELECT;
        _SELECT = "SELECT "+VARID+","+CULID+","+VARCODIGO+","+VARDESCRIPCION+","+ESTID
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
