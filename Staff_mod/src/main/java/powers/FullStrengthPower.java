package powers;

import StaffMod.StaffMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import cardmodifier.ManaAlternateCosts_Staff;

import java.util.Iterator;

@SuppressWarnings({"WhileLoopReplaceableByForEach", "rawtypes"})
public class FullStrengthPower extends AbstractPower {
    public static final String POWER_ID = StaffMod.makeID(FullStrengthPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FullStrengthPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 0;
        updateDescription();
        this.loadRegion("deva2");
    }

    private void addModifier(CardGroup cardGroup) {
        Iterator var1 = cardGroup.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            CardModifierManager.addModifier(c, new ManaAlternateCosts_Staff(false));
        }

    }

    @Override
    public void onInitialApplication() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addModifier(p.hand);
        this.addModifier(p.drawPile);
        this.addModifier(p.discardPile);
        this.addModifier(p.exhaustPile);

    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        CardModifierManager.addModifier(card, new ManaAlternateCosts_Staff(false));
        return true;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}
