package com.adr.rendimientoplanta.DATA;

import android.provider.BaseColumns;

/**
 * Created by smachado on 2016/05/13.
 */
public class T_Sucursal {
    public static final String SUCID ="Suc_Id";
    public static final String EMPID = "Emp_Id";
    public static final String ESTID = "Est_Id";
    public static final String SUCCODIGO = "Suc_Codigo";
    public static final String SUCDESCRIPCION = "Suc_Descripcion";
    public static final String SUCDIRECCION = "Suc_Direccion";
    public static final String SUCCODIGOCCH = "Suc_CodigoCCH";
    public static final String SUCESPLANTA = "Suc_EsPlanta";
    public static final String SUCZONA ="Suc_Zona";

    public static final String N_TABLA = "Sucursal";

    public static final String CREATE_T_SUCURSAL ="CREATE TABLE " + N_TABLA+"("+
            SUCID +" INTEGER PRIMARY KEY NOT NULL, "+
            EMPID +" INTEGER NOT NULL, "+
            ESTID+" INTEGER NOT NULL, "+
            SUCCODIGO+" TEXT NOT NULL, "+
            SUCDESCRIPCION+" TEXT NOT NULL, "+
            SUCDIRECCION+" TEXT NOT NULL, "+
            SUCCODIGOCCH+" TEXT NOT NULL, "+
            SUCESPLANTA+" INTEGER NOT NULL, "+
            SUCZONA+" TEXT NOT NULL "+
            ");";

    public static final String DROP_T_SUCURSAL ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(Integer ID,Integer EMID,Integer EST,String CODIGO,String DESCRIPCION
            ,String DIRECCION,String CODIGOCCH,Integer ESPLANTA,String ZONA)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+SUCID+","+EMPID+","+ESTID+","+SUCCODIGO+","+SUCDESCRIPCION+","+SUCDIRECCION+
                ","+SUCCODIGOCCH+","+SUCESPLANTA+","+SUCZONA+
                ")VALUES('"+
                ID+"','"+EMID+"','"+EST+"','"+CODIGO+"','"+DESCRIPCION+"','"+DIRECCION+"','"+CODIGOCCH+"','"+ESPLANTA+
                "','"+ZONA+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_SUC(Integer ESPLANTA,Integer EST)
    {
        String _SELECT;
        _SELECT = "SELECT "+SUCID+" AS '_id',"+EMPID+","+ESTID+","+SUCCODIGO+","+SUCDESCRIPCION+","+SUCDIRECCION+
                ","+SUCCODIGOCCH+","+SUCESPLANTA+","+SUCZONA
                +" FROM "+N_TABLA;

        if (ESPLANTA!=-1||EST!=-1)
        {
            _SELECT=_SELECT+" WHERE ";
        }
        if (ESPLANTA!=-1)
        {
            _SELECT = _SELECT+SUCESPLANTA+"='"+ESPLANTA+"'";
        }
        if (EST!=-1&&ESPLANTA!=-1)
        {
            _SELECT = _SELECT+" AND ";
        }
        if (EST!=-1)
        {
            _SELECT = _SELECT+ESTID+ "='"+EST+"'";
        }
        return _SELECT;
    }
    public static String _SELECT_SUC_EMP(Integer EMP)
    {
        String _SELECT;
        _SELECT = "SELECT "+SUCID+" AS '_id',"+EMPID+","+ESTID+","+SUCCODIGO+","+SUCDESCRIPCION+","+SUCDIRECCION+
                ","+SUCCODIGOCCH+","+SUCESPLANTA+","+SUCZONA
                +" FROM "+N_TABLA;

        if (EMP!=-1)
        {
            _SELECT = _SELECT+" WHERE "+EMPID+"='"+EMP+"'";
        }
        _SELECT = _SELECT+";";
        return _SELECT;
    }
    public static String _SELECT_SUC_EMP_PLANTA(Integer EMP,int EST,int ESPLANTA)
    {
        String _SELECT;
        _SELECT = "SELECT "+SUCID+" AS '_id',"+EMPID+","+ESTID+","+SUCCODIGO+","+SUCDESCRIPCION+","+SUCDIRECCION+
                ","+SUCCODIGOCCH+","+SUCESPLANTA+","+SUCZONA
                +" FROM "+N_TABLA;

        if (EMP!=-1)
        {
            _SELECT = _SELECT+" WHERE "+EMPID+"='"+EMP+"' AND "+ESTID+"='"+EST+"' AND "+SUCESPLANTA+"='"+ESPLANTA+"'";
        }
        _SELECT = _SELECT+";";
        return _SELECT;
    }
}
