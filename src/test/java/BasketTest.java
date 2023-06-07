import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {

    @Test
    void testAddToBasket() {
        String[] products = {"Хлеб", "Соль", "Молоко"};
        int[] prices = {30, 15, 65};
        Basket basket = new Basket(products,prices);

        basket.addToCart(2,10);
        int[] actual = basket.getQuantities();
        int[] expected = {0, 0, 10};
        Assertions.assertArrayEquals(expected,actual);
    }
    @Test
    public void testLoad() {
        Basket basket = Basket.loadFromTxtFile(new File("src/test/resources/test_basket.txt"));
        String[] actualGoods = basket.getGoods();
        String[] expectedGoods = {"Хлеб", "Соль", "Молоко"};
        int[] actualPrices = basket.getPrices();
        int[] expectedPrices = {30, 15, 65};
        Assertions.assertArrayEquals(expectedGoods,actualGoods);
        Assertions.assertArrayEquals(expectedPrices,actualPrices);
    }
    @Test
    public void testLoadJson() {
        Basket basket = Basket.loadFromJSONFile(new File("src/test/resources/testt_basket.json"));
        String[] actualGoods = basket.getGoods();
        String[] expectedGoods = {"Хлеб", "Соль", "Молоко"};
        int[] actualPrices = basket.getPrices();
        int[] expectedPrices = {30, 15, 65};
        Assertions.assertArrayEquals(expectedGoods,actualGoods);
        Assertions.assertArrayEquals(expectedPrices,actualPrices);
    }
}