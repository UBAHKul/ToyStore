import java.util.Objects;
import java.util.Scanner;
import static java.lang.System.exit;
public class toysView {
    public toysView() {
    }
    public void viewApp() {
        toysController controller = new toysController(new toysStorage(), new toysService());

        while (true) {
            System.out.println("\nВведите цифру от 1 до 5\n" +
                    "\n1 - добавление игрушки" +
                    "\n2 - редактирование игрушки" +
                    "\n3 - удаление игрушки" +
                    "\n4 - Розыгриш игрушек" +
                    "\n5 - Розыгриш игрушек с шансом пройграть" +
                    "\n6 - выход из программы");
            Scanner i = new Scanner(System.in);
            String action = i.next();

            if (Objects.equals(action, "1")) {
                controller.addToys();
                continue;
            }

            if (Objects.equals(action, "2")) {
                controller.modifyToys();
                continue;
            }

            if (Objects.equals(action, "3")) {
                controller.removeToys();
                continue;
            }

            if (Objects.equals(action, "4")) {
                controller.startGame();
                continue;
            }

            if (Objects.equals(action, "5")) {
                controller.startGameLoss();
                continue;
            }

            if (Objects.equals(action, "6")) {
                controller.exit();
                exit(0);
            }
        }
    }
}
