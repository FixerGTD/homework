package com.velsera.homework.exceptions;

public class TweetExceptionUnauthorized extends TweetException {

    public TweetExceptionUnauthorized(String message) {
        super(message);
    }

    public TweetExceptionUnauthorized(StringBuilder sb) {
        super(sb);
    }

    public TweetExceptionUnauthorized(String message, Throwable cause) {
        super(message, cause);
    }

    public TweetExceptionUnauthorized(StringBuilder sb, Throwable cause) {
        super(sb, cause);
    }
}
