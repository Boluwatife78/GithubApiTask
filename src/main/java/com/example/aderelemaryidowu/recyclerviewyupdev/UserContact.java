package com.example.aderelemaryidowu.recyclerviewyupdev;

import java.io.Serializable;

/**
 * Created by Boluwatife on 7/13/2017.
 */

public class UserContact implements Serializable{
    public String mAvatar;
    public long mId;
    public String mLogin;

    public UserContact()
    {

    }

    public UserContact( String Avatar, long Id, String Username)
    {
        mAvatar = Avatar;
        mId = Id;
        mLogin = Username;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }
}
