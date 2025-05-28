package action;

import com.badlogic.gdx.math.RandomXS128;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ChaosSpellAction extends AbstractGameAction {
    private final int maxdamage;

    public ChaosSpellAction(AbstractCreature target, AbstractCreature source, int maxdamage) {
        this.maxdamage = maxdamage;
        this.actionType = ActionType.DAMAGE;
        setValues(target,source);
    }

    @Override
    public void update() {
        RandomXS128 randomXS128 = AbstractDungeon.cardRandomRng.random;
        int R = randomXS128.nextInt(this.maxdamage);
        this.addToBot(new DamageAction(target, new DamageInfo(source, R, DamageInfo.DamageType.HP_LOSS)));
        this.isDone = true;
    }
}
