/**
 * This is the activity for creating an account
 * This is first the UI part, signup/login screen
 */

package com.classproj.placeit;

import HTTP.RequestReceiver;
import HTTP.WebUserService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);
		
		final EditText userName = (EditText) findViewById(R.id.enterUserName);
		final EditText pass = (EditText) findViewById(R.id.enterPass);
		final EditText confirmPass = (EditText) findViewById(R.id.enterConfirmPass);
		
		Button createAccountBtn = (Button) findViewById(R.id.createAcctBtn);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Account Created");
		
		
		createAccountBtn.setOnClickListener(new OnClickListener() {	
			
			public void onClick(final View v){
				
				String userNameText = userName.getText().toString();
				String passText = pass.getText().toString();
				String confirmText = confirmPass.getText().toString();
				
				if (userNameText.matches("") || passText.matches("") || confirmText.matches("")){
					Toast.makeText(CreateAccountActivity.this, 
							"One or more fields are blank", Toast.LENGTH_SHORT).show();
				}
				else{
					if(!passText.matches(confirmText)){
						Toast.makeText(CreateAccountActivity.this, 
								"Passwords do not match", Toast.LENGTH_SHORT).show();
					}
					else{
						WebUserService service = new WebUserService();
						service.signup(userNameText, passText, new RequestReceiver(){

							@Override
							public void receiveTask(String s) {
								Intent intent = new Intent(v.getContext(), Login.class);
								startActivity(intent);	
								finish();
							}
							
						});

					}
					
				}
				
			}
		});
		
	}
	

}
