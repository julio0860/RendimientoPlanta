package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/09/27.
 */
public class T_PresentacionEnvase {
    public static final String PreEnvId ="PreEnv_Id";
    public static final String PreId ="Pre_Id";
    public static final String PreDescripcion = "Pre_Descripcion";
    public static final String PreEnvCodigo = "PreEnv_Codigo";
    public static final String PreEnvDescripcion = "PreEnv_Descripcion";
    public static final String PreEnvDescripcionCor = "PreEnv_DescripcionCor";
    public static final String PreEnvPesoTorre = "PreEnv_PesoTorre";
    public static final String PreEnvCantidadTorre = "PreEnv_CantidadTorre";
    public static final String PreEnvFactor = "PreEnv_Factor";
    public static final String EstId = "Est_Id";


    // -------------NOMBRE TABLA
    public static final String NombreTabla = "PresentacionEnvase";

    public static final String Create_PresentacionEnvase ="CREATE TABLE " + NombreTabla+"("+
            PreEnvId +" INTEGER PRIMARY KEY, "+
            PreId +" INTEGER PRIMARY KEY, "+
            PreDescripcion +" TEXT NOT NULL, "+
            PreEnvCodigo +" TEXT NOT NULL, "+
            PreEnvDescripcion +" TEXT NOT NULL, "+
            PreEnvDescripcionCor +" TEXT NOT NULL, "+
            PreEnvPesoTorre+" REAL, "+
            PreEnvCantidadTorre+" INTEGER, "+
            PreEnvFactor+" REAL, "+
            EstId+" INTEGER NOT NULL "+
            ");";

    public static final String Drop_PresentacionEnvase ="DROP TABLE IF EXISTS "+NombreTabla;

    public static final String Campos_PresentacionEnvase = PreEnvId+" as _id,"+PreId
            +","+PreDescripcion+","+PreEnvCodigo+","+PreEnvDescripcion+","+PreEnvDescripcionCor+","+PreEnvPesoTorre
            +","+PreEnvCantidadTorre+","+PreEnvFactor+","+EstId;

    public static final String Campos_PresentacionEnvaseInsertar = PreEnvId+","+PreId
            +","+PreDescripcion+","+PreEnvCodigo+","+PreEnvDescripcion+","+PreEnvDescripcionCor+","+PreEnvPesoTorre
            +","+PreEnvCantidadTorre+","+PreEnvFactor+","+EstId;

    public static String PresentacionEnvase_Insertar(
            int PreEnv_Id,int Pre_Id, String Pre_Descripcion,String PreEnv_Codigo,String PreEnv_Descripcion,
            String PreEnv_DescripcionCor,double PreEnv_PesoTorre,int PreEnv_CantidadTorre, double PreEnv_Factor,
            int Est_Id)
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+Campos_PresentacionEnvaseInsertar+
                ")VALUES('"+
                PreEnv_Id+"','"+Pre_Id+"','"+Pre_Descripcion+"','"+PreEnv_Codigo+"','"
                +PreEnv_Descripcion+"','"+PreEnv_DescripcionCor+"','"+PreEnv_PesoTorre+"','"+PreEnv_CantidadTorre
                +"','"+PreEnv_Factor+"','"+Est_Id
                +"');";
        return Insertar;
    }

    public static String PresentacionEnvase_SeleccionarTodos()
    {
        String Seleccionar;
        Seleccionar = "SELECT "+Campos_PresentacionEnvase
                +" FROM "+NombreTabla+";";
        return Seleccionar;
    }
    public static String PresentacionEnvase_SeleccionarEstado(int Est_Id)
    {
        String Seleccionar;
        Seleccionar = "SELECT "+Campos_PresentacionEnvase
                +" FROM "+NombreTabla+" WHERE "+EstId+" ='"+Est_Id+"';";
        return Seleccionar;
    }
    public static String Eliminar()
    {
        String Eliminar;
        Eliminar = "DELETE FROM "+NombreTabla +";";
        return Eliminar;
    }
}
