package com.velsera.homework.exceptions;

public class TweetExceptionForbidden extends TweetException {

    public TweetExceptionForbidden(String message) {
        super(message);
    }

    public TweetExceptionForbidden(StringBuilder sb) {
        super(sb);
    }

    public TweetExceptionForbidden(String message, Throwable cause) {
        super(message, cause);
    }

    public TweetExceptionForbidden(StringBuilder sb, Throwable cause) {
        super(sb, cause);
    }
}
