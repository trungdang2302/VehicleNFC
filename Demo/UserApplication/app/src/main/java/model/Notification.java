package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Notification  implements Parcelable {

    public String title;

    public String message;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}