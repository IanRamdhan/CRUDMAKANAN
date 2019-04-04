package com.fryanramzkhar.crudmakanan.UI.UploadMakanan;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.fryanramzkhar.crudmakanan.Data.Remote.ApiClient;
import com.fryanramzkhar.crudmakanan.Data.Remote.ApiInterface;
import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananResponse;
import com.fryanramzkhar.crudmakanan.Model.UploadMakanan.UploadMakananResponse;
import com.fryanramzkhar.crudmakanan.Utils.Constant;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadMakananPresenter implements UploadMakananConstract.Presenter {

    //TODO 1 Menyiapkan variable yang dibutuhkan
    private final UploadMakananConstract.View view;
    private final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    public UploadMakananPresenter(UploadMakananConstract.View view) {
        this.view = view;
    }

    @Override
    public void getCategory() {
        view.showProgress();
        Call<MakananResponse> call = apiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showMessage(response.body().getMessage());
                        view.showSpinnerCategory(response.body().getMakananDataList());
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
                Log.i("Cek Failure", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void uploadMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan, String idCategory) {
        view.showProgress();

        if (namaMakanan.isEmpty()){
            view.showMessage("Nama Makanan tidak boleh kosong");
            view.hideProgress();
            return;
        }

        if (descMakanan.isEmpty()){
            view.showMessage("Deskripsi Makanan tidak boleh kosong");
            view.hideProgress();
            return;
        }

        if (filePath == null){
            view.showMessage("Silahkan Memilih Gambar");
            view.hideProgress();
            return;
        }

        //Mengambil alamat file Image
        File myFile = new File(filePath.getPath());
        Uri selectedImage = getImageContentUri(context, myFile, filePath);
        String partImage = getPath(context, selectedImage);
        File imageFile = new File(partImage);

        //Mengambil IdUser di dalam shared Preference
        SharedPreferences pref = context.getSharedPreferences(Constant.pref_name, 0);
        String idUser = pref.getString(Constant.KEY_USER_ID, "");

        //Mengambil Date sekarang untuk waktu upload makanan
        String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Memasukkan data yang diperlukan ke dalam request body dengan tipe form-data
        //Memasukkan image ke dalam requestbody
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        RequestBody mNamaMakanan = RequestBody.create(MediaType.parse("multipart/form-data"), namaMakanan);
        RequestBody mDescMakanan = RequestBody.create(MediaType.parse("multipart/form-data"), descMakanan);
        RequestBody mInsertTime = RequestBody.create(MediaType.parse("multipart/form-data"), dateNow);

        //Mengirim data ke API
        Call<UploadMakananResponse> call = apiInterface.uploadMakanan(Integer.valueOf(idUser),
                Integer.valueOf(idCategory),
                mNamaMakanan,
                mDescMakanan,
                mInsertTime,
                mPartImage);

        call.enqueue(new Callback<UploadMakananResponse>() {
            @Override
            public void onResponse(Call<UploadMakananResponse> call, Response<UploadMakananResponse>  response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showMessage(response.body().getMessage());
                        view.succesUpload();
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data Kosong !");
                }
            }

            @Override
            public void onFailure(Call<UploadMakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
                Log.i("Cek Error", "onFailure: " + t.getMessage());

            }
        });
    }

    private String getPath(Context context, Uri filepath) {
        Cursor cursor = context.getContentResolver().query(filepath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private Uri getImageContentUri(Context context, File imageFile, Uri filePath) {
        String fileAbsolutePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{fileAbsolutePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
// Apabila file gambar sudah pernah diapakai namun ada kondisi lain yang belum diketahui
// Apabila file gambar sudah pernah dipakai pengambilan bukan di galery

            Log.i("Isi Selected if", "Masuk cursor ada");
            return filePath;

        } else {
            Log.i("Isi Selected else", "cursor tidak ada");
            if (imageFile.exists()) {
// Apabila file gambar baru belum pernah di pakai
                Log.i("Isi Selected else", "imagefile exists");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, fileAbsolutePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
// Apabila file gambar sudah pernah dipakai
// Apabila file gambar sudah pernah dipakai di galery
                Log.i("Isi Selected else", "imagefile tidak exists");
                return filePath;
            }
        }
    }

}

