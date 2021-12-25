package fr.poweroff.web.models;

import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Model {
    protected abstract void checkIntegrity(boolean isSave) throws IllegalStateException;

    protected abstract void setValueToQuery(@NotNull PreparedStatement statement) throws SQLException;

    public void save() throws SQLException, IllegalStateException {
        this.checkIntegrity(true);
    }

    public void update() throws SQLException, IllegalStateException {
        this.checkIntegrity(false);
    }

    public void delete() throws SQLException, IllegalStateException {
        this.checkIntegrity(false);
    }
}
