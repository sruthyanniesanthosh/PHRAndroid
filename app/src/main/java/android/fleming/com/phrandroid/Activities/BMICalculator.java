package android.fleming.com.phrandroid.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BMICalculator extends AppCompatActivity {
TextView edWeight,edHeight;
String uid,status;
float height,weight,bmi;
ProgressDialog dialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        dialogs=new ProgressDialog(this);
        dialogs.setMessage("Updating please wait");

        SharedPreferences sharedPreferences=getSharedPreferences("LoginID", Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("ID",null);

        edWeight=(EditText)findViewById(R.id.edWeight);
        edHeight=(EditText)findViewById(R.id.etHeight);
    }

    public void CalculateBMI(View view)
    {

         weight=Float.parseFloat(edWeight.getText().toString());
         height=Float.parseFloat(edHeight.getText().toString());

         bmi=weight/(height*height);

        if(bmi<18.5)
        {
             status="Under weight";
            ShowNotification(status);
        }else if(bmi<=25 && bmi>=18.5)

        {
             status="Normal weight";
            ShowNotification(status);
        }
        else if(bmi>25)
        {
             status="Over weight";
            ShowNotification(status);
        }

    }

    public class UpdateBMI extends AsyncTask<Void,Void,String>
    {

        HttpURLConnection httpURLConnection=null;
        String postdata="";

        @Override
        protected String doInBackground(Void... voids) {

            try{

                httpURLConnection=(HttpURLConnection)new URL("http://192.168.43.81:16052/PHR/bmicalculator.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output=new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("userid="+uid.toString().trim()+"&weight="+weight+""+"&height="+height+""+"&bmi="+bmi+""+"&status="+status);
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
            dialogs.dismiss();
            Toast.makeText(getApplicationContext(), s+"", Toast.LENGTH_SHORT).show();

        }
    }

    public void ShowNotification(String status)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(status);
        builder.setPositiveButton("Update to server", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogs.show();
new UpdateBMI().execute();
            }
        });
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }



}
