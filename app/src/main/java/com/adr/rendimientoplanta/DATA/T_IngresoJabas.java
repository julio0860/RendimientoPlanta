package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/03.
 */
public class T_IngresoJabas {
    public static final String ID_FILA ="_Id_Ing";
    public static final String ING_FECHA = "Ing_Fecha";
    public static final String HORA_INI = "Ing_HoraIni";
    public static final String HORA_FIN = "Ing_HoraFin";
    public static final String PARADAS = "Ing_Paradas";
    public static final String PLANTA = "Ing_Planta";
    //

    //
    public static final String LINEA ="Ing_Linea";
    //

    //
    public static final String LOTE ="Ing_Lote";

    public static final String JABAS ="Ing_Jabas";
    //public static final String
    public static final String ESCAMPO ="Ing_EsCampo";
    public static final String ESREPRO ="Ing_EsRepro";
    public static final String ESTADO ="Ing_Estado";
    public static final String SINCRONIZADO ="Syn_Est";
    public static final String FECHAHORA ="Ing_FechaReg";
    // ADICIONAL CASO MIX
    public static final String SUCID = "Suc_Id";
    public static final String LINID ="Lin_Id";
    public static final String CONID ="Con_Id";
    public static final String CONIDMIX="Con_IdMix";
    public static final String LOTEMIX="Ing_LoteMix";

    public static final String JABASLOTE="Ing_JabasLote";
    public static final String JABASMIX="Ing_JabasMix";
    public static final String ESMIX="Ing_EsMix";

    public static final String USUID="Usu_Id";
    // -------------FIN CASO MIX
    public static final String N_TABLA = "T_IngresoJabas";

    public static final String CREATE_T_INGRESOJABAS ="CREATE TABLE " + N_TABLA+"("+
            ID_FILA +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ING_FECHA +" TEXT NOT NULL, "+
            HORA_INI+" TEXT NOT NULL, "+
            HORA_FIN+" TEXT NOT NULL, "+
            PARADAS+" INTEGER NOT NULL, "+
            PLANTA+" TEXT NOT NULL, "+
            LINEA+" TEXT NOT NULL, "+
            LOTE+" TEXT NOT NULL, "+
            JABAS+" INTEGER NOT NULL, "+
            ESCAMPO+" INTEGER NOT NULL, "+
            ESREPRO+" INTEGER NOT NULL, "+
            ESTADO+" INTEGER NOT NULL, "+
            FECHAHORA+" TEXT NOT NULL,"+
            SINCRONIZADO+" INTEGER NOT NULL, " +
            //NUEVO
            SUCID+" INTEGER NOT NULL, " +
            LINID+" INTEGER NOT NULL, " +
            CONID+" INTEGER NOT NULL, " +
            CONIDMIX+" INTEGER, " +
            LOTEMIX+ " TEXT, "+
            JABASLOTE+ " INTEGER NOT NULL, "+
            JABASMIX+ " INTEGER NOT NULL, "+
            ESMIX+" INTEGER NOT NULL, "+
            USUID+" INTEGER NOT NULL"+
            ");";

    public static final String DROP_T_INGRESOJABAS ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(String FECHA,String HORAINI, String HORAFIN,Integer PARADA,
                                 String PLANTAS,String LINEAS,String LOTES,Integer JABA,Integer CAMPO,
                                 Integer REPRO,Integer EST,Integer SYNC,String FECHAREG,
                                 Integer SUID,Integer LIID,Integer COID, Integer COMIXID, String LOTMIX,
                                 Integer JABLOTE,Integer JABMIX,Integer MIX,Integer UID)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+ING_FECHA+","+HORA_INI+","+HORA_FIN+","
                +PARADAS+","+PLANTA+","+LINEA+","+LOTE+","+JABAS+","+ESCAMPO+","+ESREPRO+","+ESTADO+","
                +SINCRONIZADO+","+FECHAHORA+
                ","+SUCID+","+LINID+","+CONID+","+CONIDMIX+","+LOTEMIX+","+JABASLOTE+
                ","+JABASMIX+","+ESMIX+","+USUID+
                ")VALUES('"+
                FECHA+"','"+HORAINI+"','"+HORAFIN+"','"+PARADA+"','"+PLANTAS+"','"+LINEAS+
                "','"+LOTES+"','"+JABA+"','"+CAMPO+"','"+REPRO+"','"+EST+"','"+SYNC+"','"+FECHAREG
                +"','"+SUID+"','"+LIID+"','"+COID+"','"+COMIXID+"','"+LOTMIX+"','"+JABLOTE+
                "','"+JABMIX+"','"+MIX+"','"+UID
                +"');";

        return _INSERT;
    }
    public static String _UPDATE(Integer ID,String FECHA,String HORAINI, String HORAFIN,Integer PARADA,
                                 String PLANTAS,String LINEAS,String LOTES,Integer JABA,Integer CAMPO,
                                 Integer REPRO,Integer EST,Integer SYNC,
                                 //ADICIONAL
                                 Integer SUID,Integer LIID,Integer COID, Integer COMIXID, String LOTMIX,
                                 Integer JABLOTE,Integer JABMIX,Integer MIX,Integer UID)
    {
        String _UPDATE;
        _UPDATE = "UPDATE "+N_TABLA +" SET "+ING_FECHA+"= '"+FECHA+"',"+HORA_INI+"='"+HORAINI+"',"
                +HORA_FIN+"='"+HORAFIN+"',"+PARADAS+"='"+PARADA+"',"+PLANTA+"='"+PLANTAS+"',"
                +LINEA+"='"+LINEAS+"',"+LOTE+"='"+LOTES+"',"+JABAS+"='"+JABA+"',"+ESCAMPO+"='"+CAMPO+"',"
                +ESREPRO+"='"+REPRO+"',"+ESTADO+"='"+EST+"',"+SINCRONIZADO+"='"+SYNC+
                //ADICIONAL
                "',"+SUCID+"='"+SUID+"',"+LINID+"='"+LIID+"',"+CONID+"='"+COID+
                "',"+CONIDMIX+"='"+COMIXID+"',"+LOTEMIX+"='"+LOTMIX+"',"+JABASLOTE+"='"+JABLOTE+
                "',"+JABASMIX+"='"+JABMIX+"',"+ESMIX+"='"+MIX+"',"+USUID+"='"+UID+
                "' WHERE "+ID_FILA+"='"+ID+"';";
        return _UPDATE;
    }
    public static String _SELECT_ID(Integer ID)
    {
        String _SELECT_ID;
        _SELECT_ID = "SELECT "+ID_FILA+","+ING_FECHA+","+HORA_INI+","+HORA_FIN+","
                +PARADAS+","+PLANTA+","+LINEA+","+LOTE+","+JABAS+","+ESCAMPO+","+ESREPRO+","+ESTADO+","
                +SINCRONIZADO+" FROM "+N_TABLA+" WHERE "+ID_FILA+"='"+ID+"';";
        return _SELECT_ID;
    }
    public static String _SELECT_ALL()
    {
        String _SELECT_ID;
        _SELECT_ID = "SELECT "+ID_FILA+","+ING_FECHA+","+HORA_INI+","+HORA_FIN+","
                +PARADAS+","+PLANTA+","+LINEA+","+LOTE+","+JABAS+","+ESCAMPO+","+ESREPRO+","+ESTADO+","
                +SINCRONIZADO+" FROM "+N_TABLA+";";
        return _SELECT_ID;
    }
    public static String _SELECT_LISTA(Integer SYNC,Integer Est,String Fecha)
    {
        String _SELECT_ID="";

        _SELECT_ID = "SELECT "+ID_FILA+" AS _id ,"+ING_FECHA+","+HORA_INI+","+HORA_FIN+","
                +PARADAS+","+PLANTA+","+LINEA+","+LOTE+","+JABAS+","+ESCAMPO+","+ESREPRO+","+ESTADO+","
                +SINCRONIZADO+","+FECHAHORA
                +","+SUCID+","+LINID+","+CONID+","+CONIDMIX+","+LOTEMIX+","+JABASLOTE+
                ","+JABASMIX+","+ESMIX+","+USUID
                +" FROM "+N_TABLA;
        if (SYNC!=-1||Est!=-1||Fecha.length() >0)
        {
            _SELECT_ID=_SELECT_ID+" WHERE ";
        }
        if (SYNC!=-1)
        {
            _SELECT_ID = _SELECT_ID+SINCRONIZADO+"='"+SYNC+"'";
        }
        if (Est!=-1&&SYNC!=-1)
        {
            _SELECT_ID = _SELECT_ID+" AND ";
        }
        if (Est!=-1)
        {
            _SELECT_ID = _SELECT_ID+ESTADO+ "='"+Est+"'";
        }
        if ((Est!=-1&&SYNC!=-1)&&Fecha.length()>0)
        {
            _SELECT_ID = _SELECT_ID+" AND ";
        }
        if(Fecha.length()>0)
        {
            _SELECT_ID = _SELECT_ID+ING_FECHA+"='"+Fecha+"'";
        }
        _SELECT_ID = _SELECT_ID+";";
        return _SELECT_ID;
    }
    public static String _SELECT_SYNC(Integer SYNC,Integer Est)
    {
        String _SELECT_ID="";

        _SELECT_ID = "SELECT "+ID_FILA+","+ING_FECHA+","+HORA_INI+","+HORA_FIN+","
                +PARADAS+","+PLANTA+","+LINEA+","+LOTE+","+JABAS+","+ESCAMPO+","+ESREPRO+","+ESTADO+","
                +SINCRONIZADO+","+FECHAHORA
                +","+SUCID+","+LINID+","+CONID+","+CONIDMIX+","+LOTEMIX+","+JABASLOTE+
                ","+JABASMIX+","+ESMIX+","+USUID
                +" FROM "+N_TABLA;
        if (SYNC!=-1||Est!=-1)
        {
            _SELECT_ID=_SELECT_ID+" WHERE ";
        }
        if (SYNC!=-1)
        {
            _SELECT_ID = _SELECT_ID+SINCRONIZADO+"='"+SYNC+"'";
        }
        if (Est!=-1&&SYNC!=-1)
        {
            _SELECT_ID = _SELECT_ID+" AND ";
        }
        if (Est!=-1)
        {
            _SELECT_ID = _SELECT_ID+ESTADO+ "='"+Est+"'";
        }
        _SELECT_ID = _SELECT_ID+";";
        return _SELECT_ID;
    }
    public static String _UPDATE_SYNCSTATE(Integer ID,Integer SYNC)
    {
        String _UPDATE;
        _UPDATE = "UPDATE "+N_TABLA +" SET "+SINCRONIZADO+"='"+SYNC+"' WHERE "+ID_FILA+"='"+ID+"';";
        return _UPDATE;
    }

    public static String _INSERT_SYNC(Integer ID,String FECHA,String HORAINI, String HORAFIN,
                                      Integer PARADA,String PLANTAS,String LINEAS,String LOTES,
                                      Integer JABA,Integer CAMPO,Integer REPRO,Integer EST,
                                      Integer SYNC,String MAC,String IMEI,String FECHAREG,Integer SUID,
                                      Integer LIID,Integer COID, Integer COMIXID, String LOTMIX,
                                      Integer JABLOTE,Integer JABMIX,Integer MIX,Integer UID)
    {
        String _INSERT;
        _INSERT = "INSERT INTO  IngresoJabas(Ing_IdMovil,"+ING_FECHA+","+HORA_INI+","+HORA_FIN+","
                +PARADAS+","+PLANTA+","+LINEA+","+LOTE+","+JABAS+","+ESCAMPO+","+ESREPRO+","+ESTADO+","
                +"Ing_Syn_Est"+",Ing_MacMovil,Ing_IMEI,"+FECHAHORA+
                //ADICIONAL
                ","+SUCID+","+LINID+","+CONID+","+CONIDMIX+","+LOTEMIX+","+JABASLOTE+
                ","+JABASMIX+","+ESMIX+","+USUID+
                ")VALUES('"+ID+"','"+
                FECHA+"','"+HORAINI+"','"+HORAFIN+"','"+PARADA+"','"+PLANTAS+"','"+LINEAS+
                "','"+LOTES+"','"+JABA+"','"+CAMPO+"','"+REPRO+"','"+EST+"','"+SYNC+"','"+MAC+"','"+IMEI+"','"+FECHAREG
                +"','"+SUID+"','"+LIID+"','"+COID+"','"+COMIXID+"','"+LOTMIX+"','"+JABLOTE+
                "','"+JABMIX+"','"+MIX+"','"+UID
                +"');";
        return _INSERT;
    }


    public static String _UPDATE_SYNCSQL(Integer ID,String FECHA,String HORAINI, String HORAFIN,
                                         Integer PARADA,String PLANTAS,String LINEAS,String LOTES,
                                         Integer JABA,Integer CAMPO,Integer REPRO,Integer EST,
                                         Integer SYNC,
                                         Integer SUID,Integer LIID,Integer COID, Integer COMIXID,
                                         String LOTMIX,Integer JABLOTE,Integer JABMIX,Integer MIX,Integer UID)
    {
        String _UPDATE;
        _UPDATE = "UPDATE IngresoJabas SET "+ING_FECHA+"= '"+FECHA+"',"+HORA_INI+"='"+HORAINI+"',"
                +HORA_FIN+"='"+HORAFIN+"',"+PARADAS+"='"+PARADA+"',"+PLANTA+"='"+PLANTAS+"',"
                +LINEA+"='"+LINEAS+"',"+LOTE+"='"+LOTES+"',"+JABAS+"='"+JABA+"',"+ESCAMPO+"='"+CAMPO+"',"
                +ESREPRO+"='"+REPRO+"',"+ESTADO+"='"+EST+"',"+"Ing_Syn_Est"+"='"+SYNC+
                //ADICIONAL
                "',"+SUCID+"='"+SUID+"',"+LINID+"='"+LIID+"',"+CONID+"='"+COID+
                "',"+CONIDMIX+"='"+COMIXID+"',"+LOTEMIX+"='"+LOTMIX+"',"+JABASLOTE+"='"+JABLOTE+
                "',"+JABASMIX+"='"+JABMIX+"',"+ESMIX+"='"+MIX+"',"+USUID+"='"+UID+

                "' WHERE "+"Ing_Id"+"='"+ID+"';";
        return _UPDATE;
    }

    public static String _SELECT_EXIST(Integer IDMOVIL,String IMEI,String FECHAREG)
    {
        String _SELECT_ID="";

        _SELECT_ID = "SELECT "+"Ing_Id FROM IngresoJabas WHERE Ing_IdMovil ='"+IDMOVIL+
                "' AND Ing_IMEI='"+IMEI+"' AND "+FECHAHORA+"='"+FECHAREG+"';";
        return _SELECT_ID;
    }

}
