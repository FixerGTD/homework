package com.velsera.homework.exceptions;

public class TweetExceptionBadRequest extends TweetException {

    public TweetExceptionBadRequest(String message) {
        super(message);
    }

    public TweetExceptionBadRequest(StringBuilder sb) {
        super(sb);
    }

    public TweetExceptionBadRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public TweetExceptionBadRequest(StringBuilder sb, Throwable cause) {
        super(sb, cause);
    }
}
