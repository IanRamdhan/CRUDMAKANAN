package com.fryanramzkhar.crudmakanan.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.fryanramzkhar.crudmakanan.Model.Login.LoginData;
import com.fryanramzkhar.crudmakanan.UI.Login.LoginActivity;

public class SessionManager {
    //Membuat variable Global untuk shared Preference
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;

        //Membuat Objek Shared Preference untuk siap digunakan
        pref = context.getSharedPreferences(Constant.pref_name,0);
        //Membuat Pref dengan mode edit
        editor = pref.edit();
    }

    //Membuat Function
    public void createSession(LoginData loginData){
        //Memasukkan data user yang sudah login ke dalam Shared Preference
        editor.putBoolean(Constant.KEY_IS_LOGIN, true);
        editor.putString(Constant.KEY_USER_ID, loginData.getId_user());
        editor.putString(Constant.KEY_USER_NAMAUSER, loginData.getNama_user());
        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getId_user());
        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
        editor.putString(Constant.KEY_USER_LEVEL, loginData.getLevel());
        editor.putString(Constant.KEY_USER_USERNAME, loginData.getUsername());
        //Mengeksekusi penyimpanan
        editor.commit();
    }

    //Function untuk mengecek Login apakah user sudah pernah Login
    public boolean isLogin(){
        //Mengembalikan nilai boolean dengan mengambil data dari pref KEY_IS_LOGIN
        return pref.getBoolean(Constant.KEY_IS_LOGIN, false);
    }

    //Membuat Function untuk melakukan Logout atau mengahapus isi di dalam shared preference
    public void logout(){
        //Memanggil method clear untuk menghapus data Shared Preference
        editor.clear();
        //Mengeksekusi perintah clear()
        editor.commit();

        //Membuat Intent untuk berpindah halaman
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
