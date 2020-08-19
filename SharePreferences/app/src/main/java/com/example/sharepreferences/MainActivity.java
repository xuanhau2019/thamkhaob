package com.example.sharepreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_APPEND;

public class MainActivity extends AppCompatActivity {

    private EditText edtFullNamme, edtPhoneNumber, edtAddress;
    private Button btnSave, btnRead;
    private ListView lvItems;

    private ArrayList<Person> arrayList = new ArrayList<>();

    private Person person;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtFullNamme = (EditText) findViewById(R.id.edt_full_name);
        edtPhoneNumber = (EditText) findViewById(R.id.edit_phone_number);
        edtAddress = (EditText) findViewById(R.id.edit_address);

        lvItems = (ListView) findViewById(R.id.list_item);

        btnSave = (Button) findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName, phoneNumber, address;
                fullName = edtFullNamme.getText().toString();
                phoneNumber = edtPhoneNumber.getText().toString();
                address = edtAddress.getText().toString();

                person = new Person(edtFullNamme.getText().toString(), phoneNumber, address);
                arrayList.add(person);

                try {
                    @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("person", MODE_APPEND);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("fullName",fullName);
                    editor.putString("phoneNumber", phoneNumber);
                    editor.putString("address", address);

                    editor.commit();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRead = (Button) findViewById(R.id.button_read);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value="";
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("person", Context.MODE_PRIVATE);

                value += sharedPreferences.getString("fullName","") + "\n"
                        + sharedPreferences.getString("phoneNumber","") + "\n"
                        + sharedPreferences.getString("address","") + "\n";

                ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                lvItems.setAdapter(adapter);
            }
        });
    }
}