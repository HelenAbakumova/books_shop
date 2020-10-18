package entity.Cart;

import entity.Product;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> products;

    public Cart() {
        products = new HashMap<>();
    }

    public void add(Product product) {
        if (products.containsKey(product)) {
            Integer count = products.get(product);
            products.put(product, count + 1);
        } else {
            products.put(product, 1);
        }
    }

    public void increaseCount(int id) {
        Product product = getProductById(id);
        Integer count = products.get(product);
        products.put(product, count + 1);
    }

    public void decreaseCount(int id) {
        Product product = getProductById(id);
        Integer count = products.get(product);
        if (count != 1) {
            products.put(product, count - 1);
        }
    }

    public void remove(int id) {
        products.remove(getProductById(id));
    }

    public void clear() {
        products.clear();
    }

    /**
     * Map of products.
     *
     * @return
     */
    public Map<Product, Integer> getCart() {
        return products;
    }

    public String getTotalSum() {
        double total = 0d;
        for (Map.Entry<Product, Integer> item : products.entrySet()) {
            Integer count = item.getValue();
            total += count * item.getKey().getPrice();
        }
        return String.valueOf(total);
    }

    private Product getProductById(int id) {
        for (Product product : products.keySet()) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public int getCountById(int id) {
        for (Map.Entry<Product, Integer> entrySet : products.entrySet()) {
            for (Integer i = 0; i < entrySet.getValue(); i++) {
                if (entrySet.getKey().getId() == id) {
                    return entrySet.getValue();
                }
            }
        }
        return 0;
    }

//    public static void main(String[] args) {
//        Cart cart = new Cart();
//        Product product1 = new Textbook();
//        Product product2 = new Textbook();
//        product1.setId(1);
//        product1.setPrice(10.);
//        product2.setId(2);
//        product2.setPrice(20.);
//        cart.add(product1);
//        cart.add(product1);
//        cart.add(product1);
//        cart.add(product2);
//        cart.add(product2);
//
//        System.out.println(cart.getTotalSum());
//    }
}
