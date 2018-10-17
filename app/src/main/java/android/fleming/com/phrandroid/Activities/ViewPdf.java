package android.fleming.com.phrandroid.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.Adapters.ViewImageAdapter;
import android.fleming.com.phrandroid.Adapters.ViewPDFAdapter;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewPdf extends AppCompatActivity {
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    String sourseFile, id;
    RecyclerView rs;
    RecyclerView.Adapter adapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        dialog =new ProgressDialog(this);
        dialog.setMessage("Getting  reports pleace wait...!");

        sharedPreferences = getSharedPreferences("LoginID", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("ID", null);

        rs=(RecyclerView)findViewById(R.id.rsViewPdf);

        rs.setHasFixedSize(true);
        rs.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dialog.show();
        new GetPdf().execute();
    }

    public class GetPdf extends AsyncTask<Void, Void, String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/GetPDF.jsp").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("uid="+id.trim());
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
            dialog.dismiss();

try {
    JSONArray array=new JSONArray(s);
    adapter = new ViewPDFAdapter(getApplicationContext(),array);
    rs.setAdapter(adapter);
}catch (Exception e)
{
    Log.e("Exception-->",e+"");
}
        }
    }
}
