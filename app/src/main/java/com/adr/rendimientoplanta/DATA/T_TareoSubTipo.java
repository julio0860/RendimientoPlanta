package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/05/19.
 */
public class T_TareoSubTipo {
    public static final String TARSUBTIPID ="TarSubTip_Id";
    public static  final String EMPID ="Emp_Id";
    public static final String ESTID = "Est_Id";
    public static final String TARTIPID = "TarTip_Id";
    public static final String TARSUBTIPCODIGO = "TarSubTip_Codigo";
    public static final String TARSUBTIPDESCRIPCION = "TarSubTip_Descripcion";



    public static final String N_TABLA = "TareoSubTipo";

    public static final String CREATE_T_TAREOSUBTIPO ="CREATE TABLE " + N_TABLA+"("+
            TARSUBTIPID +" INTEGER PRIMARY KEY NOT NULL, "+
            EMPID +" INTEGER NOT NULL, "+
            ESTID +" INTEGER NOT NULL, "+
            TARTIPID +" INTEGER NOT NULL, "+
            TARSUBTIPCODIGO +" TEXT NOT NULL, "+
            TARSUBTIPDESCRIPCION +" TEXT NOT NULL "+
            ");";

    public static final String DROP_T_TAREOSUBTIPO ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT(int ID,int EMID,int ESID,int TIPID,String CODIGO,
                                 String DESCRIPCION)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+TARSUBTIPID+","+EMPID+","+ESTID+","+TARTIPID+
                ","+TARSUBTIPCODIGO+","+TARSUBTIPDESCRIPCION+
                ")VALUES('"+
                ID+"','"+EMID+"','"+ESID+"','"+TIPID+"','"+CODIGO+"','"+DESCRIPCION+"');";
        return _INSERT;
    }
    public static String _DELETE()
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +";";
        return _DELETE;
    }
    public static String _SELECT_TAREOSUBTIPO(int ID,int EMID)
    {
        String _SELECT;
        _SELECT = "SELECT "+TARSUBTIPID+" AS '_id',"+EMPID+","+ESTID+","+TARTIPID+
                ","+TARSUBTIPCODIGO+","+TARSUBTIPDESCRIPCION
                +" FROM "+N_TABLA;

        if (ID!=-1||EMID!=-1)
        {
            _SELECT=_SELECT+" WHERE ";
        }
        if (ID!=-1)
        {
            _SELECT=_SELECT+TARTIPID+"='"+ID+"'";
        }
        if (EMID!=-1&&ID!=-1)
        {
            _SELECT=_SELECT+" AND ";
        }
        if (EMID!=-1)
        {
            _SELECT = _SELECT+ EMPID +"='"+EMID+"';";
        }
        return _SELECT;
    }
}
