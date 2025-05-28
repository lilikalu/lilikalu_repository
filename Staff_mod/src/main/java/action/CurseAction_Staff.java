package action;

import com.badlogic.gdx.math.RandomXS128;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import powers.EvilWithinPower;

public class CurseAction_Staff extends AbstractGameAction {
    private final boolean isFast;

    public CurseAction_Staff(AbstractCreature target, AbstractCreature source, int stackAmount, boolean isFast) {
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else if (isFast) {
            this.duration = Settings.ACTION_DUR_FASTER;
        } else {
            this.duration = Settings.ACTION_DUR_FAST;
        }
        this.setValues(target, source, stackAmount);
        this.isFast = isFast;
    }

    @Override
    public void update() {
        RandomXS128 randomXS128 = AbstractDungeon.cardRandomRng.random;
        int R = randomXS128.nextInt(3);

        switch (R) {
            case 0:
                this.addToTop(new ApplyPowerAction(this.target, this.source, new ConstrictedPower(this.target, this.source, this.amount), this.amount, this.isFast));
                break;
            case 1:
                this.addToTop(new ApplyPowerAction(this.target, this.source, new PoisonPower(this.target, this.source, this.amount), this.amount, this.isFast));
                break;
            case 2:
                this.addToTop(new ApplyPowerAction(this.target, this.source, new EvilWithinPower(this.target, this.amount), this.amount, this.isFast));
                break;
        }
        this.isDone = true;
    }
}
