//TASK 6
package sk.ness.academy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CommentExceptionController {
    @ExceptionHandler(value = CommentBodyNotFoundException.class)
    public ResponseEntity<Object> exception(CommentBodyNotFoundException exception) {
        List<String> message = new ArrayList<>();
        message.add("{\n");
        if (exception.getComment().getAuthor() == null) {
            message.add("    \"author\": [\n        \"This field is required.\"\n    ]");
        } else if (exception.getComment().getAuthor().isBlank()) {
            message.add("    \"author\": [\n        \"This field may not be blank.\"\n    ]");
        }
        if (exception.getComment().getText() == null) {
            message.add("    \"text\": [\n        \"This field is required.\"\n    ]");
        } else if (exception.getComment().getText().isBlank()) {
            message.add("    \"text\": [\n        \"This field may not be blank.\"\n    ]");
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

    @ExceptionHandler(value = CommentsNotFoundException.class)
    public ResponseEntity<Object> exception(CommentsNotFoundException exception) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = CommentNotFoundException.class)
    public ResponseEntity<Object> exception(CommentNotFoundException exception) {
        return new ResponseEntity<>("{\n\"detail\": \"Not found.\"\n}", HttpStatus.NOT_FOUND);
    }
}
//TASK 6