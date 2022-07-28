package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText edtTextID, edtTextPW;
    ImageButton BtnLogin, BtnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtTextID = findViewById(R.id.edtTextID);
        edtTextPW = findViewById(R.id.edtTextPW);
        BtnSignup = findViewById(R.id.BtnSignup);
        BtnLogin = findViewById(R.id.BtnLogin);

        // 회원가입 버튼 클릭시
        BtnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });

        // 로그인 버튼 클릭시 (저장된 데이터로 로그인을 해야되는데 데이터 저장 어케하누...)
        BtnLogin = findViewById(R.id.BtnLogin);
        BtnLogin.setOnClickListener(v -> {
            String UserID = edtTextID.getText().toString();
            String UserPW = edtTextPW.getText().toString();

            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success"); // key값이 successs인 것을 가져옴

                    if (success) { // 회원가입 성공시 success 값이 true
                        String userID = jsonResponse.getString("userID");
                        String userPW = jsonResponse.getString("userPW");
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userID",userID);
                        intent.putExtra("userPW",userPW);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            LoginRequest loginRequest = new LoginRequest(UserID, UserPW, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        });

    }
}
      // 사용자가 send 버튼을 눌렀을 때 호출됨
//    public void sendMessage(View view){
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String msg = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }
