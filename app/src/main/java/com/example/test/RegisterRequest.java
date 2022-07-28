package com.example.test;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://tpdbs1004.ivyro.net/register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPW, String userName, String userTel, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPW", userPW);
        parameters.put("userName", userName);
        parameters.put("userTel", userTel);

    }

    @Override
    public Map<String, String>getParams() throws AuthFailureError {
        return parameters;
    }
}