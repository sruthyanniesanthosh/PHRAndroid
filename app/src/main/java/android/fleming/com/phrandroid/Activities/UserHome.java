package android.fleming.com.phrandroid.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.fleming.com.phrandroid.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import java.util.Date;

public class UserHome extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String userId;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Setting pleace wait");



        sharedPreferences=getSharedPreferences("LoginID", Context.MODE_PRIVATE);
        userId=sharedPreferences.getString("ID",null);
        new CheckReminder().execute();

    }
public void BmI(View view)
{
    startActivity(new Intent(getApplicationContext(),BMI.class));

}

public void BookDoc(View view)
{
    startActivity(new Intent(getApplicationContext(),BookDocUser.class));
}

    public class CheckReminder extends AsyncTask<Void, Void, String> {

        HttpURLConnection httpURLConnection = null;
        String postdata = "";

        @Override
        protected String doInBackground(Void... voids) {

            try {

                httpURLConnection = (HttpURLConnection) new URL("http://192.168.43.81:16052/PHR/pillremindergetdate.jsp").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes("urid="+userId);
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


                Date curDate = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String DateToStr = format.format(curDate);
                System.out.println(DateToStr);


                JSONArray a=new JSONArray(s);


                for(int i=0;i<a.length();i++)
                {
                    JSONObject o=a.getJSONObject(i);
                    if(DateToStr.equals(o.getString("date")));
                    {

                        buildNotification("Time to buy"+o.getString("name"));

                    }

                }


            } catch (Exception e) {
                Log.e("Exceptionathos-->",e+"");
            }

        }
    }


    private void buildNotification(String noti) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Notifications")
                        .setContentText(noti);

        Intent notificationIntent = new Intent(this, UserHome.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void AccessPhoto(View view)
    {
            startActivity(new Intent(getApplicationContext(),AccessPicUserMain.class));


    }

    public void UploadPdf(View view)
    {
        startActivity(new Intent(getApplicationContext(),AccessPDFUserMain.class));


    }
    public void ViewPriscription(View view)
    {

              startActivity(new Intent(getApplicationContext(),ViewPriscription.class));

    }

    public void PillReminder(View view)
    {

        startActivity(new Intent(getApplicationContext(),PillReminder.class));

    }

}
