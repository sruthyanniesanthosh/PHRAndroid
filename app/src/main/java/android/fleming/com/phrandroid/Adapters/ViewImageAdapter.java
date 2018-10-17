package android.fleming.com.phrandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.fleming.com.phrandroid.Activities.DocPriscription;
import android.fleming.com.phrandroid.Activities.ViewImage;
import android.fleming.com.phrandroid.R;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewImageAdapter extends RecyclerView.Adapter<ViewImageAdapter.MyViewHolder> {
Context context;
JSONArray array;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView ptdes;
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            ptdes=(TextView)itemView.findViewById(R.id.desCription);
            img=(ImageView) itemView.findViewById(R.id.imagePic);
        }
    }

    public ViewImageAdapter(Context context, JSONArray array)
    {

        this.array=array;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_image,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewImageAdapter.MyViewHolder holder, int position) {

        try{

            final JSONObject obj = array.getJSONObject(position);
            holder.ptdes.setText(obj.getString("des"));
            holder.img.setImageBitmap(BitmapFactory.decodeFile(obj.getString("file")));
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context c = v.getContext();
                    Intent intent=new Intent(context, ViewImage.class);
                    try {
                        intent.putExtra("path",obj.getString("file"));
                    } catch (JSONException e) {
                        Log.e("JSONException..>",e+"");
                    }
                    c.startActivity(intent);

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
