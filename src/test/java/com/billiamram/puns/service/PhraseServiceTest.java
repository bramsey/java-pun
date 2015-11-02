package com.billiamram.puns.service;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class PhraseServiceTest {

  PhraseService subject = new PhraseService();

  @Test
  public void getPhrases() throws Exception {
    assertThat(subject.getPhrases()).containsAllOf(
        "Any Time at All",
        "\"Bond. James Bond.\"",
        "Harry Potter and the Chamber of Secrets",
        "The Adventures of Robin Hood",
        "get off my back");
  }
}