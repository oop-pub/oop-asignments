/**
 * Actions that need to be performed upon a patient after an investigation.
 * Can be used by the objects representing doctors/nurses/er_technicians or the queues.
 *
 * [Part of the homework's skeleton]
 */
public enum InvestigationResult {

    OPERATE ("operate"),
    HOSPITALIZE("hospitalize"),
    TREATMENT ("treatment"),
    NOT_DIAGNOSED ("not diagnosed")
    ;
    private String value;

    InvestigationResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
