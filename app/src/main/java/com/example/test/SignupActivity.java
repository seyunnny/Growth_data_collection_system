package com.example.test;

import androidx.appcompat.app.AlertDialog;
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

public class SignupActivity extends AppCompatActivity {
    ImageButton backBtn, checkBtn, cancelBtn, idBtn;
    EditText editTextID, editTextPW, editTextTel, editTextName;
    boolean validate = false;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("회원가입");

        // 뒤로가기 버튼
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());
        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(v -> onBackPressed());

        // 정보 기입
        editTextID = findViewById(R.id.editTextID);
        editTextPW = findViewById(R.id.editTextPW);
        editTextName = findViewById(R.id.editTextName);
        editTextTel = findViewById(R.id.editTextTel);

        // 아이디가 이미 존재하는 경우 데이터가 들어가지 않기 때문에 회원가입 실패함.
        //아이디 중복 체크
        idBtn = findViewById(R.id.idBtn);
        idBtn.setOnClickListener(v -> {
            String ID = editTextID.getText().toString();
            if (validate) {
                return;
            }
            if (ID.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                dialog.show();
                return;
            }
            Response.Listener<String> responseListener = response -> {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    if (success) {
                        dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                        dialog.show();
                        editTextID.setEnabled(false); //아이디값 고정
                        validate = true; //검증 완료
//                        String id = jsonResponse.getString("userID");
//                        String pw = jsonResponse.getString("userPW");
//                        Intent intent = new Intent(this, MainActivity.class);
//                        intent.putExtra("userID", id);
//                        intent.putExtra("userPW", pw);
//                        startActivity(intent);
                    } else {
                        dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            ValidateRequest validateRequest = new ValidateRequest(ID, responseListener); //아이디 중복 확인 클래스
            RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
            queue.add(validateRequest);
        });



        //확인 버튼 클릭 시 수행
        checkBtn = findViewById(R.id.checkBtn);
        checkBtn.setOnClickListener(v -> {
            String UserID = editTextID.getText().toString();
            String UserPW = editTextPW.getText().toString();
            String UserName = editTextName.getText().toString();
            String UserTel = editTextTel.getText().toString();

            //아이디 중복체크 했는지 확인
            if (!validate) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                dialog.show();
                return;
            }

            //한 칸이라도 입력 안했을 경우
                if (UserName.equals("") || UserID.equals("") || UserPW.equals("") || UserTel.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    AlertDialog dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success"); // key값이 successs인 것을 가져옴

                    if (success) { // 회원가입 성공시 success 값이 true
                        Toast.makeText(getApplicationContext(), "회원 등록 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "회원 등록 실패", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            //서버로 Volley 이용해 요청
            RegisterRequest registerRequest = new RegisterRequest(UserID, UserPW, UserName, UserTel, responseListener);
            RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
            queue.add(registerRequest);
        });
    }
}