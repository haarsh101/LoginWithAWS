package com.example.loginwithaws.utils;

public class InputValidator {
    private String EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String MOBILE_REGEX = "^[789][0-9]{9}";

    public boolean isValidMobile(String mobile) {
        if (!mobile.matches(MOBILE_REGEX))
        {
            return false;
        }
        return true;
    }

    public boolean isValidEmail(String email) {
        if(!email.matches(EMAIL_REGEX) || email.length() < 4 || email.length() > 25)
        {
            return false;
        }
        return true;
    }

    public boolean isValidPassword(String password, String name) {
        if (password.length() < 8 || password.length() >25 || password.contains(name)) {
            return false;
        }

        int upper = 0, lower = 0, number = 0, special = 0;
        for(int i = 0; i < password.length(); i++)
        {
            char ch = password.charAt(i);
            if (ch >= 'A' && ch <= 'Z')
                upper++;
            else if (ch >= 'a' && ch <= 'z')
                lower++;
            else if (ch >= '0' && ch <= '9')
                number++;
            else
                special++;
        }

        if(special<1 || number<1){
            return false;
        }
        return true;
    }
}
