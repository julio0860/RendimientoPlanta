package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/11.
 */
public class T_Menu {

    public static final String N_TABLA = "Menu";


    public static final String MENCODIGO="Men_Codigo";
    public static final String MENDESCRIPCION="Men_Descripcion";
    public static final String MENFORM="Men_Form";
    public static final String MENANTERIOR="Men_Anterior";
    public static final String MENMODULO="Men_Modulo";
    public static final String ESTID="Est_Id";


    public static final String CREATE_T_MENU ="CREATE TABLE " + N_TABLA+"("+
            MENCODIGO +" TEXT PRIMARY KEY NOT NULL, "+
            MENDESCRIPCION +" TEXT NOT NULL, "+
            MENFORM+" TEXT NOT NULL, "+
            MENANTERIOR+" TEXT NOT NULL, "+
            MENMODULO+" TEXT NOT NULL, "+
            ESTID+" INTEGER NOT NULL);";

    public static final String DROP_T_MENU ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(String CODIGO,String DESCRIPCION,String FORM,String ANTERIOR,String MODULO,
                                 Integer EST)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+MENCODIGO+","+MENDESCRIPCION+","+MENFORM+","
                +MENANTERIOR+","+MENMODULO+","+ESTID+
                ")VALUES('"+
                CODIGO+"','"+DESCRIPCION+"','"+FORM+"','"+ANTERIOR+"','"+MODULO+"','"+EST+"');";

        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }

    public static String _SELECT_SYNC(String MODULO,Integer EST)
    {
        String _SELECT_ID="";

        _SELECT_ID = "SELECT "+MENCODIGO+","+MENDESCRIPCION+","+MENFORM+","+MENANTERIOR+","
                +MENMODULO+","+ESTID+" FROM "+N_TABLA;
        if (MODULO.length()>0||EST!=-1)
        {
            _SELECT_ID=_SELECT_ID+" WHERE ";
        }
        if (MODULO.length()>0)
        {
            _SELECT_ID = _SELECT_ID+MENMODULO+"='"+MODULO+"'";
        }
        if (EST!=-1&&MODULO.length()>0)
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
