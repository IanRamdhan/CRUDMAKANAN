package com.fryanramzkhar.crudmakanan.UI.MakananByUser;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fryanramzkhar.crudmakanan.Adapter.MakananAdapter;
import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananData;
import com.fryanramzkhar.crudmakanan.R;
import com.fryanramzkhar.crudmakanan.Utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakananByUserFragment extends Fragment implements MakananByUserContract.View {


    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rl_progress_by_user)
    RelativeLayout rlProgressByUser;
    @BindView(R.id.rv_makanan)
    RecyclerView rvMakanan;
    @BindView(R.id.sr_makanan_by_user)
    SwipeRefreshLayout srMakananByUser;
    Unbinder unbinder;

    private MakananByUserPresenter makananByUserPresenter = new MakananByUserPresenter(this);
    private String idUser;

    public MakananByUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Mengambil id User dari SharedPref
        SharedPreferences pref = getContext().getSharedPreferences(Constant.pref_name, 0);
        idUser = pref.getString(Constant.KEY_USER_ID, "");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_makanan_by_user, container, false);
        unbinder = ButterKnife.bind(this, view);

        //Merequest data makanan by User
        makananByUserPresenter.getListFoodByUser(idUser);
        srMakananByUser.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srMakananByUser.setRefreshing(false);
                //Merquest data makanan by User
                makananByUserPresenter.getListFoodByUser(idUser);

            }
        });
        return view;
    }

    @Override
    public void showPrgress() {
        rlProgressByUser.setVisibility(View.VISIBLE);
        srMakananByUser.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rlProgressByUser.setVisibility(View.GONE);
        rvMakanan.setVisibility(View.VISIBLE);
        srMakananByUser.setVisibility(View.VISIBLE);

    }

    @Override
    public void showFoodByUser(List<MakananData> foodByUserList) {
        rvMakanan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMakanan.setAdapter(new MakananAdapter(MakananAdapter.TYPE_5, getContext(), foodByUserList));
    }

    @Override
    public void showFailureMessage(String msg) {
        srMakananByUser.setVisibility(View.VISIBLE);
        rvMakanan.setVisibility(View.GONE);
        rlProgressByUser.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
        txtInfo.setText(msg);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        makananByUserPresenter.getListFoodByUser(idUser);
    }
}
