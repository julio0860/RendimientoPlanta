package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/13.
 */
public class T_Presentacion {
    public static final String PREID ="Pre_Id";
    public static final String PRECODIGO = "Pre_Codigo";
    public static final String PREDESCRIPCION = "Pre_Descripcion";
    public static final String PRESINCRONIZADO = "Pre_Sincronizado";
    public static final String CULID = "Cul_Id";
    public static final String PRENUMPUNNET = "Pre_NumPunnet";
    public static final String PREPESO = "Pre_Peso";

    public static final String N_TABLA = "Presentacion";

    public static final String CREATE_T_PRESENTACION ="CREATE TABLE " + N_TABLA+"("+
            PREID +" INTEGER PRIMARY KEY NOT NULL, "+
            PRECODIGO +" TEXT NOT NULL, "+
            PREDESCRIPCION+" TEXT NOT NULL, "+
            PRESINCRONIZADO+" INTEGER NOT NULL, "+
            CULID +" INTEGER NOT NULL, "+
            PRENUMPUNNET+" NUMERIC NOT NULL, "+
            PREPESO+" NUMERIC NOT NULL"+
            ");";

    public static final String DROP_T_PRESENTACION ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(Integer ID,String CODIGO,String DESCRIPCION,Integer SINCRONIZADO,Integer CUID
            ,Double NUMPUNNET,Double PESO)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+PREID+","+PRECODIGO+","+PREDESCRIPCION+","+PRESINCRONIZADO+
                ","+CULID+","+PRENUMPUNNET+","+PREPESO+
                ")VALUES('"+
                ID+"','"+CODIGO+"','"+DESCRIPCION+"','"+SINCRONIZADO+"','"+CUID+"','"+NUMPUNNET+"','"+PESO+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_PRE(Integer SYNC)
    {
        String _SELECT;
        _SELECT = "SELECT "+PREID+","+PRECODIGO+","+PREDESCRIPCION+","+PRESINCRONIZADO+
                ","+CULID+","+PRENUMPUNNET+","+PREPESO
                +" FROM "+N_TABLA;
        if (SYNC!=-1)
        {
            _SELECT=_SELECT+" WHERE "+PRESINCRONIZADO+ "='"+SYNC+"'";
        }
        _SELECT=_SELECT+';';
        return _SELECT;
    }
}
