package com.billiamram.puns;

public class App {
  public static void main(String[] args) {
    new PunGenerator.Builder()
        .keyword("pun")
        .minScore(260)
        .minWordCount(3)
        .build()
        .generatePuns()
        .forEach(System.out::println);
  }
}
