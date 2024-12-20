package org.poo.fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public final class CommerciantInput {
    private String commerciant;
    private int id;
    private String account;
    private String type;
    private String cashbackStrategy;
}
