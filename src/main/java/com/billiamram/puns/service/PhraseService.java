package com.billiamram.puns.service;

import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PhraseService {

  private static final List<String> PHRASE_FILES = ImmutableList.of(
      "beatles_songs.txt",
      "movie-quotes.txt",
      "wikipedia_idioms.txt",
      "oscar_winning_movies.txt",
      "best-selling-books.txt"
  );

  private final String basePath = this.getClass().getClassLoader().getResource("").getPath();

  public List<String> getPhrases() {
    return PHRASE_FILES.stream()
        .map(this::getPath)
        .flatMap(this::getLines)
        .collect(Collectors.toList());
  }

  private Stream<String> getLines(String path) {
    try {
      return Files.lines(Paths.get(path));
    } catch (IOException e) {
      e.printStackTrace();
      return Stream.empty();
    }
  }

  private String getPath(String phraseFile) {
    return String.format("%s../../resources/main/%s", basePath, phraseFile);
  }
}
