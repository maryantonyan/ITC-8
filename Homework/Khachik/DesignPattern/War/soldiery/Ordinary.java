package soldiery;
import java.util.*;
import interfaces.Soldier;
import objs.*;

public class Ordinary extends Soldier {
   
    public Ordinary() {
        super();
    }

    /**
     * Fire to the target
     *
     * @param meadow singlton Meadow class object
     * @param soldier ArrayList of Solder class objects
     */
    public void fire(Meadow meadow, ArrayList<Soldier> soldier) {
        Scanner scan =  new Scanner(System.in);
        System.out.print("Enter the shot angle:\nangle = ");
        int angle = scan.nextInt();
        System.out.print("\nEnter the weapon:\npistol - 1\nak-47 - 2\ngrenade - 3\nweapon = ");
        int weapon = scan.nextInt();
        if(weapon > 5 || weapon < 1) {
            System.out.println("Wrong weapon");
            return;
        }
        Objs bullet = new Objs(super._x, super._y, angle, 30, 1, "");
        Objs shoter = meadow.getObjectByName(super._name);
        if(shoter != null) {
            if(meadow.shot(this, bullet, shoter, weapon, soldier)) {
                System.out.println("You hit the target");
            } else {
                System.out.println("You missed");
            }
        }
    }
}
