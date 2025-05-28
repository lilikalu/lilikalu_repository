package powers;

import StaffMod.StaffMod;
import action.SetManaAction_Staff;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ManaSourcePower extends AbstractPower {
    public static final String POWER_ID = StaffMod.makeID(ManaSourcePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ManaSourcePower(AbstractCreature owner, int amount){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 0;
        updateDescription();
        this.loadRegion("demonForm");
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        this.addToBot(new SetManaAction_Staff(this.amount));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
