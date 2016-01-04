package com.javapapers.android;


public class Contact
{
  int _id;
  int remain=0;
  String _info;
  String _url;
  String _from;
  String _too;
  
  public Contact() {}
  
  public Contact(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this._info = paramString1;
    this._url = paramString2;
    this._from = paramString3;
    this._too = paramString4;
    
  }
  
  public int getID()
  {
    return this._id;
  }
  
  public String getTOO()
  {
    return this._too;
  }
  
  
  
  public String getINF()
  {
    return this._info;
  }
  
  public String getURL()
  {
    return this._url;
  }
  
  public String getFROM()
  {
    return this._from;
  }
  
  
  public void setID(int paramInt)
  {
    this._id = paramInt;
  }
  
  public void setINF(String paramString)
  {
    this._info = paramString;
  }
  
  public void setURL(String paramString)
  {
    this._url = paramString;
  }
  public void setFROM(String paramString)
  {
    this._from = paramString;
  }
  
  public void setTOO(String paramString)
  {
    this._too = paramString;
  }
  
}
