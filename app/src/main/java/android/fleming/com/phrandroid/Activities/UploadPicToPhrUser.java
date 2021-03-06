package android.fleming.com.phrandroid.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.fleming.com.phrandroid.R;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadPicToPhrUser extends AppCompatActivity {
    Button btn;
    String sourseFile, id;
    EditText tvDes;
    JSONArray out, in;
    SharedPreferences sharedPreferences;
    private ImageView mImageView;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pic_to_server);
        btn = (Button) findViewById(R.id.btnAddImage);
        tvDes = (EditText) findViewById(R.id.tvDes);
        dialog =new ProgressDialog(this);
        dialog.setMessage("Uploading please wait...!");


        mImageView = (ImageView) findViewById(R.id.imageViewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }
        }
        enable_button();
    }

    public void enable_button() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(UploadPicToPhrUser.this)
                        .withRequestCode(10)
                        .start();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            mImageView.setImageBitmap(BitmapFactory.decodeFile(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)));
            sourseFile = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    public void UploadFile(View view) {
        boolean hasDrawable = (mImageView.getDrawable() != null);
        int flag = 0;
        if (!hasDrawable) {
            Toast.makeText(getApplicationContext(), "Select A Image", Toast.LENGTH_SHORT).show();
            flag = 1;
        }
        if (tvDes.getText().toString().equals("") || tvDes.getText().toString().equals(null)) {
            flag = 1;
            tvDes.setError("reqired");
        }
        if (flag == 0) {
            try {
                sharedPreferences = getSharedPreferences("LoginID", Context.MODE_PRIVATE);
                id = sharedPreferences.getString("ID", null);
                out=new JSONArray();
                JSONObject o = new JSONObject();
                o.put("file", sourseFile);
                Log.e("Value---->",sourseFile+"..........>"+tvDes.getText().toString());

                o.put("uid", id);
                o.put("des", tvDes.getText().toString());
                out.put(o);
                dialog.show();
                new UpdateFile().execute();
            } catch (Exception e) {

                Log.e("Error--->", e + "");

            }

        }

    }

    public class UpdateFile extends AsyncTask<Void, Void, String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/UpdateFile.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("values="+out.toString());
                output.flush();
                output.close();

                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader ir = new InputStreamReader(is);
                int data = ir.read();
                while (data != -1) {
                    char ch = (char) data;
                    postdata += ch;
                    data = ir.read();
                }

            } catch (Exception e) {
                Log.e("Exception-->", e + "");
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return postdata.trim();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), s + "", Toast.LENGTH_SHORT).show();
        }
    }
}
