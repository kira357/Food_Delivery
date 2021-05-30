package com.trantiendat.Service;

public class APIService  {
private static  String url = "https://kira357.000webhostapp.com/Server/";
public static DataService getService(){
    return API.getClient(url).create(DataService.class);
}
}
