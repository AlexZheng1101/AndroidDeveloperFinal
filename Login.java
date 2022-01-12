package com.example.orderfood;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.Calendar;

public class Login extends AppCompatActivity {
    EditText account,password;
    String acc,pw;
    Button login,clear,register;
    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;
    public static String[] AccountArray,AccountID,PasswordArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account =(EditText) findViewById(R.id.account);
        password =(EditText) findViewById(R.id.password);

        login=(Button) findViewById(R.id.button_signin);
        clear=(Button)findViewById(R.id.button_clear);
        register=(Button)findViewById(R.id.button_register);

        sqlDataBaseHelper = new SqlDataBaseHelper(this,DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /* Intent intent=new Intent();
                intent.setClass(Login.this,MainActivity.class);
                startActivity(intent);
                finish(); */
                acc=account.getText().toString();
                pw=password.getText().toString();

                Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);
                AccountArray = new String[c.getCount()];
                AccountID = new String[c.getCount()];
                PasswordArray = new String[c.getCount()];
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    AccountID[i] = c.getString(0);
                    AccountArray[i] = c.getString(1);
                    PasswordArray[i] = c.getString(2);
                    c.moveToNext();
                }


                if (Arrays.asList(AccountArray).contains(acc)){
                    if(Arrays.asList(PasswordArray).contains(pw)){
                        Intent intent =new Intent();
                        intent.setClass(Login.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(Login.this,"Password is wrong",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Login.this,"Please register first~ Click register button",Toast.LENGTH_LONG).show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account.setText(null);
                password.setText(null);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id;
                acc=account.getText().toString();
                pw=password.getText().toString();

                Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);
                AccountArray = new String[c.getCount()];
                AccountID = new String[c.getCount()];
                PasswordArray = new String[c.getCount()];
                c.moveToFirst();
                for(int i=0;i<c.getCount();i++){
                    AccountID[i] = c.getString(0);
                    AccountArray[i] = c.getString(1);
                    PasswordArray[i] = c.getString(2);
                    c.moveToNext();
                }
                if(!Arrays.asList(AccountArray).contains(acc)) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("account", acc);
                    contentValues.put("password", pw);
                    id = db.insert(DataBaseTable, null, contentValues);
                    Toast.makeText(Login.this, "註冊成功", Toast.LENGTH_LONG).show();
                    //放註冊按鈕功能處
                }else{
                    Toast.makeText(Login.this, "該帳號已存在", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}