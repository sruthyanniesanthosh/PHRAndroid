package android.fleming.com.phrandroid.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.Adapters.RecyclerViewAdapter;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DocPatiantsAddPrescription extends AppCompatActivity{
SharedPreferences sharedPreferences,sh2;
ProgressDialog dialog,dialog3;
String userID,Uid;
    TextView etName,etAge,etSymtoms;EditText edPres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_add_prescription);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading Patiants Data please wait");
        dialog.show();

        dialog3=new ProgressDialog(this);
        dialog3.setMessage("Saving Patiants Data please wait");


        sharedPreferences=getSharedPreferences("LoginID", Context.MODE_PRIVATE);
        userID=sharedPreferences.getString("ID",null);

        sh2=getSharedPreferences("User_id", Context.MODE_PRIVATE);
        Uid=sh2.getString("User_ID",null);

        etName=(TextView)findViewById(R.id.etName);
        etAge=(TextView)findViewById(R.id.etAge);
        etSymtoms=(TextView)findViewById(R.id.etSymtoms);
        edPres=(EditText)findViewById(R.id.edPres);
        new GetPatiantss().execute();

    }

  public void AddPresCription(View view)
  {
      int flag=0;
      if(edPres.getText().toString().equals("")||edPres.getText().toString().equals(null))
      {
          flag=1;
          edPres.setError("Required");
      }
      else
      {

          new AddPresCription().execute();
          dialog3.show();

      }
  }

    public class GetPatiantss extends AsyncTask<Void,Void,String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/DoctorViewPatiantsForPres.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("docid="+userID.trim()+"&userid="+Uid);
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
            try
            {
                dialog.dismiss();
                JSONArray jrr=new JSONArray(s);
               for(int i=0;i<jrr.length();i++)
               {
                   JSONObject o=jrr.getJSONObject(i);
                   etName.setText(o.getString("name"));
                   etAge.setText(o.getString("age"));
                   etSymtoms.setText(o.getString("symptoms"));
               }

            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"No Patiants Registerd",Toast.LENGTH_SHORT);
                Log.e("Exception-->",e+"");
            }
        }
    }


    public class AddPresCription extends AsyncTask<Void,Void,String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/DoctorPriscribeAddSymptoms.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("iddoc="+userID.trim()+"&prescription="+edPres.getText().toString()+"&userid="+Uid);
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
            try
            {
                dialog3.dismiss();

                Toast.makeText(getApplicationContext(),s+"",Toast.LENGTH_SHORT);
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"No Patiants Registerd",Toast.LENGTH_SHORT);
                Log.e("Exception-->",e+"");
            }
        }
    }
}
