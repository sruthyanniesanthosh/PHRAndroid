package android.fleming.com.phrandroid.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.Adapters.RecyclerViewAdapterViewBMI;
import android.fleming.com.phrandroid.Adapters.ViewHistoryAdapter;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewBMI extends AppCompatActivity {
String uid;
RecyclerView rs;
RecyclerView.Adapter ad;
ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bmi_status);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...!");
        dialog.show();

        SharedPreferences sharedPreferences=getSharedPreferences("LoginID", Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("ID",null);
        rs=(RecyclerView)findViewById(R.id.recyclerViewBmiStatus);
        rs.setHasFixedSize(true);
        rs.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        new GeTHistory().execute();

    }

    public class GeTHistory extends AsyncTask<Void,Void,String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/bmiviewdetails.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("uid="+uid.trim());
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
                ad=new RecyclerViewAdapterViewBMI(jrr,getApplicationContext());
                rs.setAdapter(ad);

            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"No Patiants Registerd",Toast.LENGTH_SHORT);
                Log.e("Exception-->",e+"");
            }
        }
    }
}
