package android.fleming.com.phrandroid.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PillReminder extends AppCompatActivity {
    EditText edMedcine,reDate;
    SharedPreferences sharedPreferences;
    String userId;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pillremider);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Setting pleace wait");

        edMedcine=(EditText)findViewById(R.id.edMedcine);
        reDate=(EditText)findViewById(R.id.edMedcine);
        sharedPreferences=getSharedPreferences("LoginID", Context.MODE_PRIVATE);
        userId=sharedPreferences.getString("ID",null);

    }
    public void SetReminder(View view)
    {
        dialog.show();

        new SetRemider().execute();

    }

    public class SetRemider extends AsyncTask<Void, Void, String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/pillreminder.jsp").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("urid="+userId+"&name="+edMedcine.getText().toString()+"&date="+reDate.getText().toString());
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
                Log.e("Exceptionathos-->", e + "");
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

            try {
                dialog.dismiss();

                Toast.makeText(getApplicationContext(),s+"",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Exceptionathos-->",e+"");
            }

        }
    }
}
