package gb.ru;

import gb.ru.Controller.PetController;
import gb.ru.Model.Pet;
import gb.ru.Services.IRepository;
import gb.ru.Services.PetRepository;
import gb.ru.UserInterface.ConsoleMenu;

public class Main {
    public static void main(String[] args) throws Exception {

        IRepository <Pet> myFarm = new PetRepository();
        PetController controller = new PetController(myFarm);
        new ConsoleMenu (controller).start();
    }
}


