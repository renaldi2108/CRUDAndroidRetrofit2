package id.renaldirey.restapi.network.service;

import java.util.List;

import id.renaldirey.restapi.model.Data;
import id.renaldirey.restapi.network.Endpoint;
import id.renaldirey.restapi.network.response.BaseResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DataService {
    @FormUrlEncoded
    @POST(Endpoint.API_CREATE)
    Call<BaseResponse> apiCreate(@Field("name") String nama);

    @GET(Endpoint.API_READ)
    Call<BaseResponse<List<Data>>> apiRead();

    @FormUrlEncoded
    @POST(Endpoint.API_UPDATE+"{id}")
    Call<BaseResponse> apiUpdate(
            @Path("id") String id,
            @Field("name") String name
    );

    @POST(Endpoint.API_DELETE+"{id}")
    Call<BaseResponse> apiDelete(@Path("id") String id);
}
