package com.relldesigns.quizmerika;

import java.io.InputStream;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;



public class MainActivity extends Activity {

	//JSON Object and Array declared globally
	JSONObject myJSON_object;
	JSONArray myJSON_array;

	//array of integers used to get a random arrangement of state objects 
	//shuffle this array instead of the 
	int[] indexArray;

	//array of id's for the 5 TextViews
	int[] tv_ids = {R.id.tvState0, R.id.tvState1, R.id.tvState2, R.id.tvState3, R.id.tvState4};
	//array of id's for the 5 EditTexts
	int[] et_ids = {R.id.etCapital0, R.id.etCapital1, R.id.etCapital2, R.id.etCapital3, R.id.etCapital4};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//read JSON file as shown previously
		try{
			//http://stackoverflow.com/questions/6420293/reading-android-raw-text-file
			InputStream is = getResources().openRawResource(R.raw.states_info);
			byte[] buffer = new byte[is.available()];
			while (is.read(buffer) != -1);

			String myText = new String(buffer);

			//http://stackoverflow.com/questions/9605913/how-to-parse-json-in-android
			//extract the main (top-level) JSON object from the file read above
			myJSON_object = new JSONObject(myText);

			//the main JSON object is/includes an array called employees
			//extract that array
			myJSON_array = myJSON_object.getJSONArray("states");

			//make indexArray the same size as the JSON Array
			//assign it values starting at 0
			indexArray = new int[myJSON_array.length()];
			for(int i=0; i<indexArray.length; i++){
				indexArray[i]=i;
			}
		}
		catch(Exception e){
			Toast.makeText(getBaseContext(), "Problem with file", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}  

		startQuiz();

		Button btnNewQuiz = (Button) findViewById(com.relldesigns.quizmerika.R.id.btnNewQuiz);
		btnNewQuiz.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startQuiz();
			}

			});

		Button btnGrade = (Button) findViewById(com.relldesigns.quizmerika.R.id.btnGrade);
		btnGrade.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				gradeQuiz();

			}});
	}

	//**********************************************************************************************
	public void startQuiz(){

		//shuffle index Array
		int i; 
		int j;
		int swap;
		Random rnd = new Random();

		for(i=0; i< indexArray.length; i++){
			j = rnd.nextInt(indexArray.length);
			swap = indexArray[i];
			indexArray[i]=indexArray[j];
			indexArray[j]= swap;
		} // end for

		//get state name from JSON array and put shuffled state names in Edit Texts 
		for (i=0; i < 5; i++){
			try{
				//get an individual element of the JSON array
				JSONObject aJSON_element = myJSON_array.getJSONObject(indexArray[i]);
				//get the individual properties of the JSON element
				String state = aJSON_element.getString("stateName");
			//	String capital = aJSON_element.getString("stateCapital");

				//int myId = getResources().getIdentifier("tvState"+i, "layout", getPackageName());
				//Toast.makeText(getBaseContext(), Integer.toString(myId), Toast.LENGTH_SHORT).show();
				//Toast.makeText(getBaseContext(), Integer.toString(R.id.tvState0), Toast.LENGTH_SHORT).show();

				TextView tvState = (TextView) findViewById(tv_ids[i]) ;
				tvState.setText(state);

				//declare, find and cast various edit texts 
				EditText etCapital0 = (EditText) findViewById(com.relldesigns.quizmerika.R.id.etCapital0); 
				EditText etCapital1 = (EditText) findViewById(com.relldesigns.quizmerika.R.id.etCapital1); 
				EditText etCapital2 = (EditText) findViewById(com.relldesigns.quizmerika.R.id.etCapital2); 
				EditText etCapital3 = (EditText) findViewById(com.relldesigns.quizmerika.R.id.etCapital3); 
				EditText etCapital4 = (EditText) findViewById(com.relldesigns.quizmerika.R.id.etCapital4); 
				TextView tvResultResult = (TextView) findViewById(com.relldesigns.quizmerika.R.id.tvResult);

				// code to clear out any of previous text of the the EditTexts
				etCapital0.setText("");
				etCapital1.setText("");
				etCapital2.setText("");
				etCapital3.setText("");
				etCapital4.setText("");
				tvResultResult.setText("Result: ");

			} // end try
			catch (JSONException e) 
			{
				Toast.makeText(getBaseContext(), "Dude, you have to know what the JSON looks like to parse it", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		} // end catch 
	} // end FOR LOOP

	public void gradeQuiz(){
		int i; 
		int rightCapital = 0;

		for (i=0; i < 5; i++){
			try{
				//get an individual element of the JSON array
				JSONObject aJSON_element = myJSON_array.getJSONObject(indexArray[i]);
				//get the individual properties of the JSON element
			//	String state = aJSON_element.getString("stateName");
				String capital = aJSON_element.getString("stateCapital");

				//declare, find and cast various edit texts 
				EditText etCapital0 = (EditText) findViewById(com.relldesigns.quizmerika.R.id.etCapital0); 
				EditText etCapital1 = (EditText) findViewById(com.relldesigns.quizmerika.R.id.etCapital1); 
				EditText etCapital2 = (EditText) findViewById(com.relldesigns.quizmerika.R.id.etCapital2); 
				EditText etCapital3 = (EditText) findViewById(com.relldesigns.quizmerika.R.id.etCapital3); 
				EditText etCapital4 = (EditText) findViewById(com.relldesigns.quizmerika.R.id.etCapital4); 

				//convert them to a string
				String capitalStr0 = etCapital0.getText().toString();
				String capitalStr1 = etCapital1.getText().toString();
				String capitalStr2 = etCapital2.getText().toString();
				String capitalStr3 = etCapital3.getText().toString();
				String capitalStr4 = etCapital4.getText().toString();

				Toast.makeText(getBaseContext(), capital, Toast.LENGTH_SHORT).show();

				if (capital.equalsIgnoreCase(capitalStr0)){
					rightCapital++;
				}
				if (capital.equalsIgnoreCase(capitalStr1)){
					rightCapital++;
				}
				if (capital.equalsIgnoreCase(capitalStr2)){
					rightCapital++;
				}
				if (capital.equalsIgnoreCase(capitalStr3)){
					rightCapital++;
				}
				if (capital.equalsIgnoreCase(capitalStr4)){
					rightCapital++;
				}
			}
			catch (JSONException e)
			{
				Toast.makeText(getBaseContext(), "Dude, you have to know what the JSON looks like to parse it", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}

		String correctnessString = "";

		correctnessString = "Result: " + rightCapital + " Out of 5 correct";
		TextView tvResultResult = (TextView) findViewById(com.relldesigns.quizmerika.R.id.tvResult);

		tvResultResult.setText(correctnessString);

	}//end onClick method

} //end class
