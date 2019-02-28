package com.fryanramzkhar.crudmakanan.UI.Login;

import android.content.Context;

import com.fryanramzkhar.crudmakanan.Data.Remote.ApiClient;
import com.fryanramzkhar.crudmakanan.Data.Remote.ApiInterface;
import com.fryanramzkhar.crudmakanan.Model.Login.LoginData;
import com.fryanramzkhar.crudmakanan.Model.Login.LoginResponse;
import com.fryanramzkhar.crudmakanan.Utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SessionManager mSessionManager;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String username, String password) {
        if (username.isEmpty()){
            view.usernameError("Username is Empty");
            return;
        }
        if (password.isEmpty()){
            view.paswordError("Password is Empty");
            return;
        }

        view.showProgress();

        Call<LoginResponse> call = apiInterface.loginUser(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                //menegcek apakah body ada isinya
                if (response.body() != null){
                    //Mengecek apakah isi body ada 1 atau lebih
                    if (response.body().getResult() == 1){
                        if (response.body().getData() != null){
                            //mengirim ke view karena body ada isinya
                            LoginData loginData = response.body().getData();
                            String message = response.body().getMessage();
                            view.loginSuccess(message, loginData);
                        }else {
                            view.loginFailure("Data is Empty");
                        }
                    }else {
                        view.loginFailure(response.body().getMessage());
                    }
                }else {
                    view.loginFailure("Data is Empty");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.loginFailure(t.getMessage());

            }
        });
    }

    @Override
    public void saveDataUser(Context context, LoginData loginData) {
        //Membuat Object SessionManager
        mSessionManager = new SessionManager(context);
        // Save Data dengan menggunakan method dari class SessionManager
        mSessionManager.createSession(loginData);
    }

    @Override
    public void checkLogin(Context context) {
        //Membuat Object SessionManager
        mSessionManager = new SessionManager(context);
        //Mengambil data KEY_IS_LOGIN lalu memasukkan ke dalam variable isLogin
        Boolean isLogin = mSessionManager.isLogin();
        //Mengecek apakah KEY_IS_LOGIN bernilai true
        if (isLogin){
            //Menyuruh view untuk melakukan perpindahan ke MainActivity
            view.isLogin();
        }
    }
}
