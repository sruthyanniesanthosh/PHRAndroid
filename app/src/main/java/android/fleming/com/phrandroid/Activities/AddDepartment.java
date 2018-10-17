package android.fleming.com.phrandroid.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddDepartment extends AppCompatActivity {
    EditText edDepart;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_department);
        edDepart=(EditText)findViewById(R.id.edDepart);

        SharedPreferences sharedPreferences=getSharedPreferences("LoginID", Context.MODE_PRIVATE);
        userId=sharedPreferences.getString("ID",null);
    }

   public void AddDepartdd(View view)
   {
       new AddDepartmentasy().execute();

   }

    public class AddDepartmentasy extends AsyncTask<Void, Void, String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/adddepartment.jsp").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("name="+edDepart.getText().toString()+"&hid="+userId);
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

                Toast.makeText(getApplicationContext(),s+"",Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.e("Exceptionathos-->",e+"");
            }

        }
    }
}
