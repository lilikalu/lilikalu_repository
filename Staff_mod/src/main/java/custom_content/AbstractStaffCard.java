package custom_content;

import action.SetManaAction_Staff;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import cardmodifier.ManaAlternateCosts_Staff;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import patchs.StaffmodPatch;
import powers.ManaTidePower;

public abstract class AbstractStaffCard extends CustomCard {
    public int manacost = 0;
    public boolean returnToHandOnce;

    public AbstractStaffCard(String id, String name, String img, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target){
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        CardModifierManager.addModifier(this, new ManaAlternateCosts_Staff(true));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        if (this.manacost != 0) {
            int mana = StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player);
            if (mana <= 0) {
                return false;
            }
            int requiredMana = this.manacost - (AbstractDungeon.player.hasPower(ManaTidePower.POWER_ID) ? 1 : 0);
            if (mana < requiredMana) {
                this.cantUseMessage = "法力不足。";
                return false;
            }
        }
        return true;
    }

    public void SpendMana() {
        if (this.manacost != 0) {
            if (this.purgeOnUse || this.isInAutoplay || this.freeToPlay() || EnergyPanel.totalCount >= this.costForTurn) {
                this.addToBot(new SetManaAction_Staff(-manacost));
            }
        }
    }
}
