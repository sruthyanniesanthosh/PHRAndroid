package android.fleming.com.phrandroid.Activities;



import android.content.Intent;
import android.fleming.com.phrandroid.R;

import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class AccessPicUserMain extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accespic);

    }
    public void SelectImage(View view)
    {
        startActivity(new Intent(getApplicationContext(),UploadPicToPhrUser.class));
    }

    public void ViewImage(View view)
    {
        startActivity(new Intent(getApplicationContext(),ViewImagesUser.class));
    }

}
