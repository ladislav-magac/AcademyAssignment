package sk.ness.academy.exception;

import sk.ness.academy.domain.Comment;

public class CommentBodyNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CommentBodyNotFoundException(Comment comment) {
        this.comment = comment;
    }

    private Comment comment;

    public Comment getComment() {
        return comment;
    }
}