package com.example.control;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import com.example.control.model.ReportCard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Tabel extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_text,ed_clock,ed_data;
    private Button btn_save,btn_cancellation;
    private ImageButton imageButton;
    private TextView titleCard;

    private int mYear, mMonth, mDay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabel);

        titleCard = (TextView) findViewById(R.id.title_card);
        ed_data = (EditText) findViewById(R.id.ed_data);
        ed_clock = (EditText) findViewById(R.id.ed_clock);
        ed_text = (EditText) findViewById(R.id.ed_text);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancellation = (Button) findViewById(R.id.btn_cancellation);
        imageButton = (ImageButton) findViewById(R.id.image_calend);
        imageButton.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_cancellation.setOnClickListener(this);

        titleCard.setText("Довьте запись: " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (ed_data.getText().toString() == "")
                    if (ed_clock.getText().toString() == "")
                        if (ed_text.getText().toString() == "")
                            return;
                try {
                    Log.d("TEST 06.12.2019 = ", new SimpleDateFormat("dd.MM.yyyy").parse(ed_data.getText().toString()).getTime() + "");
                    FirebaseDatabase.getInstance().getReference("report").child("users").push().setValue(
                            new ReportCard(
                                    new SimpleDateFormat("dd.MM.yyyy").parse(ed_data.getText().toString()).getTime(), //TODO CONVERT DATE STRING TO DATE LONG
                                    ed_clock.getText().toString(),
                                    ed_text.getText().toString()
                            )
                    );
                } catch (ParseException e) {
                    Log.e("TABEL", "ERROR PARSE DATE: " + e.getMessage());
                }
                ed_data.setText("");
                ed_clock.setText("");
                ed_text.setText("");
                finish();

                break;
            case R.id.btn_cancellation:
                finish();
                break;
            case R.id.image_calend:
                callDatePicker();
                break;
        }

    }

    private void callDatePicker() {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        ed_data.setText(editTextDateParam);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
