package org.poo.reporting;

public interface AccountElement {
    /**
     * @param visitor
     */
    void accept(AccountVisitor visitor);
}
