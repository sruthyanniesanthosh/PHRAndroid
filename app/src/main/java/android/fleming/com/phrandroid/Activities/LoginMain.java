package android.fleming.com.phrandroid.Activities;

import android.content.Intent;
import android.fleming.com.phrandroid.R;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
    }
    public void LoginFunction(View view)
    {

        startActivity(new Intent(getApplicationContext(),Login.class));

    }
    public void RegistrationFunction(View view)
    {

        startActivity(new Intent(getApplicationContext(),RegistrationMain.class));

    }
}
