package cards;

import StaffMod.StaffMod;
import action.SetManaAction_Staff;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import patchs.AbstractCardEnum;
import patchs.StaffmodPatch;

public class Charge extends CustomCard {
    public static final String ID = StaffMod.makeID(Charge.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int ManaCost = 1;
    public static final String IMG_PATH = "img/cards_Staff/skill.png";
    public Charge(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Staff_COLOR, CardRarity.COMMON, CardTarget.NONE);
        StaffmodPatch.ExampleField.ManaCost.set(this, ManaCost);
    }

    public void SpendMana() {
        if (this.purgeOnUse || this.isInAutoplay || this.freeToPlay() || EnergyPanel.totalCount >= this.costForTurn) {
            this.addToBot(new SetManaAction_Staff(-ManaCost));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        SpendMana();
        if (this.upgraded) {
            this.addToBot(new GainEnergyAction(2));
        } else {
            this.addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }

        int Mana = StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player);

        return Mana > 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Charge();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
