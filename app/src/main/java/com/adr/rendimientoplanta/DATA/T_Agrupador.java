package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/04/29.
 */
public class T_Agrupador {
    public static final String AGRUID ="Agru_Id";
    public static final String EMPID = "Emp_Id";
    public static  final String FECHA ="Fecha";
    public static final String SUCID = "Suc_Id";
    public static final String PROID = "Pro_Id";
    public static final String SUBID = "Sub_Id";
    public static final String LINID = "Lin_Id";
    public static final String LADO = "Lado";
    public static final String POSICION = "Posicion";
    public static final String DNI = "Dni";


    public static final String N_TABLA = "Agrupador";

    public static final String CREATE_T_AGRUPADOR ="CREATE TABLE " + N_TABLA+"("+
            AGRUID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "+
            EMPID +" INTEGER NOT NULL, "+
            FECHA +" TEXT NOT NULL, "+
            SUCID +" INTEGER NOT NULL, "+
            PROID +" INTEGER NOT NULL, "+
            SUBID +" INTEGER NOT NULL, "+
            LINID +" INTEGER NOT NULL, "+
            LADO +" TEXT NOT NULL, "+
            POSICION +" INTEGER NOT NULL, "+
            DNI +" TEXT NOT NULL, "+

            ");";

    public static final String DROP_T_AGRUPADOR ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int EmpId,String Fecha,int SucId,int ProId,int SubId,int LinId,String Lado,int Posicion,
                                 String Dni)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+EMPID+","+FECHA+","+SUCID+","+PROID+","+SUBID+","+LINID+","+LADO+","+POSICION+
                ","+DNI+
                ")VALUES('"+
                EmpId+"','"+Fecha+"','"+SucId+"','"+ProId+"','"+SubId+"','"+LinId+"','"+Lado+"','"+Posicion+"','"+Dni+"');";
        return _INSERT;
    }
}