package android.fleming.com.phrandroid.Adapters;


import android.content.Context;

import android.fleming.com.phrandroid.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONAdapter extends BaseAdapter implements ListAdapter {


    String ids,b_id;
    TextView name;
    private final Context activity;
    private final JSONArray jsonArray;
    public JSONAdapter(Context activity, JSONArray jsonArray) {
        assert activity != null;
        assert jsonArray != null;

        this.jsonArray = jsonArray;
        this.activity = activity;
    }


    @Override public int getCount() {

        return jsonArray.length();
    }

    @Override public JSONObject getItem(int position) {

        return jsonArray.optJSONObject(position);
    }

    @Override public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);

        return jsonObject.optLong("id");
    }

    @Override public View getView(int position, View view, ViewGroup parent) {
        if (view == null)
            view =  LayoutInflater.from(activity).inflate(R.layout.view_hospital_user, null);

        JSONObject jsonObject = getItem(position);
        name=(TextView)view.findViewById(R.id.hosName);


        try {
            ids=jsonObject.getString("id");
            name.setText(jsonObject.getString("name"));


            view.setTag(ids);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }


}