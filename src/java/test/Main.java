/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dao.AccountDao;
import dao.ProductDao;

/**
 *
 * @author ADMIN
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        AccountDao acc = new AccountDao();
       System.out.println(acc.kiemTraEmail("admin@gmail.com"));
    }
}
