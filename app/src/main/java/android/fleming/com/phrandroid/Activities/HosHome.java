package android.fleming.com.phrandroid.Activities;

import android.content.Intent;
import android.fleming.com.phrandroid.R;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HosHome extends AppCompatActivity{

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoshome);

    }

    public void AddDepart(View v)
    {
        startActivity(new Intent(getApplicationContext(),AddDepartment.class));

    }
    public void AddDoc(View v)
    {

        startActivity(new Intent(getApplicationContext(),AddHospital.class));
    }

}
