package com.classproj.placeit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		final EditText userName = (EditText)findViewById(R.id.userName);
		final TextView welcome = (TextView) findViewById(R.id.textView1);
		final EditText pass = (EditText)findViewById(R.id.pass);
		final TextView intro = (TextView) findViewById(R.id.textView2);
		
		Button loginBtn = (Button) findViewById(R.id.loginBtn);
		
		Button createAcctBtn = (Button) findViewById(R.id.createAcctBtn);
		createAcctBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v){
				Intent intent = new Intent(v.getContext(), CreateAccountActivity.class);
				startActivity(intent);
				
			}
		});
		
		loginBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v){
				String userNameText = userName.getText().toString();
				String passText = pass.getText().toString();
				
				if (userNameText.matches("") || passText.matches("") ){
					Toast.makeText(Login.this, 
							"One or more fields are blank", Toast.LENGTH_SHORT).show();
				}
				else{
					
						Intent intent = new Intent(v.getContext(), MainActivity.class);
						startActivity(intent);
					
				}
				
			}
		});
	}



}