package checker;

import java.util.Objects;

/**
 * DO NOT MODIFY
 * The class that contains the id and message of each QueryTest
 */
public final class QueryTest {
    /**
     * id of the action
     */
    private int id;
    /**
     * message to be written
     */
    private String message;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null
                || getClass() != o.getClass()) {
            return false;
        }
        QueryTest queryTest = (QueryTest) o;
        return id == queryTest.id
                && Objects.equals(message, queryTest.message);
    }

    @Override
    public String toString() {
        return "QueryTest{"
                + "id="
                + id
                + ", message='"
                + message
                + '\''
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message);
    }
}
