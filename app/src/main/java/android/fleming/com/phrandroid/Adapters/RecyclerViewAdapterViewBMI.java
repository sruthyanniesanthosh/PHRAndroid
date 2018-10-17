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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecyclerViewAdapterViewBMI extends RecyclerView.Adapter<RecyclerViewAdapterViewBMI.MyViewHolder> {

    Context context;
    JSONArray jrr;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView edWeight,edHeight,edBMI,edStatus,edDate;

        public MyViewHolder(View view)
        {
            super(view);
            edWeight=(TextView) view.findViewById(R.id.edWeight);
            edHeight=(TextView)view.findViewById(R.id.edHeight);
            edBMI=(TextView)view.findViewById(R.id.edBMI);
            edStatus=(TextView)view.findViewById(R.id.edStatus);
            edDate=(TextView)view.findViewById(R.id.edDate);
        }

    }

    public RecyclerViewAdapterViewBMI(JSONArray jrr, Context context)
    {

        this.jrr=jrr;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_bmi,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterViewBMI.MyViewHolder holder, int position) {
try {

    final JSONObject obj = jrr.getJSONObject(position);
    holder.edWeight.setText(obj.getString("weight"));
    holder.edHeight.setText(obj.getString("height"));
    holder.edBMI.setText(obj.getString("bmi"));
    holder.edStatus.setText(obj.getString("status"));
    holder.edDate.setText(obj.getString("date"));

}catch (Exception e)
{
    Log.e("Error at Recylcerview",e+"");
}
    }

    @Override
    public int getItemCount() {
        return jrr.length();
    }
}
