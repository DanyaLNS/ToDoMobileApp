package mirea.ck.anikin.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mirea.ck.anikin.todo.utils.DatabaseHandler;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button signin;
    DatabaseHandler db;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        signin = (Button) findViewById(R.id.btnsignin1);
        db = new DatabaseHandler(this);
        db.openDatabase();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this,
                            "Заполните обязательные поля", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.checkUsernamePassword(user, pass)) {
                        Toast.makeText(LoginActivity.this,
                                "Вход выполнен успешно", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        db.close();
    }
}
