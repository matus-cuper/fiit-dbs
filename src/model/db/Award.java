package model.db;

import java.util.Date;

/**
 * Created by mcuper on 7.4.2017.
 */
public class Award {

    private Integer id;
    private AwardLevel awardLevel;
    private AwardName awardName;
    private Date awardedAt;


    public Award(Integer id, AwardLevel awardLevel, AwardName awardName, Date awardedAt) {
        this.id = id;
        this.awardLevel = awardLevel;
        this.awardName = awardName;
        this.awardedAt = awardedAt;
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

    public void setAwardLevel(AwardLevel awardLevel) {
        this.awardLevel = awardLevel;
    }

    public AwardName getAwardName() {
        return awardName;
    }

    public void setAwardName(AwardName awardName) {
        this.awardName = awardName;
    }

    public Date getAwardedAt() {
        return awardedAt;
    }

    public void setAwardedAt(Date awardedAt) {
        this.awardedAt = awardedAt;
    }
}
