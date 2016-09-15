package com.adr.rendimientoplanta.DATA;

import java.security.PublicKey;

/**
 * Created by smachado on 2016/04/22.
 */
public class T_Rendimiento {
    public static final String ID_FILA ="_Id_Ren";
    public static final String ID_REND = "Ren_Id";
    public static final String ID_SUCURSAL = "Suc_Id";
    public static final String ID_CONSUMIDOR = "Con_Id";
    public static final String ID_SUPERVISOR = "Per_Id";
    public static final String ID_PROCESO = "Pro_Id";
    public static final String ID_SUBPROCESO = "Sub_Id";
    public static final String REND_FECHA = "Ren_Fecha";
    public static final String ID_LINEA = "Lin_Id";
    public static final String LADO = "Lado";
    public static final String SINCRONIZADO = "Syn_Est";

    public static final String N_TABLA = "T_Rendimiento";


    public static final String CREATE_T_RENDIMIENTO ="CREATE TABLE " + N_TABLA+"("+
            ID_FILA +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ID_REND +" INTEGER NOT NULL, "+
            ID_SUCURSAL+" INTEGER NOT NULL, "+
            ID_CONSUMIDOR+" INTEGER NOT NULL, "+
            ID_SUPERVISOR+" INTEGER NOT NULL, "+
            ID_PROCESO+" INTEGER NOT NULL, "+
            ID_SUBPROCESO+" INTEGER NOT NULL, "+
            REND_FECHA+ " TEXT NOT NULL, "+
            ID_LINEA+ " INTEGER NOT NULL, "+
            LADO+" TEXT NOT NULL, "+
            SINCRONIZADO+" INTEGER NOT NULL);";

    public static final String DROP_T_RENDIMIENTO ="DROP TABLE IF EXISTS "+N_TABLA;

}
