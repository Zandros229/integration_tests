package edu.iis.mto.blog.domain.errors;

public class DomainError extends RuntimeException {

    public static final String USER_NOT_CONFIRMED = "cannot like post with unconfirmed acc";
    private static final long serialVersionUID = 1L;
    public static final String UNCONFIRMED_USER = "given user is not confirmed";
    public static final String REMOVED_USER = "given user is removed";

    public static final String USER_NOT_FOUND = "unknown user";
    public static final String POST_NOT_FOUND = "unknown post";
    public static final String SELF_LIKE = "cannot like own post";

    public DomainError(String msg) {
        super(msg);
    }

}
