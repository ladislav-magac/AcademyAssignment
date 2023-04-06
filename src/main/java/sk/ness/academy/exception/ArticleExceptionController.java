package sk.ness.academy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ArticleExceptionController {
    @ExceptionHandler(value = ArticleBodyNotFoundException.class)
    public ResponseEntity<Object> exception(ArticleBodyNotFoundException exception) {
        List<String> message = new ArrayList<>();
        message.add("{\n");
        if (exception.getArticle().getAuthor() == null) {
            message.add("    \"author\": [\n        \"This field is required.\"\n    ]");
        } else if (exception.getArticle().getAuthor().isBlank()) {
            message.add("    \"author\": [\n        \"This field may not be blank.\"\n    ]");
        }
        if (exception.getArticle().getText() == null) {
            message.add("    \"text\": [\n        \"This field is required.\"\n    ]");
        } else if (exception.getArticle().getText().isBlank()) {
            message.add("    \"text\": [\n        \"This field may not be blank.\"\n    ]");
        }
        if (exception.getArticle().getTitle() == null) {
            message.add("    \"title\": [\n        \"This field is required.\"\n    ]");
        } else if (exception.getArticle().getTitle().isBlank()) {
            message.add("    \"title\": [\n        \"This field may not be blank.\"\n    ]");
        }
        message.add("\n}");
        String responseBody = "";
        for (int i = 0; i < message.size(); i++) {
            if (i > 1 && i < message.size() - 1) {
                responseBody += ",\n";
            }
            responseBody += message.get(i);
        }
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ArticlesNotFoundException.class)
    public ResponseEntity<Object> exception(ArticlesNotFoundException exception) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = ArticleNotFoundException.class)
    public ResponseEntity<Object> exception(ArticleNotFoundException exception) {
        return new ResponseEntity<>("{\n    \"detail\": \"Not found.\"\n}", HttpStatus.NOT_FOUND);
    }
}