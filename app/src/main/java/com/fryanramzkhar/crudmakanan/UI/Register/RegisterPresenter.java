package com.fryanramzkhar.crudmakanan.UI.Register;

import com.fryanramzkhar.crudmakanan.Data.Remote.ApiClient;
import com.fryanramzkhar.crudmakanan.Data.Remote.ApiInterface;
import com.fryanramzkhar.crudmakanan.Model.Login.LoginData;
import com.fryanramzkhar.crudmakanan.Model.Login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void doRegisterUser(LoginData loginData) {
        if (loginData != null){
            if (!loginData.getUsername().isEmpty()
                    && !loginData.getAlamat().isEmpty()
                    && !loginData.getJenkel().isEmpty()
                    && !loginData.getLevel().isEmpty()
                    && !loginData.getNo_telp().isEmpty()
                    && !loginData.getPassword().isEmpty()
                    && !loginData.getNama_user().isEmpty()){

                view.showProgress();
                Call<LoginResponse> call = apiInterface.registerUser(loginData.getUsername(),
                        loginData.getPassword(),
                        loginData.getNama_user(),
                        loginData.getAlamat(),
                        loginData.getJenkel(),
                        loginData.getNo_telp(),
                        loginData.getLevel()
                );
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        view.hideProgress();
                        if (response.body()!= null){
                            if (response.body().getResult() == 1){
                                view.showRegisterSuccess(response.body().getMessage());
                            }else {
                                view.hideProgress();
                                view.showError(response.body().getMessage());
                            }
                        }else {
                            view.showError("Data Kosong");
                            view.hideProgress();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        view.hideProgress();
                        view.showError(t.getMessage());

                    }
                });

            }else {
                view.showError("Tidak boleh ada yang kosong");
                view.hideProgress();
            }
        }


        view.showProgress();

    }
}
