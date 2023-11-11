public class toys implements Comparable<toys>{
    int id;
    static int idCount;
    double chanceWeight;
    String name;
    int quantity;
    private double chance;

    public toys(int id, double chanceWeight, String name, int quantity) {
        this.id = id;
        this.chanceWeight = chanceWeight;
        this.name = name;
        this.quantity = quantity;
        this.chance = 0;
    }

    public toys(double chanceWeight, String name, int quantity) {
        this(idCount++,chanceWeight,name,quantity);
    }

    public toys(double chanceWeight, String name) {
        this(idCount++,chanceWeight,name,-1);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  String.format("id:%d %s, вероятность %.1f, кол-во %d, категория редкости %.2f",this.id, this.name,this.chanceWeight,this.quantity,this.chance );
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public int compareTo(toys o) {
        if(o.chance == this.chance){
            return 0;
        }
        return this.chance < o.chance ? -1 : 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public float getWeight() {
        return (float) chanceWeight;
    }

    public void setWeight(float chanceWeight) {
        this.chanceWeight = chanceWeight;
    }
}
