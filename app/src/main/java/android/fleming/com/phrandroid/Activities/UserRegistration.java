package android.fleming.com.phrandroid.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserRegistration extends AppCompatActivity {
    EditText etName,etAddress,etPhone,etMail,etAge,etUsername,etPassword,etSecQ,etAns;
    RadioGroup genderRg;
    RadioButton gender;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Saving Please Wait...!");

        etSecQ=(EditText)findViewById(R.id.etSecQ);
        etAns=(EditText)findViewById(R.id.etAns);
        etName=(EditText)findViewById(R.id.etName);
        etAddress=(EditText)findViewById(R.id.etAddress);
        etPhone=(EditText)findViewById(R.id.etPhone);
        etMail=(EditText)findViewById(R.id.etMail);
        etAge=(EditText)findViewById(R.id.etAge);
        etUsername=(EditText)findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);
        genderRg=(RadioGroup)findViewById(R.id.genderRg);

    }
    public void BackTOHome(View view)
    {
        startActivity(new Intent(getApplicationContext(),LoginMain.class));
    }

    public void RegisterFunction(View view)
    {
        int flag=0;
        int idofRadio=genderRg.getCheckedRadioButtonId();
        gender =(RadioButton)findViewById(idofRadio);

        if (genderRg.getCheckedRadioButtonId() == -1)
        {
            flag=1;
            Toast.makeText(getApplicationContext(),"Please select a gender",Toast.LENGTH_SHORT);

        }

            if(etName.getText().toString().equals(null)||etName.getText().toString().equals(""))
            {
                flag=1;
                etName.setError("Required");
            }
        if(etAddress.getText().toString().equals(null)||etAddress.getText().toString().equals(""))
        {
            flag=1;
            etAddress.setError("Required");
        }
        if(etPhone.getText().toString().equals(null)||etPhone.getText().toString().equals(""))
        {
            flag=1;
            etPhone.setError("Required");
        }
        if(etMail.getText().toString().equals(null)||etMail.getText().toString().equals(""))
        {
            flag=1;
            etMail.setError("Required");
        }
        if(etAge.getText().toString().equals(null)||etAge.getText().toString().equals(""))
        {
            flag=1;
            etAge.setError("Required");
        }
        if(etUsername.getText().toString().equals(null)||etUsername.getText().toString().equals(""))
        {
            flag=1;
            etUsername.setError("Required");
        }
        if(etPassword.getText().toString().equals(null)||etPassword.getText().toString().equals(""))
        {
            flag=1;
            etPassword.setError("Required");
        }
        if(flag==0)
        {
            dialog.show();
            new InsertUser().execute();

        }

    }

    public class InsertUser extends AsyncTask<Void,Void,String>
    {

        HttpURLConnection httpURLConnection=null;
        String postdata="";

        @Override
        protected String doInBackground(Void... voids) {

            try{

                httpURLConnection=(HttpURLConnection)new URL("http://192.168.43.81:16052/PHR/RegisterUser.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output=new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("name="+etName.getText().toString().trim()+"&address="+etAddress.getText().toString().trim()+"&phone="+etPhone.getText().toString().trim()+"&email="+etMail.getText().toString().trim()+"&gender="+gender.getText().toString()+"&age="+etAge.getText().toString().trim()+"&unm="+etUsername.getText().toString().trim()+"&pw="+etPassword.getText().toString().trim()+"&sec="+etSecQ.getText().toString()+"&ans="+etAns.getText().toString());
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
            }
            }
            return postdata;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s+"", Toast.LENGTH_SHORT).show();
            dialog.show();
            startActivity(new Intent(getApplicationContext(),Login.class));

        }
    }
}
