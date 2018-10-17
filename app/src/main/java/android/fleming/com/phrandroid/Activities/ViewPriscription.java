package android.fleming.com.phrandroid.Activities;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.R;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.fleming.com.phrandroid.Adapters.*;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.List;

public class ViewPriscription extends AppCompatActivity {

Spinner spHospital,spDepartMent,spDoctor;
LinearLayout layoutDepartment,layoutDoc;
String ids,departmentname,idDoc,userId;
JSONObject hosname;
RecyclerView priscriptionView;
RecyclerView.Adapter adapterView;
ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_priscription);
        spHospital=findViewById(R.id.spHospital);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading prescription...");

        layoutDepartment=(LinearLayout)findViewById(R.id.layoutDepartment);
        layoutDoc=(LinearLayout)findViewById(R.id.layoutDoc);
        spDoctor=(Spinner)findViewById(R.id.spDoctor);

        priscriptionView=(RecyclerView)findViewById(R.id.rePrescription);
        priscriptionView.setHasFixedSize(true);
        priscriptionView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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
                    layoutDoc.setVisibility(View.VISIBLE);
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
                dialog.show();
                new ViewPriscriptionss().execute();

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

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/ViewPrescription.jsp").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("id="+idDoc.trim()+"&userId="+userId);
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
                dialog.dismiss();
                JSONArray arr=new JSONArray(s);

                adapterView=new ViewResultAdapter(arr,getApplicationContext());
                priscriptionView.setAdapter(adapterView);
              Log.wtf("wtf..>",s+"");

            } catch (Exception e) {
                Log.e("Exceptionathos-->",e+"");
            }

        }
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

                layoutDepartment.setVisibility(View.VISIBLE);
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

                layoutDepartment.setVisibility(View.VISIBLE);
                JSONArray arr=new JSONArray(s);
                JSONAdapter adapter=new JSONAdapter(getApplicationContext(),arr);
                spDepartMent.setAdapter(adapter);

            } catch (Exception e) {
                Log.e("ExceptionatDept-->",e+"");
            }

        }
    }


    public class ViewResultAdapter extends RecyclerView.Adapter<ViewResultAdapter.MyViewHolder> {

        Context context;
        JSONArray jrr;

        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView ptdate,ptSymptoms,ptPresciption;
            Button btnDownload;

            public MyViewHolder(View view)
            {
                super(view);
                ptdate=(TextView) view.findViewById(R.id.ptdate);
                ptSymptoms=(TextView)view.findViewById(R.id.ptSymptoms);
                ptPresciption=(TextView)view.findViewById(R.id.ptPrescription);
                btnDownload=(Button)view.findViewById(R.id.btnDownload);
            }

        }

        public ViewResultAdapter(JSONArray jrr, Context context)
        {

            this.jrr=jrr;
            this.context=context;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_view_prescription,parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewResultAdapter.MyViewHolder holder, int position) {
            try{

                final JSONObject obj = jrr.getJSONObject(position);
                holder.ptdate.setText(obj.getString("date"));
                holder.ptSymptoms.setText(obj.getString("symptoms"));
                holder.ptPresciption.setText(obj.getString("prescription"));

                holder.btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            downloadFuntion(obj.getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }catch (Exception e)
            {
                Log.e("Exception-->",e+"");
            }
        }

        @Override
        public int getItemCount() {
            return jrr.length();
        }
    }

   public void downloadFuntion(String id)
   {
       String url = "http://192.168.43.81:16052/PHR/DownloadReport.jsp?id="+id;
       DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
       request.setDescription("phr report");
       request.setTitle("PHMReport");

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
           request.allowScanningByMediaScanner();
           request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
       }
       request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "name-of-the-file.pdf");

       DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
       manager.enqueue(request);
   }
}
