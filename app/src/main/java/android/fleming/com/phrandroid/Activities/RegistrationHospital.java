package android.fleming.com.phrandroid.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationHospital extends AppCompatActivity {
    TextView etNameH,etAddressH,etPhoneH,etUsernameH,etPasswordH;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_hospital);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Saving Please Wait...!");

        etNameH=(EditText)findViewById(R.id.etNameH);
        etAddressH=(EditText)findViewById(R.id.etAddressH);
        etPhoneH=(EditText)findViewById(R.id.etPhoneH);
        etUsernameH=(EditText)findViewById(R.id.etUsernameH);
        etPasswordH=(EditText)findViewById(R.id.etPasswordH);
    }


    public void RegisterFunctionH(View view)
    {
        int flag=0;

        if(etNameH.getText().toString().equals(null)||etNameH.getText().toString().equals(""))
        {
            flag=1;
            etNameH.setError("Required");
        }
        if(etAddressH.getText().toString().equals(null)||etAddressH.getText().toString().equals(""))
        {
            flag=1;
            etAddressH.setError("Required");
        }
        if(etPhoneH.getText().toString().equals(null)||etPhoneH.getText().toString().equals(""))
        {
            flag=1;
            etPhoneH.setError("Required");
        }
        if(etUsernameH.getText().toString().equals(null)||etUsernameH.getText().toString().equals(""))
        {
            flag=1;
            etUsernameH.setError("Required");
        }
        if(etPasswordH.getText().toString().equals(null)||etPasswordH.getText().toString().equals(""))
        {
            flag=1;
            etPasswordH.setError("Required");
        }
        if(flag==0)
        {
            dialog.show();
            new InsertUserH().execute();

        }

    }

    public class InsertUserH extends AsyncTask<Void,Void,String>
    {

        HttpURLConnection httpURLConnection=null;
        String postdata="";

        @Override
        protected String doInBackground(Void... voids) {

            try{

                httpURLConnection=(HttpURLConnection)new URL("http://192.168.43.81:16052/PHR/registerHospital.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output=new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("name="+etNameH.getText().toString().trim()+"&address="+etAddressH.getText().toString().trim()+"&phone="+etPhoneH.getText().toString().trim()+"&username="+etUsernameH.getText().toString().trim()+"&password="+etPasswordH.getText().toString().trim());
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
            return postdata;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s+"", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            startActivity(new Intent(getApplicationContext(),Login.class));

        }
    }

    public void BackTOHomeH(View view)
    {
        startActivity(new Intent(getApplicationContext(),LoginMain.class));
    }
}
