package android.fleming.com.phrandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.fleming.com.phrandroid.Activities.ViewImage;
import android.fleming.com.phrandroid.R;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ViewPDFAdapter extends RecyclerView.Adapter<ViewPDFAdapter.MyViewHolder> {
Context context;
JSONArray array;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView ptdes;

        public MyViewHolder(View itemView) {
            super(itemView);
            ptdes=(TextView)itemView.findViewById(R.id.desCriptionPdf);

        }
    }

    public ViewPDFAdapter(Context context, JSONArray array)
    {

        this.array=array;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_pdf,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewPDFAdapter.MyViewHolder holder, int position) {

        try{

            final JSONObject obj = array.getJSONObject(position);
            holder.ptdes.setText(obj.getString("des"));

            holder.ptdes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context c = v.getContext();


                    try {

                        File file = new File(obj.getString("file"));
                        Intent target = new Intent(Intent.ACTION_VIEW);
                        target.setDataAndType(Uri.fromFile(file),"application/pdf");
                        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                        Intent intent = Intent.createChooser(target, "Open File");
                        c.startActivity(intent);


                    } catch (JSONException e) {
                        Log.e("JSONException..>",e+"");
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
        return array.length();
    }
}
