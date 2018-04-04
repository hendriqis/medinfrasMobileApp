package com.example.lenovo.medinfras.model;

/**
 * Created by Lenovo on 4/4/2018.
 */

public class PatientDetailModel {

    private String ImageCard;
    private String NameCard;
    private String MRNCard;
    private String GenderCard;
    private String BirthdayCard;

    public PatientDetailModel(String imageCard, String nameCard, String MRNCard, String genderCard,
                        String birthdayCard) {
        ImageCard = imageCard;
        NameCard = nameCard;
        this.MRNCard = MRNCard;
        GenderCard = genderCard;
        BirthdayCard = birthdayCard;
    }

    public String getImageCard() {
        return ImageCard;
    }

    public void setImageCard(String imageCard) {
        ImageCard = imageCard;
    }

    public String getNameCard() {
        return NameCard;
    }

    public void setNameCard(String nameCard) {
        NameCard = nameCard;
    }

    public String getMRNCard() {
        return MRNCard;
    }

    public void setMRNCard(String MRNCard) {
        this.MRNCard = MRNCard;
    }

    public String getGenderCard() {
        return GenderCard;
    }

    public void setGenderCard(String genderCard) {
        GenderCard = genderCard;
    }

    public String getBirthdayCard() {
        return BirthdayCard;
    }

    public void setBirthdayCard(String birthdayCard) {
        BirthdayCard = birthdayCard;
    }
}
