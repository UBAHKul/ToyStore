public class player implements Comparable<player>{
    String name;
    int id;
    static int totalCount = 1;
    static int count = 1;

    public player(String name) {
        this.name = name;
    }

    public player() {
        this("Ребёнок " + count++);
    }

    @Override
    public String toString() {
        return String.format("Участник %s, под номером " +totalCount++, this.name, this.id);
    }


    @Override
    public int compareTo(player o) {
        if(o.id == this.id){
            return 0;
        }
        return this.id < o.id ? -1 : 1;
    }
}
