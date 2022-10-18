package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.heroes.api.heroesapi.model.Hero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import estoreapi.model.Instrument;
import estoreapi.model.Product;
import estoreapi.persistence.ProductFileDAO;

/**
 * The junit test suit for the ProductFileDAO class
 * 
 * @author Jonathan Zhu
 */

@Tag("Persistence-tier")
public class ProductFileDAOTest {
    ProductFileDAO productFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;


    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * 
     * @throws IOException
     */

    @BeforeEach
    public void setupHeroFileDao() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(1, "Guitar", 122.99, Product.Category.WOODWINDS, 2, true, false, false) {
        }; 

        testProducts[1] = new Product(2, "Violin", 122.99, Product.Category.WOODWINDS, 2, true, false, false) {
        }; 
        testProducts[2] = new Product(3, "Viola", 122.99, Product.Category.WOODWINDS, 2, true, false, false) {
        }; 

        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Product[].class))
                .thenReturn(testProducts);
        productFileDAO = new ProductFileDAO("doesnt_matter.txt", mockObjectMapper);

    }

    @Test
    public void testGetProducts() {
        // Invoke
        Product[] products = productFileDAO.getProducts();

        // Analyze
        assertEquals(products.length, testProducts.length);
        for (int i = 0; i < testProducts.length; ++i)
            assertEquals(products[i], testProducts[i]);
    }

    @Test
    public void testFindProducts() {
        // Invoke
        Product[] products = productFileDAO.findProducts("Vi");

        // Analyze
        assertEquals(products.length, 2);
        assertEquals(products[0], testProducts[1]);
        assertEquals(products[1], testProducts[2]);

    }

    @Test
    public void testGetProduct() {
        // Invoke
        Product product = productFileDAO.getProduct(1); 

        // Analyze
        assertEquals(product, testProducts[0]);
    }

    /*
    @Test
    public void testDeleteProduct() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(1), // check if number is correct
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result, true);

        assertEquals(productFileDAO.products.size(),testProducts.length-1); // ProductFileDAO.products not visible
    }
    */

    @Test
    public void testCreateInstrument() {
        // Setup
        Instrument instrument = new Instrument(99, "Viola",
                122.99, Product.Category.WOODWINDS, 2, true, false, false, "1/2");

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.createInstrument(instrument),
                                                "Unexpected exception thrown");

        // Analyze 
        assertNotNull(result);
        Product actual = productFileDAO.getProduct(instrument.getId());
        assertEquals(actual.getId(), instrument.getId());
        assertEquals(actual.getName(), instrument.getName());
    }



    @Test
    public void testUpdateProduct() {
        Product product = new Product(99, "Guitar", 122.99, Product.Category.WOODWINDS, 2, true, false, false) {
        };

        Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(product),
                "Unexpected exception thrown");

        assertNotNull(result);
        Product actual = productFileDAO.getProduct(product.getId());
        assertEquals(actual, product);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
                .when(mockObjectMapper)
                .writeValue(any(File.class), any(Product[].class));

        Instrument instrument = new Instrument(99, "viola", 122.99, Product.Category.WOODWINDS, 2, true, false, false,
                "1/2");

        assertThrows(IOException.class,
                () -> productFileDAO.createInstrument(instrument),
                "IOException not thrown");
    }

    @Test
    public void testGetProductNotFound() {
        // Invoke
        Product product = productFileDAO.getProduct(99);

        // Analyze
        assertEquals(product, null);
    }

    /*
    @Test
    public void testDeleteProductNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(1),
                "Unexpected exception thrown");

        // Analyze
        assertEquals(result, false);
        assertEquals(productFileDAO.products.size(), testProducts.length);
        // productFileDAO.products not visible
    }
    */

    @Test
    public void testUpdateProductNotFound() {
        // Setup
        Product product = new Product(99, "Guitar", 122.99, Product.Category.WOODWINDS, 2, true, false, false) {
        };

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(product),
                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test

    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);

        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Product[].class);

        assertThrows(IOException.class, () -> new ProductFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }
}