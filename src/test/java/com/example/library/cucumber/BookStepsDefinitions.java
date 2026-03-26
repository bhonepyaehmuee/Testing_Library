package com.example.library.cucumber;

import com.example.library.dto.request.BookRequestDTO;
import com.example.library.dto.response.ApiResponse;
import com.example.library.dto.response.BookResponseDTO;
import com.example.library.repository.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
//@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookStepsDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    private BookRequestDTO bookRequest;
    private ResponseEntity<String> postResponse;
    private ResponseEntity<String> getResponse;

    @Before
    public void cleanDatabase() {
        bookRepository.deleteAll();
        log.info("Database cleaned before test");
    }

    @Given("I have a book with name {string}, author {string}, and ISBN {string}")
    public void i_have_a_book(String name, String author, String isbn) {
        bookRequest = new BookRequestDTO();
        bookRequest.setName(name);
        bookRequest.setAuthors(author);
        bookRequest.setIsbn(isbn);
        bookRequest.setPrice(10.0);
        log.info("Created book request: {} by {}", name, author);
    }

    @When("I send a POST request to {string}")
    public void i_send_a_post_request(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BookRequestDTO> request = new HttpEntity<>(bookRequest, headers);
        postResponse = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        log.info("POST response status: {}", postResponse.getStatusCode());
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int statusCode) {
        assertEquals(statusCode, postResponse.getStatusCodeValue());
        log.info("Status code verified: {}", statusCode);
    }

    @Then("the book should be retrievable via GET {string}")
    public void the_book_should_be_retrievable_via_get(String url) {
        getResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        log.info("GET request successful");
    }

    @Then("the retrieved book should have name {string} and author {string} and ISBN {string}")
    public void the_retrieved_book_should_have(String name, String author, String isbn) throws Exception {
        assertNotNull(getResponse.getBody());

        ApiResponse<BookResponseDTO> apiResponse = objectMapper.readValue(
                getResponse.getBody(),
                new TypeReference<ApiResponse<BookResponseDTO>>() {}
        );

        log.info("Retrieved book: {}", apiResponse.getData());

        BookResponseDTO retrieved = apiResponse.getData();
        assertNotNull(retrieved);

        assertEquals(name, retrieved.getName());
        assertEquals(author, retrieved.getAuthor());
        assertEquals(isbn, retrieved.getIsbn());

        log.info("Book verification successful: {} by {}", name, author);
    }
}