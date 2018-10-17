package android.fleming.com.phrandroid.Activities;

import android.content.Intent;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class DocAddMedcine extends AppCompatActivity {
EditText pill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_add_medicine);
        pill=(EditText)findViewById(R.id.etPill);


    }

   public void AddMeDicineFunction(View view)
   {

       int flag=0;
       if(pill.getText().toString().equals(null)||pill.getText().toString().equals(""))
       {
           flag=1;
           pill.setError("Required");

       }
       else
       {
           new Addpill().execute();
       }

   }

    public class Addpill extends AsyncTask<Void,Void,String>
    {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground (Void...voids){

        try {

            httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/AddPill.jsp").openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
            output.writeBytes("pill=" + pill.getText().toString().trim());
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
            if(s.trim().equals("Allready"))
            {
                pill.setError("Pill Already Addedd");

            }
            else
            {

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

            }
        }
    }
}
