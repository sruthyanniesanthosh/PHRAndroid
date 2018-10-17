package android.fleming.com.phrandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.fleming.com.phrandroid.Activities.DocPriscription;
import android.fleming.com.phrandroid.R;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    JSONArray jrr;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView ptName,ptDate;

        public MyViewHolder(View view)
        {
            super(view);
            ptName=(TextView) view.findViewById(R.id.ptName);
            ptDate=(TextView)view.findViewById(R.id.ptDate);
        }

    }

    public RecyclerViewAdapter(JSONArray jrr,Context context)
    {

        this.jrr=jrr;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_doc_view_patinats,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, int position) {
try{

    final JSONObject obj = jrr.getJSONObject(position);
    holder.ptName.setText(obj.getString("name"));
    holder.ptDate.setText(obj.getString("date"));
    holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Context c = v.getContext();
        Intent intent=new Intent(context, DocPriscription.class);
        try {
            intent.putExtra("ID",obj.getString("uid"));
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
        return jrr.length();
    }
}
