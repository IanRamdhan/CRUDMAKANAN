package com.fryanramzkhar.crudmakanan.UI.Login;

import android.content.Context;

import com.fryanramzkhar.crudmakanan.Model.Login.LoginData;

public interface LoginContract {

    interface View{
        void showProgress();
        void hideProgress();

        void loginSuccess(String msg, LoginData loginData);
        void loginFailure(String msg);
        void usernameError(String msg);
        void paswordError(String msg);
        void isLogin();
    }

    interface Presenter{
        void doLogin(String username, String password);
        void saveDataUser(Context context, LoginData loginData);
        void checkLogin(Context context);

    }
}
