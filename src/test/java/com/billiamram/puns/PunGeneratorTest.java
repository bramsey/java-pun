package com.billiamram.puns;

import com.billiamram.puns.service.PhraseService;
import com.billiamram.puns.service.RhymeService;
import com.billiamram.puns.support.ReflectionUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.stub;

public class PunGeneratorTest {

  @Mock
  PhraseService phraseService;

  @Mock
  RhymeService rhymeService;

  @InjectMocks
  PunGenerator subject;

  @Before
  public void setUp() throws NoSuchFieldException, IllegalAccessException {
    subject = new PunGenerator();
    ReflectionUtil.setField(subject, "punifier", new Punifier());
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void puns() throws NoSuchFieldException, IllegalAccessException {

    String keyword = "pun";
    List<String> phrases = Arrays.asList(
        "unrelated phrase",
        "that's a good one",
        "one for the money. two for the show.");

    Rhyme rhyme = ImmutableRhyme.builder()
        .word("one")
        .score(300)
        .build();

    stub(phraseService.getPhrases()).toReturn(phrases);
    stub(rhymeService.getRhymes(keyword))
        .toReturn(Collections.singletonList(rhyme));

    ReflectionUtil.setField(subject, "keyword", keyword);

    assertThat(subject.generatePuns()).containsExactly(
        "that's a good pun",
        "pun for the money. two for the show.");
  }
}