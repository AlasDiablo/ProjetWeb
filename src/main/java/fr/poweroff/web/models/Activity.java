package fr.poweroff.web.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity extends Model {

    private Integer activityId;
    private Date    startAt;
    private Date    endAt;
    private String  city;
    private User    owner;
    private Boolean contact;

    Activity(Integer activityId, Date startAt, Date endAt, String city, Boolean contact) {
        this.activityId = activityId;
        this.startAt    = startAt;
        this.endAt      = endAt;
        this.city       = city;
        this.contact    = contact;
    }

    private Activity(User owner) {
        this.activityId = null;
        this.startAt    = null;
        this.endAt      = null;
        this.city       = null;
        this.contact    = null;
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
                    result.getTimestamp("start_at"),
                    result.getTimestamp("end_at"),
                    result.getString("city"),
                    result.getBoolean("contact")
            );
        }
        result.close();
        return activity;
    }

    public static List<User> setContact(Date date, String address) throws SQLException {
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "update activity set contact = true where activity.city = ? and ? >= activity.start_at and ? <= activity.end_at"
        );
        statement.setString(1, address);
        statement.setTimestamp(2, new Timestamp(date.getTime()));
        statement.setTimestamp(3, new Timestamp(date.getTime()));
        statement.executeUpdate();
        statement.close();

        PreparedStatement statementGetter = DataBase.CONNECTION.prepareStatement(
                "select user_id from user_activity inner join activity on user_activity.activity_id = activity.activity_id where activity.city = ? and ? >= activity.start_at and ? <= activity.end_at"
        );
        statementGetter.setString(1, address);
        statementGetter.setTimestamp(2, new Timestamp(date.getTime()));
        statementGetter.setTimestamp(3, new Timestamp(date.getTime()));
        ResultSet  result = statementGetter.executeQuery();
        List<User> users  = new ArrayList<>();
        while (result.next()) {
            users.add(User.getFirst(result.getInt("user_id")));
        }
        statement.close();
        result.close();
        return users;
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
        statement.setTimestamp(1, new Timestamp(this.startAt.getTime()));
        statement.setTimestamp(2, new Timestamp(this.endAt.getTime()));
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

    public Boolean getContact() {
        return contact;
    }

    public void setContact(Boolean contact) {
        this.contact = contact;
    }
}
