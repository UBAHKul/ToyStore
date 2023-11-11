public class toysService {
    public toysService() {
    }
    public boolean validateToys(toys toys){
        if (toys.getName().length() < 2) {
            System.out.println("Имя введено некорректно, введите заново!");
            return false;
        }
        return true;
    }
}
