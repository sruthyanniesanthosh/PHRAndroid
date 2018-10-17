package android.fleming.com.phrandroid.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.Adapters.RecyclerViewAdapter;
import android.fleming.com.phrandroid.Adapters.ViewHistoryAdapter;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DocPatiantsViewHistory extends AppCompatActivity {
RecyclerView viewHistory;
SharedPreferences sh;
String UId;
RecyclerView.Adapter adapter;
ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_view_history);


        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading Patiants Data please wait");
        dialog.show();

        viewHistory=(RecyclerView)findViewById(R.id.rViewHistory);
        viewHistory.setHasFixedSize(true);
        viewHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sh=getSharedPreferences("User_id", Context.MODE_PRIVATE);
        UId=sh.getString("User_ID",null);

        new GeTHistory().execute();
    }
    public class GeTHistory extends AsyncTask<Void,Void,String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/ViewHistory.jsp").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("uid="+UId.trim());
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
                adapter=new ViewHistoryAdapter(getApplicationContext(),jrr);
                viewHistory.setAdapter(adapter);

            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"No Patiants Registerd",Toast.LENGTH_SHORT);
                Log.e("Exception-->",e+"");
            }
        }
    }
}
