package com.fryanramzkhar.crudmakanan.UI.Profil;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fryanramzkhar.crudmakanan.Data.Remote.ApiClient;
import com.fryanramzkhar.crudmakanan.Data.Remote.ApiInterface;
import com.fryanramzkhar.crudmakanan.Model.Login.LoginData;
import com.fryanramzkhar.crudmakanan.Model.Login.LoginResponse;
import com.fryanramzkhar.crudmakanan.Utils.Constant;
import com.fryanramzkhar.crudmakanan.Utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilPresenter implements ProfilContract.Presenter {
    private final ProfilContract.View view;
    private SharedPreferences pref;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public ProfilPresenter(ProfilContract.View view) {
        this.view = view;
    }

    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {

        //Show progress
        view.showProgress();

        Call<LoginResponse> call = apiInterface.updateUser(Integer.valueOf(loginData.getId_user()),
                loginData.getNama_user(), loginData.getAlamat(), loginData.getJenkel(), loginData.getNo_telp());

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                //Mengecek respon dan isi body
                if (response.isSuccessful() && response.body() != null){
                    //Mengecek result apakah 1
                    if (response.body().getResult() == 1){
                        Log.i("cek", loginData.getJenkel());
                        //Setelah update ke server online, lalu update ke sharedPref
                        //membuat Objek sharedpref yang sudah ada di sessionManager
                        pref = context.getSharedPreferences(Constant.pref_name, 0);
                        //Mengubah mode SharedPref menjadi edit
                        SharedPreferences.Editor editor = pref.edit();
                        //Memasukkan data ke dalam sharedPref
                        editor.putString(Constant.KEY_USER_USERNAME, loginData.getUsername());
                        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
                        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
                        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
                        // apply perubahan
                        editor.apply();
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }else {
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.showSuccessUpdateUser(t.getMessage());
            }
        });

        //membuat Objek sharedpref yang sudah ada di sessionManager
        pref = context.getSharedPreferences(Constant.pref_name, 0);
        //Mengubah mode SharedPref menjadi edit
        SharedPreferences.Editor editor = pref.edit();
        //Memasukkan data ke dalam sharedPref
        editor.putString(Constant.KEY_USER_USERNAME, loginData.getUsername());
        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
        // apply perubahan
        editor.apply();
        view.showSuccessUpdateUser("Update Success");
    }

    @Override
    public void getDataUser(Context context) {
        // Pengambilan data dari SharedPreference
        pref = context.getSharedPreferences(Constant.pref_name, 0);


        //membuat object model LoginData untuk penampung
        LoginData loginData = new LoginData();

        //memasukkan data seharedPreference ke dalam model LoginDadta
        loginData.setId_user(pref.getString(Constant.KEY_USER_ID, ""));
        loginData.setUsername(pref.getString(Constant.KEY_USER_USERNAME, ""));
        loginData.setAlamat(pref.getString(Constant.KEY_USER_ALAMAT, ""));
        loginData.setNo_telp(pref.getString(Constant.KEY_USER_NOTELP, ""));
        loginData.setJenkel(pref.getString(Constant.KEY_USER_JENKEL, ""));


        // Kirim data model LoginData ke view
        view.showDataUser(loginData);
    }

    @Override
    public void logoutSession(Context context) {
        //Membuat class session Manager untuk menaggil method logout
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.logout();
    }
}
