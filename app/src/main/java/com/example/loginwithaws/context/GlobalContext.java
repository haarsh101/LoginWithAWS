package com.example.loginwithaws.context;

import com.example.loginwithaws.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;

/**
 * Class used to store Global Context to be shared across Activities.
 */
public class GlobalContext
{
    public static User currentOnlineUser;
    // Hashable map of productId to Product

    public static OkHttpClient httpClient;
    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final String userKey = "user";
    public static String BIJAY_1 = "";
}
