package controlsystem.data.json;

/**
 * Created by dsm_045 on 2017-07-12.
 */
public class Seq {
    private int cur;
    private int end;

    public Seq(int cur, int end) {
        this.cur = cur;
        this.end = end;
    }

    public int getCur() {
        return cur;
    }

    public void setCur(int cur) {
        this.cur = cur;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
