# Introduction

Composite Design pattern is applicable well on hierarchical structures in a way that an operation can be directly executed on a leaf node,
higher level nodes can delegate the operation on their children nodes.

# The domain

In this exercise you can practice the composite design pattern.
The selected domain is the Bible book which is interestingly well-structured in a hierarchy:

- Testaments: old and new; testaments contain books
- Books contain numbered parts
- Parts contain verses (also numbered)
- Verses

Having the structure above, we can define classes for each layer. Objects of the layer represent the concrete Bible elements that can contain lower level elements.

Note: in this exercise for simplicity the testaments are ignored.

# Composite Pattern

The benefit of the hierarchical structure of the Bible is that when we would like to perform some operation on the whole book, it can be performed on the sub-elements, and the result can be aggregated.

Example: to get the number of words of the whole Bible, calculation can be performed on each book, the book can delegate the operation on the parts, etc.

The composite pattern defines a **Component** interface, which is the `Text` interface in our example.
It defines the following operations:

- `int getNumberOfWords()`: calculates the number of words in the Bible
- `List<Verse> getVersesContainingWord(String word)`:  searches for Verses that contain the given word.

![](https://raw.githubusercontent.com/epam-mep-java/exercise-specification-images/main/design-patterns-book/model.png)6


# Examples

### getNumberOfWords

`"This is the sign"` - contains 4 words

Please ignore punctuation marks when counting the words.

`"Abraham , and Sarah"` - contains 3 words

### getVersesContainingWord

Please search whole words. Searching for word "the"

- `"the sign"` - contains the word
- `"created them"` - does not contain the word

### Verse.format

Verse class should support the usual Bible verse formatting, which includes book name, part number and verse number. See example:

    Genesis 2,13 "I have set my rainbow in the clouds"

Please implement `Verse.format()` method accordingly.

# The Task

The project contains the domain classes: `AllBooks`, `Book`, `Part` and `Verse`

Each of these classes must implement the `Text` interface.

- `Verse` directly implement the operations
- `AllBooks`, `Book`, `Part` delegate the operation on the lower-level elements

Expected output of the application:

    Number of all words: 175
    Verses containing the word 'God':
    Genesis 1,27 "So God created mankind in his own image, in the image of God he created them; male and female he created them."
    Genesis 9,12 "And God said, This is the sign of the covenant I am making between me and you and every living creature with you, a covenant for all generations to come:"
    Matthew 1,23 "'The virgin will conceive and give birth to a son, and they will call him Immanuel' (which means 'God with us')."
