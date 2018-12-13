package id.renaldirey.restapi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import id.renaldirey.restapi.adapter.DataAdapter;
import id.renaldirey.restapi.listener.OnDeleteClickListener;
import id.renaldirey.restapi.listener.OnUpdateClickListener;
import id.renaldirey.restapi.model.Data;
import id.renaldirey.restapi.network.ServiceGenerator;
import id.renaldirey.restapi.network.response.BaseResponse;
import id.renaldirey.restapi.network.service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends AppCompatActivity implements OnDeleteClickListener, OnUpdateClickListener {

    private static final String TAG = DataActivity.class.getSimpleName();

    private RecyclerView rvData;
    private DataAdapter adapter;
    private DataService service;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, DataActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initViews();

        // Initialization adapter
        adapter = new DataAdapter(this);
        rvData.setLayoutManager(new LinearLayoutManager(this));
        service = ServiceGenerator.createBaseService(this, DataService.class);

        rvData.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        Call<BaseResponse<List<Data>>> call = service.apiRead();
        call.enqueue(new Callback<BaseResponse<List<Data>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Data>>> call, Response<BaseResponse<List<Data>>> response) {
                if(response.code() == 200) {
                    adapter.addAll(response.body().getData());
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Data>>> call, Throwable t) {
                Log.e(TAG+".error", t.toString());
            }
        });
    }

    private void initListener() {
        adapter.setOnDeleteClickListener(this);
        adapter.setOnUpdateClickListener(this);
    }

    private void initViews() {
        rvData = (RecyclerView) findViewById(R.id.rv_data);
    }

    private void doDelete(final int position, String id) {
        Call<BaseResponse> call = service.apiDelete(id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.code() == 200)
                    adapter.remove(position);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e(TAG+".errorDelete", t.toString());
            }
        });
    }

    @Override
    public void onDeleteClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda Yakin Ingin Logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                doDelete(position, adapter.getData(position).getId());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onUpdateClick(int position) {
        Data data = adapter.getData(position);
        UpdateActivity.newInstance(this, data);
    }
}
