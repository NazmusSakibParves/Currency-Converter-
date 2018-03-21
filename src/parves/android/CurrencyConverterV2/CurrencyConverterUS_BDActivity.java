package parves.android.CurrencyConverterV2;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CurrencyConverterUS_BDActivity extends Activity {
    /** Called when the activity is first created. */
	
    MyBroadcastReceiver myBroadcastReceiver;
	public String givenValue;
	public Boolean currency;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        myBroadcastReceiver = new MyBroadcastReceiver();
    }
    
    
    
    @Override
	protected void onPause() {
		
		super.onPause();
	}


	@Override
	protected void onResume() {
		
		super.onResume();
		IntentFilter filter = new IntentFilter(MyBroadcastReceiver.RESPONSE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(myBroadcastReceiver, filter);
	}



	public void bdt(View v){
		currency = true;
		EditText editText = (EditText)findViewById(R.id.editText);
		givenValue = editText.getText().toString();
		Intent i = new Intent(CurrencyConverterUS_BDActivity.this, HttpConnections.class);
		i.putExtra(HttpConnections.GIVENVALUE, givenValue);
		i.putExtra(HttpConnections.TYPE, currency);
		startService(i);
	}
	
	public void usd(View v){
		currency = false;
		EditText editText = (EditText)findViewById(R.id.editText);
		givenValue = editText.getText().toString();
		Intent i = new Intent(CurrencyConverterUS_BDActivity.this, HttpConnections.class);
		i.putExtra(HttpConnections.GIVENVALUE, givenValue);
		i.putExtra(HttpConnections.TYPE, currency);
		startService(i);
		
	}
	


	public class MyBroadcastReceiver extends BroadcastReceiver{

		public static final String RESPONSE = "parves.android.CurrencyConverterV2.intent.action.RESPONSE";
		//public static final String RESPONSE = "null";

		@Override
		public void onReceive(Context context, Intent i) {
			String convertedValue = i.getStringExtra(HttpConnections.VALUE);
			EditText editText = (EditText)findViewById(R.id.editText);
			editText.setText(convertedValue);
			
		}
}
	
}