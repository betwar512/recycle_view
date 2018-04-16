package android.betwarendpoint.net.cateraapplication.api;

import android.betwarendpoint.net.cateraapplication.MyDocumentDto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface MyApiService {


    @GET("/api/user/all")
    Call<List<MyDocumentDto>> listRepos();

    @Multipart
    @Headers("Content-Type:multipart/form-data")
    @POST("/api/fs/all")
    Call<ResponseBody> upload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );
}
