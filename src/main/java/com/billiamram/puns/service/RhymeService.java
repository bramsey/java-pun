package com.billiamram.puns.service;

import com.billiamram.puns.RhymeClient;
import com.billiamram.puns.domain.Rhyme;

import java.util.Collections;
import java.util.List;

public class RhymeService {
  private RhymeClient rhymeClient;

  public RhymeService(RhymeClient rhymeClient) {
    this.rhymeClient = rhymeClient;
  }

  public List<Rhyme> getRhymes(String word) {
    return rhymeClient
        .getRhymes(word)
        .toBlocking()
        .firstOrDefault(Collections.emptyList());
  }
}
