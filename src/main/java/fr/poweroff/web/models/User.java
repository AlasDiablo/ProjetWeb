package fr.poweroff.web.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User extends Model {

    private Integer userId;
    private String  firstname;
    private String  lastname;
    private String  email;
    private String  passwordHash;
    private Date    born;
    private Integer level;

    private User(Integer userId, String firstname, String lastname, String email, String passwordHash, Date born, Integer level) {
        this.userId       = userId;
        this.firstname    = firstname;
        this.lastname     = lastname;
        this.email        = email;
        this.passwordHash = passwordHash;
        this.born         = born;
        this.level        = level;
    }

    private User() {
        this.firstname    = null;
        this.lastname     = null;
        this.email        = null;
        this.passwordHash = null;
        this.born         = null;
        this.level        = null;
    }

    /**
     * Create an empty user
     *
     * @return user with all components at null
     */
    @Contract(" -> new")
    public static @NotNull User create() {
        return new User();
    }

    /**
     * get the first user with an email as query element
     *
     * @param email user email
     *
     * @return null if no user found or a user with the corresponding components
     *
     * @throws SQLException error when PreparedStatement fail
     */
    public static @Nullable User getFirst(String email) throws SQLException {
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "select * from user where email=?"
        );
        statement.setString(1, email);
        ResultSet result = statement.executeQuery();
        User      user   = null;
        if (result.next()) {
            user = new User(
                    result.getInt("user_id"),
                    result.getString("firstname"),
                    result.getString("lastname"),
                    email,
                    result.getString("password_hash"),
                    result.getDate("born"),
                    result.getInt("level")
            );
        }
        result.close();
        return user;
    }

    /**
     * Recherche de tous les utilisateurs
     * @return liste des utilisateurs
     * @throws SQLException error when PreparedStatement fail
     */
    public static @Nullable List<User> getUser() throws SQLException{
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "select * from user" );
        ResultSet result = statement.executeQuery();
        List<User> user = new ArrayList<>();
        while(result.next()){
            user.add(new User(
                    result.getInt("user_id"),
                    result.getString("firstname"),
                    result.getString("lastname"),
                    result.getString("email"),
                    result.getString("password_hash"),
                    result.getDate("born"),
                    result.getInt("level")
            ));
        }
        return user;
    }

    /**
     * get the first user with a user id as query element
     *
     * @param userId user id
     *
     * @return null if no user found or a user with the corresponding components
     *
     * @throws SQLException error when PreparedStatement fail
     */
    public static @Nullable User getFirst(Integer userId) throws SQLException {
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "select * from user where user_id=?"
        );
        statement.setInt(1, userId);
        ResultSet result = statement.executeQuery();
        User      user   = null;
        if (result.next()) {
            user = new User(
                    userId,
                    result.getString("firstname"),
                    result.getString("lastname"),
                    result.getString("email"),
                    result.getString("password_hash"),
                    result.getDate("born"),
                    result.getInt("level")
            );
        }
        result.close();
        return user;
    }

    /**
     * Save the current user into the database
     *
     * @throws SQLException          error when PreparedStatement fail
     * @throws IllegalStateException error when user components are null
     */
    @Override
    public void save() throws SQLException, IllegalStateException {
        super.save();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "insert into user(firstname, lastname, email, password_hash, born, level) VALUES(?, ?, ?, ?, ?, ?)"
        );
        this.setValueToQuery(statement);
        statement.executeUpdate();
        statement.close();
        PreparedStatement statementIdGetter = DataBase.CONNECTION.prepareStatement(
                "select user_id from user where firstname = ? and lastname = ? and email = ? and password_hash = ? and born = ? and level = ?"
        );
        this.setValueToQuery(statementIdGetter);
        ResultSet result = statementIdGetter.executeQuery();
        if (result.next()) {
            this.userId = result.getInt("user_id");
        } else {
            throw new IllegalStateException("The current user have not been save properly");
        }
    }

    /**
     * Update the current user into the database
     *
     * @throws SQLException          error when PreparedStatement fail
     * @throws IllegalStateException error when user components are null
     */
    @Override
    public void update() throws SQLException, IllegalStateException {
        super.update();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "update user set firstname=?, lastname=?, email=?, password_hash=?, born=?, level=? where user_id=?"
        );
        this.setValueToQuery(statement);
        statement.setInt(7, this.userId);
        statement.executeUpdate();
        statement.close();
    }

    /**
     * Delete the current user into the database
     *
     * @throws SQLException          error when PreparedStatement fail
     * @throws IllegalStateException error when user components are null
     */
    @Override
    public void delete() throws SQLException, IllegalStateException {
        super.delete();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "delete from user where user_id=?"
        );
        statement.setInt(1, this.userId);
        statement.executeUpdate();
        statement.close();
    }

    protected void setValueToQuery(@NotNull PreparedStatement statement) throws SQLException {
        statement.setString(1, this.firstname);
        statement.setString(2, this.lastname);
        statement.setString(3, this.email);
        statement.setString(4, this.passwordHash);
        statement.setDate(5, this.born);
        statement.setInt(6, this.level);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", born=" + born +
                ", level=" + level +
                "} " + super.toString();
    }

    @Override
    protected void checkIntegrity(boolean isSave) throws IllegalStateException {
        if (this.firstname == null || this.lastname == null || this.passwordHash == null || this.email == null || this.born == null || this.level == null) {
            throw new IllegalStateException("User component can't be null");
        }
        if (!isSave && this.userId == null) {
            throw new IllegalStateException("User component can't be null");
        }
    }

    public List<Activity> getActivities() throws SQLException {
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "select activity.activity_id as activity_id, start_at, end_at, city from activity inner join user_activity on activity.activity_id = user_activity.activity_id where user_id = ?"
        );
        statement.setInt(1, this.userId);
        ResultSet      result     = statement.executeQuery();
        List<Activity> activities = new ArrayList<>();
        while (result.next()) {
            activities.add(new Activity(
                    result.getInt("activity_id"),
                    result.getDate("start_at"),
                    result.getDate("end_at"),
                    result.getString("city")
            ));
        }
        return activities;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(@NotNull String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(@NotNull String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(@NotNull String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(@NotNull Date born) {
        this.born = born;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(@NotNull Integer level) {
        this.level = level;
    }
}
