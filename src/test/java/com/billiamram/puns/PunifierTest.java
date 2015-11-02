package com.billiamram.puns;

import org.junit.Test;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

public class PunifierTest {

  Punifier subject = new Punifier();

  @Test
  public void replaceAll_givenTargetPresentInPhrase_replacesTarget() {
    Optional<String> actualPhrase = subject
        .replaceAll("a phrase", "phrase", "match");

    assertThat(actualPhrase).isEqualTo(Optional.of("a match"));
  }

  @Test
  public void replaceAll_givenTargetMissingFromPhrase_returnsEmpty() {
    Optional<String> actualPhrase = subject
        .replaceAll("a phrase", "mismatch", "match");

    assertThat(actualPhrase).isEqualTo(Optional.empty());
  }
}