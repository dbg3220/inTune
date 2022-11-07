//package com.estore.api.estoreapi.viewmodel;
//
//import estoreapi.viewmodel.ProductController;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import estoreapi.model.Cart;
//import estoreapi.model.Product;
//import estoreapi.persistence.CartDAO;
//import estoreapi.persistence.ProductDAO;
//import estoreapi.model.Product.Category;
//
///**
// * The unit test for the product controller
// *
// * @author Donovan Cataldo
// * @author Clayton Acheson
// */
//@Tag("Controller-tier")
//public class ProductControllerTest {
//    ProductDAO mockDAO;
//    CartDAO mockCartDAO;
//    ProductController productController;
//
//    @BeforeEach
//    public void setUpProductController(){
//        mockDAO = mock(ProductDAO.class);
//        mockCartDAO = mock(CartDAO.class);
//        productController = new ProductController(mockDAO, mockCartDAO);
//    }
//
//    @Test
//    public void testGetProduct() throws Exception{
//        // Setup
//        Product product = new Product(1, "Violin Bow", 100, Category.WOODWINDS, 5, "Good beginner Bow","https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg" );
//        when(mockDAO.getProduct(product.getId())).thenReturn(product);
//
//        // Invoke
//        ResponseEntity<Product> response = productController.getProduct(product.getId());
//
//        // Analyze
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals(product,response.getBody());
//    }
//
//    @Test
//    public void testGetProductNotFound() throws Exception{
//        // Setup
//        int productID = 100000;
//        when(mockDAO.getProduct(productID)).thenReturn(null);
//
//        // Invoke
//        ResponseEntity<Product> response = productController.getProduct(productID);
//
//        // Analyze
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//    }
//
//    @Test
//    public void testGetProductHandleException() throws Exception{
//        // Setup
//        int productID = 100000;
//        doThrow(new IOException()).when(mockDAO).getProduct(productID);
//
//        // Invoke
//        ResponseEntity<Product> response = productController.getProduct(productID);
//
//        // Analyze
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
//    }
//
//    @Test
//    public void testGetProducts() throws Exception{
//        // Setup
//        Product[] products = new Product[3];
//        products[0] = new Product(1, "Violin Bow", 100.99, Category.STRINGS, 5,"Very good for begineer Violinists", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg");
//        products[1] = new Product(2, "Violin", 500.99, null, 2,"Hand crafted violin sings beautifully in the high range of the String family", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg");
//        products[2] = new Product(3, "Rosin", 1000.99, null, 6, "Have to keep the bow fresh with the ability to grip the Strings","https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg" );
//        when(mockDAO.getProducts()).thenReturn(products);
//
//        // Invoke
//        ResponseEntity<Product[]> response = productController.getProducts();
//
//        // Analyze
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals(products,response.getBody());
//    }
//
//    @Test
//    public void testGetProductsHandleException() throws Exception{
//        doThrow(new IOException()).when(mockDAO).getProducts();
//
//        ResponseEntity<Product[]> response = productController.getProducts();
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//    }
//
//    @Test
//    public void testFindProducts() throws Exception{
//        // Setup
//        String testString = "Violin";
//        Product[] products = new Product[3];
//        products[0] = new Product(1, "Violin Bow", 100, null, 5, "Good bow for beginner Violinists", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg");
//        products[1] = new Product(2, "Violin", 500, null, 2,"Hand crafted violin sings beautifully in the high range of the String family","https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg" );
//        when(mockDAO.findProducts(testString)).thenReturn(products);
//
//        // Invoke
//        ResponseEntity<Product[]> response = productController.findProducts(testString);
//
//        // Analyze
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals(products,response.getBody());
//    }
//
//    @Test
//    public void testFindProductsHandleException() throws Exception{
//        // Setup
//        String searchStr = "doesn't_matter";
//        doThrow(new IOException()).when(mockDAO).findProducts(searchStr);
//
//        // Invoke
//        ResponseEntity<Product[]> response = productController.findProducts(searchStr);
//
//        // Analyze
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//    }
//
//    @Test
//    public void testCreateProduct() throws Exception{
//        // Setup
//        Product product = new Product(52, "testing", 5, Category.STRINGS, 0, "testing the testing testing", "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg");
//        when(mockDAO.createProduct(product)).thenReturn(product);
//        // Invoke
//        ResponseEntity<Product> response = productController.createProduct(product);
//
//        // Analyze
//        assertEquals(HttpStatus.CREATED,response.getStatusCode());
//        assertEquals(product,response.getBody());
//    }
//
//    @Test
//    public void testCreateProductHandleException() throws Exception{
//        // Setup
//        Product product = new Product(0, "", 0, Category.STRINGS, 0, "", "");
//        doThrow(new IOException()).when(mockDAO).createProduct(product);
//
//        // Invoke
//        ResponseEntity<Product> response = productController.createProduct(product);
//
//        // Analyze
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
//    }
//
//    @Test
//    public void testUpdateProduct() throws IOException{
//        // Setup
//        Product product = new Product(0, "Test", 0, Category.STRINGS, 0, "Something","test.jpg");
//        when(mockDAO.updateProduct(product)).thenReturn(product);
//        when(mockCartDAO.getCarts()).thenReturn(new Cart[0]);
//
//        ResponseEntity<Product> response = productController.updateProduct(product);
//        product.setName("TestChange");
//
//        // Invoke
//        response = productController.updateProduct(product);
//
//        // Analyze
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals(product,response.getBody());
//    }
//
//    @Test
////    public void testUpdateProductFailed() throws IOException{
////        // Setup
////        Product product = new Product(1, null, 10, Category.STRINGS, 0, null,null);
////        when(mockDAO.updateProduct(product)).thenReturn(null);
////
////        // Invoke
////        ResponseEntity<Product> response = productController.updateProduct(product);
////
////        // Analyze
////        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
////    }
////
////    @Test
////    public void testUpdateProductHandleException() throws IOException{
////        // Setup
////        Product product = new Product(1, null, 0, Category.STRINGS, 0, null,null);
////        doThrow(new IOException()).when(mockDAO).updateProduct(product);
////
////        // Invoke
////        ResponseEntity<Product> response = productController.updateProduct(product);
////
////        // Analyze
////        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
////    }
////
////    @Test
////    public void testDeleteProduct() throws IOException{
////        // Setup
////        int productID = 99;
////        when(mockDAO.deleteProduct(productID)).thenReturn(true);
////        when(mockCartDAO.getCarts()).thenReturn(new Cart[0]);
////
////        // Invoke
////        ResponseEntity<Product> response = productController.deleteProduct(productID);
////
////        // Analyze
////        assertEquals(HttpStatus.OK, response.getStatusCode());
////    }
////
////    @Test
////    public void testDeleteProductNotFound() throws IOException{
////        // Setup
////        int productID = 99;
////        when(mockDAO.deleteProduct(productID)).thenReturn(false);
////
////        // Invoke
////        ResponseEntity<Product> response = productController.deleteProduct(productID);
////
////        // Analyze
////        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
////
////    }
////
////    @Test
////    public void testDeleteProductHandleException() throws IOException{
////        // Setup
////        int productID = 99;
////        doThrow(new IOException()).when(mockDAO).deleteProduct(productID);
////
////        // Invoke
////        ResponseEntity<Product> response = productController.deleteProduct(productID);
////
////        // Analyze
////        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
////    }
////}
