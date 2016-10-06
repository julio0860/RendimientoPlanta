package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/09/28.
 */
public class T_RendimientoArmado {
    public static final String RenArmId ="RenArm_Id";
    public static final String RenArmFecha ="RenArm_Fecha";
    public static final String PerDni = "Per_Dni";
    public static final String SucId = "Suc_Id";
    public static final String ProId = "Pro_Id";
    public static final String SubId = "Sub_Id";
    public static final String LinId = "Lin_Id";
    public static final String LinLado = "Lin_Lado";
    public static final String RenArmFechaReg = "RenArm_FechaReg";
    public static final String UsuId = "Usu_Id";
    public static final String RenArmMac = "RenArm_Mac";
    public static final String PreId = "Pre_Id";
    public static final String PreDescripcion = "Pre_Descripcion";
    public static final String PreEnvId = "PreEnv_Id";
    public static final String PreEnvDescripcionCor = "PreEnv_DescripcionCor";
    public static final String RenArmEntrega = "RenArm_Entrega";
    public static final String RenArmDevolucion = "RenArm_Devolucion";
    public static final String RenArmPeso = "RenArm_Peso";
    public static final String RenArmFactor = "RenArm_Factor";
    public static final String RenArmCantidad = "RenArm_Cantidad";
    public static final String RenArmEquivalente = "RenArm_Equivalente";
    public static final String RenArmHoraIni = "RenArm_HoraIni";
    public static final String EstId = "Est_Id";
    public static final String RenArmSincronizado = "RenArm_Sincronizado";
    public static final String RenArmIdServidor = "RenArm_IdServidor";
    //PARA EL SERVIDOR
    public static final String RenArmUltimaSincro = "RenArm_UltimaSincro";


    // -------------NOMBRE TABLA
    public static final String NombreTabla = "RendimientoArmado";

    public static final String Create_RendimientoArmado ="CREATE TABLE " + NombreTabla+"("+
            RenArmId +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            RenArmFecha +" TEXT NOT NULL, "+
            PerDni+" TEXT NOT NULL, "+
            SucId+" INTEGER NOT NULL, "+
            ProId+" INTEGER NOT NULL, "+
            SubId+" INTEGER NOT NULL, "+
            LinId+" INTEGER NOT NULL, "+
            LinLado+" TEXT NOT NULL, "+
            RenArmFechaReg+" TEXT NOT NULL, "+
            UsuId+" INTEGER NOT NULL, "+
            RenArmMac+" TEXT NOT NULL, "+
            PreId+" INTEGER NOT NULL,"+
            PreDescripcion+" TEXT NOT NULL, " +
            PreEnvId+" INTEGER NOT NULL," +
            PreEnvDescripcionCor+" TEXT NOT NULL," +
            RenArmEntrega+" INTEGER NOT NULL, " +
            RenArmDevolucion+" INTEGER NOT NULL, " +
            RenArmPeso+" INTEGER NOT NULL, " +
            RenArmFactor+" REAL NOT NULL, " +
            RenArmCantidad+" INTEGER NOT NULL, " +
            RenArmEquivalente+" REAL NOT NULL, " +
            RenArmHoraIni+" TEXT NOT NULL, " +
            EstId+" INTEGER NOT NULL, " +
            RenArmSincronizado+" INTEGER NOT NULL, " +
            RenArmIdServidor+" INTEGER" +
            ");";

    public static final String Drop_RendimientoArmado ="DROP TABLE IF EXISTS "+NombreTabla;

    public static final String CamposSeleccionar_RendimientoArmado = RenArmId+" as '_id',"+RenArmFecha+","+PerDni
            +","+SucId+","+ProId+","+SubId+","+LinId+","+LinLado+","+RenArmFechaReg+","+UsuId+","
            +RenArmMac+","+PreId+","+PreDescripcion+","+PreEnvId+","+PreEnvDescripcionCor+","
            +RenArmEntrega+","+RenArmDevolucion+","+RenArmPeso+","+RenArmFactor+","+RenArmCantidad+","
            +RenArmEquivalente+","+RenArmHoraIni+","+EstId+","+RenArmSincronizado+","+RenArmIdServidor;

    public static final String CamposInsertar_RendimientoArmado = RenArmFecha+","+PerDni
            +","+SucId+","+ProId+","+SubId+","+LinId+","+LinLado+","+RenArmFechaReg+","+UsuId+","
            +RenArmMac+","+PreId+","+PreDescripcion+","+PreEnvId+","+PreEnvDescripcionCor+","
            +RenArmEntrega+","+RenArmDevolucion+","+RenArmPeso+","+RenArmFactor+","+RenArmCantidad+","
            +RenArmEquivalente+","+RenArmHoraIni+","+EstId+","+RenArmSincronizado+","+RenArmIdServidor;

    public static final String CamposInsertar_RenArmServidor = RenArmFecha+","+PerDni
            +","+SucId+","+ProId+","+SubId+","+LinId+","+LinLado+","+RenArmFechaReg+","+UsuId+","
            +RenArmMac+","+PreId+","+PreDescripcion+","+PreEnvId+","+PreEnvDescripcionCor+","
            +RenArmEntrega+","+RenArmDevolucion+","+RenArmPeso+","+RenArmFactor+","+RenArmCantidad+","
            +RenArmEquivalente+","+RenArmHoraIni+","+EstId+","+RenArmSincronizado+","+RenArmUltimaSincro;

    public static final String CamposInsertar_LineaSincronizar = "";

    public static String RendimientoArmado_Insertar(
            String RenArm_Fecha,String Per_Dni, int Suc_Id,int Pro_Id,int Sub_Id,int Lin_Id,String Lin_Lado,
            String RenArm_FechaReg,int Usu_Id,String RenArm_Mac,int Pre_Id,String Pre_Descripcion,
            int PreEnv_Id,String PreEnv_DescripcionCor,int RenArm_Entrega,int RenArm_Devolucion,
            int RenArm_Peso,double RenArm_Factor,int RenArm_Cantidad,double RenArm_Equivalente,
            String RenArm_HoraIni,int Est_Id,int RenArm_Sincronizado,int RenArm_IdServidor
            )
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+CamposInsertar_RendimientoArmado+
                ")VALUES('"+
                RenArm_Fecha+"','"+Per_Dni
                +"','"+Suc_Id+"','"+Pro_Id+"','"+Sub_Id+"','"+Lin_Id+"','"+Lin_Lado+"','"+RenArm_FechaReg+"','"+Usu_Id+"','"
                +RenArm_Mac+"','"+Pre_Id+"','"+Pre_Descripcion+"','"+PreEnv_Id+"','"+PreEnv_DescripcionCor+"','"
                +RenArm_Entrega+"','"+RenArm_Devolucion+"','"+RenArm_Peso+"','"+RenArm_Factor+"','"+RenArm_Cantidad+"','"
                +RenArm_Equivalente+"','"+RenArm_HoraIni+"','"+Est_Id+"','"+RenArm_Sincronizado+"','"+RenArm_IdServidor
                +"');";
        return Insertar;
    }
    public static String RendimientoArmado_InsertarServidor(
            String RenArm_Fecha,String Per_Dni, int Suc_Id,int Pro_Id,int Sub_Id,int Lin_Id,String Lin_Lado,
            String RenArm_FechaReg,int Usu_Id,String RenArm_Mac,int Pre_Id,String Pre_Descripcion,
            int PreEnv_Id,String PreEnv_DescripcionCor,int RenArm_Entrega,int RenArm_Devolucion,
            int RenArm_Peso,double RenArm_Factor,int RenArm_Cantidad,double RenArm_Equivalente,
            String RenArm_HoraIni,int Est_Id,int RenArm_Sincronizado,String RenArm_UltimaSincro
    )
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+CamposInsertar_RenArmServidor+
                ")VALUES('"+
                RenArm_Fecha+"','"+Per_Dni
                +"','"+Suc_Id+"','"+Pro_Id+"','"+Sub_Id+"','"+Lin_Id+"','"+Lin_Lado+"','"+RenArm_FechaReg+"','"+Usu_Id+"','"
                +RenArm_Mac+"','"+Pre_Id+"','"+Pre_Descripcion+"','"+PreEnv_Id+"','"+PreEnv_DescripcionCor+"','"
                +RenArm_Entrega+"','"+RenArm_Devolucion+"','"+RenArm_Peso+"','"+RenArm_Factor+"','"+RenArm_Cantidad+"','"
                +RenArm_Equivalente+"','"+RenArm_HoraIni+"','"+Est_Id+"','"+RenArm_Sincronizado+"','"+RenArm_UltimaSincro
                +"');";
        return Insertar;
    }
    public static String RendimientoArmado_SeleccionarSincronizar(
            String RenArm_Fecha,int Suc_Id,int Pro_Id,int Sub_Id,int Lin_Id,String Lin_Lado)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_RendimientoArmado
                +" FROM "+NombreTabla
                +" WHERE "
                +RenArmFecha+"='"+RenArm_Fecha+"'"
                +" AND "+SucId+"='"+Suc_Id+"'"
                +" AND "+ProId+"='"+Pro_Id+"'"
                +" AND "+SubId+"='"+Sub_Id+"'"
                +" AND "+LinId+"='"+Lin_Id+"'"
                +" AND "+LinLado+"='"+Lin_Lado+"'"
                +";";
        return Seleccionar;
    }
    public static String RendimientoArmado_SeleccionarSincronizarPersona(
            String RenArm_Fecha,int Suc_Id,int Pro_Id,int Sub_Id,int Lin_Id,String Lin_Lado,String Dni)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_RendimientoArmado
                +" FROM "+NombreTabla
                +" WHERE "
                +RenArmFecha+"='"+RenArm_Fecha+"'"
                +" AND "+SucId+"='"+Suc_Id+"'"
                +" AND "+ProId+"='"+Pro_Id+"'"
                +" AND "+SubId+"='"+Sub_Id+"'"
                +" AND "+LinId+"='"+Lin_Id+"'"
                +" AND "+LinLado+"='"+Lin_Lado+"'"
                +" AND "+PerDni+"='"+Dni+"'"
                +";";
        return Seleccionar;
    }
    public static String RendimientoArmado_SeleccionarPorPersona(String RenArm_Fecha,String Per_Dni,
                         int Suc_Id,int Pro_Id,int Sub_Id,int Lin_Id,String Lin_Lado,int PreEnv_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_RendimientoArmado
                +" FROM "+NombreTabla
                +" WHERE "+RenArmFecha+"='"+RenArm_Fecha+"'"
                +" AND "+PerDni+"='"+Per_Dni+"'"
                +" AND "+SucId+"='"+Suc_Id+"'"
                +" AND "+ProId+"='"+Pro_Id+"'"
                +" AND "+SubId+"='"+Sub_Id+"'"
                +" AND "+LinId+"='"+Lin_Id+"'"
                +" AND "+LinLado+"='"+Lin_Lado+"'"
                +" AND "+PreEnvId+"='"+PreEnv_Id+"'"
                +";";
        return Seleccionar;
    }
    public static String RendimientoArmado_SeleccionarPorPersonaResumen(String RenArm_Fecha,String Per_Dni,
                         int Suc_Id,int Pro_Id,int Sub_Id,int Lin_Id,String Lin_Lado,int PreEnv_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+"SUM("+RenArmCantidad+"),SUM("+RenArmEquivalente+"),COUNT(*)"
                +" FROM "+NombreTabla
                +" WHERE "+RenArmFecha+"='"+RenArm_Fecha+"'"
                +" AND "+PerDni+"='"+Per_Dni+"'"
                +" AND "+SucId+"='"+Suc_Id+"'"
                +" AND "+ProId+"='"+Pro_Id+"'"
                +" AND "+SubId+"='"+Sub_Id+"'"
                +" AND "+LinId+"='"+Lin_Id+"'"
                +" AND "+LinLado+"='"+Lin_Lado+"'"
                +" AND "+PreEnvId+"='"+PreEnv_Id+"'"
                +";";
        return Seleccionar;
    }
    public static String RenArm_SeleccionarPersonaTotalResumen(String RenArm_Fecha,String Per_Dni,
         int Suc_Id,int Pro_Id,int Sub_Id,int Lin_Id,String Lin_Lado)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+"SUM("+RenArmCantidad+"),SUM("+RenArmEquivalente+"),COUNT(*)"
                +" FROM "+NombreTabla
                +" WHERE "+RenArmFecha+"='"+RenArm_Fecha+"'"
                +" AND "+PerDni+"='"+Per_Dni+"'"
                +" AND "+SucId+"='"+Suc_Id+"'"
                +" AND "+ProId+"='"+Pro_Id+"'"
                +" AND "+SubId+"='"+Sub_Id+"'"
                +" AND "+LinId+"='"+Lin_Id+"'"
                +" AND "+LinLado+"='"+Lin_Lado+"'"
                +";";
        return Seleccionar;
    }

    public static String RenArm_SeleccionarPorPersonaResumenPresentacion(
            String RenArm_Fecha,String Per_Dni,int Suc_Id,int Pro_Id,int Sub_Id,int Lin_Id,
            String Lin_Lado)
    {
        String Seleccionar;
        Seleccionar = "SELECT COUNT(*) as '_id'," +PreEnvDescripcionCor+" as '1' ,SUM("+RenArmEntrega+") as '2',SUM("+RenArmDevolucion+") as '3',SUM("
                +RenArmCantidad+") as '4',SUM("+RenArmEquivalente+") as '5'"
                +" FROM "+NombreTabla
                +" WHERE "+RenArmFecha+"='"+RenArm_Fecha+"'"
                +" AND "+PerDni+"='"+Per_Dni+"'"
                +" AND "+SucId+"='"+Suc_Id+"'"
                +" AND "+ProId+"='"+Pro_Id+"'"
                +" AND "+SubId+"='"+Sub_Id+"'"
                +" AND "+LinId+"='"+Lin_Id+"'"
                +" AND "+LinLado+"='"+Lin_Lado+"'"
                +" GROUP BY "+PreEnvDescripcionCor
                +";";
        return Seleccionar;
    }
    public static String RendimientoArmado_SeleccionarTodos()
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_RendimientoArmado
                +" FROM "+NombreTabla
                +";";
        return Seleccionar;
    }
    public static String RendimientoArmado_ActualizarIdServidor(
            int IdServidor,int IdMovil,int Sincronizado)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +RenArmIdServidor+"= '"+IdServidor+"',"
                +RenArmSincronizado+"= '"+Sincronizado+"'"
                +" WHERE "
                +RenArmId+"='"+IdMovil+"';";
        return Actualizar;
    }
    public static String Servidor_ActualizarEstado(
            int Est_Id,int Id_Servidor,String UltimaSincro)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +EstId+"= '"+Est_Id+"',"
                +RenArmUltimaSincro+"= '"+UltimaSincro+"'"
                +" WHERE "
                +RenArmId+"='"+Id_Servidor+"';";
        return Actualizar;
    }


   /*
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
    */

    /*
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
*/
    /*
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
            double LinReg_CantidadPorHora)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegCantidad+"='"+LinReg_Cantidad
                +"',"+LinRegHoraEfectiva+"='"+LinReg_HoraEfectiva
                +"',"+LinRegCantidadPorHora +"='"+LinReg_CantidadPorHora
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
            int Est_Id)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinRegHoraFin+"= '"+LinReg_HoraFin+"',"+LinRegCantidad+"='"+LinReg_Cantidad
                +"',"+LinRegHoraEfectiva+"='"+LinReg_HoraEfectiva+"',"+LinRegParadas+"='"+LinReg_Paradas
                +"',"+LinRegNumParadas+"='"+LinReg_NumParadas+"',"+LinRegCantidadPorHora +"='"+LinReg_CantidadPorHora
                +"',"+LinRegUltimaSincro+"='"+LinReg_UltimaSincro+"',"+EstId+"='"+Est_Id
                +"' WHERE "
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
    public static String LineaRegistro_SeleccionarLinea(int Lin_Id,String LinReg_Fecha)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaRegistro
                +" FROM "+NombreTabla+" WHERE "+LinId+"='"+Lin_Id+"' AND "+LinRegFecha+"='"+LinReg_Fecha+"';";
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
            int Cul_Id
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
                +Est_Id+"','"+Usu_Id+"','"+Suc_Id+"','"+Cul_Id+"');";

        return Insertar;
    }
    */
}
