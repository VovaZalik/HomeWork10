import com.google.gson.Gson;

import java.io.*;
import java.util.Arrays;

public class Basket {
    protected String[] goods;
    protected int[] prices;
    protected int[] quantities;

    public Basket() {
    }

    public Basket(String[] goods, int[] prices) {
        this.goods = goods;
        this.prices = prices;
        this.quantities = new int[goods.length];
    }
    public void addToCart(int productNum,int amount) {
        quantities[productNum] += amount;
    }
    public void printCart() {
        int sum = 0;
        for (int i = 0; i < goods.length; i++) {
            if (quantities[i] > 0) {
                System.out.println(
                        goods[i] + " " +
                                prices[i] + " руб за шт; " + " " + "Вы добавили в корзину: " +
                                quantities[i] + " шт; " + "В сумме выходит: " +
                                (quantities[i] * prices[i]));
                sum += (quantities[i] * prices[i]);
            }
        }
        System.out.println("Итого : " + sum + " руб");
    }
    public void savetxt(File textFile) throws FileNotFoundException {
        try(PrintWriter out = new PrintWriter(textFile)) {
            for (String good : goods) {
                out.print(good + " ");
            }
            out.println();
            for (int price : prices) {
                out.print(price + " ");
            }
            out.println();
            for (int quan  : quantities) {
                out.print(quan + " ");
            }
            out.println();
        }
    }
    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();
        try(BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String goodsStr = reader.readLine();
            String pricesStr = reader.readLine();
            String quantitiesStr = reader.readLine();

            basket.goods = goodsStr.split(" ");
            basket.prices = Arrays.stream(pricesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
            basket.quantities = Arrays.stream(quantitiesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
    public void saveBin(File file) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
    public void saveJSON(File file) {
        try(PrintWriter writer = new PrintWriter(file)) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            writer.print(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Basket loadFromJSONFile(File file) {
        Basket basket = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String  line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(builder.toString(),Basket.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}
