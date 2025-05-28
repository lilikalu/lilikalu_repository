package powers;

import StaffMod.StaffMod;
import action.SetManaAction_Staff;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import patchs.StaffmodPatch;

public class BattleMageRingPower extends AbstractPower {
    public static final String POWER_ID = StaffMod.makeID(BattleMageRingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BattleMageRingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;

        if (this.amount >= 999) {
            this.amount = 999;
        }
        if (this.amount <= 0) {
            this.amount = 0;
        }

        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 0;
        updateDescription();
        this.loadRegion("noPain");
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        int mana = StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player);
        if (damageAmount != 0 && info.owner != this.owner && mana >= 1) {
            this.addToBot(new SetManaAction_Staff(-1));
            damageAmount -= this.amount;
            if (damageAmount <= 0) {
                damageAmount = 0;
            }
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
