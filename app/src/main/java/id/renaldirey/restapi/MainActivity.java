package id.renaldirey.restapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.renaldirey.restapi.network.ServiceGenerator;
import id.renaldirey.restapi.network.response.BaseResponse;
import id.renaldirey.restapi.network.service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DataService service;

    private EditText etName;
    private Button btnSubmit;
    private Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListener();

        service = ServiceGenerator.createBaseService(this, DataService.class);
    }

    private void initListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();

                if(isEmpty(name))
                    etName.setError("Must not be empty");
                else
                    addData(name);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataActivity.newInstance(MainActivity.this);
            }
        });
    }

    private void addData(String name) {
        Call<BaseResponse> call = service.apiCreate(name);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.code() == 200) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
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
        btnShow = (Button) findViewById(R.id.btn_show);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }
}
