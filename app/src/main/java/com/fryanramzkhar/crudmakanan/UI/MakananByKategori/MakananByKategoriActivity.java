package com.fryanramzkhar.crudmakanan.UI.MakananByKategori;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fryanramzkhar.crudmakanan.Adapter.MakananAdapter;
import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananData;
import com.fryanramzkhar.crudmakanan.R;
import com.fryanramzkhar.crudmakanan.UI.MakananByKategori.MakananByKategoriContract.View;
import com.fryanramzkhar.crudmakanan.Utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakananByKategoriActivity extends AppCompatActivity implements View {

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rv_makanan)
    RecyclerView rvMakanan;
    @BindView(R.id.sr_makanan)
    SwipeRefreshLayout srMakanan;

    private MakananbyKategoriPresenter makananbyKategoriPresenter = new MakananbyKategoriPresenter(this);
    private String idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan_by_kategori);
        ButterKnife.bind(this);

        // Mengambil idCategory dari kiriman halaman sebelumnya
        idCategory = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_CATEGORY);

        //Merequest makanan by idCategory
        makananbyKategoriPresenter.getListFoodByCategory(idCategory);
        srMakanan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srMakanan.setRefreshing(false);
                makananbyKategoriPresenter.getListFoodByCategory(idCategory);
            }
        });
    }

    @Override
    public void showPrgress() {
        rlProgress.setVisibility(android.view.View.VISIBLE);
        srMakanan.setVisibility(android.view.View.GONE);

    }

    @Override
    public void hideProgress() {
        rlProgress.setVisibility(android.view.View.GONE);
        srMakanan.setVisibility(android.view.View.VISIBLE);
        rvMakanan.setVisibility(android.view.View.VISIBLE);

    }

    @Override
    public void showFoodByCategory(List<MakananData> foodNewsList) {
        rvMakanan.setLayoutManager(new LinearLayoutManager(this));
        rvMakanan.setAdapter(new MakananAdapter(MakananAdapter.TYPE_4,this, foodNewsList));
    }

    @Override
    public void showFailureMessage(String msg) {
        srMakanan.setVisibility(android.view.View.VISIBLE);
        rvMakanan.setVisibility(android.view.View.GONE);
        pbLoading.setVisibility(android.view.View.GONE);
        txtInfo.setText(msg);
    }
}
