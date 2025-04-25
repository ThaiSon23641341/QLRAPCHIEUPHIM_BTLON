package entity.quanly;

import java.util.ArrayList;
import entity.Phim;

public class DSPHIM {
    private ArrayList<Phim> dsPhims;

    public DSPHIM() {
        dsPhims = new ArrayList<>();
    }

    public boolean themPhim(Phim phim) {
        if (!dsPhims.contains(phim)) {
            dsPhims.add(phim);
            return true;
        }
        return false; // Trùng mã phim
    }

    public ArrayList<Phim> getDsPhims() {
        return dsPhims;
    }
}
