package rpg.game.game;

import rpg.game.game.units.Unit;

public class Armor {
    public enum TypeArmor {
       LIGHT, MEDIUM, HEAVY
    }

    TypeArmor typeArmor;

    public Armor(TypeArmor typeArmor){
        this.typeArmor = typeArmor;
    }

    public static int damageCorrection(Unit attacker, Unit target){
        int damage;

        Weapon.Type attackerTypeWeapon = attacker.getWeapon().type;
        Armor.TypeArmor targetTypeArmor = target.getArmor().typeArmor;

        if(attackerTypeWeapon == Weapon.Type.SPEAR && targetTypeArmor == Armor.TypeArmor.MEDIUM){
            damage = attacker.getWeapon().getDamage() - 1;
            return damage;
        }

        if(attackerTypeWeapon == Weapon.Type.SWORD && targetTypeArmor == TypeArmor.HEAVY){
            damage = attacker.getWeapon().getDamage() - 1;
            return damage;
        }

        damage = attacker.getWeapon().getDamage();
        return damage;
    }
}
