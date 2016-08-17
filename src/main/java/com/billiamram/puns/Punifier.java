package com.billiamram.puns;

import java.util.Optional;

class Punifier {
  Punifier() {
  }

  Optional<String> replaceAll(String phrase,
                              String target,
                              String replacement) {
    String regex = String.format("\\b%s\\b", target);

    String newPhrase = phrase.replaceAll(regex, replacement);

    return phrase.equals(newPhrase) ?
        Optional.empty() : Optional.of(newPhrase);
  }
}