package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.Payment;
import com.ecommerce.ecommerce.model.Products;
import com.ecommerce.ecommerce.repository.CartRepo;
import com.ecommerce.ecommerce.repository.PaymentRepo;
import com.ecommerce.ecommerce.repository.ProductRepo;
import com.ecommerce.ecommerce.repository.UserRepo;
import com.ecommerce.ecommerce.model.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
    @GetMapping("/payment")
    public String payment() {
        return "payment";
    }
    @GetMapping("/paymentsuccessful")
    public String paymentSuccessful() {
        return "paymentsuccessful";
    }


    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ProductRepo productsRepo;


    @PostMapping("/sign")
    public String sign(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) {
        User raw = userRepo.findByEmail(email);
        if (raw == null) {
            User ob = new User(username, email, password);
            userRepo.save(ob);
            return "login";
        } else {
            return "signup";
        }
    }


    @PostMapping("/log")
    public String log(@RequestParam("email") String email, @RequestParam("password") String password, Model m) {
        User raw = userRepo.findByEmail(email);
        if (raw != null && raw.getPassword().equals(password)) {
            List<Products> p = productRepo.findAll();
            m.addAttribute("products", p);
            return "home";
        } else {
            return "login";
        }
    }



    String upload = "C:/Users/karthik raja/IdeaProjects/ecommerce/src/main/resources/static";

    @PostMapping("/productadd")
    public String productadd(@RequestParam("name") String name, @RequestParam("stock") Integer stock, @RequestParam("quantity") String quantity, @RequestParam("description") String description, @RequestParam("price") Float price, @RequestParam("image") MultipartFile image) throws IOException {
        String img_name = image.getOriginalFilename();
        if (img_name != null) {

            File f = new File(upload, img_name);
            image.transferTo(f);
            Products ob = new Products(name, stock, quantity, description, price, img_name);
            productRepo.save(ob);

            return "add";
        } else {
            return "cart";
        }
    }

    @PostMapping("/pay")
    public String pay(@RequestParam("price") Float price, Model model) {
        model.addAttribute("price", price);
        return "payment";
    }

    @PostMapping("/paymentsuccessful")
    public String payment(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("price") float price, @RequestParam("CVV") int CVV, @RequestParam("CardNumber") Long CardNumber) {
        Payment pay = new Payment(username, email, price, CVV, CardNumber);
        paymentRepo.save(pay);
        return "paymentsuccessful";

    }





    @GetMapping("/c")
    public String c(Model m){
        List<Cart> c1 = cartRepo.findAll();

        double totalPrice= 0.0;
        for (Cart cart : c1) {
            totalPrice = totalPrice + cart.getQuantity() * cart.getProducts().getPrice();
        }
        Integer x=c1.size();
        m.addAttribute("totalPrice",totalPrice);
        m.addAttribute("size",x);
        m.addAttribute("cart",c1);
        return "cart";

    }

    @PostMapping("/addCart")
    public String addCart(@RequestParam("name") String name ){
        Products products =productsRepo.findByName(name);
        Cart c = cartRepo.findByProducts(products);


        if(c == null){
            Cart cart = new Cart(1,products);
            cartRepo.save(cart);
            return "redirect:/c";
        }
        else{
            c.setQuantity(c.getQuantity()+1);
            cartRepo.save(c);
            return "redirect:/c";
        }
    }

    @PostMapping("deleteCart")
    public String deleteCart(@RequestParam("id") Long id){
        Cart c = cartRepo.findById(id);
        cartRepo.delete(c);
        return "redirect:/c";
    }





}