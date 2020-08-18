package prudhvi.com.e_commerce.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Reviev implements Parcelable {
    private int groceryitemid;
    private String username;
    private String data;
    private String text;

    public Reviev(int groceryitemid, String username, String data, String text) {
        this.groceryitemid = groceryitemid;
        this.username = username;
        this.data = data;
        this.text = text;
    }

    protected Reviev(Parcel in) {
        groceryitemid = in.readInt();
        username = in.readString();
        data = in.readString();
        text = in.readString();
    }

    public static final Creator<Reviev> CREATOR = new Creator<Reviev>() {
        @Override
        public Reviev createFromParcel(Parcel in) {
            return new Reviev(in);
        }

        @Override
        public Reviev[] newArray(int size) {
            return new Reviev[size];
        }
    };

    public int getGroceryitemid() {
        return groceryitemid;
    }

    public void setGroceryitemid(int groceryitemid) {
        this.groceryitemid = groceryitemid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Reviev{" +
                "groceryitemid=" + groceryitemid +
                ", username='" + username + '\'' +
                ", data='" + data + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(groceryitemid);
        parcel.writeString(username);
        parcel.writeString(data);
        parcel.writeString(text);
    }
}
