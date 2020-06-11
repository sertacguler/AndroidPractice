package com.example.juba.restpostdeneme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private GitHubService service;
    private TextView txt_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_content = (TextView) findViewById(R.id.txt_content);

        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GitHubService.class);

        createPost();
        patchPost();
        updatePost();
        deletePost();
    }

    private void deletePost() {

        Call<Void> call = service.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                txt_content.append("Delete Post : \n");
                txt_content.append("Code : " + response.code() + "\n\n");
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                txt_content.setText(t.getMessage());
            }
        });
    }

    private void patchPost() {
        Post post = new Post(1,"null", "New Text");
        Call<Post> call = service.patchPost(1,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    txt_content.append("Code :" + response.code());
                    return;
                }
                txt_content.append("Patch Post : \n");
                Post postResponse = response.body();
                String content = "";
                content += "Code : " + response.code() + "\n";
                content += "ID : " + postResponse.getId() + "\n";
                content += "User ID : " + postResponse.getUserId() + "\n";
                content += "Title : " + postResponse.getTitle() + "\n";
                content += "Text : " + postResponse.getText() + "\n\n";
                txt_content.append(content);
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txt_content.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        Post post = new Post(1,"null", "New Text");
        Call<Post> call = service.putPost(1,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    txt_content.append("Code :" + response.code());
                    return;
                }
                txt_content.append("Update Post : \n");
                Post postResponse = response.body();
                String content = "";
                content += "Code : " + response.code() + "\n";
                content += "ID : " + postResponse.getId() + "\n";
                content += "User ID : " + postResponse.getUserId() + "\n";
                content += "Title : " + postResponse.getTitle() + "\n";
                content += "Text : " + postResponse.getText() + "\n\n";
                txt_content.append(content);
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txt_content.setText(t.getMessage());
            }
        });
    }

    public void createPost() {
        Post post = new Post(30,"New Title", "New Text");
        Call<Post> call = service.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    txt_content.append("Code :" + response.code());
                    return;
                }
                txt_content.append("Create Post : \n");
                txt_content.append("Post edilen -> UserId:30, Title:New Title, Text:New Text \n\n");
                Post postResponse = response.body();
                String content = "";
                content += "Code : " + response.code() + "\n";
                content += "ID : " + postResponse.getId() + "\n";
                content += "User ID : " + postResponse.getUserId() + "\n";
                content += "Title : " + postResponse.getTitle() + "\n";
                content += "Text : " + postResponse.getText() + "\n\n";
                txt_content.append(content);
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txt_content.setText(t.getMessage());
            }
        });
    }
}
