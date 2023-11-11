import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;

public class playerQueue implements Iterable<player>{
    PriorityQueue<player> drawQueue;

    public playerQueue(Collection<player> list) {
        this.drawQueue = new PriorityQueue<>(list.size());
        this.drawQueue.addAll(list);
    }

    public playerQueue() {
        this.drawQueue = new PriorityQueue<>();
    }

    void addParticipant(player p){
        this.drawQueue.add(p);
    }

    class participantIterator implements Iterator<player> {
        player current;
        public participantIterator(PriorityQueue<player> players) {
            this.current = players.peek();
        }

        @Override
        public boolean hasNext() {
            return !drawQueue.isEmpty();
        }

        @Override
        public player next() {
            return drawQueue.poll();
        }
    }

    @Override
    public Iterator<player> iterator() {
        return new participantIterator(drawQueue);
    }
}
