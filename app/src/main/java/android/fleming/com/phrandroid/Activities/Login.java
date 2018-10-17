package android.fleming.com.phrandroid.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
Spinner sp;
String selectedItem;
EditText tvUsername,tvPassword;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvUsername=(EditText)findViewById(R.id.tvUsername);
        tvPassword=(EditText)findViewById(R.id.tvPassword);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Checking Please Wait...!");

        sp=(Spinner)findViewById(R.id.SpinnerUser);

        List<String> al=new ArrayList<>();
        al.add("Hospital");
        al.add("User");
        al.add("Doctor");

        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,al);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem=sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

  public void LoginFunction(View view)
  {

int flag=0;
      if(tvUsername.getText().toString().equals(null)||tvUsername.getText().toString().equals(""))
      {
          flag=1;
          tvUsername.setError("Required");
      }
      if(tvPassword.getText().toString().equals(null)||tvPassword.getText().toString().equals(""))
      {
          flag=1;
          tvPassword.setError("Required");
      }
      if(flag==0)
      {   dialog.show();
          new LoginCheck().execute();
      }

  }
    public class LoginCheck extends AsyncTask<Void,Void,String>
    {

        HttpURLConnection httpURLConnection=null;
        String postdata="";

        @Override
        protected String doInBackground(Void... voids) {

            try{

                httpURLConnection=(HttpURLConnection)new URL("http://192.168.43.81:16052/PHR/Login.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output=new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("name="+tvUsername.getText().toString().trim()+"&pw="+tvPassword.getText().toString().trim()+"&type="+selectedItem);
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
            dialog.dismiss();

            String res=s.trim();
            if(s.equals("Login failed!"))
            {
                Toast.makeText(getApplicationContext(), s+"", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder b=new AlertDialog.Builder(Login.this);
                b.setTitle("Login Faild").setMessage("Change Username and Password ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(),SecurityCheckApp.class));
                    }
                });
               b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
               AlertDialog d=b.create();
               d.show();

            }
            else
            {
                String []temp=res.split("@");
                String id=temp[0];
                SharedPreferences sharedPreferences=getSharedPreferences("LoginID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("ID",id);
                editor.commit();

                if(selectedItem.equals("Hospital"))
                {

                    startActivity(new Intent(getApplicationContext(),HosHome.class));

                }
                else if(selectedItem.equals("User"))
                {

                    startActivity(new Intent(getApplicationContext(),UserHome.class));

                }
                else if(selectedItem.equals("Doctor"))
                {

                    startActivity(new Intent(getApplicationContext(),DocHome.class));

                }
                else{}
            }

        }
    }

}
