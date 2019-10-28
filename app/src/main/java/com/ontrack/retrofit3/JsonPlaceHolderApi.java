package com.ontrack.retrofit3;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    @GET("getModels")
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] id,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("getModels")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET
    Call<List<Comment>> getComments(@Url String url);

    @POST("getModels")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("getModels")
    Call<Post> createPost(
            @Field("id") int id,
            @Field("name") String name,
            @Field("ignition") String ignition

    );

    @FormUrlEncoded
    @POST("getModels")
    Call<Post> createPost(@FieldMap Map<String, String> fields);

 /* to include header but this is not a correct way

  @Headers({"Static-Header1: 123", "Static-Header2: 456"})
    @PUT("posts/{id}")
    Call<Post> putPost(@Header("Dynamic-Header") String header,
                       @Path("id") int id,
                       @Body Post post);
*/
    @PATCH("posts/{id}")
    Call<Post> patchPost(@HeaderMap Map<String, String> headers,
                         @Path("id") int id,
                         @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
