package com.example.control;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.control.model.ReportCard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UTFDataFormatException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Tabel extends AppCompatActivity implements View.OnClickListener {

    private EditText reportCardTextEt, hoursEt, dateEt;
    private Button btn_save,btn_cancellation;
    private ImageButton imageButton;
    private TextView titleCard;


    private int mYear, mMonth, mDay;
    private static final String TAG = "Tabel";
    DatabaseReference currentRecordRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabel);
        titleCard = (TextView) findViewById(R.id.title_card);
        dateEt = (EditText) findViewById(R.id.ed_data);
        hoursEt = (EditText) findViewById(R.id.ed_clock);
        reportCardTextEt = (EditText) findViewById(R.id.ed_text);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancellation = (Button) findViewById(R.id.btn_cancellation);
        imageButton = (ImageButton) findViewById(R.id.image_calend);
        imageButton.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_cancellation.setOnClickListener(this);

        titleCard.setText("Довьте запись: " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        if (getIntent().getStringExtra("firebaseId") != null) {
            // Get a reference to our posts
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            currentRecordRef = FirebaseDatabase.getInstance()
                    .getReference("report")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(getIntent().getStringExtra("firebaseId"));

            // Attach a listener to read the data at our posts reference
            currentRecordRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ReportCard currentReportCard = dataSnapshot.getValue(ReportCard.class);
                    hoursEt.setText(currentReportCard.getHours());
                    reportCardTextEt.setText(currentReportCard.getReportCardText());
                    dateEt.setText(currentReportCard.getDateOfWorkString());
                    currentRecordRef.removeEventListener(this);
                    Log.d(TAG, "TEST : "  + currentReportCard);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "ERROR CODE : "  + databaseError.getCode());
                    Log.e(TAG, databaseError.getMessage());
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (dateEt.getText().toString() == "")
                    if (hoursEt.getText().toString() == "")
                        if (reportCardTextEt.getText().toString() == "")
                            return;
                try {
                    Log.d("TEST 06.12.2019 = ", new SimpleDateFormat("dd.MM.yyyy").parse(dateEt.getText().toString()).getTime() + "");
                    if (currentRecordRef != null) { //update
                        currentRecordRef.setValue(
                                new ReportCard(
                                        new SimpleDateFormat("dd.MM.yyyy").parse(dateEt.getText().toString()).getTime(), //TODO CONVERT DATE STRING TO DATE LONG
                                        hoursEt.getText().toString(),
                                        reportCardTextEt.getText().toString()
                                )
                        );
                    } else { //create
                        FirebaseDatabase.getInstance().getReference("report").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(
                                new ReportCard(
                                        new SimpleDateFormat("dd.MM.yyyy").parse(dateEt.getText().toString()).getTime(), //TODO CONVERT DATE STRING TO DATE LONG
                                        hoursEt.getText().toString(),
                                        reportCardTextEt.getText().toString()
                                )
                        );
                    }
                } catch (ParseException e) {
                    Log.e("TABEL", "ERROR PARSE DATE: " + e.getMessage());
                }
                dateEt.setText("");
                hoursEt.setText("");
                reportCardTextEt.setText("");
                finish();

                break;
            case R.id.btn_cancellation:
                finish();
                break;
            case R.id.image_calend:
                Utility.callDatePicker(this, dateEt);
                break;
        }
    }

}
