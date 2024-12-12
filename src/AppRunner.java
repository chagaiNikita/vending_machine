import PaymentMethods.CoinAcceptor;
import PaymentMethods.BankCard;
import PaymentMethods.PaymentMethods;
import enums.ActionLetter;
import exceptions.InvalidCardAccessException;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private static PaymentMethods paymentMethod;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
//        paymentMethod = new CoinAcceptor(120);
        paymentMethod = new BankCard(100, "4449 0799 0087 4517", 1234);
    }

    public static void run() {
        AppRunner app = new AppRunner();
        checkCard();
        while (!isExit) {
            app.startSimulation();
        }
    }


    private static void checkCard() {
        Scanner sc = new Scanner(System.in);
        if (paymentMethod instanceof BankCard) {
            while (true) {
                try {
                    System.out.print("Введите номер карты: ");
                    String cardNum = sc.nextLine();
                    if (cardNum.isBlank()) {
                        throw new InvalidCardAccessException("Строка не может быть пустой");
                    }
                    if (!cardNum.strip().equals(((BankCard) paymentMethod).getCardNumber())) {
                        throw new InvalidCardAccessException("Неверный номер карты");
                    }
                    System.out.print("Введите код: ");
                    int cardPin = sc.nextInt();
                    if (cardPin != ((BankCard) paymentMethod).getCardPassword()) {
                        throw new InvalidCardAccessException("Неверный пинкод");
                    }
                    break;
                } catch (InvalidCardAccessException e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Пинкод не может содержать символов или букв");
                }
                sc.nextLine();
            }



        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Ваш баланс: " + paymentMethod.getAmount());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (paymentMethod.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            paymentMethod.setAmount(paymentMethod.getAmount() + 10);
            print("Вы пополнили баланс на 10");
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    paymentMethod.setAmount(paymentMethod.getAmount() - products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попрбуйте еще раз.");
                chooseAction(products);
            }
        }


    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
