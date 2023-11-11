import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class toysStorage {
    private List<toys> toysses;
    HashMap<Integer, toys> toys = new HashMap<>();
    String toyFilepath;
    fileIO f = new fileIO();
    protected int maxKey;
    /**
     * добавляем одну игрушку. Если ключи совпали, назначается id = максимально_знакомый_ключ+1
     * @param t объект игрушки
     * @return сообщаем,под каким id игрушка была записана
     */
    int addToysStorage(toys t){
        int finalId = t.id;
        if(toys.containsKey(t.id)){
            finalId = ++maxKey;
            t.setId(finalId);
        }
        toys.put(t.id,t);
        return finalId;
    }
    void removeToy(int idNum){
        toys.remove(idNum);
    }

    void saveToFile(){
        f.writeToys(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder();
        for (toys t: toys.values()){
            sb.append(t.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public toysStorage() {
        this.toysses = new ArrayList<>();
    }

    public List<toys> getToys() {
        return toysses;
    }

    public void add (toys toys) {
        toysses.add(toys);
    }

    public void update (toys toys) {
        for (toys l: this.toysses) {
            if (l.getId() == toys.getId()) {
                l.setName(toys.getName());
                l.setWeight(toys.getWeight());
                break;
            }
        }
    }

    public void remove (toys toys) {
        for (toys l: this.toysses) {
            if (l.getId() == toys.getId()) {
                this.toysses.remove(l);
                break;
            }
        }
    }
}