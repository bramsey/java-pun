package com.billiamram.puns;

public class App {
  public static void main(String[] args) {
    new PunGenerator.Builder()
        .keyword("pun")
        .minScore(250)
        .minWordCount(5)
        .build()
        .generatePuns()
        .forEach(System.out::println);
  }
}
