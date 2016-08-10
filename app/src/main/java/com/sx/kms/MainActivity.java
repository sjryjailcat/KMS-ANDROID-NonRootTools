package com.sx.kms;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import java.io.OutputStream;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
public class MainActivity extends Activity {
	private String app_path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app_path = getApplicationContext().getFilesDir().getAbsolutePath();
		varifyFile(getApplicationContext(), "kms");
		setContentView(R.layout.main);
	}
	private void varifyFile(Context context, String fileName) {


        try {
        	context.openFileInput(fileName);
        } catch (FileNotFoundException notfoundE) {
            try {
                copyFromAssets(context, fileName, fileName);
                String script = "chmod 777 " + app_path + "/" + fileName;
                exe(script);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	private void copyFromAssets(Context context, String source,
								String destination) throws IOException {
		InputStream is = context.getAssets().open(source);
		int size = is.available();
		byte[] buffer = new byte[size];
		is.read(buffer);
		is.close();
		FileOutputStream output = context.openFileOutput(destination,
														 Context.MODE_WORLD_READABLE);
		output.write(buffer);
		output.close();
	}
	private void exe(String cmd) {
		Process process = null;  
        try {  
            process = Runtime.getRuntime().exec(cmd); 
          InputStream is = process.getInputStream();
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
	}

}

