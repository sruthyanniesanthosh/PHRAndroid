package android.fleming.com.phrandroid.Activities;

import android.content.Intent;
import android.fleming.com.phrandroid.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegistrationMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_main);
    }
public void User(View view)
{
    startActivity(new Intent(getApplicationContext(),UserRegistration.class));
}
    public void Hospital(View view)
    {
        startActivity(new Intent(getApplicationContext(),RegistrationHospital.class));
    }
}
