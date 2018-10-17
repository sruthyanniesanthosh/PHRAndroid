package android.fleming.com.phrandroid.Activities;

import android.fleming.com.phrandroid.R;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ViewImage extends AppCompatActivity {
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_full);
        im=(ImageView)findViewById(R.id.idFull);
        String filpath=getIntent().getExtras().getString("path");
        im.setImageBitmap(BitmapFactory.decodeFile(filpath));
    }
}
