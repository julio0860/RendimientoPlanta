package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/04/22.
 */
public class T_Personal {
    public static final String PERID ="Per_Id";
    public static final String EMPID = "Emp_Id";
    public static final String ESTID = "Est_Id";
    public static final String PERCODIGO = "Per_Codigo";
    public static final String PERAPEPATERNO = "Per_ApePaterno";
    public static final String PERAPEMATERNO = "Per_ApeMaterno";
    public static final String PERNOMBRES = "Per_Nombres";
    public static final String PERFOTOCHECK = "Per_Fotocheck";
    public static final String PERSEXO ="Per_Sexo";

    public static final String N_TABLA = "Personal";

    public static final String CREATE_T_PERSONAL ="CREATE TABLE " + N_TABLA+"("+
            PERID +" INTEGER PRIMARY KEY NOT NULL, "+
            EMPID +" INTEGER NOT NULL, "+
            ESTID+" INTEGER NOT NULL, "+
            PERCODIGO+" TEXT NOT NULL, "+
            PERAPEPATERNO+" TEXT NOT NULL, "+
            PERAPEMATERNO+" TEXT NOT NULL, "+
            PERNOMBRES+" TEXT NOT NULL, "+
            PERFOTOCHECK+" TEXT NOT NULL, "+
            PERSEXO+" TEXT NOT NULL"+
            ");";

    public static final String DROP_T_PERSONAL ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(Integer ID,Integer EMID,Integer EST,String CODIGO,String APEPATERNO
            ,String APEMATERNO,String NOMBRES,String FOTOCHECK,String SEXO)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+PERID+","+EMPID+","+ESTID+","+PERCODIGO+","+PERAPEPATERNO+","+PERAPEMATERNO+
                ","+PERNOMBRES+","+PERFOTOCHECK+","+PERSEXO+
                ")VALUES('"+
                ID+"','"+EMID+"','"+EST+"','"+CODIGO+"','"+APEPATERNO+"','"+APEMATERNO+"','"+NOMBRES+"','"+FOTOCHECK+
                "','"+SEXO+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_PER(Integer EST)
    {
        String _SELECT;
        _SELECT = "SELECT "+PERID+","+EMPID+","+ESTID+","+PERCODIGO+","+PERAPEPATERNO+","+PERAPEMATERNO+
                ","+PERNOMBRES+","+PERFOTOCHECK+","+PERSEXO
                +" FROM "+N_TABLA;
        if (EST!=-1)
        {
            _SELECT=_SELECT+" WHERE "+ESTID+ "='"+EST+"'";
        }
        _SELECT=_SELECT+';';
        return _SELECT;
    }
}
