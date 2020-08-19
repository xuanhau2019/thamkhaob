package com.example.internalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {

    private EditText edtFullNamme, edtPhoneNumber, edtAddress;
    private Button btnSave, btnRead, btnDelete;
    private ListView lvItems;

    private ArrayList<Person> arrayList = new ArrayList<>();

    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                save();
            }
        });

        btnRead = (Button) findViewById(R.id.button_read);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read();
            }
        });

        btnDelete = (Button) findViewById(R.id.button_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    public void delete() {
        String file = "person.txt";
        deleteFile(file);
    }

    public void read() {
        String file = "person.txt";
        try {
            ArrayList list = new ArrayList();

            String data = ""; int c;
            FileInputStream fin = openFileInput(file);
//            while((c = fin.read())!=-1){
//                data += Character.toString((char)c);
//
//            }
//            list.add(data);
//            fin.close();
//            lvItems.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));

            BufferedReader br = new BufferedReader(new InputStreamReader(fin)); //Đọc văn bản trong file "person.txt" dùng để đọc dữ liệu theo từng dòng
            StringBuilder builder = new StringBuilder(1000); //Tạo ra một chuỗi Builder với độ dài chỉ định là 1000
            while ((data = br.readLine()) != null){
                //builder.append(data).append("\n");
                list.add(data);
                data = "";
            }

            lvItems.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list));

        }catch (Exception e){
            Toast.makeText(this, "Read fail", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    public void save() {
        String fullName, phoneNumber, address;
        fullName = edtFullNamme.getText().toString();
        phoneNumber = edtPhoneNumber.getText().toString();
        address = edtAddress.getText().toString();

        person = new Person(fullName, phoneNumber, address);
        arrayList.add(person);

        String file = "person.txt";
        try {
            // Mở file tên person và cập nhật nội dung trong file, tạo file nếu chưa tồn tại
            FileOutputStream fout = openFileOutput(file,Context.MODE_APPEND);

            String data= "";
            data += person;

            fout.write(data.getBytes()); //Ghi dữ liệu "data" xuống file theo từng byte.
            fout.close();//Đóng file
        }catch (Exception e){
            Toast.makeText(this, "Add fail", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}