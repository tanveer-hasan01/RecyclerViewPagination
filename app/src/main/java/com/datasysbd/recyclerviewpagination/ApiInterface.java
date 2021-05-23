package com.datasysbd.recyclerviewpagination;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

@GET("v2/list")
Call<List> getData(
     @Query("page")int page,
     @Query("limit") int limit
);

}
