package android.betwarendpoint.net.cateraapplication.api;

import android.betwarendpoint.net.cateraapplication.MyDocumentDto;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApiController {

  private   Retrofit retrofit;

 private MyApiService apirService;

 private final String API_BASE_URL = "http://10.0.0.84:443/";

    public MyApiController() {

   OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
httpClient.addInterceptor(new HeaderInterceptor());

//          new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
//                Request original = chain.request();
//                Request  request = original.newBuilder()
//                        .header("Access-Control-Allow-Credentials", "true")
//                        .header("content-type" ,"application/x-www-form-urlencoded; charset=UTF-8")
//                        .header("Authorization","Basic Y2xpZW50LWFwcDp0ZXN0c2VjcmV0")
//                        .method( original.method()   , original.body())
//                        .build();
//
//                return chain.proceed(request);
//            }
//        });
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient clientInter = new OkHttpClient.Builder().addInterceptor(interceptor).build();
   OkHttpClient client = httpClient.build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
             .client(client)
                .client(clientInter)
                .build();

        this.apirService = retrofit.create(MyApiService.class);

    }

    public void  gteUsers(){
        apirService.
                listRepos()
                .enqueue(new Callback<List<MyDocumentDto>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<MyDocumentDto>> call,
                                           @NonNull Response<List<MyDocumentDto>> response) {

             }

                    @Override
                    public void onFailure(@NonNull Call<List<MyDocumentDto>> call,
                            @NonNull Throwable t) {

                 }
            });
    }



    public void uploadFile(   RequestBody requestFile , File file) {
        // create upload service client

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestFile);


        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",
                              file.getName(),
                        RequestBody.create(MediaType.parse("text/plain"), file))
                .build();


        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = this.apirService.upload(formBody, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

}
