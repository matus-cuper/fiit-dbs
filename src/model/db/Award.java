package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * This class represents awards table
 */
public class Award {

    private Integer id;
    private AwardLevel awardLevel;
    private AwardName awardName;
    private Date awardedAt;


    public Award(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("award_id");
        this.awardLevel = new AwardLevel(resultSet.getInt("award_level_id"), resultSet.getString("award_level_name"));
        this.awardName = new AwardName(resultSet.getInt("award_name_id"), resultSet.getString("award_name_name"));
        this.awardedAt = resultSet.getDate("awarded_at");
    }

    public Award(Integer id, AwardLevel awardLevel, AwardName awardName, Date awardedAt) {
        this.id = id;
        this.awardLevel = awardLevel;
        this.awardName = awardName;
        this.awardedAt = awardedAt;
    }

    public Award(AwardName awardName, AwardLevel awardLevel, Date awardedAt) throws IllegalArgumentException {
        if (awardName == null || awardLevel == null || awardedAt == null)
            throw new IllegalArgumentException();

        this.awardedAt = awardedAt;
        this.awardName = awardName;
        this.awardLevel = awardLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AwardLevel getAwardLevel() {
        return awardLevel;
    }

    public AwardName getAwardName() {
        return awardName;
    }

    public Date getAwardedAt() {
        return awardedAt;
    }
}
