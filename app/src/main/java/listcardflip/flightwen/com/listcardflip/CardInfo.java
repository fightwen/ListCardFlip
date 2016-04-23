package listcardflip.flightwen.com.listcardflip;

import android.graphics.Bitmap;

/**
 * Created by wendy on 2016/4/23.
 */
public class CardInfo {
    public Bitmap icon;
    public String info;
    public String moreInfo;

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }
}
