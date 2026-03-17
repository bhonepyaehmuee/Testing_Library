Feature: Library Book Management

  Scenario: Store book in the library
    Given I have a book with name "The Lord of the Rings", author "J.R.R. Tolkien", and ISBN "0395974684"
    When I send a POST request to "/books"
    Then the response status should be 201
    And the book should be retrievable via GET "/books/0395974684"
    And the retrieved book should have name "The Lord of the Rings" and author "J.R.R. Tolkien" and ISBN "0395974684"