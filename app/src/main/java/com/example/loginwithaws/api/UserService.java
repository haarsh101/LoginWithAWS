package com.example.loginwithaws.api;

import com.example.loginwithaws.utils.HttpCallHelper;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UserService {

    // replace with your api gateway url
    private final static String BASE_URL = "https://abcdefgh.execute-api.ap-south-1.amazonaws.com";
    private final static String SIGNUP_USER_PATH = "/live/users/register";
    private final static String LOGIN_USER_PATH = "/live/users/login";

    public UserService() {
    }

    public void signupUser(final String phone,
                                 final String name,
                                 final String password,
                                 final Runnable postFinishRunnable) {
        String jsonBody = String.format("{ \"phone\": \"%s\", \"name\": \"%s\", \"password\": \"%s\" }",
                phone,
                name,
                password);

        try {
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), jsonBody);

            Request.Builder httpPost = new Request.Builder()
                    .url(BASE_URL + SIGNUP_USER_PATH)
                    .post(body);
            HttpCallHelper.executeHttpCall(httpPost, postFinishRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginUser(final String phone,
                           final String password,
                           final Runnable postFinishRunnable) {
        String jsonBody = String.format("{ \"phone\": \"%s\", \"password\": \"%s\" }",
                phone,
                password);

        try {
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), jsonBody);

            Request.Builder httpPost = new Request.Builder()
                    .url(BASE_URL + LOGIN_USER_PATH)
                    .post(body);
            HttpCallHelper.executeHttpCall(httpPost, postFinishRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
