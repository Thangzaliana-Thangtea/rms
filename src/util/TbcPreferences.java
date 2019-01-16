/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.prefs.Preferences;

/**
 *
 * @author hatred
 */
public class TbcPreferences {
         private  Preferences pref=Preferences.userRoot().node(this.getClass().getName());

    public static String TAX_VALUE="TAX_VALUE";
     public static String PERCENT_OPTION="PERCENT_OPTION";
     public static String FAVICON_LOC="icon_location";
     public static String STOCK_LIMIT="stock_limit";
     
    public static String PORT="port";
    public static String SERVER="server";
    public static String DATABASE="database";
    public static String USER="databaseuser";
    public static String PASSWORD="password";
    public static String DISCOUNT_PERITEM="itemtax";
    public static String AMOUNT_OR_PERCENT_IN_ITEM_DISCOUNT="amountorpercent";
    
    private static final String  DATABASE_LOC="database_location";
    public static final String MYSQL_LOC="mysql_loc";
    private static final String PRINT_INVOICE="print_by_default";
    private static final String MASTER_UNAME="masteruname";
    private static final String MASTER_PWD="masterpwd";
    private static final String RENTAL_NAME="receipt_store_name";
    private static final String RENTAL_ADDRESS="receipt_store_address";
    private static final String RENTAL_DESC="rental_description";
    private static final String CONTACT="00000";
    private static final String REMIND_BOOKING="remind_me";
    private static final String AVATAR_LOC="tbc_icon_loc";
    private static final String DATE_PATTERN="tbc_date_pattern";
    private static final String RENTAL_CONTACT="receipt_contact";
    
    private static final String TEMPLATE_CHOICE="receipt_template";
    private static final String REPLACE_ITEM="Replace_XML_DATA";
   
    
   
     public String getIconLocation(){
         String loc=this.getClass().getResource("/resource/icons/avatar.png").toExternalForm();
         return pref.get(FAVICON_LOC, loc);
     }
     public void setIconLocation(String location){
         pref.put(FAVICON_LOC, location);
     }

    public String getPort() {
        return pref.get(PORT, "3306");
    }
    public  void setPort(String port){
        pref.put(PORT, port);
    }

    public String getServerName() {
        return pref.get(SERVER, "localhost");
    }
    public void setServerName(String name){
         pref.put(SERVER, name);
    }

    public String getDatabaseName() {
        return pref.get(DATABASE, "billdb");
    }
    public void setDatabaseName(String name){
        pref.put(DATABASE, name);
    }

    public String getDatabaseUser() {
        return pref.get(USER, "root");
    }
    public void setDatabaseUser(String user){
        pref.put(USER, user);
    }

    public String getPassword() {
        return pref.get(PASSWORD, "");
    }

    public void setPassword(String pwd){
        pref.put(PASSWORD, pwd);
    }

    public String getMasterUsername() {
        return pref.get(MASTER_UNAME, "root");
    }

    public String getMasterPassword() {
        return pref.get(MASTER_PWD, "toor");
    }

    public String getStoreName() {
return pref.get(RENTAL_NAME,"Tbc car rental");
    }

    public String getStoreAddress() {
        return pref.get(RENTAL_ADDRESS, "Address");
    }

    public String getStoreContact() {
        return pref.get(RENTAL_CONTACT, "00000000");
    }

    public void setMasterUsername(String username) {
        pref.put(MASTER_UNAME, username);
    }

    public void setMasterPassword(String pwd) {
        pref.put(MASTER_PWD, pwd);
    }

    public void setStoreName(String trim) {
       pref.put(RENTAL_NAME, trim);
    }

    public void setAddress(String trim) {
        pref.put(RENTAL_ADDRESS, trim);
    }

    public void setContact(String trim) {
        pref.put(RENTAL_CONTACT, trim);
    }
    public String getAvatarPath() {
        return pref.get(AVATAR_LOC, null);
    }

    public String getEmail() {
        return pref.get("rental_email", "email@mail.com");
    }
    
    public void setRentalEmail(String val){
        pref.put("rental_email", val);
    }
    public void setWebsite(String val){
        pref.put("rental_website", val);
    }

    public String getWebsite() {
        return pref.get("rental_website", "www.website.com");
    }

    public void setTemplate(Integer choice) {
        pref.putInt(TEMPLATE_CHOICE, choice);
    }
    public int getTemplate() {
        return pref.getInt(TEMPLATE_CHOICE,1);
    }

    public boolean isReplace() {
return this.pref.getBoolean(REPLACE_ITEM, false);
    }

    public void setReplaceItem(boolean val){
        this.pref.putBoolean(REPLACE_ITEM, val);
    }
    
}
