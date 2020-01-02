package com.example.control;

import android.os.Bundle;

import com.example.control.model.ReportCard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Report extends AppCompatActivity implements View.OnClickListener {
	private static final String TAG = "Report";
	private Button applyBtn, cancleBtn;
	//private Integer counter;
	private EditText start_date_et;
	private EditText end_date_et;
	private ImageButton imageButton1, imageButton2;

	private TextView reportTV;
 // google

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		start_date_et = findViewById(R.id.start_date_et);
		end_date_et = findViewById(R.id.end_date_et);
		reportTV = findViewById(R.id.report_tv);
		reportTV.setMovementMethod(new ScrollingMovementMethod());

		imageButton1 = (ImageButton) findViewById(R.id.image_calendreport1);
		imageButton1.setOnClickListener(this);
		imageButton2 = (ImageButton) findViewById(R.id.image_calendreport2);
		imageButton2.setOnClickListener(this);


		cancleBtn =findViewById(R.id.cancleBtn);
		cancleBtn.setOnClickListener(this);
		applyBtn = findViewById(R.id.apply_btn);
		applyBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				final String startDate = (start_date_et.getText().toString() == null)? "02.01.1971" : start_date_et.getText().toString();
				final String endDate = end_date_et.getText().toString();
				DatabaseReference ref = FirebaseDatabase.getInstance()
						.getReference("report")
						.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
				ref.orderByChild("dateOfWork")
						.startAt(Utility.convertDateTextToLong(startDate))
						.endAt(Utility.convertDateTextToLong(endDate))
						.addValueEventListener(
						new ValueEventListener() {
							@Override
							public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
								Log.d(TAG, "onDataChange " + dataSnapshot.getChildrenCount());
								int totalHours = 0;
								String repotString = startDate + " - " + endDate;
								repotString += "\n Спиок выполненых работ: \n";
								for (DataSnapshot reportCardDS: dataSnapshot.getChildren()) {
									ReportCard reportCard = reportCardDS.getValue(ReportCard.class);
									totalHours += Integer.valueOf( reportCard.getHours() + "");
									repotString += reportCard.getReportCardText() + ";\n";
								}

								repotString += "\n Сумма отработанных часов за выбранный период  : " + totalHours;
								reportTV.setText(repotString);
							}

							@Override
							public void onCancelled(@NonNull DatabaseError databaseError) {

							}
						}
				);
			}
		});
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.image_calendreport1:
				Utility.callDatePicker(this, start_date_et);
				break;
			case R.id.image_calendreport2:
				Utility.callDatePicker(this, end_date_et);
				break;
			case R.id.cancleBtn:
				finish();
				break;
		}
	}
}
