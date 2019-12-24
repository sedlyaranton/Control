package com.example.control;

import android.os.Bundle;

import com.example.control.model.ReportCard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Report extends AppCompatActivity {
	private static final String TAG = "Report";
	private Button applyBtn;
	//private Integer counter;
	private EditText start_date_et;
	private EditText end_date_et;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});


		applyBtn = findViewById(R.id.apply_btn);
		applyBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

//				switch (view.getId()) {
//					case R.id.apply_btn:
//						if (start_date_et.getText().toString() == "")
//							if (end_date_et.getText().toString() == "")
//									return;
//
//							FirebaseDatabase.getInstance().getReference().push().setValue(
//									new ReportCard(
//											new SimpleDateFormat("dd.MM.yyyy").parse(ed_data.getText().toString()).getTime(), //TODO CONVERT DATE STRING TO DATE LONG
//											ed_clock.getText().toString(),
//											ed_text.getText().toString()
//									)
//							);
//						start_date_et.setText("");
//						end_date_et.setText("");
//						finish();
//
//						break;
//
//				}
//




//				Log.d(TAG, "*** REPOR ***");
//				DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//				ref.orderByChild("dateOfWork").startAt(1575244800000l).endAt(1575244800000l).addValueEventListener(
//						new ValueEventListener() {
//							@Override
//							public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//								Log.d(TAG, "onDataChange " + dataSnapshot.getChildrenCount());
//								for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
//									//TODO REPORT RESULT
//
//										ReportCard rp = (ReportCard) dataSnapshot.getValue(ReportCard.class);
//
////									counter =+ Integer.valueOf(rp.getHours());
//
//
//									Log.d(TAG, "RESULT = " + rp);
//									Log.d(TAG, "onDataChange " + dataSnapshot1);
//									Log.d(TAG, "dataSnapshot k" + dataSnapshot1.getKey());
//									Log.d(TAG, "dataSnapshot v" + dataSnapshot1.getValue());
//								}
//
//							}
//
//							@Override
//							public void onCancelled(@NonNull DatabaseError databaseError) {
//
//							}
//						}
//				);
//						.queryOrderedByChild(posterId)
//						.queryStartingAtValue("Range1")
//						.queryEndingAtValue("Range2")
//						.observeEventType(.Value, withBlock: { snapshot in
//					print(snapshot.key)
//				})
			}
		});
	}

}
