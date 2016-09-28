package com.adr.rendimientoplanta.DATA;


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
    public static final String DNI = "DNI";
    public static final String HORALECTURA="HoraLectura";
    public static final String HORAINGRESO="HoraIngreso";
    public static final String HORASALIDA="HoraSalida";
    public static final String MOTIVO="Motivo";
    public static final String ESTADO="Est_Id";
    public static final String IDSERVIDOR="Agru_IdServidor";



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
            HORALECTURA +" TEXT NOT NULL, "+
            HORAINGRESO +" TEXT NOT NULL, "+
            HORASALIDA +" TEXT  NULL, "+
            MOTIVO +" INTEGER NOT NULL, "+
            ESTADO +" INTEGER NOT NULL, "+
            IDSERVIDOR+" INTEGER  NULL "+
            ");";

    public static final String DROP_T_AGRUPADOR ="DROP TABLE IF EXISTS "+N_TABLA;


    public static final String CamposSeleccionar_NoSincronizados = AGRUID+","+EMPID+","+FECHA
            +","+SUCID+","+PROID+","+SUBID+","+LINID
            +","+LADO+","+POSICION+","+DNI+","+HORALECTURA
            +","+HORAINGRESO+","+HORASALIDA+","+MOTIVO+","+ESTADO+","+IDSERVIDOR;

    public static final String CamposSeleccionar_Servidor = AGRUID+","+EMPID+","+FECHA
            +","+SUCID+","+PROID+","+SUBID+","+LINID
            +","+LADO+","+POSICION+","+DNI+","+HORALECTURA
            +","+HORAINGRESO+","+HORASALIDA+","+MOTIVO+","+ESTADO;

    public static String _INSERT(int EmpId,String Fecha,int SucId,int ProId,int SubId,int LinId,String Lado,int Posicion,
                                 String Dni,String HoraLectura,String HoraIngreso,int Motivo,int Estado)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+EMPID+","+FECHA+","+SUCID+","+PROID+","+SUBID+","+LINID+","+LADO+","+POSICION+
                ","+DNI+","+HORALECTURA+","+HORAINGRESO+","+MOTIVO+","+ESTADO+
        ")VALUES('"+
                EmpId+"','"+Fecha+"','"+SucId+"','"+ProId+"','"+SubId+"','"+LinId+"','"+Lado+"','"+Posicion+
                "','"+Dni+"','"+HoraLectura+"','"+HoraIngreso+"','"+Motivo+"','"+Estado+"');";
        return _INSERT;
    }

    public static String _UPDATE(int AgruId,int EmpId,String Fecha,int SucId,int ProId,int SubId,int LinId,String Lado,int Posicion,
                                 String Dni,String HoraLectura,String HoraIngreso,String HoraSalida,int Motivo,int Estado)
    {
        String _UPDATE;
        _UPDATE = "UPDATE "+N_TABLA +" SET "+EMPID+"='"+EmpId+"',"+FECHA+"='"+Fecha+"',"+SUCID+"='"+SucId+"',"+PROID+"='"+ProId+"',"+SUBID+"='"+SubId+"',"+
                   LINID+"='"+LinId+"',"+LADO+"='"+Lado+"',"+POSICION+"='"+Posicion+"',"+DNI+"='"+Dni+"',"+HORALECTURA+"='"+HoraLectura+"',"+HORAINGRESO+"='"+HoraIngreso+"',"+
                   HORASALIDA+"='"+HoraSalida+"',"+MOTIVO+"='"+Motivo+"',"+ESTADO+"='"+Estado+"' WHERE "+AGRUID+"='"+AgruId+"'";
        return _UPDATE;
    }

   public static String ActualizarIdServidorLocal(int EmpId,String Fecha,int SucId,int ProId,int SubId,int LinId,String Lado,int Posicion,
                                 String Dni,int Motivo,int Estado,int Idservidor)
    {
        String ActualizarIdServidorLocal;
        ActualizarIdServidorLocal = "UPDATE "+N_TABLA +" SET "+IDSERVIDOR+"='"+Idservidor+"' WHERE "+EMPID+"='"+EmpId+"' AND "+FECHA+"='"+Fecha+"' AND "+SUCID+"='"+SucId+"' AND "+PROID+"='"+ProId+"' AND "
                +SUBID+"='"+SubId+"' AND "+LINID+"='"+LinId+"' AND "+LADO+"='"+Lado+"' AND "+POSICION+"='"+Posicion+"' AND "+DNI+"='"+Dni+"' AND "+LINID+"='"+LinId+"' AND "+MOTIVO+"='"+Motivo+"' AND "+ESTADO+"='"+Estado+"'";
        return ActualizarIdServidorLocal;
    }

    public static String _DELETE(int AgruId)
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +"WHERE "+AGRUID+"='"+AgruId+";";
        return _DELETE;
    }

    public static String _SELECCIONAR_TODOS(String Fecha)
            //SON TODOS LOS QUE TIENEN IDSERVIDOR NULL
    {
        String _SELECCIONAR_NO_SINCRONIZADOS;
        _SELECCIONAR_NO_SINCRONIZADOS = "SELECT "+CamposSeleccionar_NoSincronizados +" FROM "+N_TABLA +" WHERE "+FECHA+"='"+Fecha+"'"+
                ";";
        return _SELECCIONAR_NO_SINCRONIZADOS;
    }

    public static String _INSERTSERVIDOR(int EmpId,String Fecha,int SucId,int ProId,int SubId,int LinId,String Lado,int Posicion,
                                 String Dni,String HoraLectura,String HoraIngreso,String HoraSalida,int Mot_Id,int Est_Id)
    {
        String _INSERTSERVIDOR;
        _INSERTSERVIDOR = "INSERT INTO "+N_TABLA +"("+EMPID+","+FECHA+","+SUCID+","+PROID+","+SUBID+","+LINID+","+LADO+","+POSICION+
                ","+DNI+","+HORALECTURA+","+HORAINGRESO+","+HORASALIDA+","+MOTIVO+","+ESTADO+
                ")VALUES('"+
                EmpId+"','"+Fecha+"','"+SucId+"','"+ProId+"','"+SubId+"','"+LinId+"','"+Lado+"','"+Posicion+
                "','"+Dni+"','"+HoraLectura+"','"+HoraIngreso+"','"+HoraSalida+"','"+Mot_Id+"','"+Est_Id+"');";
        return _INSERTSERVIDOR;
    }

    public static String _SELECCIONAR_TODOSSERVIDOR(String Fecha)
    //SON TODOS LOS REGISTROS DEL SERVIDOR
    {
        String _SELECCIONAR_NO_SINCRONIZADOS;
        _SELECCIONAR_NO_SINCRONIZADOS = "SELECT "+CamposSeleccionar_Servidor +" FROM "+N_TABLA +" WHERE "+FECHA+"='"+Fecha+"'"+
                ";";
        return _SELECCIONAR_NO_SINCRONIZADOS;
    }

    public static String _INSERTDESDESERVIDOR(int EmpId,String Fecha,int SucId,int ProId,int SubId,int LinId,String Lado,int Posicion,
                                 String Dni,String HoraLectura,String HoraIngreso,String HoraSalida,int Motivo,int Estado,int IdServidor)
    {
        String _INSERTDESDESERVIDOR;
        _INSERTDESDESERVIDOR = "INSERT INTO "+N_TABLA +"("+EMPID+","+FECHA+","+SUCID+","+PROID+","+SUBID+","+LINID+","+LADO+","+POSICION+
                ","+DNI+","+HORALECTURA+","+HORAINGRESO+","+HORASALIDA+","+MOTIVO+","+ESTADO+IDSERVIDOR+
                ")VALUES('"+
                EmpId+"','"+Fecha+"','"+SucId+"','"+ProId+"','"+SubId+"','"+LinId+"','"+Lado+"','"+Posicion+
                "','"+Dni+"','"+HoraLectura+"','"+HoraIngreso+"','"+HoraSalida+"','"+Motivo+"','"+Estado+"','"+IdServidor+"');";
        return _INSERTDESDESERVIDOR;
    }
}