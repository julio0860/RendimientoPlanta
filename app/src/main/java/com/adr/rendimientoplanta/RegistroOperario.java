package com.adr.rendimientoplanta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Agrupador;
import com.adr.rendimientoplanta.DATA.T_MotivoParada;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class RegistroOperario extends AppCompatActivity {
    private TextView lblEmpresa;
    private TextView lblSucursal;
    private TextView lblProceso;
    private TextView lblSubproceso;
    private TextView lblLinea;
    private TextView lblLado;
    private TextView lblHoraSalida;
    private Funciones fnc;
    private EditText displayTime;
    private EditText edtHora;
    private EditText edtHoraSalida;
    private EditText edtFecha;
    private EditText edtDni;
    private EditText edtPersonal;
    private Spinner spnMotivos;
    private ImageButton imbHora;
    private ImageButton imbHoraSalida;
    private Button btnRegistrar;
    private Button btnEliminar;
    private SimpleCursorAdapter adspnMotivos;
    private int pHour;
    private int pMinute;

    static final int TIME_DIALOG_ID = 1;
    private static final String TAG = "RegistroOperario";


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
        lblHoraSalida = (TextView) findViewById(R.id.lblHoraSalida);
        edtHora = (EditText) findViewById(R.id.edtHora);
        edtHoraSalida = (EditText) findViewById(R.id.edtHoraSalida);
        edtFecha = (EditText) findViewById(R.id.lblFecha);
        edtDni = (EditText) findViewById(R.id.edtDni);
        edtPersonal = (EditText) findViewById(R.id.edtPersonal);
        spnMotivos = (Spinner) findViewById(R.id.spnMotivos);
        imbHora = (ImageButton) findViewById(R.id.imbHora);
        imbHoraSalida = (ImageButton) findViewById(R.id.imbHoraSalida);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);

        //ASIGNACIÓN DE PARAMETROS A LA ACTIVIDAD
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblProceso.setText(Variables.Pro_Descripcion);
        lblSubproceso.setText(Variables.Sub_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText("LADO: " + Variables.Lin_Lado);
        edtFecha.setText(Variables.FechaStr);
        edtHora.setText(Variables.HoraIngreso1);

        lblHoraSalida.setVisibility(View.INVISIBLE);
        edtHoraSalida.setVisibility(View.INVISIBLE);
        imbHoraSalida.setVisibility(View.INVISIBLE);
        btnEliminar.setVisibility(View.INVISIBLE);
        spnMotivos.setEnabled(false);


        if (Variables.Agru_Id > 0) {
            edtDni.setText(Variables.Per_Dni);
            edtPersonal.setText(Variables.Per_Nombres);
            edtHora.setText(Variables.HoraIngreso);
            btnEliminar.setVisibility(View.VISIBLE);
            spnMotivos.setEnabled(true);

        }
        edtDni.setOnKeyListener(new View.OnKeyListener() {
                                    @Override
                                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                                        //if (event.getAction() == KeyEvent.ACTION_UP ||event.getAction() == KeyEvent.KEYCODE_ENTER) {
                                        if (event.getAction() == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.KEYCODE_ENTER) {
                                            //do something here
                                            //if (keyCode==13)
                                            if (keyCode == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                                                Variables.Per_Dni = edtDni.getText().toString();
                                                Cursor Rse = LocBD.rawQuery("SELECT Per_Codigo,Per_ApePaterno || ' '|| Per_ApeMaterno||' '||Per_Nombres AS 'PERSONAL' FROM PERSONAL WHERE Per_Codigo='" + Variables.Per_Dni + "'", null);
                                                if (Rse.moveToFirst()) {
                                                    //Recorremos el cursor hasta que no haya más registros
                                                    do {
                                                        String codigo = Rse.getString(0);
                                                        String Personal = Rse.getString(1);
                                                        edtPersonal.setText(Personal);
                                                    } while (Rse.moveToNext());
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

        spnMotivos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v,
                                       int position, long id) {
                Cursor MotId = (Cursor) parent.getItemAtPosition(position);
                Variables.Mot_Id = MotId.getInt(MotId.getColumnIndex(BaseColumns._ID));
                switch (Variables.Mot_Id) {
                    case 7:
                        Variables.Agru_EstId = 1;
                        lblHoraSalida.setVisibility(View.INVISIBLE);
                        edtHoraSalida.setVisibility(View.INVISIBLE);
                        imbHoraSalida.setVisibility(View.INVISIBLE);
                        imbHoraSalida.setEnabled(false);
                        edtHoraSalida.setEnabled(false);
                        break;
                    case 8:
                        Variables.Agru_EstId = 2;
                        lblHoraSalida.setVisibility(View.VISIBLE);
                        edtHoraSalida.setVisibility(View.VISIBLE);
                        imbHoraSalida.setVisibility(View.VISIBLE);
                        imbHora.setEnabled(false);
                        edtHora.setEnabled(false);
                        imbHoraSalida.setEnabled(true);
                        edtHoraSalida.setEnabled(true);
                        break;
                    case 9:
                        Variables.Agru_EstId = 3;
                        lblHoraSalida.setVisibility(View.VISIBLE);
                        edtHoraSalida.setVisibility(View.VISIBLE);
                        imbHoraSalida.setVisibility(View.VISIBLE);
                        imbHora.setEnabled(false);
                        edtHora.setEnabled(false);
                        imbHoraSalida.setEnabled(false);
                        edtHoraSalida.setEnabled(false);
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        imbHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayTime = edtHora;
                showDialog(fnc.TIME_DIALOG_ID);
            }
        });

        imbHoraSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayTime = edtHoraSalida;
                showDialog(fnc.TIME_DIALOG_ID);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean EstDni = false;
                if (Variables.Per_Dni.equals("")) {
                    Mensaje("INGRESE NUMERO DE DNI");
                } else if (Variables.Per_Dni.length() < 8) {

                    Mensaje("EL NUMERO DE DNI DEBE TENER 8 CARACTERES");
                } else {

                    Boolean Estado = false;
                    Variables.HoraLectura = fnc.HoraSistema();
                    Variables.HoraIngreso = edtHora.getText().toString();
                    Variables.HoraSalida = edtHoraSalida.getText().toString();

                    if (Variables.Agru_Id > 0) {
                        try {
                            LocBD.execSQL(T_Agrupador._UPDATE(Variables.Agru_Id, Variables.Emp_Id, Variables.FechaStrBD, Variables.Suc_Id, Variables.Pro_Id, Variables.Sub_Id, Variables.Lin_Id, Variables.Lin_Lado, Variables.Per_Ubicacion, Variables.Per_Dni,
                                    Variables.HoraLectura, Variables.HoraIngreso, Variables.HoraSalida, Variables.Mot_Id, Variables.Agru_EstId,1));
                            Estado = true;
                            Mensaje("LOS DATOS HAN SIDO ACTUALIZADOS EXITOSAMENTE");
                            Intent ActividadRegresarLista = new Intent(RegistroOperario.this, RendArmado_Lista.class);
                            startActivity(ActividadRegresarLista);

                        } catch (Exception e) {
                            Estado = false;
                            Log.e(TAG, "Error Exception: " + e);
                            Toast.makeText(RegistroOperario.this, "ERROR AL ACTUALIZAR INFORMACION" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Cursor Rse = LocBD.rawQuery("SELECT DNI FROM AGRUPADOR WHERE Fecha='"+ Variables.FechaStrBD+"' AND Est_Id=1 AND DNI='"+ Variables.Per_Dni+"'", null);
                        if (Rse.moveToFirst()) {

                            do {
                                String codigo = Rse.getString(0);
                                if (Variables.Per_Dni.equals(codigo))
                                {
                                    EstDni = true;
                                }
                            } while (Rse.moveToNext());
                        }

                        if (EstDni == true) {
                            Mensaje("ESTE DNI YA SE ENCUENTRA REGISTRADO");
                        }

                        else{
                            try {
                                LocBD.execSQL(T_Agrupador._INSERT(Variables.Emp_Id, Variables.FechaStrBD, Variables.Suc_Id, Variables.Pro_Id, Variables.Sub_Id, Variables.Lin_Id, Variables.Lin_Lado, Variables.Per_Ubicacion, Variables.Per_Dni,
                                        Variables.HoraLectura, Variables.HoraIngreso, Variables.Mot_Id, Variables.Agru_EstId,1));
                                Estado = true;
                                Mensaje("LOS DATOS HAN SIDO REGISTRADOS EXITOSAMENTE");
                                Intent ActividadRegresarLista = new Intent(RegistroOperario.this, RendArmado_Lista.class);
                                startActivity(ActividadRegresarLista);

                            } catch (Exception e) {
                                Estado = false;
                                Log.e(TAG, "Error Exception: " + e);
                                Toast.makeText(RegistroOperario.this, "ERROR AL REGISTRAR INFORMACION" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (Estado == false) {
                                Mensaje("ERRROR:REVISE BIEN LOS DATOS");

                            }
                        }

                    }
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Variables.Agru_Id > 0) {
                    AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(RegistroOperario.this);
                    alertDialog1.setTitle("AVISO");
                    alertDialog1.setMessage("DESESAS ELIMINAR EL REGISTRO:");
                    alertDialog1.setPositiveButton(
                            "Si",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Variables.Agru_EstId = 0;
                                    Variables.HoraLectura = fnc.HoraSistema();
                                    try {
                                        LocBD.execSQL(T_Agrupador._UPDATE(Variables.Agru_Id, Variables.Emp_Id, Variables.FechaStrBD, Variables.Suc_Id, Variables.Pro_Id, Variables.Sub_Id, Variables.Lin_Id, Variables.Lin_Lado, Variables.Per_Ubicacion, Variables.Per_Dni,
                                                Variables.HoraLectura, Variables.HoraIngreso, Variables.HoraSalida, Variables.Mot_Id, Variables.Agru_EstId,1));

                                        Mensaje("EL REGISTRO HA SIDO ELIMINADO CORRECTAMENTE");
                                        Intent ActividadRegresarLista = new Intent(RegistroOperario.this, RendArmado_Lista.class);
                                        startActivity(ActividadRegresarLista);

                                    } catch (Exception e) {

                                        Log.e(TAG, "Error Exception: " + e);
                                        Toast.makeText(RegistroOperario.this, "ERROR AL ELIMINAR REGISTRO" + e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                    alertDialog1.setNegativeButton(
                            "No",
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

    private void Mensaje(String mensaje)
    {
        AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(RegistroOperario.this);
        alertDialog1.setTitle("AVISO");
        alertDialog1.setMessage(mensaje);
        alertDialog1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        alertDialog1.create();
        alertDialog1.show();
    }


}