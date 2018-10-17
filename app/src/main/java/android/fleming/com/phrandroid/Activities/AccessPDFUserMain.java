package android.fleming.com.phrandroid.Activities;


import android.content.Intent;
import android.fleming.com.phrandroid.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class AccessPDFUserMain extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accespdf);

    }
    public void SelectPDF(View view)
    {
        startActivity(new Intent(getApplicationContext(),AddPDFReport.class));
    }

    public void ViewFile(View view)
    {
        startActivity(new Intent(getApplicationContext(),ViewPdf.class));
    }

}
