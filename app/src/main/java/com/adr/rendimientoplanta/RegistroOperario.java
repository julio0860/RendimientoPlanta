package com.adr.rendimientoplanta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_MotivoParada;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.text.SimpleDateFormat;

public class RegistroOperario extends AppCompatActivity {
    private TextView lblEmpresa;
    private TextView lblSucursal;
    private TextView lblProceso;
    private TextView lblSubproceso;
    private TextView lblLinea;
    private TextView lblLado;
    private Funciones fnc;
    private EditText displayTime;
    private EditText edtHora;
    private EditText edtFecha;
    private EditText edtDni;
    private EditText edtPersonal;
    private Spinner spnMotivos;
    private ImageButton imbHora;
    private Button btnRegistrar;
    private SimpleCursorAdapter adspnMotivos;
    private int pHour;
    private int pMinute;
    static final int TIME_DIALOG_ID = 1;
    String Dni;
    SimpleDateFormat Formato = new SimpleDateFormat("HH:mm:ss",java.util.Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_operario);
        LocalBD LBD = new LocalBD(RegistroOperario.this);

        final SQLiteDatabase LocBD = LBD.getWritableDatabase();
        fnc = new Funciones();

        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblProceso = (TextView) findViewById(R.id.lblProceso);
        lblSubproceso = (TextView) findViewById(R.id.lblSubproceso);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblLado = (TextView) findViewById(R.id.lblLado);
        edtHora = (EditText) findViewById(R.id.edtHora);
        edtFecha=(EditText) findViewById(R.id.edtFecha);
        edtDni=(EditText)findViewById(R.id.edtDni);
        edtPersonal=(EditText)findViewById(R.id.edtPersonal);
        spnMotivos=(Spinner)findViewById(R.id.spnMotivos) ;
        imbHora= (ImageButton) findViewById(R.id.imbHora);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrar) ;

        //ASIGNACIÓN DE PARAMETROS A LA ACTIVIDAD
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblProceso.setText(Variables.Pro_Descripcion);
        lblSubproceso.setText(Variables.Sub_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText("LADO: "+Variables.Lin_Lado);
        edtFecha.setText(Variables.FechaStr);



        edtDni.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //if (event.getAction() == KeyEvent.ACTION_UP ||event.getAction() == KeyEvent.KEYCODE_ENTER) {
                if (event.getAction() == KeyEvent.ACTION_DOWN ||keyCode==KeyEvent.KEYCODE_ENTER) {
                    //do something here
                    //if (keyCode==13)
                    if (keyCode==KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER|| event.getAction() == KeyEvent.KEYCODE_ENTER)
                    {
                        Dni=edtDni.getText().toString();
                        Cursor Rse=LocBD.rawQuery("SELECT Per_Codigo,Per_ApePaterno || ' '|| Per_ApeMaterno||' '||Per_Nombres AS 'PERSONAL' FROM PERSONAL WHERE Per_Codigo='"+Dni+"'",null);
                        if (Rse.moveToFirst()) {
                            //Recorremos el cursor hasta que no haya más registros
                            do {
                                String codigo= Rse.getString(0);
                                String Personal = Rse.getString(1);
                                edtPersonal.setText(Personal);
                            } while(Rse.moveToNext());
                        }
                    }
                }
                return false;
            }
        }
        );

       Cursor Motivos = LocBD.rawQuery("SELECT Mot_Id As '_id',Mot_Descripcion FROM MOTIVOS WHERE MOT_EsRendimiento=1", null);
       adspnMotivos = new SimpleCursorAdapter(RegistroOperario.this, android.R.layout.simple_dropdown_item_1line,
               Motivos, new String[]{T_MotivoParada.MotDescripcion}, new int[]{android.R.id.text1},
               SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
       spnMotivos.setAdapter(adspnMotivos);

        imbHora.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){

                displayTime = edtHora;
                showDialog(fnc.TIME_DIALOG_ID);
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
              if (Dni.equals("")){
                  AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(RegistroOperario.this);
                  alertDialog1.setTitle("AVISO");
                  alertDialog1.setMessage("INGRESE NUMERO DE DNI");
                  alertDialog1.setPositiveButton(
                          "Yes",
                          new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                  dialog.cancel();
                              }
                          });
                  alertDialog1.create();
                  alertDialog1.show();
                }
            }
        });
}
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;
                    fnc.EstablecerHoraEdt(displayTime,pHour,pMinute);
                    //EstablecerHoraEdt(displayTime);
                    //displayToast();
                }
            };

    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,mTimeSetListener, pHour, pMinute,false);
        }
        return null;
    }
}