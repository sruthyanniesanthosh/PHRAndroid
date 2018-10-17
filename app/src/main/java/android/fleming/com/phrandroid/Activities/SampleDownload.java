package android.fleming.com.phrandroid.Activities;

import android.app.DownloadManager;
import android.content.Context;
import android.fleming.com.phrandroid.R;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SampleDownload extends AppCompatActivity {
EditText url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_layout);
    url=(EditText)findViewById(R.id.url);
    }


    public void DownloadFunction(View view)
    {
        String urls =url.getText().toString();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urls));
        request.setDescription("Data from server");
        request.setTitle("Downloading please wait");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "name-of-the-file.pdf");

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
