package com.fryanramzkhar.crudmakanan.Model.Login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LoginData implements Parcelable {

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("nama_user")
    private String nama_user;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("no_telp")
    private String no_telp;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("level")
    private String level;

    @SerializedName("jenkel")
    private String jenkel;

    public String getJenkel() {
        return jenkel;
    }

    public void setJenkel(String jenkel) {
        this.jenkel = jenkel;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_user);
        dest.writeString(this.nama_user);
        dest.writeString(this.alamat);
        dest.writeString(this.no_telp);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.level);
    }

    public LoginData() {

    }

    protected LoginData(Parcel in) {
        this.id_user = in.readString();
        this.nama_user = in.readString();
        this.alamat = in.readString();
        this.no_telp = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.level = in.readString();
    }

    public static final Parcelable.Creator<LoginData> CREATOR = new Parcelable.Creator<LoginData>() {
        @Override
        public LoginData createFromParcel(Parcel source) {
            return new LoginData(source);
        }

        @Override
        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };
}
