package android.fleming.com.phrandroid.Activities;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.Adapters.JSONAdapter;
import android.fleming.com.phrandroid.R;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookDocUser extends AppCompatActivity{

    Spinner spHospital,spDepartMent,spDoctor;
    LinearLayout layoutDepartment,layoutDoc;
    String ids,departmentname,idDoc,userId;
    JSONObject hosname;
    ProgressDialog dialog;
    EditText ptSymptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_view_prescription);
        spHospital=findViewById(R.id.spHospital);

        ptSymptoms=(EditText)findViewById(R.id.ptSymptoms);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading prescription...");

        layoutDepartment=(LinearLayout)findViewById(R.id.layoutDepartment);
        layoutDoc=(LinearLayout)findViewById(R.id.layoutDoc);
        spDoctor=(Spinner)findViewById(R.id.spDoctor);



        SharedPreferences sharedPreferences=getSharedPreferences("LoginID", Context.MODE_PRIVATE);
        userId=sharedPreferences.getString("ID",null);
        Log.e("User_id........>",userId);
        spDepartMent=(Spinner)findViewById(R.id.spDepartMent);
        spHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ids=view.getTag().toString();

                new ViewDepartment().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spDepartMent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    hosname=new JSONObject(spDepartMent.getSelectedItem().toString());
                    departmentname=hosname.getString("name");
                    new ViewDoc().execute();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idDoc=view.getTag().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new ViewHospital().execute();
    }


    public class ViewPriscriptionss extends AsyncTask<Void, Void, String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/userbookdoctor.jsp").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("docid="+idDoc.trim()+"&uid="+userId+"&hosid="+ids+"&deptnm"+departmentname+"&symptoms"+ptSymptoms.getText().toString());
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
                Log.e("Exceptionathos-->", e + "");
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

                Toast.makeText(getApplicationContext(),s+"",Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.e("Exceptionathos-->",e+"");
            }

        }
    }



    public void ViewPres(View view)
    {

        new ViewPriscriptionss().execute();
    }

    public class ViewDoc extends AsyncTask<Void, Void, String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/ViewDoc.jsp").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("id="+ids.trim()+"&dept="+departmentname);
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
                Log.e("Exceptionathos-->", e + "");
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
                spDoctor.setAdapter(adapter);

            } catch (Exception e) {
                Log.e("Exceptionathos-->",e+"");
            }

        }
    }

    public class ViewHospital extends AsyncTask<Void, Void, String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/ViewHospital.jsp").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();



                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader ir = new InputStreamReader(is);
                int data = ir.read();
                while (data != -1) {
                    char ch = (char) data;
                    postdata += ch;
                    data = ir.read();
                }

            } catch (Exception e) {
                Log.e("Exceptiondoc-->", e + "");
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
                spHospital.setAdapter(adapter);

            } catch (Exception e) {
                Log.e("Exceptiondoc-->",e+"");
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
                output.writeBytes("id="+ids.trim());
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
                spDepartMent.setAdapter(adapter);

            } catch (Exception e) {
                Log.e("ExceptionatDept-->",e+"");
            }

        }
    }


}

