package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/09/16.
 */
public class T_LineaIngreso {
    public static final String LinIngId ="LinIng_Id";
    public static final String LinRegIdMovil ="LinReg_IdMovil";
    public static final String ConId = "Con_Id";
    public static final String ConDescripcionCor = "Con_DescripcionCor";
    public static final String LinIngCantidad = "LinIng_Cantidad";
    public static final String MatPriOriId = "MatPriOri_Id";
    public static final String MatPriOriDescripcion = "MatPriOri_Descripcion";
    public static final String MatPriOriFactor = "MatPriOri_Factor";
    public static final String LinIngEquivalente = "LinIng_Equivalente";
    public static final String LinIngHoraIni = "LinIng_HoraIni";
    public static final String LinIngHoraFin = "LinIng_HoraFin";
    public static final String LinIngtEfectivo = "LinIng_tEfectivo";
    public static final String LinIngMix = "LinIng_Mix";
    public static final String LinIngFechaHora = "LinIng_FechaHora";
    public static final String LinIngSincronizado = "LinIng_Sincronizado";

    // -------------NOMBRE TABLA
    public static final String NombreTabla = "LineaIngreso";

    public static final String Create_LineaIngreso ="CREATE TABLE " + NombreTabla+"("+
            LinIngId +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            LinRegIdMovil +" INTEGER, "+
            ConId+" INTEGER NOT NULL, "+
            ConDescripcionCor+" TEXT NOT NULL, "+
            LinIngCantidad+" INTEGER NOT NULL, "+
            MatPriOriId+" INT NOT NULL, "+
            MatPriOriDescripcion+" TEXT NOT NULL, "+
            MatPriOriFactor+" REAL NOT NULL,"+
            LinIngEquivalente+" REAL NOT NULL,"+
            LinIngHoraIni+" TEXT NOT NULL,"+
            LinIngHoraFin+" TEXT NOT NULL,"+
            LinIngtEfectivo+" REAL NOT NULL,"+
            LinIngMix+" INTEGER NOT NULL,"+
            LinIngFechaHora+" TEXT NOT NULL,"+
            LinIngSincronizado+" INTEGER NOT NULL"+
            ");";

    public static final String Drop_LineaIngreso ="DROP TABLE IF EXISTS "+NombreTabla;

    public static final String CamposSeleccionar_LineaIngreso = LinIngId+" as '_id',"+LinRegIdMovil+","+ConId
            +","+ConDescripcionCor+","+LinIngCantidad+","+MatPriOriId+","+MatPriOriDescripcion
            +","+MatPriOriFactor+","+LinIngEquivalente+","+LinIngHoraIni+","+LinIngHoraFin+","+LinIngtEfectivo
            +","+LinIngMix+","+LinIngFechaHora+","+LinIngSincronizado;

    public static final String CamposInsertar_LineaIngreso = LinRegIdMovil+","+ConId
            +","+ConDescripcionCor+","+LinIngCantidad+","+MatPriOriId+","+MatPriOriDescripcion
            +","+MatPriOriFactor+","+LinIngEquivalente+","+LinIngHoraIni+","+LinIngHoraFin+","+LinIngtEfectivo
            +","+LinIngMix+","+LinIngFechaHora+","+LinIngSincronizado;

    public static final String CamposInsertar_LineaIngresoServidor = "LinReg_Id"+","+ConId
            +","+ConDescripcionCor+","+LinIngCantidad+","+MatPriOriId+","+MatPriOriDescripcion
            +","+MatPriOriFactor+","+LinIngEquivalente+","+LinIngHoraIni+","+LinIngHoraFin+","+LinIngtEfectivo
            +","+LinIngMix+","+LinIngFechaHora+","+LinIngSincronizado;

    public static String LineaIngreso_Insertar(
            int LinReg_IdMovil,int Con_Id, String Con_DescripcionCor,int LinIng_Cantidad,
            int MatProOri_Id,String MatPriOri_Descripcion,double MatPriOri_Factor,double LinIng_Equivalente,
            String LinIng_HoraIni,String LinIng_HoraFin,double LinIng_tEfectivo,int LinIng_Mix,
            String LinIng_FechaHora,int LinIng_Sincronizado)
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+CamposInsertar_LineaIngreso+
                ")VALUES('"+
                LinReg_IdMovil+"','"+Con_Id+"','"+Con_DescripcionCor+"','"+LinIng_Cantidad+"','"
                +MatProOri_Id+"','"+MatPriOri_Descripcion+"','"+MatPriOri_Factor+"','"+LinIng_Equivalente+"','"
                +LinIng_HoraIni+"','"+LinIng_HoraFin+"','"+LinIng_tEfectivo+"','"+LinIng_Mix+"','"
                +LinIng_FechaHora+"','"+LinIng_Sincronizado
                +"');";
        return Insertar;
    }
    public static String LineaIngreso_InsertarServidor(
            int LinReg_Id,int Con_Id, String Con_DescripcionCor,int LinIng_Cantidad,
            int MatProOri_Id,String MatPriOri_Descripcion,double MatPriOri_Factor,double LinIng_Equivalente,
            String LinIng_HoraIni,String LinIng_HoraFin,double LinIng_tEfectivo,int LinIng_Mix,
            String LinIng_FechaHora,int LinIng_Sincronizado)
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+CamposInsertar_LineaIngresoServidor+
                ")VALUES('"+
                LinReg_Id+"','"+Con_Id+"','"+Con_DescripcionCor+"','"+LinIng_Cantidad+"','"
                +MatProOri_Id+"','"+MatPriOri_Descripcion+"','"+MatPriOri_Factor+"','"+LinIng_Equivalente+"','"
                +LinIng_HoraIni+"','"+LinIng_HoraFin+"','"+LinIng_tEfectivo+"','"+LinIng_Mix+"','"
                +LinIng_FechaHora+"','"+LinIng_Sincronizado
                +"');";
        return Insertar;
    }

    public static String LineaIngreso_ActualizarSincronizado(
            int LinIng_Id,int LinIng_Sincronizado)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinIngSincronizado+"= '"+LinIng_Sincronizado
                +"' WHERE "
                +LinIngId+"='"+LinIng_Id+"';";
        return Actualizar;
    }
    public static String LineaIngreso_ActualizarHora(
            int LinIng_Id,double tEfectivo, String HoraFin,int Sincronizado)
    {
        String Actualizar;
        Actualizar = "UPDATE "+NombreTabla +" SET "
                +LinIngHoraFin+"= '"+HoraFin+"',"
                +LinIngtEfectivo+"= '"+tEfectivo+"',"
                +LinIngSincronizado+"= '"+Sincronizado+"'"
                +" WHERE "
                +LinIngId+"='"+LinIng_Id+"';";
        return Actualizar;
    }

    public static String LineaIngreso_SeleccionarIdCabecera(int LinReg_IdMovil)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaIngreso
                +" FROM "+NombreTabla+" WHERE "+LinRegIdMovil+"='"+LinReg_IdMovil+"';";
        return Seleccionar;
    }
    public static String LineaIngreso_SeleccionarSincronizar(int LinReg_IdMovil,int LinReg_Sincronizado)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaIngreso
                +" FROM "+NombreTabla+" WHERE "
                +LinRegIdMovil+"='"+LinReg_IdMovil +"' AND "
                +LinIngSincronizado+"='"+LinReg_Sincronizado +"'"
                +";";
        return Seleccionar;
    }

    public static String LineaIngreso_SeleccionarId(int LinIng_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+CamposSeleccionar_LineaIngreso
                +" FROM "+NombreTabla+" WHERE "+LinIngId+"='"+LinIng_Id+"';";
        return Seleccionar;
    }
    public static String CantidadPorId(int LinReg_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT COUNT(*)"
                +" FROM "+NombreTabla+" WHERE "+LinRegIdMovil+"='"+LinReg_Id+"';";
        return Seleccionar;
    }
    public static String ResumenPorId(int LinReg_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT COUNT(*),SUM("+LinIngtEfectivo+")"
                +" FROM "+NombreTabla+" WHERE "+LinRegIdMovil+"='"+LinReg_Id+"';";
        return Seleccionar;
    }

}
