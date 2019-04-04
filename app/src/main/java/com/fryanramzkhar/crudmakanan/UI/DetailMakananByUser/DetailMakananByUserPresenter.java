package com.fryanramzkhar.crudmakanan.UI.DetailMakananByUser;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.fryanramzkhar.crudmakanan.Data.Remote.ApiClient;
import com.fryanramzkhar.crudmakanan.Data.Remote.ApiInterface;
import com.fryanramzkhar.crudmakanan.Model.DetailMakanan.DetailMakananResponse;
import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMakananByUserPresenter implements DetailMakananByUserContract.Presenter {
    private final  DetailMakananByUserContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public DetailMakananByUserPresenter(DetailMakananByUserContract.View view) {
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
    public void getDetailMakanan(String idMakanan) {
        view.showProgress();
        if (idMakanan == null){
            view.showMessage("ID Makanan tidak ada");
            view.hideProgress();
            return;
        }

        Call<DetailMakananResponse> call = apiInterface.getDetailMakanan(Integer.valueOf(idMakanan));
        call.enqueue(new Callback<DetailMakananResponse>() {
            @Override
            public void onResponse(Call<DetailMakananResponse> call, Response<DetailMakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1){
                        view.showDetailMakanan(response.body().getMakananData());
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data Kosong !");
                }
            }

            @Override
            public void onFailure(Call<DetailMakananResponse> call, Throwable t) {
                view.showMessage(t.getMessage());
            }
        });

    }

    @Override
    public void updateDataMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan, String idCategory, String namaFotoMakanan) {

    }

    @Override
    public void deleteMakanan(String idMakanan, String namaFotoMakanan) {
        view.showProgress();

        if (idMakanan.isEmpty()){
            view.showMessage("Id Makanan Tidak Ada");
            return;
        }
        if (namaFotoMakanan.isEmpty()){
            view.showMessage("Nama Makanan Tidak Ada");
            return;
        }

        Call<MakananResponse> call = apiInterface.deleteMakanan(Integer.valueOf(idMakanan), namaFotoMakanan);
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();

                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.successDelete();
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
            }
        });
    }
}
