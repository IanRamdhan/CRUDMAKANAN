package com.fryanramzkhar.crudmakanan.UI.UploadMakanan;

import android.content.Context;
import android.net.Uri;

import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananData;

import java.util.List;

public interface UploadMakananConstract {

    interface View{
        void showProgress();
        void hideProgress();
        void showMessage(String msg);
        void succesUpload();
        void showSpinnerCategory(List<MakananData> categoryDataList);

    }

    interface Presenter{
        void getCategory();
        void uploadMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan, String idCategory);
    }
}
