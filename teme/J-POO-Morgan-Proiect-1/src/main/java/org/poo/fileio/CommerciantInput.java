package org.poo.fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public final class CommerciantInput {
    private int id;
    private String description;
    private List<String> commerciants;
}
