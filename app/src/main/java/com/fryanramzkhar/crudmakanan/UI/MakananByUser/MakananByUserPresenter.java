package com.fryanramzkhar.crudmakanan.UI.MakananByUser;

import android.util.Log;

import com.fryanramzkhar.crudmakanan.Data.Remote.ApiClient;
import com.fryanramzkhar.crudmakanan.Data.Remote.ApiInterface;
import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananByUserPresenter implements MakananByUserContract.Presenter {

    private final MakananByUserContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananByUserPresenter(MakananByUserContract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodByUser(String idUser) {
        view.showPrgress();

        if (idUser.isEmpty()) {
            view.showFailureMessage("ID User tidak ada");
            return;
        }

            Call<MakananResponse> call = mApiInterface.getMakananByUser(Integer.valueOf(idUser));
            call.enqueue(new Callback<MakananResponse>() {
                @Override
                public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                    view.hideProgress();
                    if (response.body() != null) {
                        if (response.body().getResult() == 1) {
                            view.showFoodByUser(response.body().getMakananDataList());
                            Log.i("Cek ", "Data Ada" );
                        } else {
                            view.showFailureMessage(response.body().getMessage());
                        }
                    } else {
                        view.showFailureMessage("Data kosong");
                        Log.i("Cek Data ", "Kosong");
                    }
                }

                @Override
                public void onFailure(Call<MakananResponse> call, Throwable t) {
                    view.showFailureMessage(t.getMessage());
                }
            });
        }
    }
