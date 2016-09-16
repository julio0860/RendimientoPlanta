package com.adr.rendimientoplanta.LIBRERIA;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
//HOLA MUNDO CRUEL 2.1
/**
 * Created by smachado on 2016/05/11.
 */
public class Funciones extends AppCompatActivity {
    private static String CadenaEncriptada = "|°!#$%&/()=?¡'¿<}~ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑª¿®¦ÁÂÀ©¦ãÃ.j+";
    private static String CadenaDesencriptada= "ABCDEFGHIJKLLLMNOPQRSTUVWXYZabcdefrgihklllmnopqrstuvwxyz1234567890 .j+";

    public final int DATE_ID=0;
    public final int TIME_DIALOG_ID = 1;
    private int mMes,mAño,mDia,sDia,sMes,sAño;
    //PRUEBA DIALOG FECHA
    private int pHour;
    private int pMinute;

    public static EditText edtFecha;
    public static EditText displayTime;

    public static String Encriptar(String texto)
    {
        String TextEncriptado ="";
        for (int i=1;i<=texto.trim().length();i++)
        {
            TextEncriptado = TextEncriptado+ CadenaEncriptada.substring(CadenaDesencriptada.lastIndexOf(texto.substring(i-1,i)),CadenaDesencriptada.lastIndexOf(texto.substring(i-1,i))+1);
        }
        return TextEncriptado;
    }

    public final String GetMAC()
    {
        String MAC;
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        MAC = info.getMacAddress();
        return MAC;
    }
    public final String GetIMEI()
    {
        String IMEI;
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        IMEI=telephonyManager.getDeviceId();
        return IMEI;
    }

    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public static void setIndexStr(Spinner spinner, String columnName, String searchString) {

        //Log.d(LOG_TAG, "getIndex(" + searchString + ")");

        if (searchString == null || spinner.getCount() == 0) {
            spinner.setSelection(-1); // Not found
        } else {

            Cursor cursor = (Cursor) spinner.getItemAtPosition(0);

            for (int i = 0; i < spinner.getCount(); i++) {

                cursor.moveToPosition(i);
                String itemText = cursor.getString(cursor.getColumnIndex(columnName));

                if (itemText.equals(searchString)) {
                    spinner.setSelection(i);
                    break;
                }
            }
            //spinner.setSelection(-1); // Not found
        }
    }
    public static void setIndexInt(Spinner spinner, String columnName, int searchInteger) {
        //Log.d(LOG_TAG, "getIndex(" + searchString + ")");

        if (searchInteger == 0 || spinner.getCount() == 0) {
            spinner.setSelection(-1); // Not found
        }
        else {

            Cursor cursor = (Cursor)spinner.getItemAtPosition(0);

            for (int i = 0; i < spinner.getCount(); i++) {

                cursor.moveToPosition(i);
                int itemInt = cursor.getInt(cursor.getColumnIndex(columnName));

                if (itemInt==searchInteger) {
                    spinner.setSelection(i);
                    break;
                }
            }
            //spinner.setSelection(-1); // Not found
        }
    }
    private void ColocarFecha(EditText edtFecha)
    {
        //ASIGNACION DE FECHA
        edtFecha.setText(new StringBuilder().append(pad(mDia))
                .append("/").append(pad(mMes+1)).append("/").append(mAño));
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            mAño=year;
            mMes=monthOfYear;
            mDia=dayOfMonth;
            ColocarFecha(edtFecha);
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;
                    EstablecerHoraEdt(displayTime,1,1);
                    //displayToast();
                }
            };
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_ID:
                return new DatePickerDialog(this,mDateSetListener,sAño,sMes,sDia);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,mTimeSetListener, pHour, pMinute,false);
        }
        return null;
    }
    public void EstablecerHoraEdt(EditText edtHora,int Hor,int Min) {
        edtHora.setText(
                new StringBuilder()
                        .append(pad(Hor)).append(":")
                        .append(pad(Min)).append(":")
                        .append(pad(0)));
    }

    //Función que retorna valor de un SimpleCursosAdapter asociado a layout de un solo campo a mostrar
    public SimpleCursorAdapter AdaptadorSpinnerSimpleLarge(Context contexto, Cursor data, String campo)
    {
        SimpleCursorAdapter Adaptador = new SimpleCursorAdapter(contexto,android.R.layout.simple_dropdown_item_1line,
                data,new String[]{campo},new int[]{android.R.id.text1},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        return Adaptador;
    }
    public void ActualizarHoraMin(int Hora,int Min)
    {
        final Calendar C = Calendar.getInstance();
        Hora = C.get(Calendar.HOUR_OF_DAY);
        Min = C.get(Calendar.MINUTE);
    }

}
