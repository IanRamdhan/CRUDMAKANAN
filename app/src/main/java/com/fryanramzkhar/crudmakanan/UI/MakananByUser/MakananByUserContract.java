package com.fryanramzkhar.crudmakanan.UI.MakananByUser;

import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananData;

import java.util.List;

public interface MakananByUserContract {

    interface View{
        void showPrgress();
        void hideProgress();
        void showFoodByUser(List<MakananData> foodByUserList);
        void showFailureMessage(String msg);

    }

    interface Presenter{
        void getListFoodByUser(String idUser);
    }
}
