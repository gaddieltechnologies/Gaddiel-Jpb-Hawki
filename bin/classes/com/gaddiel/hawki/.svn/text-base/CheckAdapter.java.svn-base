package com.gaddiel.hawki;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CheckAdapter extends BaseAdapter {
	private JSONParser jsonParser = new JSONParser();
	private Context context;
	ArrayList<HashMap<String, String>> MyArrListg;
	
	
	

	public CheckAdapter(Context c, ArrayList<HashMap<String, String>> myList) {
		// super( c, R.layout.activity_column, R.id.rowTextView, );
		// TODO Auto-generated method stub
		context = c;
		MyArrListg = myList;

	}

	public int getCount() {
		// TODO Auto-generated method stub
		String getActivityUrl = "http://hawki-beta.gaddieltech.com/androidphp/getActivities.php";
		try {

			JSONArray data = new JSONArray(jsonParser.getJSONUrl(getActivityUrl));

			MyArrListg = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			for (int i = 0; i < data.length(); i++) {
				JSONObject c = data.getJSONObject(i);

				map = new HashMap<String, String>();
				map.put("Id", c.getString("Id"));
				map.put("Activity_Name", c.getString("Activity_Name"));
				MyArrListg.add(map);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MyArrListg.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_column, null);

		}

		// ColID
		TextView txtID = (TextView) convertView.findViewById(R.id.ColID);
		txtID.setText(MyArrListg.get(position).get("Activity_Id") + ".");

		// ColCode
		TextView txtCode = (TextView) convertView.findViewById(R.id.ColCode);
		txtCode.setText(MyArrListg.get(position).get("Activity_Name"));

		// ColChk
		CheckBox Chk = (CheckBox) convertView.findViewById(R.id.ColChk);
		Chk.setTag(MyArrListg.get(position).get("Activity_Name"));

		return convertView;

	}

}
