package android.fleming.com.phrandroid.Activities;

import android.content.Intent;
import android.fleming.com.phrandroid.R;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DocHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_home);

    }

    public void Patients(View view)
    {

        startActivity(new Intent(getApplicationContext(),DocViewPatiants.class));

    }
    public void AddMedicine(View view)
    {
        startActivity(new Intent(getApplicationContext(),DocAddMedcine.class));
    }

}
