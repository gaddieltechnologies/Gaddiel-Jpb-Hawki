package com.gaddiel.hawki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// DB Class to perform DB related operations
	DBController controller = new DBController(this);
	SQLiteOperations sqloperation = new SQLiteOperations(this);
	SQLiteoperationEmp sqloperationEmp = new SQLiteoperationEmp(this);
	SQLiteoperationCustomerVisit customerVisitSqlOperation = new SQLiteoperationCustomerVisit(
			this);
	
	// Progress Dialog Object
	ProgressDialog prgDialog;
	AlertDialogManager eAlert = new AlertDialogManager();

	// Check Internet
	ConnectionDetector cd;

	// Alarm pending Intent
	private PendingIntent pendingIntent;

	// JSONParser
	private JSONParser jsonParser = new JSONParser();
	ArrayList<HashMap<String, String>> MyArrListg;
	ArrayList<HashMap<String, String>> MyArrList;
	ArrayList<HashMap<String, String>> customerArrList;
	
	String Emp_Id;
	String Active = null;
	String recordCount;
	String sCustomerId;
	String sCustomer;
	String sPurposeId;
	String sPurpose;
	Spinner spinnercustomer;
	Spinner spinnerpurpose;
	ListView activity;
	private Button btnSubmit, btnReset, btnExit;
	EditText eComment, travelAmount;
	String commentInput, ExpenseAmt;
	String[] dateTimeValues;
	String phonno;
	String body;
	String locations;
	String addressText;
	String addressString;
	long timeval;
	double lat;
	double lng;
	String newtime;
	String loc, latLongString;
	String[] locValue, customerArray, purposeArray;
	CheckBox checkbox;
	String selectedCheckBox;
	String checkboxValues = "";
	PowerManager.WakeLock wl;
	Location location;
	LocationManager locationManager;
	String provider;
	String getCustomerUrl, getPurposeUrl, getActivityUrl;
    String customerAutoCompletee;
    String purposeAutoCompletee;
	AutoCompleteTextView customerAutoComplete = null;
	AutoCompleteTextView purposeAutoComplete = null;
	String follow;
	EditText efollow;
	CheckBox efollowcheck;
    String followupcheck;
    String expenseRef;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("MainActivity: onCreate", "Start");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// do your things, even when screen is off
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");

		// Permission StrictMode
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		/*// Initialize Progress Dialog properties
		prgDialog = new ProgressDialog(getApplicationContext());
		prgDialog.setMessage("Loading Please wait...");
		prgDialog.setCancelable(false);
		prgDialog.show();*/
		// OnClick Submit On Main Screen
		addListenerOnButton();

		btnExit = (Button) findViewById(R.id.button2);
		btnReset = (Button) findViewById(R.id.button3);

		btnReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(MainActivity.this, MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);

			}
		});

		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				onBackPressed();

			}
		});

		// Dialog Box
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		// alert.setIcon(R.drawable.jindal);
		alert.setTitle("Activate Your Mobile");
		alert.setMessage("Enter Employee Id");
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {
				Emp_Id = input.getText().toString().trim();

				if (validateEmployee()) {
					Toast.makeText(
							getBaseContext(),
							"Invalid Employee Id "
									+ "/n Please Contact your Admin Manager",
							Toast.LENGTH_SHORT).show();
					Log.d("Kill", "Killed");
					android.os.Process.killProcess(android.os.Process.myPid());
				}

				addEmployee();
				Toast.makeText(getBaseContext(),
						"Registration Successfull \n Open Gaddiel Hawk-i Application ", Toast.LENGTH_SHORT).show();
				//System.exit(0);
				finish();
				 
				/* Intent i =
				 getBaseContext().getPackageManager().getLaunchIntentForPackage(
				 getBaseContext().getPackageName() );
				 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 startActivity(i);*/
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						android.os.Process.killProcess(android.os.Process
								.myPid());
						dialog.cancel();
					}
				});

		// Get EmployeeID From SQLite
		HashMap resultMap = sqloperationEmp.getEmpValue();
		String empKey = (String) resultMap.get("Emp_Id");
		Log.d("EmpID", "EmpId=" + empKey);
		if (empKey == null) {

			alert.show();

		} else {

			// Check if Internet present
			cd = new ConnectionDetector(getApplicationContext());
			if (!cd.isConnectingToInternet()) {
				// Internet Connection is not present
				eAlert.showAlertDialog(MainActivity.this,
						"Internet Connection Error",
						"Please connect to working Internet connection", false);

				// stop executing code by return
				Log.d("Connection", "Failed");

			} else {

				Log.d("MainActivity: onCreate : Connection",
						"Internet Found on Get URL");
				// TODO: Move this code to AsyncTask
				// fetchMainScreenValuesFromMySql();
				new DownloadFromServerTask().execute();

			} // if there's internet

			BackgroundTasks bg = new BackgroundTasks(getApplicationContext());
			bg.execute();
		}

		Log.d("MainActivity: onCreate", "End");
		
		
	}// oncreate end

	private class DownloadFromServerTask
			extends
			AsyncTask<String, Void, ArrayList<ArrayList<HashMap<String, String>>>> {

		@Override
		protected ArrayList<ArrayList<HashMap<String, String>>> doInBackground(
				String... params) {
			return fetchMainScreenValuesFromMySql();
		}

		@Override
		protected void onPostExecute(
				ArrayList<ArrayList<HashMap<String, String>>> result) {

			// Show the Checkboxes

			activity = (ListView) findViewById(R.id.listView1);

			CheckAdapter chkAdapter = new CheckAdapter(getApplicationContext(),
					result.get(0));
			activity.setAdapter(chkAdapter);
			
			
			// AutoComplete Value For Purpose Name
			purposeAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
			
			SimpleAdapter sAdap;
			sAdap = new SimpleAdapter(MainActivity.this, result.get(1),
					R.layout.activity_purpose, new String[] { "Purpose_Name" },
					new int[] { R.id.textpurposename });
			purposeAutoComplete.setAdapter(sAdap);
			
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
			
			customerAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
			SimpleAdapter simpleCustomerAdapter;
			simpleCustomerAdapter = new SimpleAdapter(MainActivity.this, result.get(2),
					R.layout.activity_customer, new String[] { "Client_Name" },
					new int[] { R.id.textcustomername });
			// spinnercustomer.setAdapter(sAdap);
			customerAutoComplete.setAdapter(simpleCustomerAdapter);
			
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
			
		}

		private ArrayList<ArrayList<HashMap<String, String>>> fetchMainScreenValuesFromMySql() {
			// ****** Get Customer From MySQL To ***************/
			ArrayList<ArrayList<HashMap<String, String>>> returnList = new ArrayList<ArrayList<HashMap<String, String>>>();

			// 1st element of ArrayList is for Checkboxes
			String getActivityUrl = "http://hawki-beta.gaddieltech.com/androidphp/getActivities.php";
			ArrayList<HashMap<String, String>> myArrayList = new ArrayList<HashMap<String, String>>();
			try {

				JSONArray data = new JSONArray(
						jsonParser.getJSONUrl(getActivityUrl));

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
			returnList.add(MyArrListg);
			
			// 2nd element of ArrayList is for PurposeAutoComplete
			String getPurposeUrl = "http://hawki-beta.gaddieltech.com/androidphp/getVistiPurposes.php";
			try {

				JSONArray data = new JSONArray(jsonParser.getJSONUrl(getPurposeUrl));

				MyArrList = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> map;

				for (int i = 0; i < data.length(); i++) {
					JSONObject c = data.getJSONObject(i);

					map = new HashMap<String, String>();
					map.put("Id", c.getString("Id"));
					map.put("Purpose_Name", c.getString("Purpose_Name"));

					MyArrList.add(map);
				}
				

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			returnList.add(MyArrList);	
			// 3rd element of ArrayList is for CustomerAutoComplete
			String getCustomerUrl = "http://hawki-beta.gaddieltech.com/androidphp/getClientDetails.php";
			try {

				JSONArray data = new JSONArray(
						jsonParser.getJSONUrl(getCustomerUrl));

				customerArrList = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> map;

				for (int i = 0; i < data.length(); i++) {
					JSONObject c = data.getJSONObject(i);

					map = new HashMap<String, String>();
					map.put("Id", c.getString("Id"));
					map.put("Client_Name", c.getString("Client_Name"));					
					customerArrList.add(map);

				}
								
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			returnList.add(customerArrList);	
			
			return returnList;

			// //// Get Activity From MySQL To// ListView/////////////////

		}

	} // class DownloadFromServerTask

	// //////////////////////////////////////////////// Add Employee To
	// SQLite//////////////////////////////////////////
	public void addEmployee() {

		HashMap<String, String> queryVal = new HashMap<String, String>();
		queryVal.put("Emp_Id", Emp_Id);
		queryVal.put("Active", Active);

		sqloperationEmp.insertEmp(queryVal);
		Log.d("MainActivity: insert", Emp_Id);

	}

	// Add Customer To////////////
	public void addCustomer() {

		HashMap<String, String> customerQueryVal = new HashMap<String, String>();
		// customerQueryVal.put("Id", "");
		HashMap mapEmp = sqloperationEmp.getEmpValue();
		String Emp_Id = (String) mapEmp.get("Emp_Id");
		customerQueryVal.put("Emp_Id", Emp_Id);
		customerQueryVal.put("Visit_Date", dateTimeValues[0]);
		customerQueryVal.put("Visit_Time", dateTimeValues[1]);		
		customerQueryVal.put("Client_Id", sCustomerId);
		customerQueryVal.put("Purpose_Id", sPurposeId);
		customerQueryVal.put("Visit_Activity", checkboxValues);
		customerQueryVal.put("Expense_Amt", ExpenseAmt);
		customerQueryVal.put("Comment", commentInput);
		customerQueryVal.put("Visit_Rep_LngLat", loc);
		customerQueryVal.put("followupActive", followupcheck);
		customerQueryVal.put("Expense_Ref", expenseRef);	
		// customerQueryVal.put("Loc_Address", locValue[1]);

		customerVisitSqlOperation.insertCustomer(customerQueryVal);
		Log.d("MainActivity: insert", "\n" + sCustomerId + "\n" + sPurposeId
				+ "\n" + commentInput + "\n" + dateTimeValues[0] + "\n"
				+ dateTimeValues[1] + "\n" + loc + "\n" + followupcheck+ "\n"
				+ expenseRef);

	}

	// //////////////////////////////////////////////// Validate And Add
	// Employee To MySQL /////////////////////////////////////
	public boolean validateEmployee() {

		String updateEmpFromMobile = "http://hawki-beta.gaddieltech.com/androidphp/updateEmpFromMobile.php";
		final ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("Emp_Id", Emp_Id));
			Log.d("Empnum: ", ">>>" + Emp_Id);
			String json = jsonParser.makeHttpRequest(updateEmpFromMobile,
					"POST", nameValuePairs);
			Log.d("Response: ", ">>>" + json);

			HashMap<String, String> map;

			JSONObject jsonResponse = new JSONObject(json);
			JSONArray jsonMainNode = jsonResponse.getJSONArray("ListOfArray");

			recordCount = jsonMainNode.getJSONObject(0).getString("rec_count");
			Log.d("reccount", "rec=" + recordCount);
			if (recordCount.equals("0"))
				return true;
			else
				return false;

		}// try
		catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("MainActivity: Exception", "Exception is: " + e.getMessage());
			e.printStackTrace();
		}// catc
		return true;
	}

	// ////////////////////////////////////////// get the selected dropdown list
	// value ////////////////////////////////////////
	public void addListenerOnButton() {
		eComment = (EditText) findViewById(R.id.editTextcomment);
		travelAmount = (EditText) findViewById(R.id.editTextAmount);
		//efollow = (EditText) findViewById(R.id.followeditText);
		efollowcheck = (CheckBox) findViewById(R.id.followcheckBox);
		// spinnercustomer = (Spinner) findViewById(R.id.spinner1);
		btnSubmit = (Button) findViewById(R.id.button1);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sCustomerId==null) {					
					Log.d("showalert","Client= "+sCustomerId);
					customerAutoComplete.setError("Invalid Client Name");					
		    	    return;
		    	}
				
				
				if (sPurposeId==null) {							
					Log.d("showalert","Purpose= "+sPurposeId);
					purposeAutoComplete.setError("Invalid Purpose Of Visit");
		    	    return;
		    	}
				
				// Get Location
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);
				criteria.setAltitudeRequired(false);
				criteria.setBearingRequired(false);
				criteria.setCostAllowed(true);
				criteria.setPowerRequirement(Criteria.POWER_LOW);
				provider = locationManager.getBestProvider(criteria, true);
				location = locationManager.getLastKnownLocation(provider);
				loc = updateWithNewLocation(location);
				locationManager.requestLocationUpdates(provider, 2000, 10,
						locationListener);

				// locValue = loc.split(";");

				int count = activity.getAdapter().getCount();

				for (int i = 0; i < count; i++) {
					LinearLayout itemLayout = (LinearLayout) activity
							.getChildAt(i); // Find by under LinearLayout
					checkbox = (CheckBox) itemLayout.findViewById(R.id.ColChk);
					if (checkbox.isChecked()) {
						// Log.d("Item "+String.valueOf(i),
						// checkbox.getTag().toString());
						if (checkboxValues.isEmpty()) {
							checkboxValues = checkbox.getTag().toString();
						} else {
							checkboxValues = checkboxValues + "|"
									+ checkbox.getTag().toString();
						}

						Log.d("Itemlist ", checkboxValues);

					}
				}

				commentInput = eComment.getText().toString().trim();
				ExpenseAmt = travelAmount.getText().toString().trim();
				HashMap mapEmp = sqloperationEmp.getEmpValue();
				String Emp_Idi = (String) mapEmp.get("Emp_Id");
				DateFormat dtFormat = new SimpleDateFormat("yyMMdd-HHmm");
				Date dt = new Date();
				String dTime = dtFormat.format(dt);
				if (ExpenseAmt.isEmpty() || (ExpenseAmt.equals("0"))) {
					expenseRef = "0";
				} else {
					expenseRef = Emp_Idi + "-" + dTime;
				}
				if (efollowcheck.isChecked()){
					
					//efollowcheck.getTag().toString();
					followupcheck = "Y";
				}else{
					followupcheck = "N";
				}
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd-HH:mm:ss");
				Date date = new Date();
				String dateTime = dateFormat.format(date);
				System.out.println(dateFormat.format(date));
				dateTimeValues = dateTime.split("-");
				Log.d("Print LngLat", "" + loc);
				
				addCustomer();
				// Check if Internet present
				if (cd.isConnectingToInternet()) {

					try {
						Log.d("MainActivity: OnClick Submit",
								"Internet Found - Post the values to the server");

						controller.postEmpLocTrackerValue();
						customerVisitSqlOperation.putEmpCustVisitValue();
					} catch (Exception e) {
						Log.e("MainActivity: OnClick Submit Exception Error",
								"server Not found  " + e.toString());
					}
				}
				Toast.makeText(getBaseContext(),
						"Report Submitted ", Toast.LENGTH_SHORT).show();
				if (expenseRef.equals("0")) {
					Toast.makeText(getBaseContext(),
							"No expense reported", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(getBaseContext(),
							"Expense Reference: " + expenseRef,
							Toast.LENGTH_SHORT).show();
				}
				clear();
				finish();
			}

		});

	}

	public void clear() {

		customerAutoComplete.setText("");
		purposeAutoComplete.setText("");
		eComment.setText("");
		travelAmount.setText("");
		efollowcheck.setChecked(false);

		checkboxValues="";

		int count = activity.getAdapter().getCount();

		for (int i = 0; i < count; i++) {
			LinearLayout itemLayout = (LinearLayout) activity
					.getChildAt(i); // Find by under LinearLayout
			checkbox = (CheckBox) itemLayout.findViewById(R.id.ColChk);
			checkbox.setChecked(false);			
		}

	}

	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider) {
			updateWithNewLocation(null);
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	private String updateWithNewLocation(Location location) {

		String latLongString;

		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			// latLongString = "Location"+lat+","+lng;
			latLongString = Double.toString(lat) + "," + Double.toString(lng);

		} else {
			latLongString = "NULL,NULL";
		}

		return latLongString;
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.refresh) {

			// controller.syncSQLiteMySQLDB();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MainActivity.this);

		// Setting Dialog Title
		alertDialog.setTitle("Confirm Exit...");

		// Setting Dialog Message
		alertDialog.setMessage("Do you want to Exit?");

		// Setting Icon to Dialog
		// alertDialog.setIcon(R.drawable.delete);

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Toast.makeText(Tracked_Mobiles.this,
						// "User Has Been Logout ", Toast.LENGTH_LONG).show();
						Log.d("MainActivity:", "Exit");
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						// userFunctions.logoutUser(getApplicationContext());
						finish();

					}
				});

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();

	}

}
