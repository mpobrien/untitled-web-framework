package com.mob.web;

public class AnonymousUser implements SiteUser{

    public boolean getIsLoggedIn(){ return false; }

}
