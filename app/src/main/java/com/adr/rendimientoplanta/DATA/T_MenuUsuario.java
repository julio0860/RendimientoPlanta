package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/12.
 */
public class T_MenuUsuario {
    public static final String N_TABLA = "MenuUsuario";

    public static final String MENUSUID="MenUsu_Id";
    public static final String USUID="Usu_Id";
    public static final String MENCODIGO="Men_Codigo";
    public static final String MENUSUPERMITIDO="MenUsu_Permitido";
    public static final String MENUSUNUEVO="MenUsu_Nuevo";
    public static final String MENUSUEDITAR="MenUsu_Editar";
    public static final String MENUSUIMPRIMIR="MenUsu_Imprimir";
    public static final String MENUSUAPROBAR="MenUsu_Aprobar";
    public static final String MENUSUANULAR="MenUsu_Anular";
    public static final String MENUSUVERLOGS="MenUsu_VerLogs";
    public static final String MENUSUGUARDAR="MenUsu_Guardar";
    public static final String MENUSUSINCRONIZAR="MenUsu_Sincronizar";


    public static final String CREATE_T_MENUUSUARIO ="CREATE TABLE " + N_TABLA+"("+
            MENUSUID +" INTEGER PRIMARY KEY NOT NULL, "+
            USUID +" INTEGER NOT NULL, "+
            MENCODIGO+" TEXT NOT NULL, "+
            MENUSUPERMITIDO+" INTEGER NOT NULL, "+
            MENUSUNUEVO+" INTEGER NOT NULL, "+
            MENUSUEDITAR+" INTEGER NOT NULL, "+
            MENUSUIMPRIMIR+" INTEGER NOT NULL, "+
            MENUSUAPROBAR+" INTEGER NOT NULL, "+
            MENUSUANULAR+" INTEGER NOT NULL, "+
            MENUSUVERLOGS+" INTEGER NOT NULL, "+
            MENUSUGUARDAR+" INTEGER NOT NULL, "+
            MENUSUSINCRONIZAR+" INTEGER NOT NULL);";

    public static final String DROP_T_MENUUSUARIO ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(Integer ID,Integer UID,String MCODIGO,Integer PERMITIDO,Integer NUEVO,
                                 Integer EDITAR,Integer IMPRIMIR,Integer APROBAR,Integer ANULAR,Integer VERLOGS
                                ,Integer GUARDAR,Integer SINCRONIZAR)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+MENUSUID+","+USUID+","+MENCODIGO+","
                +MENUSUPERMITIDO+","+MENUSUNUEVO+","+MENUSUEDITAR+","+MENUSUIMPRIMIR+","+MENUSUAPROBAR+
                ","+MENUSUANULAR+","+MENUSUVERLOGS+","+MENUSUGUARDAR+","+MENUSUSINCRONIZAR+
                ")VALUES('"+
                ID+"','"+UID+"','"+MCODIGO+"','"+PERMITIDO+"','"+NUEVO+"','"+EDITAR+"','"+IMPRIMIR+"','"+APROBAR
                +"','"+ANULAR+"','"+VERLOGS+"','"+GUARDAR+"','"+SINCRONIZAR
                +"');";

        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }

    public static String _SELECT_SYNC(Integer UID,String MCODIGO)
    {
        String _SELECT_MENUSU="";

        _SELECT_MENUSU = "SELECT "+MENUSUID+","+USUID+","+MENCODIGO+","
                +MENUSUPERMITIDO+","+MENUSUNUEVO+","+MENUSUEDITAR+","+MENUSUIMPRIMIR+","+MENUSUAPROBAR+
                ","+MENUSUANULAR+","+MENUSUVERLOGS+","+MENUSUGUARDAR+","+MENUSUSINCRONIZAR+" FROM "+N_TABLA
                +" WHERE "+USUID+"='"+USUID+"' AND "+MENCODIGO+"='"+MCODIGO+"';";
        return _SELECT_MENUSU;
    }
    public static String _SELECT_MODULO(String MMODULO)
    {
        String _SELECT_MENUMOD="";

        _SELECT_MENUMOD = "SELECT "+"MU."+MENUSUID+","+"MU."+USUID+","+"MU."+MENCODIGO+","
                +"MU."+MENUSUPERMITIDO+","+"MU."+MENUSUNUEVO+","+"MU."+MENUSUEDITAR+","+"MU."+MENUSUIMPRIMIR+","+"MU."+MENUSUAPROBAR+
                ","+"MU."+MENUSUANULAR+","+"MU."+MENUSUVERLOGS+","+"MU."+MENUSUGUARDAR+","+"MU."+MENUSUSINCRONIZAR+" FROM "+N_TABLA
                +" MU INNER JOIN Menu M ON M.Men_Codigo = MU.Men_Codigo"
                +" WHERE M.Men_Modulo='"+MMODULO+"';";
        return _SELECT_MENUMOD;
    }
    public static String _SELECTPRIVILEGIOS(Integer ID)
    {
        String _SELECT_PRIVILEGIO="";

        _SELECT_PRIVILEGIO = "SELECT "
                +"M.Men_Form,"+"M.Men_Descripcion,"
                +"MU."+MENUSUID+","+"MU."+USUID+","+"MU."+MENCODIGO+","
                +"MU."+MENUSUPERMITIDO+","+"MU."+MENUSUNUEVO+","+"MU."+MENUSUEDITAR+","+"MU."+MENUSUIMPRIMIR+","+"MU."+MENUSUAPROBAR+
                ","+"MU."+MENUSUANULAR+","+"MU."+MENUSUVERLOGS+","+"MU."+MENUSUGUARDAR+","+"MU."+MENUSUSINCRONIZAR+" FROM "+N_TABLA
                +" MU INNER JOIN Menu M ON M.Men_Codigo = MU.Men_Codigo"
                +" WHERE "+USUID+"='"+ID+"';";
        return _SELECT_PRIVILEGIO;
    }
}
