package cards;

import StaffMod.StaffMod;
import action.SetManaAction_Staff;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patchs.AbstractCardEnum;
import patchs.StaffmodPatch;

public class BackupEnergy extends CustomCard {
    public static final String ID = StaffMod.makeID(BackupEnergy.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -2;
    private static final int MagicNumber = 1;
    private static final int UP_MagicNumber = 1;
    public static final String IMG_PATH = "img/cards_Staff/skill.png";

    public BackupEnergy() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Staff_COLOR, CardRarity.BASIC, CardTarget.NONE);
        this.baseMagicNumber = MagicNumber;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void triggerWhenDrawn() {
        if (StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player) != 0) {
            this.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        }
    }

    @Override
    public void triggerOnExhaust() {
        this.addToBot(new GainEnergyAction(this.magicNumber));
        this.addToBot(new SetManaAction_Staff(this.magicNumber * 2));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new BackupEnergy();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UP_MagicNumber);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

}
