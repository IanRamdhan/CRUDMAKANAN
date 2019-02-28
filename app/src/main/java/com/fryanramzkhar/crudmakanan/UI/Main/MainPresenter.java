package com.fryanramzkhar.crudmakanan.UI.Main;

import android.content.Context;

import com.fryanramzkhar.crudmakanan.Utils.SessionManager;

public class MainPresenter implements MainContract.Presenter {
    @Override
    public void logoutSession(Context context) {
        //Membuat Object Session manager agar siap digunakan
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();
    }
}
