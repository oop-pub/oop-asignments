package enums;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum ElvesType {

    @JsonProperty("yellow")
    YELLOW("yellow"),

    @JsonProperty("black")
    BLACK("black"),

    @JsonProperty("pink")
    PINK("pink"),

    @JsonProperty("white")
    WHITE("white");

    private String value;

    ElvesType(final String value) {
        this.value = value;
    }
}
