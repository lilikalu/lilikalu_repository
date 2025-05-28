package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import patchs.StaffmodPatch;
import powers.ManaTidePower;

public class SetManaAction_Staff extends AbstractGameAction {
    private final int amount;
    public SetManaAction_Staff(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int Mana = StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player);

        if (p.hasPower(ManaTidePower.POWER_ID) && Mana > 0) {
            Mana += amount + 1;
        }else {
            Mana += amount;
        }

        StaffmodPatch.ExampleField2.Mana.set(AbstractDungeon.player, Mana);
        this.isDone = true;
    }
}
