package com.epam.training.book;

import com.epam.training.book.domain.AllBooks;
import com.epam.training.book.domain.Book;
import com.epam.training.book.domain.Part;
import com.epam.training.book.domain.Verse;

public class TestDataCreator {
    public static AllBooks createAllBooks() {
        AllBooks newAllBooks = new AllBooks();

        newAllBooks.addBook(createBookGenesis());
        newAllBooks.addBook(createBookMatthew());

        return newAllBooks;
    }

    private static Book createBookGenesis() {
        Book bookGenesis = new Book("Genesis");
        Part genesisPart9 = new Part(9);
        Part genesisPart1 = new Part(1);
        Verse verse27 = new Verse(27,
                "So God created mankind in his own image, in the image of God he created them; male and female he created them.");
        Verse verse12 = new Verse(12,
                "And God said, This is the sign of the covenant I am making between me and you and every living creature with you, a covenant for all generations to come:");
        Verse verse13 = new Verse(13,
                "I have set my rainbow in the clouds, and it will be the sign of the covenant between me and the earth.");

        bookGenesis.addPart(genesisPart1);
        genesisPart1.addVerse(verse27);
        bookGenesis.addPart(genesisPart9);
        genesisPart9.addVerse(verse12);
        genesisPart9.addVerse(verse13);

        return bookGenesis;
    }

    private static Book createBookMatthew() {
        Book bookMatthew = new Book("Matthew");
        Part matthewPart1 = new Part(1);
        Part matthewPart6 = new Part(6);
        Verse verse21 = new Verse(21,
                "She will give birth to a son, and you are to give him the name Jesus, because he will save his people from their sins.");
        Verse verse22 = new Verse(22, "All this took place to fulfill what the Lord had said through the prophet:");
        Verse verse23 = new Verse(23,
                "'The virgin will conceive and give birth to a son, and they will call him Immanuel' (which means 'God with us').");
        Verse verse6 =
                new Verse(3,
                        "But when you give to the needy, do not let your left hand know what your right hand is doing,");
        Verse verse4 = new Verse(4,
                "so that your giving may be in secret. Then your Father, who sees what is done in secret, will reward you.");

        matthewPart1.addVerse(verse21);
        matthewPart1.addVerse(verse22);
        matthewPart1.addVerse(verse23);
        matthewPart6.addVerse(verse6);
        bookMatthew.addPart(matthewPart1);
        matthewPart6.addVerse(verse4);
        bookMatthew.addPart(matthewPart6);

        return bookMatthew;
    }
}
