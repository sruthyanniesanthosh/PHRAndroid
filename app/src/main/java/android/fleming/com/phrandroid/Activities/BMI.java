package android.fleming.com.phrandroid.Activities;

import android.content.Intent;
import android.fleming.com.phrandroid.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BMI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

    }

    public void Calculate(View view)
    {
        startActivity(new Intent(getApplicationContext(),BMICalculator.class));
    }
    public void ViewHistory(View view)
    {
        startActivity(new Intent(getApplicationContext(),ViewBMI.class));
    }
}
