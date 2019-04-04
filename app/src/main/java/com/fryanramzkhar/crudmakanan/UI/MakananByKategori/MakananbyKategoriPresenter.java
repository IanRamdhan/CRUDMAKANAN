package com.fryanramzkhar.crudmakanan.UI.MakananByKategori;

import com.fryanramzkhar.crudmakanan.Data.Remote.ApiClient;
import com.fryanramzkhar.crudmakanan.Data.Remote.ApiInterface;
import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananbyKategoriPresenter implements MakananByKategoriContract.Presenter {

    private final MakananByKategoriContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananbyKategoriPresenter(MakananByKategoriContract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodByCategory(String idCategory) {
        view.showPrgress();

        if (idCategory.isEmpty()){
            view.showFailureMessage("Id Kategori tidak ada");
            return;
        }

        Call<MakananResponse> call = apiInterface.getMakananCategory(Integer.valueOf(idCategory));
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showFoodByCategory(response.body().getMakananDataList());
                    }else {
                        view.showFailureMessage(response.body().getMessage());
                    }
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}
