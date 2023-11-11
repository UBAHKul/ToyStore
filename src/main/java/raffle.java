import java.io.BufferedWriter;
import java.io.IOException;
import java.util.PriorityQueue;

public class raffle {
    toysStorage currentToys;
    playerQueue currentParticipants;
    double lossWeight = 0; //0 для соответствия заданию, где веса разбиваются на полную вероятность в 100%
    int lossId;
    chanceCalc cc = new chanceCalc();
    raffle.QuantityCalc qc = new raffle.QuantityCalc();

    public raffle(playerQueue kids, toysStorage tl) {
        this.currentToys = cc.assignChance(tl);
        this.currentParticipants = kids;
    }

    /**
     * основной метод перебора розыгрыша
     */
    public void runRaffle() {
        playerQueue kids = this.currentParticipants;
        toysStorage tl = this.currentToys;
        PriorityQueue<toys> prizes = new PriorityQueue<>(tl.toys.values());
        try {
            BufferedWriter log = fileIO.raffleLog();
            while(kids.iterator().hasNext()){
                double winRoll = cc.doRoll();
                player k = kids.iterator().next();
                try {
                    toys win = cc.checkPrize(prizes, winRoll);
//                    showRoll(k,win,winRoll);
                    prizes = qc.adjustQuantityLeft(win,tl,prizes);
                    log.write(showWin(k, win) + "\n");
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            log.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    String showWin(player kid, toys prize) {
        String winLine;
        if(prize.name.equals("ничего")){
            winLine = kid.toString() + " не выиграл ничего";
        } else {
            winLine = kid.toString() + " выиграл " + prize.name;
        }
        System.out.println(winLine);
        return winLine;
    }

    void showRoll(player kid, toys prize, double roll) {
        System.out.printf("%s бросает на %.3f , это ниже шанса %.2f у %s%n", kid.name, roll, prize.getChance(), prize.name);
    }

    /**
     * Вручную задайте вес проигрыша. Относительные вероятности в розыгрыше пересчитаются соответственно.
     *
     * @param lossWeight вес по той же шкале, что и веса игрушек.
     */
    public void setLossWeight(double lossWeight) {
        this.lossWeight = lossWeight;
        this.lossId = this.currentToys.addToysStorage(new toys(lossWeight, "ничего", -1));
        cc.assignChance(currentToys);
    }

    /**
     * Здесь собраны методы, отвечающие за учет остатков призов.Чтобы не плодить сущности,
     * заводя новый ChanceCalc на каждый выигрыш, является внутренним классом, и использует объект cc из Raffle
     * общение с  классом Raffle через :
     *  * Outer<-Inner   Raffle.QuantityCalc qc = new Raffle.QuantityCalc()
     *  * Inner<-Outer   Raffle.this.
     */
    class QuantityCalc {
        /**
         * Точка входа в класс, остальные вспомогательные методы
         *
         * @param t    - выигранная игрушка
         * @param tl список игрушек текущего розыгрыша
         * @return возвращает скорректированный список
         */
        PriorityQueue<toys> adjustQuantityLeft(toys t, toysStorage tl, PriorityQueue<toys> currentQueue) {
            if(t.quantity > 0){
                t.quantity -= 1;
            }
            if(t.quantity == 0){
                removeStock(t.id, tl);
                raffle.this.cc.assignChance(tl);
                currentQueue = new PriorityQueue<>(tl.toys.values());
            }
            return currentQueue;
        }

        void removeStock(int idNum, toysStorage toys) {
            toys.removeToy(idNum);
        }
    }
}
