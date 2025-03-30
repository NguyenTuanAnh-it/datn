package Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class Cart {
    private TreeMap<Product, Integer> list;
    private long cartID;

    public Cart() {
        list = new TreeMap<>();
        cartID = System.currentTimeMillis();
    }

    public Cart(TreeMap<Product, Integer> list, int cartID) {
        this.list = list;
        this.cartID = cartID;
    }

    public TreeMap<Product, Integer> getList() {
        return list;
    }

    public void setList(TreeMap<Product, Integer> list) {
        this.list = list;
    }

    public long getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }
    
    public void addToCart(Product product, int quantity){
        boolean check = list.containsKey(product);
        if(check){
            int sl = list.get(product);
            quantity += sl;
            list.put(product, quantity);
        }else{
            list.put(product, quantity);
        }
    }
    
    public void subToCart(Product product, int quantity){
        boolean check = list.containsKey(product);
        if(check){
            int sl = list.get(product);
            quantity = sl - quantity;
            if(quantity <= 0){
                list.remove(product);
            }
            else{
                list.remove(product);
                list.put(product, quantity);
            }
        }
    }
    
    public void removeToCart(Product product){
        boolean check = list.containsKey(product);
        if(check){
            list.remove(product);
        }
    }
    public int getQuantity(Product product) {
        return list.getOrDefault(product, 0);
    }
    
}


