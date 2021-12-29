package fr.poweroff.web.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Notification extends Model {

    private final Integer notificationId;
    private       String  content;
    private       Boolean unRead;
    private       User    target;

    private Notification(User target) {
        this.notificationId = null;
        this.content        = null;
        this.unRead         = null;
        this.target         = target;
    }

    private Notification(Integer notificationId, String content, User target, Boolean unRead) {
        this.notificationId = notificationId;
        this.content        = content;
        this.target         = target;
        this.unRead         = unRead;
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

    public void setUnRead(Boolean unRead) {
        this.unRead = unRead;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", content='" + content + '\'' +
                ", target=" + target +
                "} " + super.toString();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    @Override
    public void save() throws SQLException, IllegalStateException {
        super.save();
    }

    @Override
    public void delete() throws SQLException, IllegalStateException {
        super.delete();
    }

    @Override
    public void update() throws SQLException, IllegalStateException {
        super.update();
    }

    @Override
    protected void checkIntegrity(boolean isSave) throws IllegalStateException {

    }

    @Override
    protected void setValueToQuery(@NotNull PreparedStatement statement) throws SQLException {

    }
}
