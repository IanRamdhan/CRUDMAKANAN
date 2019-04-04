package com.fryanramzkhar.crudmakanan.UI.MakananByKategori;

import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananData;

import java.util.List;

public interface MakananByKategoriContract {

    interface View{
        void showPrgress();
        void hideProgress();
        void showFoodByCategory(List<MakananData> foodNewsList);
        void showFailureMessage(String msg);
    }

    interface Presenter {
        void getListFoodByCategory(String idCategory);
    }
}
