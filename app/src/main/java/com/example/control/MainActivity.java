package com.example.control;

//тот самый проект
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    public static int SIGN_IN_CODE = 99;
    private FloatingActionButton sendBtnAdd;
    public FirebaseListAdapter<ReportCard> adapters;
    ListView listOfReportCard;

    ArrayList<ReportCard> arrayListReportCard = new ArrayList<>();

    private static FirebaseDatabase firebaseDatabase;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE){
            Toast.makeText(this, "Вы авторизованы", Toast.LENGTH_SHORT).show();
            displayAllReportCard();
        } else {
            Toast.makeText(this, "Вы не авторизованы", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvMain = (ListView) findViewById(R.id.list_of_messages_report_card);
        registerForContextMenu(lvMain);

        sendBtnAdd = findViewById(R.id.sendBtnAdd);
        sendBtnAdd.setOnClickListener(this);

        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
        }
        //Авторизация пользователей
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            //сохранение
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
        } else {
            Toast.makeText(this, "Вы авторизованы", Toast.LENGTH_SHORT).show();
            displayAllReportCard();
        }
    }

    private void displayAllReportCard() {
        listOfReportCard = findViewById(R.id.list_of_messages_report_card);
        adapters = new FirebaseListAdapter<ReportCard>(this,
                ReportCard.class,
                R.layout.list_repord_card,
                FirebaseDatabase
                        .getInstance()
                        .getReference("report")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
        ) {
            @Override
            protected void populateView(View v, ReportCard model, int position) {
                DatabaseReference itemRef = getRef(position);
                String itemKey = itemRef.getKey();

                TextView reportCartDate, reportCardData, reportCardClok, reportCardText;
                reportCartDate = v.findViewById(R.id.report_cart_date);
                reportCardData = v.findViewById(R.id.report_cart_data);
                reportCardClok = v.findViewById(R.id.report_card_clok);
                reportCardText = v.findViewById(R.id.report_card_text);

                reportCartDate.setText(DateFormat.format("dd-MM-yyyy HH:mm:ss", model.getReportCardDate()));
                reportCardData.setText("Дата:  " + model.getDateOfWorkString());
                reportCardClok.setText("Время:  " + model.getHours());
                reportCardText.setText("Отчет:  " + model.getReportCardText());

                TextView firebaseIdTv = v.findViewById(R.id.firebase_id_tv);
                firebaseIdTv.setText(itemKey);
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


/**/    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Log.d(MainActivity.TAG, "TEST view" + (info.position));
        TextView firebaseIdTv = info.targetView.findViewById(R.id.firebase_id_tv);
        Log.d(MainActivity.TAG, "targetView " +  firebaseIdTv.getText());
        switch (item.getItemId()) {
            case R.id.edit:
                Intent chat1 = new Intent(this, Tabel.class);
                chat1.putExtra("firebaseId", firebaseIdTv.getText().toString());
                startActivity(chat1);
                break;
            case R.id.delete:
                 deleteReportCard(firebaseIdTv.getText().toString()); //sent to delete firebaseID
                break;
            default:
               //
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteReportCard(String deleteId) {
        Log.d(TAG, "deletedId = " + deleteId);
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(
              "report"
        )
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(deleteId);

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
