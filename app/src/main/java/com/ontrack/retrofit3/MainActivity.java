package com.ontrack.retrofit3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private ImageView imageView;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<Post> mExampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //a textViewResult = findViewById(R.id.text_view_result);

       //a imageView= findViewById(R.id.imageView);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request newRequest = originalRequest.newBuilder()
                                .header("Authorization", "Basic YWRtaW5BdXRoOnZpc2h1QDE5OTI=")
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.on-track.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getPosts();
        //getComments();
        //createPost();
        //updatePost();
        //deletePost();
    }

    private void getPosts() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
//for loop to fetch all item present in json file

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String BikeIgnition = "";
                    String Bikeimgurl= "";
                    String BikeId= "";
                    String BikeName="";
                   // ImageView img= imageView;
              //      ImageView imgView = new ImageView(MainActivity.this); //create imageview dynamically
                   // content += "ID: " + post.getId() + "\n";
                    BikeId= "Bike ID: " + post.getId();
                    BikeName = post.getName();
                    BikeIgnition = "Bike Ignition: " + post.getIgnition();
                    Bikeimgurl= post.getImage();

                    mExampleList.add(new Post(BikeId, BikeName, Bikeimgurl, BikeIgnition));


               //     textViewResult.append(content);

                /*    Glide
                            .with(getApplicationContext())
                            .load(imgurl)
                            .override(80, 80)
                            .into(imageView); */
                }
                mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                mRecyclerView.setAdapter(mExampleAdapter);
            }


            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }






    //to fetch data from another api like comments

    /*
    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHolderApi
                .getComments("https://jsonplaceholder.typicode.com/posts/3/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for (Comment comment : comments) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }    */





   //to add or create something on json

    private void createPost() {

        //input your data here which u want to insert in json file (manual method)

     Post post = new Post("23", "Bike Name", "Bike_Image_Url", "type ignition here");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
              //  content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getId() + "\n";
                content += "Title: " + postResponse.getName() + "\n";
                content += "Text: " + postResponse.getIgnition() + "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
// To update data


    private void updatePost() {

        //enter you data here which you want to update
       Post post = new Post("12", null, "image_url", "ignition");

        Map<String, String> headers = new HashMap<>();
        headers.put("Map-Header1", "def");
        headers.put("Map-Header2", "ghi");

        //Enter post ID here with which you want to update yourdata
      Call<Post> call = jsonPlaceHolderApi.patchPost(headers, 5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
            //    content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getId() + "\n";
                content += "Title: " + postResponse.getName() + "\n";
                content += "Text: " + postResponse.getIgnition() + "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }





    //To delete any data from json
    private void deletePost() {

        //Only Enter Post ID Which you Want to Delete

        Call<Void> call = jsonPlaceHolderApi.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


}
