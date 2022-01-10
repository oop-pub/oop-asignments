package enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CityStrategyEnum {
    @JsonProperty("niceScoreCity")
    NICE_SCORE_CITY("niceScoreCity"),

    @JsonProperty("id")
    ID("id"),

    @JsonProperty("niceScore")
    NICE_SCORE("niceScore");

    private String value;

    CityStrategyEnum(final String value) {
        this.value = value;
    }
}
