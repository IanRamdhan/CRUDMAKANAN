package com.fryanramzkhar.crudmakanan.UI.Makanan;

import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananData;

import java.util.List;

public interface MakananContract {

    interface View{
        void showPrgress();
        void hideProgress();
        void showFoodNewsList(List<MakananData> foodNewsList);
        void showFoodPopulerList(List<MakananData> foodPopulerList);
        void showFoodKategoryList(List<MakananData> foodKategoryList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodNews();
        void getListFoodPopuler();
        void getListFoodKategory();
    }
}
