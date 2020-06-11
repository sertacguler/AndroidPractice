package com.example.juba.restdeneme;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface GitHubService {

    @GET("posts")
    Call<List<Repo>> listRepos();

    @GET("posts")
    Call<List<Repo>> getUserid(
            @Query("userId") int userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    // üstteki yere bir tane daha userId2 ekleyebiklrisin int değil
    // Integer olarak düzelt ikisinide sonra parametre larak 3 sayı gönder cevap 2 side geliyor
    // Yada Interger[] böyle yazabilirm ozmana paramentre tarfaına new Integer[]{2,3,6} yazardım
    // path de ise yani yol üstündeyse @Path diyoz.
    // ama sorgu ise yani userid=? bu ise @Query diyoz.


    @GET("posts")
    Call<List<Repo>> getUserid(@QueryMap Map<String, String> parameters);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComment(@Path("id") int postId);

    @GET
    Call<List<Comment>> getComment(@Url String url);
}


