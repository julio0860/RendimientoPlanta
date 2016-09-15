package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/06/13.
 */
public class T_Tareo {
    public static final String TARID ="Tar_Id";
    public static final String EMPID = "Emp_Id";
    public static final String ESTID = "Est_Id";

    public static final String TARORIID = "TarOri_Id";
    public static final String TARTIPID = "TarTip_Id";
    public static final String TARSUBTIPID = "TarSubTip_Id";
    public static final String SUCID = "Suc_Id";
    public static final String CONID = "Con_Id";
    public static final String ACTID = "Act_Id";
    public static final String LABID = "Lab_Id";
    public static final String LINPROID = "LinPro_Id";
    public static final String RESID = "Res_Id";
    public static final String TARCANTIDADINI = "Tar_CantidadIni";
    public static final String TARCANTIDADFIN = "Tar_CantidadFin";
    public static final String TARHORAINI = "Tar_HoraIni";
    public static final String TARHORAFIN = "Tar_HoraFin";
    public static final String TARFECHA = "Tar_Fecha";
    public static final String TARLADO = "Tar_Lado";

    public static final String TARIDPDA = "Tar_Id_PDA";


    public static final String N_TABLA = "Tareo";

    public static final String CREATE_T_TAREO ="CREATE TABLE " + N_TABLA+"("+
            TARID +" INTEGER PRIMARY KEY NOT NULL, "+
            EMPID +" INTEGER NOT NULL, "+
            ESTID+" INTEGER NOT NULL, "+
            TARORIID+" INTEGER, "+
            TARTIPID+" INTEGER, "+
            TARSUBTIPID+" INTEGER, "+
            SUCID+" INTEGER, "+
            CONID+" INTEGER, "+
            ACTID+" INTEGER, "+
            LABID+" INTEGER, "+
            LINPROID+" INTEGER, "+
            RESID+" INTEGER NOT NULL, "+
            TARCANTIDADINI+" INTEGER NOT NULL, "+
            TARCANTIDADFIN+" INTEGER NOT NULL, "+
            TARHORAINI+" TEXT NOT NULL, "+
            TARHORAFIN+" TEXT NOT NULL, "+
            TARFECHA+" TEXT NOT NULL, "+
            TARLADO+" TEXT NOT NULL "+
            ");";

    public static final String DROP_T_TAREO ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT_SYNC(int ID,int EMID,int EST,int ORIID,int TIPID,int SUID,int CID,int AID,int LAID
            ,int LIID,int RID,int CANTIDADINI,int CANTIDADFIN,String FECHA,String TLADO)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+TARIDPDA+","+EMPID+","+ESTID+","+TARORIID+","+TARTIPID+","+TARSUBTIPID+
                ","+SUID+","+CONID+","+ACTID+","+LABID+","+LINPROID+","+RESID+","+TARCANTIDADINI+","+TARCANTIDADFIN+","+TARFECHA+
                ","+TARLADO+
                ")VALUES('"+
                ID+"','"+EMID+"','"+EST+"','"+ORIID+"','"+TIPID+"','"+SUID+"','"+CID+"','"+AID+"','"+LAID+"','"
                +LIID+"','"+CANTIDADINI+"','"+CANTIDADFIN+"','"+FECHA+"','"+TLADO+"');";
        return _INSERT;
    }
    public static String _INSERT(int EMID,int EST,int ORIID,int TIPID,int SUBTIPID,int SUID,int CID,int AID,int LAID
            ,int LIID,int RID,int CANTIDADINI,int CANTIDADFIN,String HORAINI,String HORAFIN,String FECHA,String TLADO)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+EMPID+","+ESTID+","+TARORIID+","+TARTIPID+","+TARSUBTIPID+
                ","+SUCID+","+CONID+","+ACTID+","+LABID+","+LINPROID+","+RESID+","+TARCANTIDADINI+","+
                TARCANTIDADFIN+","+TARHORAINI+","+TARHORAFIN+","+TARFECHA+","+TARLADO+
                ")VALUES('"+
                EMID+"','"+EST+"','"+ORIID+"','"+TIPID+"','"+SUBTIPID+"','"+SUID+"','"+CID+"','"+AID+"','"+LAID+"','"
                +LIID+"','"+RID+"','"+CANTIDADINI+"','"+CANTIDADFIN+"','"+HORAINI+"','"+HORAFIN+"','"+FECHA+"','"+TLADO+"');";
        return _INSERT;
    }
    public static String _UPDATE(int ID,int EMID,int EST,int ORIID,int TIPID,int SUBTIPID,int SUID,int CID,int AID,int LAID
            ,int LIID,int RID,int CANTIDADINI,int CANTIDADFIN,String HORAINI,String HORAFIN,String FECHA,String TLADO)
    {
        String _UPDATE;
        _UPDATE = "UPDATE "+N_TABLA +" SET "+EMPID+"= '"+EMID+"',"+ESTID+"='"+EST+"',"
                +TARORIID+"='"+ORIID+"',"+TARTIPID+"='"+TIPID+"',"+TARSUBTIPID+"='"+SUBTIPID+"',"
                +SUCID+"='"+SUID+"',"+CONID+"='"+CID+"',"+ACTID+"='"+AID+"',"+LABID+"='"+LAID+"',"+LINPROID+"='"+LIID+"',"
                +RESID+"='"+RID+"',"+TARCANTIDADINI+"='"+CANTIDADINI+"',"+TARCANTIDADFIN+"='"+CANTIDADFIN+"',"
                +TARHORAINI+"='"+HORAINI+"',"+TARHORAFIN+"='"+HORAFIN+"',"
                +ESTID+"='"+EST+"',"+TARFECHA+"='"+FECHA+"',"+TARLADO+"='"+TLADO+
                "' WHERE "+TARID+"='"+ID+"';";
        return _UPDATE;
    }
    public static String _DELETE(int ID)
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +" WHERE "+TARID+"='"+ID+"';";
        return _DELETE;
    }
    public static String _SELECTMAX()
    {
        String _SELECT;
        _SELECT = "SELECT MAX("+TARID+")"
                +" FROM "+N_TABLA;
        return _SELECT;
    }
    public static String _SELECT_TAREOID(int TID)
    {
        String _SELECT;
        _SELECT = "SELECT "+TARID+" AS '_id',"+EMPID+","+ESTID+","+TARORIID+","+TARTIPID+","+TARSUBTIPID+
                ","+SUCID+","+CONID+","+ACTID+","+LABID+","+LINPROID+","+RESID+","+TARCANTIDADINI+","+TARCANTIDADFIN+
                ","+TARHORAINI+","+TARHORAFIN+","+TARFECHA+","+TARLADO
                +" FROM "+N_TABLA;
        if (TID!=-1)
        {
            _SELECT=_SELECT+" WHERE ";
            _SELECT = _SELECT+TARID+ "='"+TID+"'";
        }
        return _SELECT;
    }
    public static String _SELECT_TAREO(int EST)
    {
        String _SELECT;
        _SELECT = "SELECT "+TARID+" AS '_id',"+EMPID+","+ESTID+","+TARORIID+","+TARTIPID+","+TARSUBTIPID+
                ","+SUCID+","+CONID+","+ACTID+","+LABID+","+LINPROID+","+RESID+","+TARCANTIDADINI+","+TARCANTIDADFIN+
                ","+TARHORAINI+","+TARHORAFIN+","+TARFECHA+","+TARLADO
                +" FROM "+N_TABLA;
        if (EST!=-1)
        {
            _SELECT=_SELECT+" WHERE ";
            _SELECT = _SELECT+ESTID+ "='"+EST+"'";
        }
        return _SELECT;
    }
    public static String _SELECT_TAREOLISTA(int EST)
    {
        String _SELECT;
        _SELECT = "SELECT T."+TARID+" AS '_id',T."+EMPID+",T."+ESTID+",T."+TARORIID+",T."+TARTIPID+",T."+TARSUBTIPID+
                ",T."+SUCID+",T."+CONID+",T."+ACTID+",T."+LABID+",T."+LINPROID+",T."+RESID+",T."+TARCANTIDADINI+
                ",T."+TARCANTIDADFIN+",T."+TARHORAINI+",T."+TARHORAFIN+",T."+TARFECHA+",T."+TARLADO+
                ", C.Con_Descripcion ||' | '|| A.Act_Descripcion AS ENCABEZADO," +
                "L.Lab_Descripcion ||'  | LECTURAS: '|| T."+ T_Tareo.TARCANTIDADINI+"||' | '|| T."+T_Tareo.TARFECHA+" AS DETALLE"
                +" FROM "+N_TABLA+" T"
        +" INNER JOIN "+T_Consumidor.N_TABLA+" C ON T.Con_Id=C.Con_Id "
        +" INNER JOIN "+T_Actividad.N_TABLA+" A ON T.Act_Id=A.Act_Id"
        +" INNER JOIN "+T_Labor.N_TABLA+" L ON T.Lab_Id=L.Lab_Id" ;
        if (EST!=-1)
        {
            _SELECT=_SELECT+" WHERE ";
            _SELECT = _SELECT+"T."+ESTID+ "='"+EST+"'";
        }
        return _SELECT;
    }
    public static String _SELECT_TAREOSYNC(int EST)
    {
        String _SELECT;
        _SELECT = "SELECT T."+TARID+" AS '_id',T."+EMPID+",T."+ESTID+",T."+TARORIID+",T."+TARTIPID+",T."+TARSUBTIPID+
                ",T."+SUCID+",T."+CONID+",T."+ACTID+",T."+LABID+",T."+LINPROID+",T."+RESID+",T."+TARCANTIDADINI+",T."+TARCANTIDADFIN+
                ",T."+TARHORAINI+",T."+TARHORAFIN+",T."+TARFECHA+",T."+TARLADO+
                ",TD."+T_TareoDetalle.TARDETID+",TD."+T_TareoDetalle.PERID+
                ",TD."+T_TareoDetalle.TARDETFECHAHORINI+",TD."+T_TareoDetalle.TARDETFECHAHORFIN+",TD."+T_TareoDetalle.PERFOTOCHECK
                +" FROM "+N_TABLA+" T INNER JOIN "+T_TareoDetalle.N_TABLA+" TD ON T."+TARID+"=TD."+TARID;
        if (EST!=-1)
        {
            _SELECT=_SELECT+" WHERE ";
            _SELECT = _SELECT+ESTID+ "='"+EST+"'";
        }
        return _SELECT;
    }

}
