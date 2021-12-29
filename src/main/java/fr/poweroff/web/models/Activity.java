package fr.poweroff.web.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Activity extends Model {

    private Integer activityId;
    private Date    startAt;
    private Date    endAt;
    private String  city;
    private User    owner;

    Activity(Integer activityId, Date startAt, Date endAt, String city) {
        this.activityId = activityId;
        this.startAt    = startAt;
        this.endAt      = endAt;
        this.city       = city;
    }

    private Activity(User owner) {
        this.activityId = null;
        this.startAt    = null;
        this.endAt      = null;
        this.city       = null;
        this.owner      = owner;
    }

    @Contract(" -> new")
    public static @NotNull Activity create() {
        return new Activity(null);
    }

    @Contract("_ -> new")
    public static @NotNull Activity create(@Nullable User owner) {
        return new Activity(owner);
    }

    @Contract("_ -> new")
    public static @NotNull Activity create(@NotNull Integer userId) throws SQLException {
        return new Activity(User.getFirst(userId));
    }

    public static @Nullable Activity getFirst(Integer activityId) throws SQLException {
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "select * from activity where activity_id=?"
        );
        statement.setInt(1, activityId);
        ResultSet result   = statement.executeQuery();
        Activity  activity = null;
        if (result.next()) {
            activity = new Activity(
                    activityId,
                    result.getDate("start_at"),
                    result.getDate("end_at"),
                    result.getString("city")
            );
        }
        result.close();
        return activity;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", text='" + city + '\'' +
                "} " + super.toString();
    }

    @Override
    protected void checkIntegrity(boolean isSave) throws IllegalStateException {
        if (this.startAt == null || this.endAt == null || this.city == null) {
            throw new IllegalStateException("Activity component can't be null");
        }
        if (!isSave && this.activityId == null) {
            throw new IllegalStateException("Activity ID can't be null");
        }
        if (isSave && this.owner == null) {
            throw new IllegalStateException("Owner can't be null");
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
        statement.setInt(4, this.activityId);
        statement.executeUpdate();
        statement.close();
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

        PreparedStatement statementInnerJoin = DataBase.CONNECTION.prepareStatement(
                "insert into user_activity(user_id, activity_id) value (?, ?)"
        );
        statementInnerJoin.setInt(1, this.owner.getUserId());
        statementInnerJoin.setInt(2, this.activityId);
        statementInnerJoin.executeUpdate();
        statementInnerJoin.close();
    }

    @Override
    protected void setValueToQuery(@NotNull PreparedStatement statement) throws SQLException {
        statement.setDate(1, this.startAt);
        statement.setDate(2, this.endAt);
        statement.setString(3, this.city);
    }

    public User getOwner() throws SQLException {
        if (this.owner == null) {
            PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                    "select user.user_id from user inner join user_activity on user.user_id = user_activity.user_id where activity_id = ?"
            );
            statement.setInt(1, this.activityId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int userId = result.getInt(1);
                this.owner = User.getFirst(userId);
            }
        }
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public String getCity() {
        return city;
    }

    public void setCity(@NotNull String city) {
        this.city = city;
    }
}
