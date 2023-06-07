import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner s = new Scanner(System.in);
    static String[] products = {"Хлеб", "Соль", "Молоко"};
    static int[] prices = {30, 15, 65};

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        XMLSetRead settings = new XMLSetRead(new File("shop.xml"));
        File loadFile  = new File(settings.loadFile);
        File saveFile  = new File(settings.saveFile);
        File logFile  = new File(settings.logFile);

        Basket basket = createBasket(loadFile, settings.isLoad, settings.loadFormat);
        ClientLog log = new ClientLog();

        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + " " + products[i] + " " + prices[i] + " " + "руб/шт;");
        }

        while (true) {
            menu();
            String input = s.nextLine();
            if ("end".equals(input)) {
                if (settings.isLog){
                    log.exportAsCSV(logFile);
                }
                break;
            }

            String[] parts = input.split(" ");
            int numberProducts = Integer.parseInt(parts[0]) - 1;
            int numberPrices = Integer.parseInt(parts[1]);

            basket.addToCart(numberProducts,numberPrices);
            if (settings.isLog) {
                log.log(numberProducts,numberPrices);
            }
            if (settings.isSave) {
                switch (settings.saveFormat) {
                    case "json" -> basket.saveJSON(saveFile);
                    case "txt" ->  basket.savetxt(saveFile);
                }
            }
        }
        basket.printCart();
    }
    private static Basket createBasket (File loadFile,boolean isLoad, String loadFormat) {
        Basket basket;
        if (isLoad && loadFile.exists()) {
            basket = switch (loadFormat) {
                case "json" -> Basket.loadFromJSONFile(loadFile);
                case "txt" -> Basket.loadFromTxtFile(loadFile);
                default -> new Basket(products,prices);
            };
        } else {
            basket = new Basket(products,prices);
        }
        return basket;
    }
    public static void menu() {
        System.out.println("Введите номер товара и его количество: ");
        System.out.println("Чтобы звершить набор корзины - введите end");
    }
}