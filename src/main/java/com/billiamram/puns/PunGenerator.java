package com.billiamram.puns;

import com.billiamram.puns.domain.GsonAdaptersDomain;
import com.billiamram.puns.domain.Rhyme;
import com.billiamram.puns.service.PhraseService;
import com.billiamram.puns.service.RhymeService;
import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class PunGenerator {

  private Punifier punifier;
  private PhraseService phraseService;
  private RhymeService rhymeService;

  private String keyword;
  private int minScore;
  private int minWordCount;

  PunGenerator() {
  }

  private PunGenerator(Punifier punifier,
                       PhraseService phraseService,
                       RhymeService rhymeService,
                       Builder builder) {
    this.punifier = punifier;
    this.phraseService = phraseService;
    this.rhymeService = rhymeService;
    this.keyword = builder.keyword;
    this.minScore = builder.minScore;
    this.minWordCount = builder.minWordCount;
  }

  List<String> generatePuns() {
    List<String> phrases = phraseService.getPhrases();
    List<Rhyme> rhymes = rhymeService.getRhymes(keyword);

    return phrases.stream()
        .filter(p -> getWordCount(p) > minWordCount)
        .flatMap(p -> rhymes.stream()
            .filter(r -> r.score() > minScore)
            .map(r -> punifier.replaceAll(p, r.word(), keyword)))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

  private int getWordCount(String phrase) {
    return Splitter.on(" ").splitToList(phrase).size();
  }

  static final class Builder {

    private final PhraseService phraseService;
    private final RhymeService rhymeService;
    private final Punifier punifier;
    private final RhymeClient rhymeClient;

    private String keyword;
    private int minScore;
    private int minWordCount;

    Builder() {
      rhymeClient = getRhymeClient();
      punifier = getPunifier();
      rhymeService = getRhymeService();
      phraseService = getPhraseService();
    }

    Builder keyword(String keyword) {
      this.keyword = keyword;
      return this;
    }

    Builder minScore(int minScore) {
      this.minScore = minScore;
      return this;
    }

    Builder minWordCount(int minWordCount) {
      this.minWordCount = minWordCount;
      return this;
    }

    PunGenerator build() {
      return new PunGenerator(punifier, phraseService, rhymeService, this);
    }

    private static Punifier getPunifier() {
      return new Punifier();
    }

    private RhymeService getRhymeService() {
      return new RhymeService(rhymeClient);
    }

    private PhraseService getPhraseService() {
      return new PhraseService();
    }

    private Gson getGson() {
      return new GsonBuilder()
          .registerTypeAdapterFactory(new GsonAdaptersDomain())
          .create();
    }

    private RhymeClient getRhymeClient() {
      return new Retrofit.Builder()
          .baseUrl("http://rhymebrain.com")
          .addConverterFactory(GsonConverterFactory.create(getGson()))
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build()
          .create(RhymeClient.class);
    }
  }
}
