package cardmodifier;

import action.SetManaAction_Staff;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.AlternateCardCostModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import custom_content.CustomTags_Staff;
import patchs.StaffmodPatch;
import powers.ManaTidePower;

public class ManaAlternateCosts_Staff extends AbstractCardModifier implements AlternateCardCostModifier {
    public static String ID = ManaAlternateCosts_Staff.class.getSimpleName();
    private final boolean isInherent;
    private int ManaCost = 0;

    public ManaAlternateCosts_Staff(boolean isInherent) {
        this.priority = 1;
        this.isInherent = isInherent;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return isInherent;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format("魔法 。 NL %s", rawDescription);
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ManaAlternateCosts_Staff.ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags_Staff.MAGIC);
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(CustomTags_Staff.MAGIC);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ManaAlternateCosts_Staff(this.isInherent);
    }

    @Override
    public int getAlternateResource(AbstractCard card) {
        this.ManaCost = StaffmodPatch.ExampleField.ManaCost.get(card);
        int Mana = StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player);
        int amount = Mana - this.ManaCost;

        if (AbstractDungeon.player.hasPower(ManaTidePower.POWER_ID) && Mana > 0) {
            ++amount;
        }
        return Math.max(amount, 0);
    }

    @Override
    public boolean canSplitCost(AbstractCard card) {
        return true;
    }

    @Override
    public int spendAlternateCost(AbstractCard card, int costToSpend) {
        int resource = getAlternateResource(card);
        if (card.cost == -1) {
            int Mana = StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player);
            this.addToBot(new SetManaAction_Staff(-Mana));
            return costToSpend;
        }
        if (resource >= costToSpend) {
            this.addToBot(new SetManaAction_Staff(-(this.ManaCost + costToSpend)));
            costToSpend = 0;
        }
        return costToSpend;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
