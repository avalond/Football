package cn.brision.football.model;

/**
 * Created by brision on 16/10/21.
 */
public class FifterStatusInfo {
    public int pos;
    public String title;
    public Boolean isChecked;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
