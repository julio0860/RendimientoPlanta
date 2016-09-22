package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/09/16.
 */
public class T_MotivoParada {
    public static final String MotParId ="Mot_Id";
    public static final String MotDescripcion ="Mot_Descripcion";
    public static final String MotCantidad = "Mot_Cantidad";
    public static final String MotEsRendimiento = "Mot_EsRendimiento";
    public static final String EstId = "Est_Id";

    // -------------NOMBRE TABLA
    public static final String NombreTabla = "Motivos";

    public static final String Create_MotivoParada ="CREATE TABLE " + NombreTabla+"("+
            MotParId +" INTEGER PRIMARY KEY, "+
            MotDescripcion +" TEXT NOT NULL, "+
            MotCantidad+" INTEGER, "+
            EstId+" INTEGER NOT NULL, "+
            MotEsRendimiento+" INTEGER NOT NULL"+
            ");";

    public static final String Drop_MotivoParada ="DROP TABLE IF EXISTS "+NombreTabla;

    public static final String Campos_MotivoParada = MotParId+" as _id,"+MotDescripcion
            +","+MotCantidad+","+EstId+","+MotEsRendimiento;

    public static final String Campos_MotivoParadaInsertar = MotParId+","+MotDescripcion
            +","+MotCantidad+","+EstId+","+MotEsRendimiento;

    public static String MotivoParada_Insertar(
            int Mot_Id,String Mot_Descripcion, int Mot_Cantidad,int Est_Id,int Mot_EsRendimiento)
    {
        String Insertar;
        Insertar = "INSERT INTO "+NombreTabla +"("+Campos_MotivoParadaInsertar+
                ")VALUES('"+
                Mot_Id+"','"+Mot_Descripcion+"','"+Mot_Cantidad+"','"+Est_Id+"','"+Mot_EsRendimiento+"');";

        return Insertar;
    }

    public static String MotivoParada_SeleccionarTodos()
    {
        String Seleccionar;
        Seleccionar = "SELECT "+Campos_MotivoParada
                +" FROM "+NombreTabla+";";
        return Seleccionar;
    }
    public static String MotivoParada_SeleccionarParadas()
    {
        String Seleccionar;
        Seleccionar = "SELECT "+Campos_MotivoParada
                +" FROM "+NombreTabla+" WHERE "+MotEsRendimiento+" ='0';";
        return Seleccionar;
    }
    public static String Eliminar()
    {
        String Eliminar;
        Eliminar = "DELETE FROM "+NombreTabla +";";
        return Eliminar;
    }
}
