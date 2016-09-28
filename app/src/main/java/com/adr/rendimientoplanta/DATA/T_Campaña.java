package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/16.
 */
public class T_Campaña {
    public static final String CAMID ="Cam_Id";
    public static final String CAMCODIGO = "Cam_Codigo";
    public static  final String CULID ="Cul_Id";
    public static final String CAMDESCRIPCION = "Cam_Descripcion";
    public static final String CAMFECHAINI = "Cam_FechaIni";
    public static final String CAMFECHATER = "Cam_FechaTer";
    public static final String CAMORIGEN = "Cam_Origen";

    public static final String N_TABLA = "Campaña";

    public static final String CREATE_T_CAMPAÑA ="CREATE TABLE " + N_TABLA+"("+
            CAMID +" INTEGER PRIMARY KEY NOT NULL, "+
            CAMCODIGO +" TEXT NOT NULL, "+
            CULID +" INTEGER NOT NULL, "+
            CAMDESCRIPCION +" TEXT NOT NULL, "+
            CAMFECHAINI +" TEXT NOT NULL, "+
            CAMFECHATER +" TEXT NOT NULL, "+
            CAMORIGEN +" TEXT NOT NULL "+
            ");";
    public static final String DROP_T_CAMPAÑA ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int CamId,String CamCodigo,int CulId,String CamDescripcion,String FechaIni,String FechaFin,
                                 String CamOrigen)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+CAMID+","+CAMCODIGO+","+CULID+","+CAMDESCRIPCION+","+CAMFECHAINI+","+CAMFECHATER+
                ","+CAMORIGEN+
                ")VALUES('"+
                CamId+"','"+CamCodigo+"','"+CulId+"','"+CamDescripcion+"','"+FechaIni+"','"+FechaFin+"','"+CamOrigen+"');";
        return _INSERT;

    }

    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }

    public static String _SELECT_CAMPAÑA()
    {
        String _SELECT_CAMPAÑA;
        _SELECT_CAMPAÑA = "SELECT "+CAMID+" AS '_id',"+CAMCODIGO+","+CULID+","+CAMDESCRIPCION+","+CAMFECHAINI+","+CAMFECHATER+
                ","+CAMORIGEN
                +" FROM "+N_TABLA;

        return _SELECT_CAMPAÑA;
    }
}
