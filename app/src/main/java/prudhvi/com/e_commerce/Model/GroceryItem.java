package prudhvi.com.e_commerce.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import prudhvi.com.e_commerce.Utils;

public class GroceryItem implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String imageOrl;
    private String category;
    private int availableamount;
    private double price;
    private int popularityPoint;
    private int rate;
    private int userpoint;
    private ArrayList<Reviev> revievs;

    public GroceryItem( String name, String description, String imageOrl, String category,
                       int availableamount, double price) {
        this.id = Utils.getID();
        this.name = name;
        this.description = description;
        this.imageOrl = imageOrl;
        this.category = category;
        this.availableamount = availableamount;
        this.price = price;
        this.popularityPoint =0;
        this.userpoint = 0;
        this.rate=0;
        this.revievs = new ArrayList<>();
    }


    protected GroceryItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        imageOrl = in.readString();
        category = in.readString();
        availableamount = in.readInt();
        price = in.readDouble();
        popularityPoint = in.readInt();
        rate = in.readInt();
        userpoint = in.readInt();
        revievs = in.createTypedArrayList(Reviev.CREATOR);
    }

    public static final Creator<GroceryItem> CREATOR = new Creator<GroceryItem>() {
        @Override
        public GroceryItem createFromParcel(Parcel in) {
            return new GroceryItem(in);
        }

        @Override
        public GroceryItem[] newArray(int size) {
            return new GroceryItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageOrl() {
        return imageOrl;
    }

    public void setImageOrl(String imageOrl) {
        this.imageOrl = imageOrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAvailableamount() {
        return availableamount;
    }

    public void setAvailableamount(int availableamount) {
        this.availableamount = availableamount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPopularityPoint() {
        return popularityPoint;
    }

    public void setPopularityPoint(int popularityPoint) {
        this.popularityPoint = popularityPoint;
    }

    public int getUserpoint() {
        return userpoint;
    }

    public void setUserpoint(int userpoint) {
        this.userpoint = userpoint;
    }

    public ArrayList<Reviev> getRevievs() {
        return revievs;
    }

    public void setRevievs(ArrayList<Reviev> revievs) {
        this.revievs = revievs;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "GroceryItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageOrl='" + imageOrl + '\'' +
                ", category='" + category + '\'' +
                ", availableamount=" + availableamount +
                ", price=" + price +
                ", popularityPoint=" + popularityPoint +
                ", rate=" + rate +
                ", userpoint=" + userpoint +
                ", revievs=" + revievs +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(imageOrl);
        parcel.writeString(category);
        parcel.writeInt(availableamount);
        parcel.writeDouble(price);
        parcel.writeInt(popularityPoint);
        parcel.writeInt(rate);
        parcel.writeInt(userpoint);
        parcel.writeTypedList(revievs);
    }
}
