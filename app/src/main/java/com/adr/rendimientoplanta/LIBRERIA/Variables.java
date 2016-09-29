package com.adr.rendimientoplanta.LIBRERIA;

import android.support.v7.app.AppCompatActivity;
//HOLA MUNDO !!! X2 123
// HOLA MUNDO PARTE DOS RED MOVIL
/**
 * Created by smachado on 2016/05/03.
 */
public class Variables extends AppCompatActivity{
    public static final String BaseLocal = "DBLocal";
    public static final Integer VersionBD = 9;

    public static String Usu_Alias="";
    public static String Usu_Codigo="";
    public static int Usu_Id=0;

    public  static Integer Res_Id=0;

    public  static  Integer Cul_Id=0;
    public  static  String Cul_Descripcion="";

    public static String MAC= "";
    public static String IMEI = "";

    public  static String FechaStr;
    public  static String FechaStrBD;

    public  static Integer Con_Id=0;
    public static  Class ActividadPrevia;
    public static String UltimaHoraFinal="";

    public static int TarOri_Id = 0;
    public static String TarOri_Descripcion="";
    public static String TarOri_Codigo="";

    public static int TarTipo_Id =0;
    public  static String TarTipo_Descripcion="";
    public  static String TarTipo_Codigo="";

    public static int TarSubTipo_Id = 0;
    public static String TarSubTipo_Descripcion="";

    public static int Per_Id = 0;
    public static String Per_Nombre="";

    public static String Per_ApePaterno="";
    public static String Per_ApeMaterno="";

    public static String Per_CodigoOpe="";

    //VARIABLES USADAS PARA EL AGRUPADOR
    public static Integer Cam_Id=0;
    public static Integer Lin_Id = 0;
    public static String Lin_Descripcion = "";
    public static Integer Sub_Id=0;
    public static String Sub_Descripcion="";
    public static Integer Pro_Id=0;
    public static String  Pro_Descripcion="";
    public  static Integer Emp_Id=0;
    public  static String Emp_Descripcion="";
    public static String Per_Nombres="";
    public static String Per_Dni="";
    public static int Per_Ubicacion=0;
    public static String Lin_Lado = "";
    public  static String Emp_Abrev="";
    public static Integer Suc_Id = -1;
    public static String Suc_Descripcion="";
    public static int Suc_EsPlanta=-1;
    public static int Agru_Id=0;
    public static String HoraLectura="";
    public static String HoraIngreso="";
    public static String HoraSalida="";
    public static int Mot_Id=0;
    public static int Agru_EstId=0;

    //VARIABLES USADAS PARA REGISTRO ARMADO
    public static int Pre_Id=0;
    public static String Pre_Descripcion="";
    public static int PreEnv_Id=0;
    public static String PreEnv_DescripcionCor="";
    public static double PreEnv_PesoTorre=0;
    public static int PreEnv_CantidadTorre=0;
    public static double PreEnv_Factor=0;



}
