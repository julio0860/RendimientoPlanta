package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/04/29.
 */
public class T_Consumidor {
    public static final String CONID ="Con_Id";
    public static final String EMPID = "Emp_Id";
    public static  final String SUCID ="Suc_Id";
    public static final String ESTID = "Est_Id";
    public static final String CONCODIGO = "Con_Codigo";
    public static final String CONDESCRIPCION = "Con_Descripcion";
    public static final String CONDESCRIPCIONCOR = "Con_DescripcionCor";
    public static final String CULID = "Cul_Id";
    public static final String VARID = "Var_Id";
    public static final String CONTIPO ="Con_Tipo";
    public static final String CONESLOTE ="Con_EsLote";

    public static final String N_TABLA = "Consumidor";

    public static final String CREATE_T_CONSUMIDOR ="CREATE TABLE " + N_TABLA+"("+
            CONID +" INTEGER PRIMARY KEY NOT NULL, "+
            EMPID +" INTEGER NOT NULL, "+
            SUCID+" INTEGER NOT NULL, "+
            ESTID+" INTEGER NOT NULL, "+
            CONCODIGO+" TEXT NOT NULL, "+
            CONDESCRIPCION+" TEXT NOT NULL, "+
            CONDESCRIPCIONCOR+" TEXT NOT NULL, "+
            CULID+" INTEGER NOT NULL, "+
            VARID+" INTEGER NOT NULL, "+
            CONTIPO+" TEXT NOT NULL, "+
            CONESLOTE+" INTEGER NOT NULL "+
            ");";

    public static final String DROP_T_CONSUMIDOR ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(Integer ID,Integer EMID,Integer SID,Integer EST,String CODIGO,String DESCRIPCION
            ,String DESCRIPCIONCOR,Integer CID,Integer VID,String TIPO,Integer ESLOTE)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+CONID+","+EMPID+","+SUCID+","+ESTID+","+CONCODIGO+","+CONDESCRIPCION+
                ","+CONDESCRIPCIONCOR+","+CULID+","+VARID+","+CONTIPO+","+CONESLOTE+
                ")VALUES('"+
                ID+"','"+EMID+"','"+SID+"','"+EST+"','"+CODIGO+"','"+DESCRIPCION+"','"+DESCRIPCIONCOR+"','"+CID+
                "','"+VID+"','"+TIPO+"','"+ESLOTE+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_CON(Integer ESLOTE,Integer EST,Integer CUID,int EMID)
    {
        String _SELECT;
        _SELECT = "SELECT "+CONID+" As '_id',"+EMPID+","+SUCID+","+ESTID+","+CONCODIGO+","+CONDESCRIPCION+
                ","+CONDESCRIPCIONCOR+","+CULID+","+VARID+","+CONTIPO+","+CONESLOTE
                +" FROM "+N_TABLA;

        _SELECT = _SELECT+" WHERE ";
        if(ESLOTE!=-1)
        {
            _SELECT = _SELECT+CONESLOTE+"='"+ESLOTE+"' AND ";
        }else
        {
            _SELECT = _SELECT+ESLOTE+"='"+ESLOTE+"' AND ";
        }
        if (EST !=-1)
        {
            _SELECT= _SELECT+ESTID+"='"+EST+"' AND ";
        }
        else
        {
            _SELECT= _SELECT+EST+"='"+EST+"' AND ";
        }
        if(CUID!=-1)
        {
            _SELECT = _SELECT+CULID+ "='"+CUID+"' AND ";
        }else
        {
            _SELECT = _SELECT+CUID+ "='"+CUID+"' AND ";
        }
        if(EMID!=-1)
        {
            _SELECT = _SELECT+EMPID+ "='"+EMID+"'";
        }else
        {
            _SELECT = _SELECT+EMID+ "='"+EMID+"'";
        }

        _SELECT = _SELECT +" ORDER BY "+CONDESCRIPCIONCOR + " ASC";
        return _SELECT;
    }
    public static String _SELECT_CON_SUC(int SUID)
    {
        String _SELECT;
        _SELECT = "SELECT "+CONID+" As '_id',"+EMPID+","+SUCID+","+ESTID+","+CONCODIGO+","+CONDESCRIPCION+
                ","+CONDESCRIPCIONCOR+","+CULID+","+VARID+","+CONTIPO+","+CONESLOTE
                +" FROM "+N_TABLA;

        _SELECT = _SELECT+" WHERE ";
        if(SUID!=-1)
        {
            _SELECT = _SELECT+SUCID+"='"+SUID+"'";
        }else
        {
            _SELECT = _SELECT+SUID+"='"+SUID+"'";
        }
        _SELECT = _SELECT +" ORDER BY "+CONDESCRIPCIONCOR + " ASC";
        return _SELECT;
    }
}
