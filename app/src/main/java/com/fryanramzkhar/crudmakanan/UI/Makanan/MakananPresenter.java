package com.fryanramzkhar.crudmakanan.UI.Makanan;

import android.util.Log;

import com.fryanramzkhar.crudmakanan.Data.Remote.ApiClient;
import com.fryanramzkhar.crudmakanan.Data.Remote.ApiInterface;
import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananPresenter implements MakananContract.Presenter {

    //TODO 1 Menyiapkan variable yang dibutuhkan
    private final MakananContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananPresenter(MakananContract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodNews() {
        view.showPrgress();
        Call<MakananResponse> call = mApiInterface.getMakananBaru();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body().getMakananDataList() != null){
                    view.showFoodNewsList(response.body().getMakananDataList());
                    Log.i("Cek Success", "Data Ada");
                }else {
                    view.showFailureMessage("Data Kosong");
                    Log.i("Cek Error", "Data Kosong");

                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
                Log.i("cek error", t.getMessage());
            }
        });
    }

    @Override
    public void getListFoodPopuler() {
        view.showPrgress();
        Call<MakananResponse> call = mApiInterface.getMakananPopuler();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body().getMakananDataList() != null){
                    view.showFoodPopulerList(response.body().getMakananDataList());
                    Log.i("Cek Success", "Data Ada");
                }else {
                    view.showFailureMessage("Data Kosong");
                    Log.i("Cek Error", "Data Kosong");

                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
                Log.i("cek error", t.getMessage());
            }
        });

    }

    @Override
    public void getListFoodKategory() {
        view.showPrgress();
        Call<MakananResponse> call = mApiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body().getMakananDataList() != null){
                    view.showFoodKategoryList(response.body().getMakananDataList());
                    Log.i("Cek Success", "Data Ada");
                }else {
                    view.showFailureMessage("Data Kosong");
                    Log.i("Cek Error", "Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
                Log.i("cek error", t.getMessage());
            }
        });
    }
}
