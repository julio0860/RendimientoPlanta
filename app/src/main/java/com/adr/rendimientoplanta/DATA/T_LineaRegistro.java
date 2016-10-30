package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/09/16.
 */
public class T_LineaRegistro {
    public static final String LinRegIdMovil ="LinReg_IdMovil";
    public static final String LinRegId ="LinReg_Id";
    public static final String LinId = "Lin_Id";
    public static final String LinRegFecha = "LinReg_Fecha";
    public static final String LinRegHoraIni = "LinReg_HoraIni";
    public static final String LinRegHoraFin = "LinReg_HoraFin";
    public static final String LinRegCantidad = "LinReg_Cantidad";
    public static final String LinRegHoraEfectiva = "LinReg_HoraEfectiva";
    public static final String LinRegParadas = "LinReg_Paradas";
    public static final String LinRegNumParadas = "LinReg_NumParadas";
    public static final String LinRegCantidadPorHora = "LinReg_CantidadPorHora";
    public static final String LinRegMac = "LinReg_Mac";
    public static final String LinRegFechaHora = "LinReg_FechaHora";
    public static final String LinRegUltimaSincro = "LinReg_UltimaSincro";
    public static final String EstId = "Est_Id";
    public static final String UsuId = "Usu_Id";
    public static final String SucId = "Suc_Id";
    public static final String CulId = "Cul_Id";
    //NUEVOS CAMPOS
    public static final String LinRegTiempoTotal = "LinReg_TiempoTotal";
    public static final String LinRegNumIngresos = "LinReg_NumIngresos";
    // -------------NOMBRE TABLA
    public static final String NombreTabla = "LineaRegistro";

    public static final String Create_LineaRegistro ="CREATE TABLE " + NombreTabla+"("+
            LinRegIdMovil +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            LinRegId +" INTEGER, "+
            LinId+" INTEGER NOT NULL, "+
            LinRegFecha+" TEXT NOT NULL, "+
            LinRegHoraIni+" TEXT NOT NULL, "+
            LinRegHoraFin+" TEXT, "+
            LinRegCantidad+" REAL, "+
            LinRegHoraEfectiva+" REAL, "+
            LinRegParadas+" REAL, "+
            LinRegNumParadas+" INTEGER, "+
            LinRegCantidadPorHora+" REAL, "+
            LinRegMac+" TEXT NOT NULL, "+
            LinRegFechaHora+" TEXT NOT NULL,"+
            LinRegUltimaSincro+" TEXT, " +
            EstId+" INTEGER NOT NULL," +
            UsuId+" INTEGER NOT NULL, " +
            SucId+" INTEGER NOT NULL, " +
            CulId+" INTEGER NOT NULL, " +
            LinRegTiempoTotal+" REAL,"+
            LinRegNumIngresos+" INTEGER"
            +");";

    public static final String Drop_LineaRegistro ="DROP TABLE IF EXISTS "+NombreTabla;

    public static final String CamposSeleccionar_LineaRegistro = LinRegIdMovil+","+LinRegId+","+LinId
            +","+LinRegFecha+","+LinRegHoraIni+","+LinRegHoraFin+","+LinRegCantidad
            +","+LinRegHoraEfectiva+","+LinRegParadas+","+LinRegNumParadas+","+LinRegCantidadPorHora
            +","+LinRegMac+","+LinRegFechaHora+","+LinRegUltimaSincro+","+EstId+","+UsuId+","+SucId+","+CulId
            +","+LinRegTiempoTotal+","+LinRegNumIngresos;

    public static final String CamposInsertar_LineaRegistro = LinId+","+LinRegFecha
            +","+LinRegHoraIni+","+LinRegMac+","+LinRegFechaHora+","+EstId+","+UsuId+","+SucId+","+CulId+","+LinRegHoraFin;

    public static final String CamposInsertar_LineaSincronizar = LinRegIdMovil+","+LinId+","+LinRegFecha
            +","+LinRegHoraIni +","+LinRegHoraFin +","+LinRegCantidad+","+LinRegHoraEfectiva+","+LinRegParadas
            +","+LinRegNumParadas+","+LinRegCantidadPorHora+","+LinRegMac+","+LinRegFechaHora+","+LinRegUltimaSincro
            +","+EstId+","+UsuId+","+SucId+","+CulId+","+LinRegTiempoTotal+","+LinRegNumIngresos;

    public static String LineaRegistro_Insertar(
            int Lin_Id,String LinReg_Fecha, String LinReg_HoraIni,String LinReg_Mac,
            String LinReg_FechaHora,int Est_Id,int Usu_Id,int Suc_Id,int Cul_Id,String LinReg_HoraFin)
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+CamposInsertar_LineaRegistro+
                ")VALUES('"+
                Lin_Id+"','"+LinReg_Fecha+"','"+LinReg_HoraIni+"','"+LinReg_Mac+"','"
                +LinReg_FechaHora+"','"+Est_Id+"','"+Usu_Id+"','"+Suc_Id+"','"+Cul_Id+"','"+LinReg_HoraFin+"');";

        return Insertar;
    }
    public static String LineaRegistro_Actualizar(
            int LinReg_IdMovil,String LinReg_HoraFin,double LinReg_Cantidad,double LinReg_HoraEfectiva,
            double LinReg_Paradas,int LinReg_NumParadas,double LinReg_CantidadPorHora,
            String LinReg_Mac,String LinReg_FechaHora,String LinReg_UltimaSincro,int Est_Id)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegHoraFin+"= '"+LinReg_HoraFin+"',"+LinRegCantidad+"='"+LinReg_Cantidad
                +"',"+LinRegHoraEfectiva+"='"+LinReg_HoraEfectiva+"',"+LinRegParadas+"='"+LinReg_Paradas
                +"',"+LinRegNumParadas+"='"+LinReg_NumParadas+"',"+LinRegCantidadPorHora +"='"+LinReg_CantidadPorHora
                +"',"+LinRegMac+"='"+LinReg_Mac+"',"+LinRegFechaHora+"='"+LinReg_FechaHora
                +"',"+LinRegUltimaSincro+"='"+LinReg_UltimaSincro+"',"+EstId+"='"+Est_Id
                +"' WHERE "
                +LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Actualizar;
    }
    public static String LinReg_ModificarEstado(
            int LinReg_IdMovil,int Est_Id)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +EstId+"='"+Est_Id+"'"
                +" WHERE "
                +LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Actualizar;
    }

    public static String LineaRegistro_ActualizarTermino(
            int LinReg_IdMovil,String LinReg_HoraFin,int Est_Id)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegHoraFin+"='"+LinReg_HoraFin
                +"',"+EstId+"='"+Est_Id
                +"' WHERE "
                +LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Actualizar;
    }

    public static String LineaRegistro_ActualizarParadas(
            int LinReg_IdMovil,double LinReg_Paradas,int LinReg_NumParadas)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegParadas+"='"+LinReg_Paradas
                +"',"+LinRegNumParadas+"='"+LinReg_NumParadas
                +"' WHERE "
                +LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Actualizar;
    }
    public static String LineaRegistro_ActualizarIngreso(
            int LinReg_IdMovil,double LinReg_Cantidad,double LinReg_HoraEfectiva,
            double LinReg_CantidadPorHora,double LinReg_TiempoTotal,int LinReg_NumIngresos)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegCantidad+"='"+LinReg_Cantidad
                +"',"+LinRegHoraEfectiva+"='"+LinReg_HoraEfectiva
                +"',"+LinRegCantidadPorHora +"='"+LinReg_CantidadPorHora
                +"',"+LinRegTiempoTotal +"='"+LinReg_TiempoTotal
                +"',"+LinRegNumIngresos +"='"+LinReg_NumIngresos
                +"' WHERE "
                +LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Actualizar;
    }
    public static String LinReg_ActualizarHoraIni(int LinReg_IdMovil,
            String LinReg_HoraIni)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegHoraIni+"='"+LinReg_HoraIni
                +"' WHERE "
                +LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Actualizar;
    }


    public static String LineaRegistro_ActualizarResumen(
            int LinReg_IdMovil,int LinReg_NumParadas,double LinReg_Paradas,double LinReg_TiempoTotal,
            double LinReg_HoraEfectiva,double LinReg_Cantidad,double LinReg_CantidadPorHora,
            int LinReg_NumIngresos)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegNumParadas+"='"+LinReg_NumParadas
                +"',"+LinRegParadas+"='"+LinReg_Paradas
                +"',"+LinRegTiempoTotal+"='"+LinReg_TiempoTotal
                +"',"+LinRegHoraEfectiva+"='"+LinReg_HoraEfectiva
                +"',"+LinRegCantidad +"='"+LinReg_Cantidad
                +"',"+LinRegCantidadPorHora +"='"+LinReg_CantidadPorHora
                +"',"+LinRegNumIngresos +"='"+LinReg_NumIngresos
                +"' WHERE "
                +LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Actualizar;
    }


    public static String LineaRegistro_ActualizarIdServidor(
            int LinReg_IdMovil,int LinReg_Id)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegId+"= '"+LinReg_Id
                +"' WHERE "
                +LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Actualizar;
    }
    public static String LineaRegistro_ActualizarHoraSincro(
            int LinReg_IdMovil,String LinReg_UltimaSincro)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegUltimaSincro+"= '"+LinReg_UltimaSincro
                +"' WHERE "
                +LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Actualizar;
    }
    public static String LineaRegistro_ActualizarServidor(
            int LinReg_Id,
            String LinReg_HoraFin,
            double LinReg_Cantidad,
            double LinReg_HoraEfectiva,
            double LinReg_Paradas,
            int LinReg_NumParadas,
            double LinReg_CantidadPorHora,
            String LinReg_UltimaSincro,
            int Est_Id,
            double LinReg_TiempoTotal,
            int LinReg_NumIngresos,
            String LinReg_HoraIni)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegHoraFin+"= '"+LinReg_HoraFin+"',"+LinRegCantidad+"='"+LinReg_Cantidad
                +"',"+LinRegHoraEfectiva+"='"+LinReg_HoraEfectiva+"',"+LinRegParadas+"='"+LinReg_Paradas
                +"',"+LinRegNumParadas+"='"+LinReg_NumParadas+"',"+LinRegCantidadPorHora +"='"+LinReg_CantidadPorHora
                +"',"+LinRegUltimaSincro+"='"+LinReg_UltimaSincro+"',"+EstId+"='"+Est_Id+"',"
                +LinRegTiempoTotal+"='"+LinReg_TiempoTotal+"',"+LinRegNumIngresos+"='"+LinReg_NumIngresos+"',"
                +LinRegHoraIni+"='"+LinReg_HoraIni+"'"
                +" WHERE "
                +LinRegId+"='"+LinReg_Id+"';";
        return Actualizar;
    }

    public static String LineaRegistro_SeleccionarId(int LinReg_IdMovil)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaRegistro
                +" FROM "+NombreTabla+" WHERE "+LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Seleccionar;
    }

    public static String LineaRegistro_SeleccionarLinea(int Lin_Id,String LinReg_Fecha,int Cul_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaRegistro
                +" FROM "+NombreTabla+" WHERE "+LinId+"='"+Lin_Id+"' AND "+LinRegFecha+"='"+LinReg_Fecha
                +"' AND "+CulId+"='"+Cul_Id
                +"';";
        return Seleccionar;
    }

    public static String LineaRegistro_SeleccionarSincronizar(String LinReg_Fecha,int Suc_Id,int Cul_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaRegistro
                +" FROM "+NombreTabla+" WHERE "+LinRegFecha+"='"+LinReg_Fecha+"'"
                +" AND "+SucId+"='"+Suc_Id+"'"
                +" AND "+CulId+"='"+Cul_Id+"'"
                +";";
        return Seleccionar;
    }



    public static String LineaRegistro_SeleccionarIdServidor(String LinReg_Fecha,int Suc_Id,int Cul_Id,int LinReg_IdMovil)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaRegistro
                +" FROM "+NombreTabla+" WHERE "+LinRegFecha+"='"+LinReg_Fecha+"'"
                +" AND "+SucId+"='"+Suc_Id+"'"
                +" AND "+CulId+"='"+Cul_Id+"'"
                +" AND "+LinRegIdMovil+"='"+LinReg_IdMovil+"'"
                +";";
        return Seleccionar;
    }
    public static String LineaRegistro_InsertarServidor(
            int Lin_IdMovil,
            int Lin_Id,
            String LinReg_Fecha,
            String LinReg_HoraIni,
            String LinReg_HoraFin,
            int LinReg_Cantidad,
            double LinReg_HoraEfectiva,
            double LinReg_Paradas,
            int LinReg_NumParadas,
            double LinReg_CantidadPorHora,
            String LinReg_Mac,
            String LinReg_FechaHora,
            String LinReg_UltimaSincro,
            int Est_Id,
            int Usu_Id,
            int Suc_Id,
            int Cul_Id,
            double LinReg_TiempoTotal,
            int LinReg_NumIngresos
    )
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+CamposInsertar_LineaSincronizar+
                ")VALUES('"+
                Lin_IdMovil+"','"+Lin_Id+"','"+LinReg_Fecha+"','"+LinReg_HoraIni
                +"','"+LinReg_HoraFin
                +"','"
                +LinReg_Cantidad+"','"+LinReg_HoraEfectiva+"','"+LinReg_Paradas+"','"+LinReg_NumParadas+"','"
                +LinReg_CantidadPorHora+"','"+LinReg_Mac+"','"+LinReg_FechaHora+"','"+LinReg_UltimaSincro+"','"
                +Est_Id+"','"+Usu_Id+"','"+Suc_Id+"','"+Cul_Id+"','"+LinReg_TiempoTotal+"','"+LinReg_NumIngresos+"');";

        return Insertar;
    }
    public static String LinReg_JabasHora(String LinReg_Fecha,int Suc_Id,int Cul_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT IFNULL(SUM(LR.LinReg_Cantidad)/SUM(LR.LinReg_HoraEfectiva)*COUNT(*),0)"
                +" FROM "+NombreTabla+" LR WHERE "
                +LinRegFecha+"='"+LinReg_Fecha+"'"
                +" AND "+SucId+"='"+Suc_Id+"'"
                +" AND "+CulId+"='"+Cul_Id+"'"
                +";";
        return Seleccionar;
    }


}
