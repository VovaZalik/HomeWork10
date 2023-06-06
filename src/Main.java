import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String[] products = {"Хлеб", "Соль", "Молоко"};
        int[] prices = {30, 15, 65};
        int[] counts = new int[products.length];
        File saveFile = new File("basket.txt");
        Basket basket = null;
        if (saveFile.exists()) {
            basket = Basket.loadFromTxtFile(saveFile);
        } else {
            basket = new Basket(products,prices);
        }

        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + " " + products[i] + " " + prices[i] + " " + "руб/шт;");
        }

        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("Введите номер товара и его количество: ");
            System.out.println("Чтобы звершить набор корзины - введите end");
            String input = s.nextLine();
            if ("end".equals(input)) break;

            String[] parts = input.split(" ");
            int numberProducts = Integer.parseInt(parts[0]) - 1;
            int numberPrices = Integer.parseInt(parts[1]);
            basket.addToCart(numberProducts,numberPrices);
            basket.savetxt(saveFile);
        }
        basket.printCart();
    }
}