package fr.poweroff.web.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Notification extends Model {

    private Integer notificationId;
    private String  content;
    private Boolean unRead;
    private User    target;

    private Notification(User target) {
        this.notificationId = null;
        this.content        = null;
        this.unRead         = null;
        this.target         = target;
    }

    private Notification(Integer notificationId, String content, Boolean unRead) {
        this.notificationId = notificationId;
        this.content        = content;
        this.target         = null;
        this.unRead         = unRead;
    }

    public static @Nullable Notification getFirst(Integer notificationId) throws SQLException {
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "select * from notification where notification_id=?"
        );
        statement.setInt(1, notificationId);
        ResultSet    result       = statement.executeQuery();
        Notification notification = null;
        if (result.next()) {
            notification = new Notification(
                    notificationId,
                    result.getString("content"),
                    result.getBoolean("un_read")
            );
        }
        result.close();
        return notification;
    }

    public static @Nullable List<Integer> getNotification(Integer userId) throws SQLException {
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "select * from user_notification where user_id=?"
        );
        statement.setInt(1, userId);
        ResultSet     result        = statement.executeQuery();
        List<Integer> notifications = new ArrayList<>();
        while (result.next()) {
            notifications.add(result.getInt("notification_id"));
        }
        result.close();
        return notifications;
    }

    @Contract(" -> new")
    public static @NotNull Notification create() {
        return new Notification(null);
    }

    @Contract("_ -> new")
    public static @NotNull Notification create(@Nullable User target) {
        return new Notification(target);
    }

    @Contract("_ -> new")
    public static @NotNull Notification create(@NotNull Integer userId) throws SQLException {
        return new Notification(User.getFirst(userId));
    }

    public Boolean isUnRead() {
        return unRead;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", content='" + content + '\'' +
                ", unRead=" + unRead +
                ", target=" + target +
                "} " + super.toString();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getUnRead() {
        return this.unRead;
    }

    public void setUnRead(Boolean unRead) {
        this.unRead = unRead;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }


    public void saveAmi() throws SQLException, IllegalStateException {
        super.save();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "insert into notification(content, un_read) value (?, ?)"
        );
        this.setValueToQuery(statement);
        statement.executeUpdate();
        statement.close();
        PreparedStatement statementId = DataBase.CONNECTION.prepareStatement(
                "select notification_id from notification"
        );
        ResultSet result   = statementId.executeQuery();
        Integer   notif_id = 0;
        while (result.next()) {
            notif_id = result.getInt("notification_id");
        }
        statementId.close();
        PreparedStatement statementJoin = DataBase.CONNECTION.prepareStatement(
                "insert into user_notification(notification_id, user_id) value (?, ?)"
        );
        statementJoin.setInt(1, notif_id);
        statementJoin.setInt(2, this.target.getUserId());
        statementJoin.executeUpdate();
        statementJoin.close();
    }

    @Override
    public void save() throws SQLException, IllegalStateException {
        super.save();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "insert into notification(content, un_read) value (?, ?)"
        );
        this.setValueToQuery(statement);
        statement.executeUpdate();
        statement.close();
        PreparedStatement statementIdGetter = DataBase.CONNECTION.prepareStatement(
                "select notification_id from notification where content = ? and un_read = ?"
        );
        this.setValueToQuery(statementIdGetter);
        ResultSet result = statementIdGetter.executeQuery();
        if (result.next()) {
            this.notificationId = result.getInt("activity_id");
        } else {
            throw new IllegalStateException("The current activity have not been save properly");
        }
        statementIdGetter.close();
        PreparedStatement statementJoin = DataBase.CONNECTION.prepareStatement(
                "insert into user_notification(notification_id, user_id) value (?, ?)"
        );
        statementJoin.setInt(1, this.notificationId);
        statementJoin.setInt(2, this.target.getUserId());
        statementJoin.executeUpdate();
        statementJoin.close();
    }

    @Override
    public void delete() throws SQLException, IllegalStateException {
        super.delete();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "delete from notification where notification_id = ?"
        );
        statement.setInt(1, this.notificationId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void update() throws SQLException, IllegalStateException {
        super.update();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "update notification set content = ?, un_read = ? where notification_id = ?"
        );
        this.setValueToQuery(statement);
        statement.setInt(3, this.notificationId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    protected void checkIntegrity(boolean isSave) throws IllegalStateException {
        if (this.content == null) {
            throw new IllegalStateException("Notification component can't be null");
        }
        if (!isSave && this.notificationId == null) {
            throw new IllegalStateException("Notification ID can't be null");
        }
        if (isSave && this.target == null) {
            throw new IllegalStateException("Target can't be null");
        }
    }

    @Override
    protected void setValueToQuery(@NotNull PreparedStatement statement) throws SQLException {
        statement.setString(1, this.content);
        statement.setBoolean(2, this.unRead);
    }
}
