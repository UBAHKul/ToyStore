import java.util.List;
import java.util.Scanner;

public class toysController {

    private final toysStorage storage;
    private final toysService validator;
    private final toysStorage toyss = new toysStorage();

    private final playerQueue pq = new playerQueue(List.of(
            new player("Женя"),
            new player("Петя"),
            new player("Света"),
            new player("Галя"),
            new player("Женя"),
            new player("Вася"),
            new player("Данила"),
            new player("Денис"),
            new player("Катя"),
            new player("Оля"),
            new player("Коля")
    ));
    public toysController(toysStorage storage, toysService validator) {
        this.storage = storage;
        this.validator = validator;
    }

    public void addToys() {

        System.out.println("Создание обьекта игрушка: ");
        Scanner n = new Scanner(System.in);

        System.out.println("Введите название игрушки: ");
        String name = n.next();

        System.out.println("Введите вес игрушки: ");
        double chanceWeight = Double.parseDouble(n.next());

        System.out.println("Введите количество игрушек: ");
        int quantity = Integer.parseInt(n.next());

        int id = storage.getToys().size() + 1;

        toyss.addToysStorage(new toys(id, chanceWeight, name, quantity));
        toys toys = new toys(id, chanceWeight, name, quantity);
        if (!validator.validateToys(toys)) {
            return;
        }
        storage.add(toys);
        System.out.println(toyss);
    }

    public void modifyToys() {

        System.out.println(storage.toString());
        System.out.println("Введите id игрушки для редактирования: ");
        Scanner n = new Scanner(System.in);
        int id = Integer.parseInt(n.next());

        for (toys l: storage.getToys()) {
            if (l.getId() == id) {
                System.out.println("Введите измененные данные: ");

                System.out.println("Введите новое название игрушки: ");
                String name = n.next();

                System.out.println("Введите новый вес игрушки: ");
                double chanceWeight = Double.parseDouble(n.next());

                System.out.println("Введите новое количество игрушек: ");
                int quantity = Integer.parseInt(n.next());

                toyss.addToysStorage(new toys(id, chanceWeight, name, quantity));
                toys toys = new toys(id, chanceWeight, name, quantity);
                if (!validator.validateToys(toys)) { return; }

                storage.update(toys);
                System.out.println(toyss);
                return;
            }
        }
        System.out.println("Игрушка с таким id не найдена, повторите операцию!");
    }

    public void removeToys() {
        System.out.println(storage.toString());
        System.out.println("Введите id игрушки для удаления: ");
        Scanner n = new Scanner(System.in);
        int id = Integer.parseInt(n.next());
        toyss.removeToy(id);

        for (toys l: storage.getToys()) {
            if (l.getId() == id) {
                storage.remove(l);
                System.out.println(storage);
                return;
            }
        }
        System.out.println("Игрушка с таким id не найдена, повторите операцию!");

    }
    public void startGame() {

        //сам розыгрыш
        //Вывод бросков для наглядности расчета можно сделать, раскомментировав 32-ю строчку в Raffle
        raffle raf = new raffle(pq, toyss);
        System.out.println(raf.currentToys.toString());
        raf.runRaffle();
        //Можно записать финальное состояние призов для склада
        toyss.saveToFile();
    }

    public void startGameLoss(){

        System.out.println("\nРозыгрыш с вероятностью проиграть\n");
        playerQueue pqloss = new playerQueue();
        for (int i = 1; i <= 10; i++) {
            pqloss.addParticipant(new player());
        }
        raffle raf2 = new raffle(pqloss, toyss);
        raf2.setLossWeight(30);
        System.out.println(raf2.currentToys.toString());
        raf2.runRaffle();

        //Можно записать финальное состояние призов для склада
        toyss.saveToFile();
    }

    public void exit(){
        System.out.println("Программа закончила свою работу");
    }
}
