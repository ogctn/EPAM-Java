package com.epam.training.book;

import com.epam.training.book.domain.Verse;
import java.util.List;

public interface Text {
    int getNumberOfWords();

    List<Verse> getVersesContainingWord(String word);
}
