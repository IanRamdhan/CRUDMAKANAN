package com.fryanramzkhar.crudmakanan.UI.Profil;

import android.content.Context;

import com.fryanramzkhar.crudmakanan.Model.Login.LoginData;

public interface ProfilContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showSuccessUpdateUser(String msg);
        void showDataUser(LoginData loginData);
    }

    interface Presenter{
        void updateDataUser(Context context, LoginData loginData);
        void getDataUser(Context context);
        void logoutSession(Context context);
    }
}
