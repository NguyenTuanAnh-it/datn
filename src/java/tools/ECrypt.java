/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.security.MessageDigest;

/**
 *
 * @author ADMIN
 */
public class ECrypt {
    public static String maHoaMD5(String str){
        byte[] defaultBytes = str.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest [] = algorithm.digest();
            StringBuffer hexString = new StringBuffer();
            for(int i = 0; i < messageDigest.length; i++){
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if(hex.length() == 1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            str = hexString + "";
        } catch (Exception e) {
        }
        return str;
    }
}
