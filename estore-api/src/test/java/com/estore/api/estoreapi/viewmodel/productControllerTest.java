package com.estore.api.estoreapi.viewmodel;

import estoreapi.viewmodel.ProductController;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import estoreapi.model.Instrument;
import estoreapi.model.Equipment;
import estoreapi.model.Lesson;
import estoreapi.model.Product;
import estoreapi.persistence.ProductDAO;
import estoreapi.persistence.ProductFileDAO;
/**
 * The unit test for the product controller
 * 
 * @author Donovan Cataldo
 */
public class productControllerTest {
    ProductDAO mockDAO;
    ProductController productController;

    @BeforeEach
    public void setUpProductController(){
        mockDAO = mock(ProductDAO.class);
        productController = new ProductController(mockDAO);
    }

    @Test
    public void testGetProduct() throws IOException{
        // Setup
        Product product = new Equipment(1, "Violin Bow", 100, null, 5, false, true, false);
        when(mockDAO.getProduct(product.getId())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = ProductController.getProduct(product.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    public void testGetProductNotFound() throws IOException{
        // Setup
        int productID = 100000;
        when(mockDAO.getProduct(productID)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = ProductController.getProduct(productID);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    public void testGetProductHandleException() throws IOException{
        // Setup
        int productID = 100000;
        doThrow(new IOException()).when(mockDAO).getProduct(productID);

        // Invoke
        ResponseEntity<Product> response = ProductController.getProduct(productID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetProducts() throws IOException{
        // Setup
        Product[] products = new Product[3];
        products[0] = new Equipment(1, "Violin Bow", 100, null, 5, false, true, false);
        products[1] = new Instrument(2, "Violin", 500, null, 2, true, false, false, "Massive");
        products[2] = new Lesson(3, "9 am monday Lesson", 30, null, 2, false, false, true, "Clayton", null, null, null, false);
        when(mockDAO.getProducts()).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = ProductController.getProducts();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    } 

    @Test
    public void testsearchProductsHandleException() throws IOException{
        // Setup
        doThrow(new IOException()).when(mockDAO).getProducts();

        // Invoke
        ResponseEntity<Product[]> response = ProductController.getProducts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testsearchProductsByName() throws IOException{
        // Setup
        String testString = "Violin";
        Product[] products = new Product[3];
        products[0] = new Equipment(1, "Violin Bow", 100, null, 5, false, true, false);
        products[1] = new Instrument(2, "Violin", 500, null, 2, true, false, false, "Massive");
        when(mockDAO.findProducts(testString)).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = productController.searchProductsByName(testString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    // @Test
    // public void testsearchProductsByNameHandleException(){
    //     // Setup
    //     String testString = "Violin";
    //     doThrow(new IOException()).when(mockDAO).searchProductsByName(testString);

    //     // Invoke
    //     ResponseEntity<Product[]> response = productController.searchProductsByName(testString);

    //     // Analyze
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    // }

    @Test
    public void testCreateProduct() throws IOException{
        // Setup
        Equipment product = new Equipment(1, "test", 5, null, 0, false, true, false);
        when(mockDAO.createEquipment(product)).thenReturn(product);
        // Invoke
        ResponseEntity<Product> response = productController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product,response.getBody());

        // // Setup
        // product = new Instrument(2, "test", 10, null, 10, true, false, false, null);
        // when(mockDAO.createProduct(product)).thenReturn(product);

        // // Invoke
        // response = productController.createProduct(product);

        // // Analyze
        // assertEquals(HttpStatus.CREATED,response.getStatusCode());
        // assertEquals(product,response.getBody());

        // Setup
    //     product = new Lesson(3, "test", 0, null, 0, true, false, false, null, null, null, null, null);
    //     when(mockDAO.createProduct(product)).thenReturn(product);

    //     // Invoke
    //     response = productController.createProduct(product);

    //     // Analyze
    //     assertEquals(HttpStatus.CREATED,response.getStatusCode());
    //     assertEquals(product,response.getBody());
    // }

    // @Test
    // public void testCreateProductHandleException(){
    //     // Setup
    //     Product product = new Equipment(0, null, 0, null, 0, false, true, false);
    //     doThrow(new IOException()).when(mockDAO).createProduct(product);

    //     // Invoke
    //     ResponseEntity<Product> response = productController.createProduct(product);

    //     // Analyze
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    //     product = new Instrument(0, null, 0, null, 0, true, false, false, null);
    //     doThrow(new IOException()).when(mockDAO).createProduct(product);

    //     // Invoke
    //     response = productController.createProduct(product);

    //     // Analyze
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    //     product = new Lesson(0, null, 0, null, 0, false, false, true, null, null, null, null, null);
    //     doThrow(new IOException()).when(mockDAO).createProduct(product);

    //     // Invoke
    //     response = productController.createProduct(product);

    //     // Analyze
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() throws IOException{
        // Setup
        Product product = new Equipment(0, null, 0, null, 0, false, true, false);
        when(mockDAO.updateProduct(product)).thenReturn(product);
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
        Product product = new Equipment(1, null, 10, null, 0, false, true, false);
        when(mockDAO.updateProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateProductHandleException() throws IOException{
        // Setup
        Product product = new Equipment(1, null, 0, null, 0, false, true, false);
        doThrow(new IOException()).when(mockDAO).updateProduct(product);

        // Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException{
        // Setup
        int productID = 99;
        when(mockDAO.deleteProduct(productID)).thenReturn(true);

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
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());

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
