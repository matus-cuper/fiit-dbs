package controller.detail;

import java.util.Date;

/**
 * Created by Matus Cuper on 9.4.2017.
 *
 * Represent {@link model.db.Award} in {@link controller.DetailedView}
 * Class can be replaced after overriding toString on {@link model.db.AwardName}
 * and {@link model.db.AwardLevel}
 * Getters have to be listed because of {@link javafx.scene.control.cell.PropertyValueFactory}
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

    public Date getAwardedAt() {
        return awardedAt;
    }
}
