package controller.detail;

import java.util.Date;

/**
 * Created by Matus Cuper on 9.4.2017.
 *
 * This class represents {@link model.db.Award} in {@link controller.DetailedView}
 */
public class AwardTable {

    private String name;
    private String level;
    private Date awardedAt;


    public AwardTable(String name, String level, Date awardedAt) {
        this.name = name;
        this.level = level;
        this.awardedAt = awardedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getAwardedAt() {
        return awardedAt;
    }

    public void setAwardedAt(Date awardedAt) {
        this.awardedAt = awardedAt;
    }
}
