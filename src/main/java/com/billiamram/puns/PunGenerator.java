package com.billiamram.puns;

import com.billiamram.puns.service.PhraseService;
import com.billiamram.puns.service.RhymeService;
import com.google.common.base.Splitter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PunGenerator {

  private Punifier punifier;
  private PhraseService phraseService;
  private RhymeService rhymeService;

  private String keyword;
  private int minScore;
  private int minWordCount;

  protected PunGenerator() {
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

  public List<String> generatePuns() {
    List<String> phrases = phraseService.getPhrases();
    List<Rhyme> rhymes = rhymeService.getRhymes(keyword);

    return phrases.stream()
        .filter(p -> getWordCount(p) > minWordCount)
        .flatMap(p -> rhymes.stream()
            .filter(r -> r.score > minScore)
            .map(r -> punifier.replaceAll(p, r.word, keyword)))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

  private int getWordCount(String phrase) {
    return Splitter.on(" ").splitToList(phrase).size();
  }

  public static final class Builder {

    private final PhraseService phraseService;
    private final RhymeService rhymeService;
    private final Punifier punifier;
    private final RhymeClient rhymeClient;

    private String keyword;
    private int minScore;
    private int minWordCount;

    public Builder() {
      rhymeClient = getRhymeClient();
      punifier = getPunifier();
      rhymeService = getRhymeService();
      phraseService = getPhraseService();
    }

    public Builder keyword(String keyword) {
      this.keyword = keyword;
      return this;
    }

    public Builder minScore(int minScore) {
      this.minScore = minScore;
      return this;
    }

    public Builder minWordCount(int minWordCount) {
      this.minWordCount = minWordCount;
      return this;
    }

    public PunGenerator build() {
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

    private RhymeClient getRhymeClient() {
      return new Retrofit.Builder()
          .baseUrl("http://rhymebrain.com")
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(RhymeClient.class);
    }
  }
}
