package com.cookandroid.ott_teacher_final;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductionCompany {private Integer id;
    private Object logoPath;
    private String name;
    private String originCountry;
    public final static Parcelable.Creator<ProductionCompany> CREATOR = new Parcelable.Creator<ProductionCompany>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductionCompany createFromParcel(Parcel in) {
            return new ProductionCompany(in);
        }

        public ProductionCompany[] newArray(int size) {
            return (new ProductionCompany[size]);
        }

    }
            ;

    protected ProductionCompany(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.logoPath = ((Object) in.readValue((Object.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.originCountry = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ProductionCompany() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(Object logoPath) {
        this.logoPath = logoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(logoPath);
        dest.writeValue(name);
        dest.writeValue(originCountry);
    }

    public int describeContents() {
        return  0;
    }

    @Override
    public String toString() {
        return "ProductionCompany{\n" +
                "       id=" + id + '\n' +
                ",      logoPath=" + logoPath +
                ",      name='" + name + '\n' +
                ",      originCountry='" + originCountry + '\n' +
                '}';
    }
}

