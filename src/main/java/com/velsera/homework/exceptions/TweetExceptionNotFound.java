package com.velsera.homework.exceptions;

public class TweetExceptionNotFound extends TweetException {

    public TweetExceptionNotFound(String message) {
        super(message);
    }

    public TweetExceptionNotFound(StringBuilder sb) {
        super(sb);
    }

    public TweetExceptionNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public TweetExceptionNotFound(StringBuilder sb, Throwable cause) {
        super(sb, cause);
    }
}
