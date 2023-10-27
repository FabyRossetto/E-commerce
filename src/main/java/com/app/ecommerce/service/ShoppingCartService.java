/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.service;

import com.app.ecommerce.Enum.OrderStatus;
import com.app.ecommerce.entity.Product;
import com.app.ecommerce.entity.Order;
import com.app.ecommerce.entity.ShoppingCart;

import com.app.ecommerce.repository.OrderRepository;

import com.app.ecommerce.repository.ShoppingCartRepository;
import com.app.ecommerce.security.entity.User;
import com.app.ecommerce.security.repository.UserRepository;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * In this class the service logic of the purchase order is embodied, fill the
 * cart, empty it, and check out
 */
@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository scr;

    @Autowired
    private ProductService productservice;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private OrderService orderSer;

    //This method fills the shopping cart while the user is making the purchase, 
    //and create the purchase order
    @Transactional
    public void FillCart(Integer OrderId, int idProduct, Integer IdUser, Integer amount, double totalPrice, String ShippingAddress, String ShippingWay, boolean OrderSentForAcceptance, String comments) throws Exception {
        Product addProduct = productservice.findOneProduct(idProduct);

        ShoppingCart ShoppingCart = new ShoppingCart();

        ShoppingCart.setIdproduct(addProduct.getId());
        ShoppingCart.setAmount(amount);
        ShoppingCart.setPriceProduct((Double) (Math.round(addProduct.getPrice() * 100.0) / 100.0));
        scr.save(ShoppingCart);
        Order order = new Order();

        order.setStatus(OrderStatus.NOTSENT);

        List<ShoppingCart> addProductToCart = new ArrayList();

        order.setSc(addProductToCart);
        order.setTotalPrice(ShoppingCart.getPriceProduct());

        order = orderSer.startOrder(OrderId, IdUser, order.getSc(), order.getTotalPrice(), ShippingAddress, ShippingWay, true, comments);
        Optional<User> gettingUser = userRepo.findById(IdUser);
        if (gettingUser.isPresent()) {
            order.setUser(gettingUser.get());
        }
        ShoppingCart.setOrder(order);
        orderRepo.save(order);
        scr.save(ShoppingCart);

    }

    //This method brings the order, searching for it by its id, from that order it brings the shopping cart list,
//    from which it removes the shopping cart you want to delete (which was previously searched for by its id),
//   then sets and save that list . It also removes that shopping cart from the table of the carts table. 
//    It also has a logic that if the list of carts is empty, it deletes the order, since no product has been saved.
    @Transactional
    public void RemoveProduct(Integer idShoppingCart, Integer idOrder) throws Exception {

        Order gettingOrder = orderRepo.lookingOrderForId(idOrder);

        List<ShoppingCart> ShoppingCartProducts = gettingOrder.getSc();
        ShoppingCart sc = scr.lookingScForId(idShoppingCart);

        ShoppingCartProducts.remove(sc);
        gettingOrder.setSc(ShoppingCartProducts);
        orderRepo.save(gettingOrder);
        scr.delete(sc);
        if (gettingOrder.getSc().isEmpty()) {
            orderSer.cancelOrder(idOrder);
        }

    }

//This method brings the order, looking for it by its id, of that order,
//brings the list of shopping carts and goes through it adding the price of each product, 
//and saving them in a variable.
//Finally, it sets the total price of the purchase order for that variable in which all the prices of the products were saved,
//and sets the purchase order as accepted. Then it saves it.
    public Order CheckOut(Integer idOrder) {

        Order ordering = orderRepo.lookingOrderForId(idOrder);

        Double totalPrice = 0.00;
        Double sentProduct = 1000.00;
        if (ordering != null) {

            List<ShoppingCart> FinalCart = ordering.getSc();

            for (ShoppingCart sc : FinalCart) {
                Double productPrice = (sc.getPriceProduct() * sc.getAmount());
                totalPrice = totalPrice + productPrice;
            }

            Double totalProductsAndSent = (totalPrice + sentProduct);
            ordering.setTotalPrice(totalProductsAndSent);
            ordering.setStatus(OrderStatus.ACCEPTED);
            orderRepo.save(ordering);
        }

        return ordering;

    }

}
