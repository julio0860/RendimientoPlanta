package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/09/16.
 */
public class T_LineaParadas {
    public static final String LinParId ="LinPar_Id";
    public static final String LinRegIdMovil ="LinReg_IdMovil";
    public static final String MotId = "Mot_Id";
    public static final String LinParHoraIni = "LinPar_HoraIni";
    public static final String LinParHoraFin = "LinPar_HoraFin";
    public static final String LinParParada = "LinPar_Parada";
    public static final String LinParSincronizado = "LinPar_Sincronizado";
    public static final String LinParFechaHora = "LinPar_FechaHora";
    public static final String MotParDescripcion = "MotPar_Descripcion";
    //--------------NUEVOS CAMPOS
    public static final String EstId = "Est_Id";
    public static final String LinParIdServidor = "LinPar_IdServidor";
    // -------------NOMBRE TABLA
    public static final String NombreTabla = "LineaParadas";

    public static final String Create_LineaParadas ="CREATE TABLE " + NombreTabla+"("+
            LinParId +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            LinRegIdMovil +" INTEGER, "+
            MotId+" INTEGER NOT NULL, "+
            LinParHoraIni+" TEXT NOT NULL, "+
            LinParHoraFin+" TEXT NOT NULL, "+
            LinParParada+" REAL NOT NULL, "+
            LinParSincronizado+" INTEGER NOT NULL, "+
            LinParFechaHora+" TEXT NOT NULL,"+
            MotParDescripcion+" TEXT NOT NULL,"+
            EstId+" INTEGER,"+
            LinParIdServidor+" INTEGER"
            +");";

    public static final String Drop_LineaParadas ="DROP TABLE IF EXISTS "+NombreTabla;

    public static final String CamposSeleccionar_LineaParadas = LinParId+" as '_id',"+LinRegIdMovil+","+MotId
            +","+LinParHoraIni+","+LinParHoraFin+","+LinParParada+","+LinParSincronizado
            +","+LinParFechaHora+","+MotParDescripcion+","+EstId+","+LinParIdServidor;
            //+","+EstId+","+LinParIdServidor;

    public static final String CamposInsertar_LineaParadas =
            LinRegIdMovil+","+MotId+","+LinParHoraIni+","+LinParHoraFin+","+LinParParada
            +","+LinParSincronizado+","+LinParFechaHora+","+MotParDescripcion+","+EstId+","+LinParIdServidor;

    public static final String CamposInsertar_LineaParadasServidor =
            "LinReg_Id"+","+MotId+","+LinParHoraIni+","+LinParHoraFin+","+LinParParada
                    +","+LinParSincronizado+","+LinParFechaHora+","+MotParDescripcion+","+EstId;

    public static String LineaParadas_Insertar(
            int LinReg_IdMovil,int MotPar_Id, String LinPar_HoraIni,String LinPar_HoraFin,
            double LinPar_Parada,int LinPar_Sincronizado,String LinPar_FechaHora,String MotPar_Descripcion
            ,int Est_Id,int LinPar_IdServidor)
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+CamposInsertar_LineaParadas+
                ")VALUES('"+
                LinReg_IdMovil+"','"+MotPar_Id+"','"+LinPar_HoraIni+"','"+LinPar_HoraFin+"','"
                +LinPar_Parada+"','"+LinPar_Sincronizado+"','"+LinPar_FechaHora+"','"+MotPar_Descripcion
                +"','"+Est_Id+"','"+LinPar_IdServidor
                +"');";
        return Insertar;
    }
    public static String LineaParadas_InsertarServidor(
            int LinReg_Id,int MotPar_Id, String LinPar_HoraIni,String LinPar_HoraFin,
            double LinPar_Parada,int LinPar_Sincronizado,String LinPar_FechaHora,String MotPar_Descripcion,
            int Est_Id)
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+CamposInsertar_LineaParadasServidor+
                ")VALUES('"+
                LinReg_Id+"','"+MotPar_Id+"','"+LinPar_HoraIni+"','"+LinPar_HoraFin+"','"
                +LinPar_Parada+"','"+LinPar_Sincronizado+"','"+LinPar_FechaHora+"','"+MotPar_Descripcion
                +"','"+Est_Id
                +"');";
        return Insertar;
    }

    public static String LineaParadas_ActualizarSincronizado(
            int LinPar_Id,int LinPar_Sincronizado)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinParSincronizado+"= '"+LinPar_Sincronizado
                +"' WHERE "
                +LinParId+"='"+LinPar_Id+"';";
        return Actualizar;
    }
    public static String LineaParadas_SeleccionarIdCabecera(int LinReg_IdMovil)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaParadas
                +" FROM "+NombreTabla+" WHERE "+LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Seleccionar;
    }
    public static String LineaParadas_SeleccionarSincronizar(int LinReg_IdMovil,int LinPar_Sincronizado)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaParadas
                +" FROM "+NombreTabla+" WHERE "+LinRegIdMovil+"='"+LinReg_IdMovil+"'"
                +" AND "+LinParSincronizado+"='"+LinPar_Sincronizado+"'"
                +";";
        return Seleccionar;
    }
    public static String LineaParadas_SeleccionarId(int LinPar_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaParadas
                +" FROM "+NombreTabla+" WHERE "+LinParId+"='"+LinPar_Id
                +"';";
        return Seleccionar;
    }
    public static String CantidadPorId(int LinReg_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT COUNT(*)"
                +" FROM "+NombreTabla+" WHERE "+LinRegIdMovil+"='"+LinReg_Id
                +"';";
        return Seleccionar;
    }
    public static String ResumenPorId(int LinReg_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT COUNT(*),SUM("+LinParParada+")"
                +" FROM "+NombreTabla+" WHERE "+LinRegIdMovil+"='"+LinReg_Id
                +"';";
        return Seleccionar;
    }
}
