package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/09/16.
 */
public class T_MotivoParada {
    public static final String MotParId ="MotPar_Id";
    public static final String MotParDescripcion ="MotPar_Descripcion";
    public static final String MotParCantidad = "MotPar_Cantidad";
    public static final String EstId = "Est_Id";

    // -------------NOMBRE TABLA
    public static final String NombreTabla = "MotivoParada";

    public static final String Create_MotivoParada ="CREATE TABLE " + NombreTabla+"("+
            MotParId +" INTEGER PRIMARY KEY, "+
            MotParDescripcion +" TEXT NOT NULL, "+
            MotParCantidad+" INTEGER, "+
            EstId+" INTEGER NOT NULL"+
            ");";

    public static final String Drop_MotivoParada ="DROP TABLE IF EXISTS "+NombreTabla;

    public static final String Campos_MotivoParada = MotParId+","+MotParDescripcion
            +","+MotParCantidad+","+EstId;

    public static String MotivoParada_Insertar(
            int MotPar_Id,String MotPar_Descripcion, int MotPar_Cantidad,int Est_Id)
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+Campos_MotivoParada+
                ")VALUES('"+
                MotPar_Id+"','"+MotPar_Descripcion+"','"+MotPar_Cantidad+"','"+Est_Id+"');";

        return Insertar;
    }

    public static String MotivoParada_SeleccionarTodos()
    {
        String Seleccionar;
        Seleccionar = "SELECT "+Campos_MotivoParada
                +" FROM "+NombreTabla+";";
        return Seleccionar;
    }

    public static String Eliminar()
    {
        String Eliminar;
        Eliminar = "DELETE FROM "+NombreTabla +";";
        return Eliminar;
    }
}
