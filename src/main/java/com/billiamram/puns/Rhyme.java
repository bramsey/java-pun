package com.billiamram.puns;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface Rhyme {
  String word();

  Integer score();

  Optional<Integer> freq();

  Optional<String> flags();

  Optional<String> syllables();
}
