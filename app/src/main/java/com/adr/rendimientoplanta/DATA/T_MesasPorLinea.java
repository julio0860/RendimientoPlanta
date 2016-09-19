package com.adr.rendimientoplanta.DATA;

/**
 * Created by jcasas on 16/09/2016.
 */
public class T_MesasPorLinea {

    public static final String MESLINID ="MesLin_Id";
    public static final String CAMID = "Cam_Id";
    public static final String PROID ="Pro_Id";
    public static final String SUBID ="Sub_Id";
    public static final String LINID ="Lin_Id";
    public static final String LADO = "Lado";
    public static final String MESA = "Mesa";

    public static final String N_TABLA = "MesaLinea";

    public static final String CREATE_T_MESALINEA ="CREATE TABLE " + N_TABLA+"("+
            MESLINID +" INTEGER PRIMARY KEY NOT NULL, "+
            CAMID +" INTEGER NOT NULL, "+
            PROID +" INTEGER NOT NULL, "+
            SUBID +" INTEGER NOT NULL, "+
            LINID +" INTEGER NOT NULL, "+
            LADO +" TEXT NOT NULL, "+
            MESA +" INTEGER NOT NULL "+
            ");";

    public static final String DROP_T_MESALINEA ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int MesLin_Id,int Cam_Id,int Pro_Id,int Sub_Id,int Lin_Id,String Lado,
                                 int Mesa)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+MESLINID+","+CAMID+","+PROID+","+SUBID+","+LINID+","+LADO+
                ","+MESA+
                ")VALUES('"+
                MesLin_Id+"','"+Cam_Id+"','"+Pro_Id+"','"+Sub_Id+"','"+Lin_Id+"','"+Lado+"','"+Mesa+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }

    public static String _SELECT_MESALINEA(int ID)
    {
        String _SELECT;
        _SELECT = "SELECT "+MESLINID+" AS '_id',"+CAMID+","+PROID+","+SUBID+","+LINID+","+LADO+
                ","+MESA
                +" FROM "+N_TABLA;
        if (ID!=-1)
        {
            _SELECT=_SELECT+" WHERE "+MESLINID+"='"+ID+"'";
        }

        return _SELECT;
    }
}
