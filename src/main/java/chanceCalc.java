import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class chanceCalc {
    Random r = new Random();
    double maxChance;
    double totalWeight;

    /** Выдает случайное число от 0 до maxChance. Здесь можно корректировать диапазон при изменении механики весов.
     *
     * @return вещественное число, символизирующее бросок участника.
     */
    double doRoll(){
        return r.nextDouble()*maxChance;
    }

    /**
     * Здесь тянутся из очереди игрушки в соответствии с броском
     * @param prizes игрушки в розыгрыше
     * @param roll бросок текущего участника
     * @return возвращает объект Toy выигранной игрушки
     */
    toys checkPrize(PriorityQueue<toys> prizes, double roll) throws Exception {
        // делаем копию на итерацию, чтобы очередь шансов оставалась нетронутой
        PriorityQueue<toys> onePoll = new PriorityQueue<>(prizes);

        while(!onePoll.isEmpty()){
            toys p = onePoll.poll();
            if(roll <= p.getChance()){
                return checkTies(onePoll,p);
            }
        }
        throw new Exception("Приз с такой вероятностью не найден");
    }

    /**
     * первоначальная обработка списка игрушек для выставления шансов, формула нормализации весов:
     * toyWeight/totalRaffleWeight
     * @param tl список игрушек Toylist
     * @return Toylist с дописанными chance
     */
    toysStorage assignChance(toysStorage tl){
        this.totalWeight = 0;
        this.maxChance = 0;
        for (toys t:tl.toys.values()){
            this.totalWeight += t.chanceWeight;
        }

        for (toys t:tl.toys.values()){
            double ch = t.chanceWeight/totalWeight;
            t.setChance(ch);
            if(maxChance < ch ){
                maxChance = ch;
            }
        }
        return tl;
    }
    /**
     * метод, чтобы по настоящему случайно выбрать игрушку с одинаковыми весами вероятности, иначе очередь всегда будет брать
     * левый нижестоящий узел(который постоянен из-за упорядочения по другим значениям объекта, помимо значения в компараторе очереди)
     */
    toys checkTies(PriorityQueue<toys> leftovers, toys drawn  ){
        PriorityQueue<toys> tiePoll = new PriorityQueue<>(leftovers);
        ArrayList<toys> sameChance = new ArrayList<>();
        while(!tiePoll.isEmpty()){
            if(drawn.getChance() == tiePoll.peek().getChance()){
                sameChance.add(tiePoll.poll());
            }else {break;}
        }
        sameChance.add(drawn);
        int pickRandom = r.nextInt(sameChance.size());
        return sameChance.get(pickRandom);
    }
}
