package com.velsera.homework.exceptions;

public class TweetException extends RuntimeException {

    public TweetException(String message) {
        super(message);
    }

    public TweetException(StringBuilder sb) {
        super(sb.toString());
    }

    public TweetException(String message, Throwable cause) {
        super(message, cause);
    }

    public TweetException(StringBuilder sb, Throwable cause) {
        super(sb.toString(), cause);
    }
}
