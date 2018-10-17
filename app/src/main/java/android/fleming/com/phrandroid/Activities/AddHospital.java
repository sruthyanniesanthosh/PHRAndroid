package android.fleming.com.phrandroid.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.Adapters.JSONAdapter;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddHospital extends AppCompatActivity {
String userId;
Spinner spDipartment;
    String ids,departmentname,idDoc;
    JSONObject hosname;
    EditText etName,etPhone,etMail,etUsername,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_doc);
        spDipartment=(Spinner)findViewById(R.id.spDep);

        etName=(EditText)findViewById(R.id.etName);
        etPhone=(EditText)findViewById(R.id.etPhone);
        etMail=(EditText)findViewById(R.id.etMail);
        etUsername=(EditText)findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);

        SharedPreferences sharedPreferences=getSharedPreferences("LoginID", Context.MODE_PRIVATE);
        userId=sharedPreferences.getString("ID",null);

        spDipartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    hosname=new JSONObject(spDipartment.getSelectedItem().toString());
                    departmentname=hosname.getString("name");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        new ViewDepartment().execute();


    }


    public void RegisterFunction(View view)
    {

        new AddDoc().execute();

    }

    public class AddDoc extends AsyncTask<Void, Void, String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/adddoctor.jsp").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("name="+etName.getText().toString()+"&hid="+userId+"&phone="+etPhone.getText().toString()+"&email="+etMail.getText().toString()+"&dept="+departmentname+"&unm="+etUsername.getText().toString()+"&pw="+etPassword.getText().toString());
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
                Log.e("Exceptiondept-->", e + "");
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


                JSONArray arr=new JSONArray(s);
                JSONAdapter adapter=new JSONAdapter(getApplicationContext(),arr);
                spDipartment.setAdapter(adapter);

            } catch (Exception e) {
                Log.e("ExceptionatDept-->",e+"");
            }

        }
    }


    public class ViewDepartment extends AsyncTask<Void, Void, String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/VeiwDepartMent.jsp").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("id="+userId.trim());
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
                Log.e("Exceptiondept-->", e + "");
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


                JSONArray arr=new JSONArray(s);
                JSONAdapter adapter=new JSONAdapter(getApplicationContext(),arr);
                spDipartment.setAdapter(adapter);

            } catch (Exception e) {
                Log.e("ExceptionatDept-->",e+"");
            }

        }
    }
}
