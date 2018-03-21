package parves.android.CurrencyConverterV2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import parves.android.CurrencyConverterV2.CurrencyConverterUS_BDActivity.MyBroadcastReceiver;
import android.app.IntentService;
import android.content.Intent;

public class HttpConnections extends IntentService {

	public static final String GIVENVALUE = "myValue";
	public static final String TYPE = "type";
	public static final String VALUE = "convertedValue";
	public String val;
	public Boolean currency;
	private String rate = "";
	private String DataString = "";
	int a,b;
	private String[] convRate = new String[2];
	
	public HttpConnections() {
		super("Connection");
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {

			currency = intent.getExtras().getBoolean(HttpConnections.TYPE);
			val = intent.getExtras().getString(HttpConnections.GIVENVALUE);
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet("http://www.hrhafiz.com/converter/index.php");

			HttpResponse response = httpclient.execute(httpget);

			InputStream content = response.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(
            new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                DataString += s;
            }
            int i=0;
            for(String temp:DataString.split(",", 2)){
            	convRate[i] = temp;
            	i++;
            }
            
            if(currency){
            	rate = convRate[1].substring(convRate[1].indexOf(":") + 1, convRate[1].indexOf("}"));
            	val = String.valueOf(Double.valueOf(val)*Double.valueOf(rate));
            	
            }
            else{
            	rate = convRate[0].substring(convRate[0].indexOf(":") + 1, convRate[0].indexOf("}"));
            	val = String.valueOf(Double.valueOf(val)*Double.valueOf(rate));
            	
            }
			
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(MyBroadcastReceiver.RESPONSE);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(VALUE,val);
		sendBroadcast(broadcastIntent);
		
	}

}
