package fr.poweroff.web.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Activity extends Model {

    private Integer activityId;
    private Date    startAt;
    private Date    endAt;
    private String  text;

    public Activity(Integer activityId, Date startAt, Date endAt, String text) {
        this.activityId = activityId;
        this.startAt    = startAt;
        this.endAt      = endAt;
        this.text       = text;
    }

    public Activity() {
        this.activityId = null;
        this.startAt    = null;
        this.endAt      = null;
        this.text       = null;
    }

    @Contract(" -> new")
    public static @NotNull Activity create() {
        return new Activity();
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", text='" + text + '\'' +
                "} " + super.toString();
    }

    @Override
    protected void checkIntegrity(boolean isSave) throws IllegalStateException {
        if (this.startAt == null || this.endAt == null || this.text == null) {
            throw new IllegalStateException("Activity component can't be null");
        }
        if (!isSave && this.activityId == null) {
            throw new IllegalStateException("Activity component can't be null");
        }
    }

    @Override
    public void delete() throws SQLException, IllegalStateException {
        super.delete();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "delete from activity where activity_id = ?"
        );
        statement.setInt(1, this.activityId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void update() throws SQLException, IllegalStateException {
        super.update();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "update activity set start_at = ?, end_at = ?, city = ? where activity_id = ?"
        );
        this.setValueToQuery(statement);
        statement.executeUpdate();
        statement.close();
        PreparedStatement statementIdGetter = DataBase.CONNECTION.prepareStatement(
                "select activity_id from activity where start_at = ? and end_at = ? and city = ?"
        );
        this.setValueToQuery(statementIdGetter);
        ResultSet result = statementIdGetter.executeQuery();
        if (result.next()) {
            this.activityId = result.getInt("activity_id");
        } else {
            throw new IllegalStateException("The current activity have not been save properly");
        }
    }

    @Override
    public void save() throws SQLException, IllegalStateException {
        super.save();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "insert into activity(start_at, end_at, city) value (?, ?, ?)"
        );
        this.setValueToQuery(statement);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    protected void setValueToQuery(@NotNull PreparedStatement statement) throws SQLException {
        statement.setDate(1, this.startAt);
        statement.setDate(2, this.endAt);
        statement.setString(3, this.text);
    }

    public Integer getActivityId() {
        return activityId;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(@NotNull Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(@NotNull Date endAt) {
        this.endAt = endAt;
    }

    public String getText() {
        return text;
    }

    public void setText(@NotNull String text) {
        this.text = text;
    }
}
