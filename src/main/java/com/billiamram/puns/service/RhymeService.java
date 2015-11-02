package com.billiamram.puns.service;

import com.billiamram.puns.Rhyme;
import com.billiamram.puns.RhymeClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RhymeService {
  private RhymeClient rhymeClient;

  public RhymeService(RhymeClient rhymeClient) {
    this.rhymeClient = rhymeClient;
  }

  public List<Rhyme> getRhymes(String word) {
    try {
      return rhymeClient
          .getRhymes(word)
          .execute()
          .body();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }
}
