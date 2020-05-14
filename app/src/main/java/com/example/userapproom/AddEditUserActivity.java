package com.example.userapproom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditUserActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.noteapproom.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.userapproom.EXTRA_NAME";
    public static final String EXTRA_PHONE = "com.example.userapproom.EXTRA_PHONE";
    public static final String EXTRA_EMAIL = "com.example.userapproom.EXTRA_EMAIL";

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        nameEditText = findViewById(R.id.name_et);
        phoneEditText = findViewById(R.id.phone_et);
        emailEditText = findViewById(R.id.email_et);
        saveButton = findViewById(R.id.add_user_button);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit user");
            saveButton.setText(R.string.edit_user);

            nameEditText.setText(intent.getStringExtra(EXTRA_NAME));
            phoneEditText.setText(intent.getStringExtra(EXTRA_PHONE));
            emailEditText.setText(intent.getStringExtra(Intent.EXTRA_EMAIL));
        }else {
            setTitle("Add user");
            saveButton.setText(R.string.add_user);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();


                if (name.isEmpty()){
                    Toast.makeText(AddEditUserActivity.this, "Please enter user info", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent replyIntent = new Intent();

                replyIntent.putExtra(EXTRA_NAME, name);
                replyIntent.putExtra(EXTRA_PHONE, phone);
                replyIntent.putExtra(EXTRA_EMAIL, email);

                int id = getIntent().getIntExtra(EXTRA_ID , -1);

                if (id != -1){
                    replyIntent.putExtra(EXTRA_ID, id);
                }

                setResult(RESULT_OK,replyIntent);
                finish();
            }
        });
    }
}
