package com.estore.api.estoreapi.viewmodel;

import estoreapi.viewmodel.ProductController;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import estoreapi.model.Cart;
import estoreapi.model.Product;
import estoreapi.persistence.ProductDAO;
import estoreapi.persistence.UserDAO;
import estoreapi.model.Review;

/**
 * The unit test for the product controller
 * 
 * @author Donovan Cataldo
 * @author Clayton Acheson
 */
@Tag("Controller-tier")
public class ProductControllerTest {
    ProductDAO mockDAO;
    UserDAO mockUserDAO;
    ProductController productController;

    @BeforeEach
    public void setUpProductController(){
        mockDAO = mock(ProductDAO.class);
        mockUserDAO = mock(UserDAO.class);
        productController = new ProductController(mockDAO, mockUserDAO);
    }

    @Test
    public void testGetProduct() throws Exception{
        // Setup
        Review[] reviewList = new Review[3];
        Product product = new Product(1, "Violin Bow", 100, "WOODWINDS", 5, "Good beginner Bow","https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList );
        when(mockDAO.getProduct(product.getId())).thenReturn(product);
        
        // Invoke
        ResponseEntity<Product> response = productController.getProduct(product.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testGetProductNotFound() throws Exception{
        // Setup
        int productID = 100000;
        when(mockDAO.getProduct(productID)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = productController.getProduct(productID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetProductHandleException() throws Exception{
        // Setup
        int productID = 100000;
        doThrow(new IOException()).when(mockDAO).getProduct(productID);

        // Invoke
        ResponseEntity<Product> response = productController.getProduct(productID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetProducts() throws Exception{
        // Setup
        Product[] products = new Product[3];
        Review[] reviewList = new Review[3];
        products[0] = new Product(1, "Violin Bow", 100.99, null, 5,"Very good for begineer Violinists", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        products[1] = new Product(2, "Violin", 500.99, null, 2,"Hand crafted violin sings beautifully in the high range of the String family", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        products[2] = new Product(3, "Rosin", 1000.99, null, 6, "Have to keep the bow fresh with the ability to grip the Strings","https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        when(mockDAO.getProducts()).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = productController.getProducts();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    } 

    @Test
    public void testGetProductsHandleException() throws Exception{
        doThrow(new IOException()).when(mockDAO).getProducts();

        ResponseEntity<Product[]> response = productController.getProducts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testFindProducts() throws Exception{
        // Setup
        String testString = "Violin";
        Product[] products = new Product[3];
        Review[] reviewList = new Review[3];
        products[0] = new Product(1, "Violin Bow", 100, null, 5, "Good bow for beginner Violinists", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        products[1] = new Product(2, "Violin", 500, null, 2,"Hand crafted violin sings beautifully in the high range of the String family","https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        when(mockDAO.findProducts(testString)).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = productController.findProducts(testString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    @Test
    public void testFindProductsHandleException() throws Exception{
        // Setup
        String searchStr = "doesn't_matter";
        doThrow(new IOException()).when(mockDAO).findProducts(searchStr);

        // Invoke
        ResponseEntity<Product[]> response = productController.findProducts(searchStr);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateProduct() throws Exception{
        // Setup
        Review[] reviewList = new Review[3];
        Product product = new Product(52, "testing", 5, "STRINGS", 0, "testing the testing testing", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        when(mockDAO.createProduct(product)).thenReturn(product);
        // Invoke
        ResponseEntity<Product> response = productController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testCreateNullProduct() throws Exception{
        // Setup
        Review[] reviewList = new Review[3];
        Product product = new Product(52, "testing", 5, "STRINGS", 0, "testing the testing testing", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        when(mockDAO.createProduct(product)).thenReturn(product);
        // Invoke
        ResponseEntity<Product> response = productController.createProduct(null);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateProductHandleException() throws Exception{
        // Setup
        Review[] reviewList = new Review[3];
        Product product = new Product(0, "", 0, null, 0, "", "", reviewList);
        doThrow(new IOException()).when(mockDAO).createProduct(product);

        // Invoke
        ResponseEntity<Product> response = productController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() throws IOException{
        // Setup
        Review[] reviewList = new Review[3];
        Product product = new Product(0, "Test", 0, "STRINGS", 0, "Something","test.jpg", reviewList);
        when(mockDAO.updateProduct(product)).thenReturn(product);
        when(mockUserDAO.getCarts()).thenReturn(new Cart[0]);

        ResponseEntity<Product> response = productController.updateProduct(product);
        product.setName("TestChange");

        // Invoke
        response = productController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testUpdateProductFailed() throws IOException{
        // Setup
        Review[] reviewList = new Review[3];
        Product product = new Product(1, null, 10, "", 0, null,null, reviewList);
        when(mockDAO.updateProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateProductHandleException() throws IOException{
        // Setup
        Review[] reviewList = new Review[3];
        Product product = new Product(1, null, 0, "", 0, null,null, reviewList);
        doThrow(new IOException()).when(mockDAO).updateProduct(product);

        // Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateProductBadPrice() throws IOException{
        // Setup
        Review[] reviewList = new Review[3];
        Product product = new Product(0, "Test", 0, "STRINGS", 0, "Something","test.jpg", reviewList);
        when(mockDAO.updateProduct(product)).thenReturn(product);
        when(mockUserDAO.getCarts()).thenReturn(new Cart[0]);

        ResponseEntity<Product> response = productController.updateProduct(product);
        product.setPrice(-5);

        // Invoke
        response = productController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void testUpdateProductBadQuantity() throws IOException{
        // Setup
        Review[] reviewList = new Review[3];
        Product product = new Product(0, "Test", 0, "STRINGS", 0, "Something","test.jpg", reviewList);
        when(mockDAO.updateProduct(product)).thenReturn(product);
        when(mockUserDAO.getCarts()).thenReturn(new Cart[0]);

        ResponseEntity<Product> response = productController.updateProduct(product);
        product.setQuantity(-5);

        // Invoke
        response = productController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());

    }

    @Test
    public void testUpdateProductCart() throws IOException{
        // Setup
        Review[] reviewList = new Review[3];
        Product[] products = new Product[3];
        products[0] = new Product(0, "Test", 0, "STRINGS", 10, "Something","test.jpg", reviewList);
        products[1] = new Product(1, "Violin Bow", 100, null, 0, "Good bow for beginner Violinists", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        products[2] = new Product(2, "Violin", 500, null, 0,"Hand crafted violin sings beautifully in the high range of the String family","https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        ArrayList<Integer> prodictIDs = new ArrayList<>();
        prodictIDs.add(0);
        prodictIDs.add(1);
        prodictIDs.add(2);
        ArrayList<Integer> quantites = new ArrayList<>();
        quantites.add(10);
        quantites.add(10);
        quantites.add(10);
        Cart[] carts = new Cart[3];
        carts[0] = new Cart(1,prodictIDs, quantites);
        carts[1] = new Cart(2, prodictIDs, quantites);
        carts[2] = new Cart(3, prodictIDs, quantites);
        when(mockDAO.updateProduct(products[0])).thenReturn(products[0]);
        when(mockUserDAO.getCarts()).thenReturn(carts);
        ResponseEntity<Product> response = productController.updateProduct(products[0]);
        products[0].setQuantity(0);
        // Invoke
        response = productController.updateProduct(products[0]);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

    @Test
    public void testDeleteProduct() throws IOException{
        // Setup
        int productID = 99;
        when(mockDAO.deleteProduct(productID)).thenReturn(true);
        when(mockUserDAO.getCarts()).thenReturn(new Cart[0]);
        
        // Invoke
        ResponseEntity<Product> response = productController.deleteProduct(productID);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProductCart() throws IOException{
        // Setup
        int productID = 0;
        Review[] reviewList = new Review[3];
        Product[] products = new Product[3];
        products[0] = new Product(0, "Test", 0, "STRINGS", 10, "Something","test.jpg", reviewList);
        products[1] = new Product(1, "Violin Bow", 100, null, 0, "Good bow for beginner Violinists", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        products[2] = new Product(2, "Violin", 500, null, 0,"Hand crafted violin sings beautifully in the high range of the String family","https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg", reviewList);
        ArrayList<Integer> prodictIDs = new ArrayList<>();
        prodictIDs.add(0);
        prodictIDs.add(1);
        prodictIDs.add(2);
        ArrayList<Integer> quantites = new ArrayList<>();
        quantites.add(10);
        quantites.add(10);
        quantites.add(10);
        Cart[] carts = new Cart[3];
        carts[0] = new Cart(1,prodictIDs, quantites);
        carts[1] = new Cart(2, prodictIDs, quantites);
        carts[2] = new Cart(3, prodictIDs, quantites);
        when(mockDAO.deleteProduct(productID)).thenReturn(true);
        
        when(mockUserDAO.getCarts()).thenReturn(carts);

        // Invoke
        ResponseEntity<Product> response = productController.deleteProduct(productID);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException{
        // Setup
        int productID = 99;
        when(mockDAO.deleteProduct(productID)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = productController.deleteProduct(productID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

    }

    @Test
    public void testDeleteProductHandleException() throws IOException{
        // Setup
        int productID = 99;
        doThrow(new IOException()).when(mockDAO).deleteProduct(productID);

        // Invoke
        ResponseEntity<Product> response = productController.deleteProduct(productID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
