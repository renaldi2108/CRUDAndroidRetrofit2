package id.renaldirey.restapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.renaldirey.restapi.model.Data;
import id.renaldirey.restapi.network.ServiceGenerator;
import id.renaldirey.restapi.network.response.BaseResponse;
import id.renaldirey.restapi.network.service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.TextUtils.isEmpty;

public class UpdateActivity extends AppCompatActivity {

    private static final String TAG = UpdateActivity.class.getSimpleName();

    private EditText etName;
    private Button btnSubmit;
    private DataService service;
    private Data data;

    public static void newInstance(Context context, Data data) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG+".data", data);
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtras(bundle);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        if(getIntent() != null) {
            data = getIntent().getParcelableExtra(TAG+".data");
        }

        initViews();

        service = ServiceGenerator.createBaseService(this, DataService.class);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                Log.e("name", name);

                if(isEmpty(name))
                    etName.setError("Must not be empty");
                else
                    updateData(name);
            }
        });
    }

    private void updateData(String name) {
        Call<BaseResponse> call = service.apiUpdate(data.getId(), name);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.code() == 200) {
                    Toast.makeText(UpdateActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e(TAG + ".error", t.toString());
            }
        });
    }

    private void initViews() {
        etName = (EditText) findViewById(R.id.et_name);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        etName.setText(data.getName());
    }
}
