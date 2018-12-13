package com.copordrop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPassword;

    private Button mCreateBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mFirstName = (EditText) findViewById(R.id.firstNameFeild);
        mFirstName.setImeOptions(EditorInfo.IME_ACTION_DONE);

        mLastName = (EditText) findViewById(R.id.lastNameField);
        mLastName.setImeOptions(EditorInfo.IME_ACTION_DONE);

        mEmail = (EditText) findViewById(R.id.createEmailField);
        mEmail.setImeOptions(EditorInfo.IME_ACTION_DONE);

        mPassword = (EditText) findViewById(R.id.createPassField);
        mPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);

        mCreateBtn = (Button) findViewById(R.id.createButton);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

        mProgress = new ProgressDialog(this);
    }

    public void startRegister(){
        final String firstName = mFirstName.getText().toString().trim();
        final String lastName = mLastName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        final boolean isFirstTime = true;

        if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgress.setMessage("Creating Account...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        String user_id = mAuth.getCurrentUser().getUid();

                        DatabaseReference current_user_db = mDatabase.child(user_id);

                        current_user_db.child("first_name").setValue(firstName);
                        current_user_db.child("last_name").setValue(lastName);
                        current_user_db.child("email").setValue(email);
                        current_user_db.child("pass").setValue(password);

                        current_user_db.child("first_time").setValue(isFirstTime);

                        mProgress.dismiss();

                        Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                        mAuth.signOut();

                        Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);

                    } else {

                        mProgress.dismiss();

                        Toast.makeText(RegisterActivity.this, "Error creating account", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        
    }
}
