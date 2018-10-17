package android.fleming.com.phrandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class DocPriscription extends AppCompatActivity {
String st;
SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_priscription);
      st=getIntent().getStringExtra("ID").toString();

      sh=getSharedPreferences("User_id", Context.MODE_PRIVATE);
      SharedPreferences.Editor edit=sh.edit();
      edit.putString("User_ID",st);
      edit.commit();

      Log.d("Id of User..>",st+"");

    }
    public void ViewHistory(View view)
    {

        startActivity(new Intent(getApplicationContext(),DocPatiantsViewHistory.class));

    }
    public void AddPriscription(View view)
    {

        startActivity(new Intent(getApplicationContext(),DocPatiantsAddPrescription.class));

    }
}
