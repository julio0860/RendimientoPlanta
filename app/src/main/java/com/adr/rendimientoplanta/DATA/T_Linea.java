package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/04/29.
 */
public class T_Linea {

    public static final String LINID ="Lin_Id";
    public static final String EMPID = "Emp_Id";
    public static final String ESTID = "Est_Id";
    public static  final String SUCID ="Suc_Id";
    public static final String LINCODIGO ="Lin_Codigo";
    public static final String LINDESCRIPCION ="Lin_Descripcion";


    public static final String N_TABLA = "Linea";

    public static final String CREATE_T_LINEA ="CREATE TABLE " + N_TABLA+"("+
            LINID +" INTEGER PRIMARY KEY NOT NULL, "+
            EMPID +" INTEGER NOT NULL, "+
            ESTID+" INTEGER NOT NULL, "+
            SUCID+" INTEGER NOT NULL, "+
            LINCODIGO+" TEXT NOT NULL, " +
            LINDESCRIPCION+" TEXT NOT NULL" +
            ");";

    public static final String DROP_T_LINEA ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(Integer ID,Integer EMID,Integer EID,Integer SID,String CODIGO,String DESCRIPCION)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+LINID+","+EMPID+","+ESTID+","+SUCID+","+LINCODIGO+","+LINDESCRIPCION+
                ")VALUES('"+
                ID+"','"+EMID+"','"+EID+"','"+SID+"','"+CODIGO+"','"+DESCRIPCION+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_LIN(Integer SID,Integer EST)
    {
        String _SELECT_LIN;
        _SELECT_LIN = "SELECT "+LINID+" as '_id',"+EMPID+","+ESTID+","+SUCID+","+LINCODIGO+","+LINDESCRIPCION
                +" FROM "+N_TABLA;

        if (SID!=-1||EST!=-1)
        {
            _SELECT_LIN=_SELECT_LIN+" WHERE ";
        }
        if (SID!=-1)
        {
            _SELECT_LIN = _SELECT_LIN+SUCID+"='"+SID+"'";
        }
        if (EST!=-1&&SID!=-1)
        {
            _SELECT_LIN = _SELECT_LIN+" AND ";
        }
        if (EST!=-1)
        {
            _SELECT_LIN = _SELECT_LIN+ESTID+ "='"+EST+"'";
        }
        return _SELECT_LIN;
    }

    public static String Linea_SeleccionarEstado(Integer SID,Integer EST,String Fecha)
    {
        String _SELECT_LIN;
        _SELECT_LIN = "SELECT LN."+LINID+" as '_id', LN."+EMPID+",LN."+ESTID+",LN."+SUCID+",LN."+LINCODIGO+",LN."+LINDESCRIPCION
                +", IFNULL(LR.Est_Id,0) AS 'Est_Id'"
                +" FROM "+N_TABLA
        +" LN LEFT JOIN LineaRegistro LR ON LN."+LINID+" = IFNULL(LR.Lin_Id,LN.Lin_Id) AND LR.LinReg_Fecha='"+Fecha+"'"
        +" WHERE LN."+SUCID+"='"+SID+"' AND LN."+ESTID+"='"+EST+"'";


        return _SELECT_LIN;
    }

}
