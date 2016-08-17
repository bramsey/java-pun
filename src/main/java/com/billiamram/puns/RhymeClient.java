package com.billiamram.puns;


import com.billiamram.puns.domain.Rhyme;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import java.util.List;

public interface RhymeClient {
  @GET("/talk?function=getRhymes&maxResults=0&lang=en")
  Observable<List<Rhyme>> getRhymes(@Query("word") String word);
}
