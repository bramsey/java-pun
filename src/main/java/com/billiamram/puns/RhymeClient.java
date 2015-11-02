package com.billiamram.puns;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

public interface RhymeClient {
  @GET("/talk?function=getRhymes&maxResults=0&lang=en")
  Call<List<Rhyme>> getRhymes(@Query("word") String word);
}
