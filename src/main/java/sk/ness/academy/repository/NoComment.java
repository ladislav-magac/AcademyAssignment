package sk.ness.academy.repository;

import java.util.Date;
public interface NoComment {
    Integer getId();
    String getTitle();
    String getAuthor();
    String getText();
    Date getCreateTimestamp();

}