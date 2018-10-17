package android.fleming.com.phrandroid.Adapters;

import android.content.Context;
import android.fleming.com.phrandroid.R;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewHistoryAdapter extends RecyclerView.Adapter<ViewHistoryAdapter.MyViewHolder> {
Context context;
JSONArray array;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView ptName,ptDate,ptAge,ptBook,ptSymptoms,ptPrescription;
        public MyViewHolder(View itemView) {
            super(itemView);

            ptName=(TextView)itemView.findViewById(R.id.ptName);
            ptDate=(TextView)itemView.findViewById(R.id.ptDate);
            ptAge=(TextView)itemView.findViewById(R.id.ptAge);
            ptBook=(TextView)itemView.findViewById(R.id.ptBook);
            ptSymptoms=(TextView)itemView.findViewById(R.id.ptSymptoms);
            ptPrescription=(TextView)itemView.findViewById(R.id.ptPrescription);

        }
    }

    public ViewHistoryAdapter(Context context,JSONArray array)
    {

        this.array=array;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layour_view_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHistoryAdapter.MyViewHolder holder, int position) {

        try{

            final JSONObject obj = array.getJSONObject(position);
            holder.ptName.setText(obj.getString("name"));
            holder.ptDate.setText(obj.getString("datereg"));
            holder.ptAge.setText(obj.getString("age"));
            holder.ptBook.setText(obj.getString("datebook"));
            holder.ptSymptoms.setText(obj.getString("symptoms"));
            holder.ptPrescription.setText(obj.getString("prescription"));

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
