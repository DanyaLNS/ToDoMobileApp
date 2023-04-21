package mirea.ck.anikin.todo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mirea.ck.anikin.todo.utils.DatabaseHandler;

public class RegisterActivity extends AppCompatActivity {
    EditText username, password, repassword;
    Button signup, signin;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);
        db = new DatabaseHandler(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.openDatabase();
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                if (user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(RegisterActivity.this,
                            "Заполните обязательные поля", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(repass)) {
                        if (!db.checkUsername(user)) {
                            boolean insert = db.insertUser(user, pass);
                            if (insert == true) {
                                Toast.makeText(RegisterActivity.this,
                                        "Вы успешно зарегистрированы", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterActivity.this,
                                        "Пользователь с таким логином уже зарегистрирован", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this,
                                "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        db.close();
    }
}