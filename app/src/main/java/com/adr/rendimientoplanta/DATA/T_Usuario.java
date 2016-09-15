package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/11.
 */
public class T_Usuario {

    public static final String N_TABLA = "Usuario";

    public static final String USUID="Usu_Id";
    public static final String USUCODIGO="Usu_Codigo";
    public static final String USUNOMBRE="Usu_Nombre";
    public static final String USUPASSWORD="Usu_Password";
    public static final String USUALIAS="Usu_Alias";
    public static final String ESTID="Est_Id";
    public static final String USUINICIALES="Usu_Iniciales";
    public static final String USUESMOVIL="Usu_EsMovil";


    public static final String CREATE_T_USUARIO ="CREATE TABLE " + N_TABLA+"("+
            USUID +" INTEGER PRIMARY KEY NOT NULL, "+
            USUCODIGO +" TEXT NOT NULL, "+
            USUNOMBRE+" TEXT NOT NULL, "+
            USUPASSWORD+" TEXT NOT NULL, "+
            USUALIAS+" INTEGER NOT NULL, "+
            ESTID+" INTEGER NOT NULL, "+
            USUINICIALES+" TEXT NOT NULL, "+
            USUESMOVIL+" INTEGER NOT NULL);";

    public static final String DROP_T_USUARIO ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(Integer ID,String CODIGO,String NOMBRE,String PASSWORD,String ALIAS,
                                 Integer EST,String INICIALES,Integer MOVIL)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+USUID+","+USUCODIGO+","+USUNOMBRE+","
                +USUPASSWORD+","+USUALIAS+","+ESTID+","+USUINICIALES+","+USUESMOVIL+
                ")VALUES('"+
                ID+"','"+CODIGO+"','"+NOMBRE+"','"+PASSWORD+"','"+ALIAS+"','"+EST+
                "','"+INICIALES+"','"+MOVIL+"');";

        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_USUARIO(String USUARIO)
    {
        String _SELECT_USUARIO;
        _SELECT_USUARIO = "SELECT "+USUNOMBRE+","+USUPASSWORD+","
                +USUALIAS+","+USUID+","+USUCODIGO+","+ESTID+","+USUINICIALES+","+USUESMOVIL+" FROM "
                +N_TABLA+
                " WHERE "+USUNOMBRE+"='"+USUARIO+"';";
        return _SELECT_USUARIO;
    }

    public static String _SELECT_SYNC(Integer MOVIL,Integer EST)
    {
        String _SELECT_ID="";

        _SELECT_ID = "SELECT "+USUID+","+USUCODIGO+","+USUNOMBRE+","+USUPASSWORD+","
                +USUALIAS+","+ESTID+","+USUINICIALES+","+USUESMOVIL+" FROM "+N_TABLA;
        if (MOVIL!=-1||EST!=-1)
        {
            _SELECT_ID=_SELECT_ID+" WHERE ";
        }
        if (MOVIL!=-1)
        {
            _SELECT_ID = _SELECT_ID+USUESMOVIL+"='"+MOVIL+"'";
        }
        if (EST!=-1&&MOVIL!=-1)
        {
            _SELECT_ID = _SELECT_ID+" AND ";
        }
        if (EST!=-1)
        {
            _SELECT_ID = _SELECT_ID+ESTID+ "='"+EST+"'";
        }
        _SELECT_ID = _SELECT_ID+";";
        return _SELECT_ID;
    }

}
