package com.fryanramzkhar.crudmakanan.UI.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fryanramzkhar.crudmakanan.Model.Login.LoginData;
import com.fryanramzkhar.crudmakanan.R;
import com.fryanramzkhar.crudmakanan.UI.Main.MainActivity;
import com.fryanramzkhar.crudmakanan.UI.Register.RegisterActivity;
import com.fryanramzkhar.crudmakanan.Utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txt_register)
    TextView txtRegister;

    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter.checkLogin(this);
    }

    @OnClick({R.id.btnLogin, R.id.txt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                loginPresenter.doLogin(edtUsername.getText().toString(), edtPassword.getText().toString());
                break;
            case R.id.txt_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();

    }

    @Override
    public void loginSuccess(String msg, LoginData loginData) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        //Menyimpan data ke dalam Shared Preference
        loginPresenter.saveDataUser(this, loginData);

        LoginData mLoginData = new LoginData();
        mLoginData.setId_user(loginData.getId_user());
        mLoginData.setNama_user(loginData.getNama_user());
        mLoginData.setAlamat(loginData.getAlamat());
        mLoginData.setPassword(loginData.getPassword());
        mLoginData.setNo_telp(loginData.getNo_telp());
        mLoginData.setLevel(loginData.getLevel());
        mLoginData.setJenkel(loginData.getJenkel());
        mLoginData.setUsername(loginData.getUsername());

        startActivity(new Intent(this, MainActivity.class).putExtra(Constant.KEY_LOGIN, mLoginData));
        finish();

    }



    @Override
    public void loginFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void usernameError(String msg) {
        edtUsername.setError(msg);
        edtUsername.setFocusable(true);
    }

    @Override
    public void paswordError(String msg) {
        edtPassword.setError(msg);
        edtPassword.setFocusable(true);

    }

    @Override
    public void isLogin() {
        //Berpindah halaman apabila user sudah login
        startActivity(new Intent(this, MainActivity.class));
    }


}
