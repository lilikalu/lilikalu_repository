package cards;

import StaffMod.StaffMod;
import action.SetManaAction_Staff;
import basemod.abstracts.CustomCard;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import action.SelfUpgradeAction_Staff;
import patchs.StaffmodPatch;
import patchs.AbstractCardEnum;
import powers.ManaTidePower;

public class SelfUpgrade extends CustomCard {
    public static final String ID = StaffMod.makeID(SelfUpgrade.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Staff/skill.png";
    private static final int COST = 2;
    private static final int ManaCost = 2;

    public SelfUpgrade(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Staff_COLOR, CardRarity.RARE, CardTarget.NONE);
        StaffmodPatch.ExampleField.ManaCost.set(this, ManaCost);
        CardModifierManager.addModifier(this, new EtherealMod());
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    public void SpendMana() {
        if (this.purgeOnUse || this.isInAutoplay || this.freeToPlay() || EnergyPanel.totalCount >= this.costForTurn) {
            this.addToBot(new SetManaAction_Staff(-ManaCost));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        SpendMana();
        this.addToBot(new SelfUpgradeAction_Staff());
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }

        int Mana = StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player);

        if (Mana <= 0) {
            return false;
        }
        if (!AbstractDungeon.player.hasPower(ManaTidePower.POWER_ID) && Mana < ManaCost) {
            this.cantUseMessage = "法力不足。";
            return false;
        }
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SelfUpgrade();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            CardModifierManager.removeModifiersById(this, EtherealMod.ID, false);
        }
    }
}
