package entity.quanly;

import java.util.ArrayList;
import entity.SuatChieu;

public class DSSUATCHIEU {
    private ArrayList<SuatChieu> dsSuatChieu;

    public DSSUATCHIEU() {
        dsSuatChieu = new ArrayList<>();
    }

    public boolean themSuatChieu(SuatChieu suatChieu) {
        if (!dsSuatChieu.contains(suatChieu)) {
            dsSuatChieu.add(suatChieu);
            return true;
        }
        return false; // Trùng suất chiếu
    }

    public ArrayList<SuatChieu> getDsSuatChieu() {
        return dsSuatChieu;
    }
}
