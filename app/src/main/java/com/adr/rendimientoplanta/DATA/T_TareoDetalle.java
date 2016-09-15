package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/06/15.
 */
public class T_TareoDetalle {
    public static final String TARDETID ="TarDet_Id";
    public static final String TARID ="Tar_Id";
    public static final String PERID="Per_Id";
    public static final String TARDETFECHAHORINI="TarDet_FechaHorIni";
    public static final String TARDETFECHAHORFIN="TarDet_FechaHorFin";
    public static final String PERFOTOCHECK="Per_Fotocheck";


    public static final String N_TABLA = "TareoDetalle";

    public static final String CREATE_T_TAREODETALLE ="CREATE TABLE " + N_TABLA+"("+
            TARDETID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            TARID +" INTEGER NOT NULL, "+
            PERID +" INTEGER, "+
            TARDETFECHAHORINI +" TEXT NOT NULL, "+
            TARDETFECHAHORFIN +" TEXT NOT NULL, "+
            PERFOTOCHECK +" TEXT NOT NULL "+
            ");";

    public static final String DROP_T_TAREODETALLE ="DROP TABLE IF EXISTS "+N_TABLA;

    public static String _INSERT_SYNC(int DETID,int ID,int PID,String FECHAHORINI,
                                      String FECHAHORAFIN,String FOTOCHECK)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+TARDETID+","+TARID+","+PERID+","+TARDETFECHAHORINI+
                ","+TARDETFECHAHORFIN+","+PERFOTOCHECK+
                ")VALUES('"
                +DETID+"','"+ID+"','"+PID+"','"+FECHAHORINI+"','"+FECHAHORAFIN+"','"+FOTOCHECK+"');";
        return _INSERT;
    }
    public static String _INSERT(int ID,int PID,String FECHAHORINI,
                                 String FECHAHORAFIN,String FOTOCHECK)
    {
        String _INSERT;
        _INSERT = "INSERT INTO "+N_TABLA +"("+TARID+","+PERID+","+TARDETFECHAHORINI+
                ","+TARDETFECHAHORFIN+","+PERFOTOCHECK+
                ")VALUES('"
                +ID+"','"+PID+"','"+FECHAHORINI+"','"+FECHAHORAFIN+"','"+FOTOCHECK+"');";
        return _INSERT;
    }

    public static String _DELETE(int ID)
    {
        String _DELETE;
        _DELETE = "DELETE FROM "+N_TABLA +" WHERE "+TARID+"='"+ID+"';";
        return _DELETE;
    }
    public static String _SELECT_TAREODET(int ID)
    {
        String _SELECT;
        _SELECT = "SELECT "+TARDETID+" AS '_id',"+TARID+","+PERID+","+TARDETFECHAHORINI+","
                +TARDETFECHAHORFIN+","+PERFOTOCHECK+
                " FROM "+N_TABLA;
        if (ID!=-1)
        {
            _SELECT=_SELECT+" WHERE ";
            _SELECT = _SELECT+TARID+ "='"+ID+"'";
        }
        return _SELECT;
    }
    public static String _SELECT_TAREODETLIST(int ID)
    {
        String _SELECT;
        _SELECT = "SELECT TD."+TARDETID+" AS '_id',TD."+TARID+",TD."+PERID+",TD."+TARDETFECHAHORINI+",TD."
                +TARDETFECHAHORFIN+",TD."+PERFOTOCHECK+", IFNULL(P.Per_ApePaterno,'NO REGISTRADO')" +
                "||' '||IFNULL(P.Per_ApeMaterno,'')||' '||IFNULL(P.Per_Nombres,'') AS NOMBRES "+
                " FROM "+N_TABLA
        +" TD LEFT JOIN PERSONAL P ON TD."+PERFOTOCHECK+"=P."+PERFOTOCHECK;
        if (ID!=-1)
        {
            _SELECT=_SELECT+" WHERE ";
            _SELECT = _SELECT+"TD."+TARID+ "='"+ID+"'";
        }
        return _SELECT;
    }
}
