package com.adr.rendimientoplanta;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Cultivo;
import com.adr.rendimientoplanta.DATA.T_Empresa;
import com.adr.rendimientoplanta.DATA.T_Sucursal;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class IngresoJabas_Parametros extends AppCompatActivity {

    static final int DATE_ID=0;
    private int mMes,mAño,mDia,sDia,sMes,sAño;

    private EditText txtFecha;
    private EditText edtNumRegistros;



    private Button btnEstablecer;

    private Spinner spnSucursal;
    private Spinner spnCultivo;
    private Spinner spnEmpresa;

    private ImageButton imbFecha;

    private Funciones fnc;

    LocalBD LBD;
    SQLiteDatabase LocBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_parametros);

        Calendar Cal = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        txtFecha = (EditText) findViewById(R.id.edtFecha);
        edtNumRegistros = (EditText) findViewById(R.id.edtNumRegistros);

        txtFecha.setText(df.format(Cal.getInstance().getTime()).toString());
        txtFecha.setEnabled(false);

        LBD = new LocalBD(IngresoJabas_Parametros.this) ;
        LocBD = LBD.getWritableDatabase();
        fnc = new Funciones();

        btnEstablecer = (Button) findViewById(R.id.btnEstablecer);
        spnSucursal = (Spinner) findViewById(R.id.spnSucursal);
        spnCultivo = (Spinner) findViewById(R.id.spnCultivo);
        spnEmpresa = (Spinner) findViewById(R.id.spnEmpresa);

        imbFecha = (ImageButton) findViewById(R.id.imbFecha);

        Cursor Empresa = LocBD.rawQuery(T_Empresa._SELECT_EMP(-1),null);
        spnEmpresa.setAdapter(fnc.AdaptadorSpinnerSimpleLarge(this,Empresa,T_Empresa.EMPDESCRIPCION));

        Cursor Sucursal = LocBD.rawQuery(T_Sucursal._SELECT_SUC(1,2),null);
        spnSucursal.setAdapter(fnc.AdaptadorSpinnerSimpleLarge(this,Sucursal,T_Sucursal.SUCDESCRIPCION));

        Cursor Cultivo = LocBD.rawQuery(T_Cultivo._SELECT_CULT(-1,2),null);
        spnCultivo.setAdapter(fnc.AdaptadorSpinnerSimpleLarge(this,Cultivo,T_Cultivo.CULDESCRIPCION));

        if (Variables.FechaStr.length()>0)
        {
            txtFecha.setText(Variables.FechaStr);
        }
        if (Variables.Cul_Id!=0)
        {
            fnc.setIndexInt(spnCultivo, BaseColumns._ID, Variables.Cul_Id);
        }
        if (Variables.Suc_Id!=0)
        {
            fnc.setIndexInt(spnSucursal,BaseColumns._ID, Variables.Suc_Id);
        }
        if (Variables.Emp_Id!=0)
        {
            fnc.setIndexInt(spnEmpresa,BaseColumns._ID, Variables.Emp_Id);
        }
        if(Variables.LinReg_RegistrosMinimos!=0)
        {
            edtNumRegistros.setText(String.valueOf(Variables.LinReg_RegistrosMinimos));
        }

        btnEstablecer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
            //Se crea un Intent para llamar a una nueva Actividad(Pantalla)

            //Pasar información de un activity a otra
                //Variables.Suc_Id = spnSucursal.getSelectedItem().getClass()
                Cursor CurEmpresa = (Cursor) spnEmpresa.getAdapter().getItem(spnEmpresa.getSelectedItemPosition());
                Variables.Emp_Id= CurEmpresa.getInt(CurEmpresa.getColumnIndex(BaseColumns._ID));
                Variables.Emp_Descripcion= CurEmpresa.getString(CurEmpresa.getColumnIndex(T_Empresa.EMPDESCRIPCION));
                Variables.Emp_Abrev= CurEmpresa.getString(CurEmpresa.getColumnIndex(T_Empresa.EMPABREV));

                Cursor CurSucursal = (Cursor) spnSucursal.getAdapter().getItem(spnSucursal.getSelectedItemPosition());
                Variables.Suc_Id = CurSucursal.getInt(CurSucursal.getColumnIndex(BaseColumns._ID));
                Variables.Suc_Descripcion= CurSucursal.getString(CurSucursal.getColumnIndex(T_Sucursal.SUCDESCRIPCION));

                Cursor CurCultivo = (Cursor) spnCultivo.getAdapter().getItem(spnCultivo.getSelectedItemPosition());
                Variables.Cul_Id= CurCultivo.getInt(CurCultivo.getColumnIndex(BaseColumns._ID));
                Variables.Cul_Descripcion= CurCultivo.getString(CurCultivo.getColumnIndex(T_Cultivo.CULDESCRIPCION));

                Variables.FechaStr = txtFecha.getText().toString();
                if (edtNumRegistros.length()>0)
                {
                    Variables.LinReg_RegistrosMinimos= Integer.parseInt(edtNumRegistros.getText().toString());
                }

            Intent ActividadNueva = new Intent(IngresoJabas_Parametros.this, IngresoJabas_Grilla.class);
            startActivity(ActividadNueva);
            }
        }
        );


    }
    private void ColocarFecha(EditText edtFecha)
    {
        //ASIGNACION DE FECHA
        edtFecha.setText(new StringBuilder().append(fnc.pad(mDia))
                .append("/").append(fnc.pad(mMes+1)).append("/").append(mAño));
    }
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_ID:
                return new DatePickerDialog(this,mDateSetListener,sAño,sMes,sDia);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            mAño=year;
            mMes=monthOfYear;
            mDia=dayOfMonth;
            ColocarFecha(txtFecha);
        }
    };

}
