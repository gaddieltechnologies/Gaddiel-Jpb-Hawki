/*import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

private void fetchMainScreenValuesFromMySql() {
		// ****** Get Customer From MySQL To ***************/
/*
		getCustomerUrl = "http://gaddieltech.com/hawki/getClientDetails.php";
		try {

			JSONArray data = new JSONArray(
					jsonParser.getJSONUrl(getCustomerUrl));

			final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			for (int i = 0; i < data.length(); i++) {
				JSONObject c = data.getJSONObject(i);

				map = new HashMap<String, String>();
				map.put("Id", c.getString("Id"));
				map.put("Client_Name", c.getString("Client_Name"));
				// map.put("phone", c.getString("phone"));
				MyArrList.add(map);

			}

			customerAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
			SimpleAdapter sAdap;
			sAdap = new SimpleAdapter(MainActivity.this, MyArrList,
					R.layout.activity_customer, new String[] { "Client_Name" },
					new int[] { R.id.textcustomername });
			// spinnercustomer.setAdapter(sAdap);
			customerAutoComplete.setAdapter(sAdap);
			// sCustomer = spinnercustomer.getSelectedItem().toString();
			customerAutoComplete
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							String sCustomervalue1 = parent.getItemAtPosition(
									position).toString();
							String[] sCustomervalue = sCustomervalue1
									.split(",");
							Log.d("MainActivity: ", "AutoComplete Customer ="
									+ sCustomervalue1);

							int startIndex = sCustomervalue[1].indexOf('=');
							int endIndex = sCustomervalue[1].indexOf('}');
							sCustomerId = sCustomervalue[1].substring(
									startIndex + 1, endIndex);

							customerArray = sCustomervalue[0].split("=");
							customerAutoComplete.setText(customerArray[1]);
							Log.d("AutoComplete", "" + customerArray[1]);
							Log.d("AutoComplete", sCustomerId);
						}

					});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ****** Get Purpose From MySQL To ***************/
/*		getPurposeUrl = "http://gaddieltech.com/hawki/getVistiPurposes.php";
		try {

			JSONArray data = new JSONArray(jsonParser.getJSONUrl(getPurposeUrl));

			final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			for (int i = 0; i < data.length(); i++) {
				JSONObject c = data.getJSONObject(i);

				map = new HashMap<String, String>();
				map.put("Id", c.getString("Id"));
				map.put("Purpose_Name", c.getString("Purpose_Name"));

				MyArrList.add(map);
			}
			purposeAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
			// spinnerpurpose = (Spinner) findViewById(R.id.spinner2);

			SimpleAdapter sAdap;
			sAdap = new SimpleAdapter(MainActivity.this, MyArrList,
					R.layout.activity_purpose, new String[] { "Purpose_Name" },
					new int[] { R.id.textpurposename });
			purposeAutoComplete.setAdapter(sAdap);
			// sPurpose = spinnerpurpose.getSelectedItem().toString();

			purposeAutoComplete
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							// sCustomerId =
							// parent.getItemAtPosition(0).toString();
							String sPurposevalue1 = parent.getItemAtPosition(
									position).toString();
							String[] sPurposevalue = sPurposevalue1.split(",");
							Log.d("MainActivity: ", "Purpose=" + sPurposevalue1);

							int startIndex = sPurposevalue[1].indexOf('=');
							int endIndex = sPurposevalue[1].indexOf('}');
							sPurposeId = sPurposevalue[1].substring(
									startIndex + 1, endIndex);

							purposeArray = sPurposevalue[0].split("=");
							purposeAutoComplete.setText(purposeArray[1]);
							Log.d("MainActivity: ", "AutoComplete="
									+ purposeArray[1]);

						}
					});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// //// Get Activity From MySQL To// ListView/////////////////

		activity = (ListView) findViewById(R.id.listView1);

		activity.setAdapter(new CheckAdapter(this));

	}
*/