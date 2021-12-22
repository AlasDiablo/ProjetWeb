package fr.poweroff.web.models;

import java.sql.SQLException;

public abstract class Model {
    protected abstract void checkIntegrity() throws IllegalStateException;

    public void save() throws SQLException, IllegalStateException {
        this.checkIntegrity();
    }

    public void update() throws SQLException, IllegalStateException {
        this.checkIntegrity();
    }

    public void delete() throws SQLException, IllegalStateException {
        this.checkIntegrity();
    }
}
