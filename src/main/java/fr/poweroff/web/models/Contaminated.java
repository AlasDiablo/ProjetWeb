package fr.poweroff.web.models;

import fr.poweroff.web.util.Pair;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Contaminated {

    public static List<Pair<Integer, Date>> get() throws SQLException {
        List<Pair<Integer, Date>> output = new ArrayList<>();
        PreparedStatement statement = DataBase.CONNECTION.prepareStatement(
                "select count(contaminated_at) as count, contaminated_at as date from contaminated group by contaminated_at"
        );
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            output.add(new Pair<>(
                    result.getInt("count"),
                    result.getDate("date")
            ));
        }
        return output;
    }
}
