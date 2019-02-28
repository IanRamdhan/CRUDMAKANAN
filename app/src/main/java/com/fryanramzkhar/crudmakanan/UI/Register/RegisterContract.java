package com.fryanramzkhar.crudmakanan.UI.Register;

import android.widget.EditText;

import com.fryanramzkhar.crudmakanan.Model.Login.LoginData;

public interface RegisterContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showError(String msg);
        void showRegisterSuccess(String msg);

    }

    interface Presenter{
        void doRegisterUser(LoginData loginData);
    }
}
