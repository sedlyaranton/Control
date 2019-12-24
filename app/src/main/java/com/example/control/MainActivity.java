package com.example.control;

//тот самый проект
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.control.model.ReportCard;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static int SIGN_IN_CODE = 1;
    private FloatingActionButton sendBtnAdd;
    public FirebaseListAdapter<ReportCard> adapters;
    private static final String TAG = "MainActivity";
    private FirebaseDatabase database;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE){
            if (requestCode == RESULT_OK){
                Toast.makeText(this, "Вы авторизованы", Toast.LENGTH_SHORT).show();
                displayAllReportCard();
            }else {
                Toast.makeText(this, "Вы не авторизованы", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= FirebaseDatabase.getInstance();
        //сохранение
        database.setPersistenceEnabled(true);

        ListView lvMain = (ListView) findViewById(R.id.list_of_messages_report_card);
        registerForContextMenu(lvMain);

        sendBtnAdd = findViewById(R.id.sendBtnAdd);
        sendBtnAdd.setOnClickListener(this);

        //Авторизация пользователей
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
        else
            Toast.makeText(this, "Вы авторизованы", Toast.LENGTH_SHORT).show();
            displayAllReportCard();

    }

    private void displayAllReportCard() {
        ListView listOfReportCard = findViewById(R.id.list_of_messages_report_card);
        adapters = new FirebaseListAdapter<ReportCard>(this, ReportCard.class, R.layout.list_repord_card, FirebaseDatabase.getInstance().getReference("report").child("users")) {
            @Override
            protected void populateView(View v, ReportCard model, int position) {
                TextView reportCartDate, reportCardData, reportCardClok, reportCardText;
                reportCartDate = v.findViewById(R.id.report_cart_date);
                reportCardData = v.findViewById(R.id.report_cart_data);
                reportCardClok = v.findViewById(R.id.report_card_clok);
                reportCardText = v.findViewById(R.id.report_card_text);

                reportCartDate.setText(DateFormat.format("dd-MM-yyyy HH:mm:ss", model.getReportCardDate()));
                reportCardData.setText("Дата:  " + model.getDateOfWorkString());
                reportCardClok.setText("Время:  " + model.getHours());
                reportCardText.setText("Отчет:  " + model.getReportCardText());


            }
        };
        listOfReportCard.setAdapter(adapters);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                return true;
            case R.id.delete:
                 deleteReportCard();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteReportCard() {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("report").child("users");

        //removing artist
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Artist Deleted", Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Операции для выбранного пункта меню
        switch (item.getItemId())
        {
            case R.id.calc_zp:
                Intent chat = new Intent(this, CalculatorZP.class);
                startActivity(chat);
                return true;
            case R.id.exit:
                revokeAccess();
                return true;
            case R.id.report:
                Intent report = new Intent(this, Report.class);
                startActivity(report);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void revokeAccess() {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            finish();

                        }
                    });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendBtnAdd:
                Intent chat1 = new Intent(this, Tabel.class);
                startActivity(chat1);
                break;
            default:
        }

    }

}
