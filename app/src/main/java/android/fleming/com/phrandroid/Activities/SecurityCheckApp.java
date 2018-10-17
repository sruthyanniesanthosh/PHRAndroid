package android.fleming.com.phrandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecurityCheckApp extends AppCompatActivity {
String Userid;
TextView etSecQ;
    JSONObject o;
  LinearLayout passsssss,question,Phone;
  EditText etUsername,etPasswerd,etPhone,etAns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_check_app);

        passsssss=(LinearLayout)findViewById(R.id.passsssss);
        question=(LinearLayout)findViewById(R.id.question);
        Phone=(LinearLayout)findViewById(R.id.Phone);


        etSecQ=(TextView) findViewById(R.id.etSecQ);
        etAns=(EditText) findViewById(R.id.etAns);

        etUsername=(EditText) findViewById(R.id.etUsername);
        etPasswerd=(EditText) findViewById(R.id.etPassword);
        etPhone=(EditText) findViewById(R.id.etPhone);




    }

    public void getId(View view)
    {

        if(etPhone.getText().toString().equals("")||etPhone.getText().toString().equals(null))
        {
            etPhone.setError("Required");
        }
        else
        {
            new getId().execute();
        }

    }

    public class getId extends AsyncTask<Void,Void,String>
    {
        HttpURLConnection httpURLConnection=null;
        String postdata="";

        @Override
        protected String doInBackground(Void... voids) {

            try{

                httpURLConnection=(HttpURLConnection)new URL("http://192.168.43.81:16052/PHR/GetUserId.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output=new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("phone="+etPhone.getText().toString());
                output.flush();
                output.close();

                InputStream is=httpURLConnection.getInputStream();
                InputStreamReader ir=new InputStreamReader(is);
                int data=ir.read();
                while(data!=-1)
                {
                    char ch=(char)data;
                    postdata+=ch;
                    data=ir.read();
                }

            }catch (Exception e)
            {
                Log.e("Exception-->",e+"");}
            finally
            {if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }}
            return postdata.trim();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject o= null;
            try {
                o = new JSONObject(s);
                Userid=o.getString("id");
                Phone.setVisibility(View.GONE);
                question.setVisibility(View.VISIBLE);
                new getQuestion().execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public void update (View view)
    {

        new UpdateUsername().execute();

    }

    public class UpdateUsername extends AsyncTask<Void,Void,String>
    {

        HttpURLConnection httpURLConnection=null;
        String postdata="";

        @Override
        protected String doInBackground(Void... voids) {

            try{

                httpURLConnection=(HttpURLConnection)new URL("http://192.168.43.81:16052/PHR/updateUsername.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output=new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("uid="+Userid+"&username="+etUsername.getText().toString()+"&password="+etPasswerd.getText().toString());
                output.flush();
                output.close();

                InputStream is=httpURLConnection.getInputStream();
                InputStreamReader ir=new InputStreamReader(is);
                int data=ir.read();
                while(data!=-1)
                {
                    char ch=(char)data;
                    postdata+=ch;
                    data=ir.read();
                }

            }catch (Exception e)
            {
                Log.e("Exception-->",e+"");}
            finally
            {if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }}
            return postdata.trim();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s+"", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(),Login.class));

        }
    }

    public void Check(View view) throws JSONException {
        if(o.getString("ans").equals(etAns.getText().toString()))
        {
            question.setVisibility(View.GONE);
            passsssss.setVisibility(View.VISIBLE);

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Faild..", Toast.LENGTH_SHORT).show();
        }
    }

    public class getQuestion extends AsyncTask<Void,Void,String>
    {

        HttpURLConnection httpURLConnection=null;
        String postdata="";

        @Override
        protected String doInBackground(Void... voids) {

            try{

                httpURLConnection=(HttpURLConnection)new URL("http://192.168.43.81:16052/PHR/secCheck.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output=new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("uid="+Userid);
                output.flush();
                output.close();

                InputStream is=httpURLConnection.getInputStream();
                InputStreamReader ir=new InputStreamReader(is);
                int data=ir.read();
                while(data!=-1)
                {
                    char ch=(char)data;
                    postdata+=ch;
                    data=ir.read();
                }

            }catch (Exception e)
            {
                Log.e("Exception-->",e+"");}
            finally
            {if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }}
            return postdata.trim();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                 o=new JSONObject(s);
                etSecQ.setText(o.getString("qus"));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
