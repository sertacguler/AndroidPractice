package com.example.juba.restdeneme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private GitHubService service;
    private TextView txt_data;
    private Button get_posts,get_comments,get_userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_data = (TextView) findViewById(R.id.txt_data);
        get_posts = (Button) findViewById(R.id.get_posts);
        get_comments = (Button) findViewById(R.id.get_comments);
        get_userid = (Button) findViewById(R.id.get_userid);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GitHubService.class);

        //getPosts();
        //getComments();
        //getUserId();

        get_posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPosts();
            }
        });
        get_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComments();
            }
        });
        get_userid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserId();
            }
        });
    }

    private void getUserId() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order","desc");

        Call<List<Repo>> repos = service.getUserid(parameters); //üsteki gibi yazmaz isek alttaki gibi yazmalıyız
        //Call<List<Repo>> repos = service.getUserid(4, "id", "desc"); //null null diyebiliriz

        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> allRepos = response.body();
                txt_data.setText("");
                for (Repo repo : allRepos)
                {
                    String content = "";
                    content += "UserId : "+repo.getUserId()+"\n";
                    content += "Id : "+repo.getId()+"\n";
                    content += "Title : "+repo.getTitle()+"\n";
                    content += "Text : "+repo.getText()+"\n\n";
                    txt_data.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();
                txt_data.setText("");
                txt_data.setText("Hata");
            }
        });

    }

    private void getComments() {

        Call<List<Comment>> call = service.getComment( "posts/3/comments");
        //Call<List<Comment>> call = service.getComment(3);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()){
                    txt_data.setText("");
                    txt_data.setText(response.code());
                    return;
                }
                List<Comment> comments = response.body();
                txt_data.setText("");
                for (Comment comment : comments){
                    String content = "";
                    content += "Id : "+comment.getId()+"\n";
                    content += "Post Id : "+comment.getPostId()+"\n";
                    content += "Name : "+comment.getName()+"\n";
                    content += "Email : "+comment.getEmail()+"\n\n";
                    content += "Text : "+comment.getText()+"\n\n";
                    txt_data.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                txt_data.setText("");
                txt_data.setText("Hata");
            }
        });
    }

    private void getPosts() {

        Call<List<Repo>> repos = service.listRepos();

        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> allRepos = response.body();
                txt_data.setText("");
                for (Repo repo : allRepos)
                {
                    String content = "";
                    content += "UserId : "+repo.getUserId()+"\n";
                    content += "Id : "+repo.getId()+"\n";
                    content += "Title : "+repo.getTitle()+"\n";
                    content += "Text : "+repo.getText()+"\n\n";
                    txt_data.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();
                txt_data.setText("");
                txt_data.setText("Hata");
            }
        });
    }
}
