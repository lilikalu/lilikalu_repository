package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import patchs.StaffmodPatch;

public class SilentCastingAction_Staff extends AbstractGameAction {
    public SilentCastingAction_Staff(AbstractCreature source, int amount) {
        this.setValues(this.target, source, amount);
        this.actionType = ActionType.DRAW;
    }

    @Override
    public void update() {
        int toDraw = this.amount - AbstractDungeon.player.hand.size();
        int mana = StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player);
        if (mana != 0){
            if(toDraw > mana) {
                this.addToTop(new DrawCardAction(this.source, mana));
                this.addToBot(new SetManaAction_Staff(-mana));
            }else {
                this.addToTop(new DrawCardAction(this.source, toDraw));
                this.addToBot(new SetManaAction_Staff(-toDraw));
            }
        }

        this.isDone = true;
    }
}
