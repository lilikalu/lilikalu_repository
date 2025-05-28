package cards;

import StaffMod.StaffMod;
import action.SetManaAction_Staff;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import patchs.AbstractCardEnum;
import patchs.StaffmodPatch;

public class AcceleratedChanting extends CustomCard {
    public static final String ID = StaffMod.makeID(AcceleratedChanting.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int ManaCost = 1;
    private static final int MagicNumber = 2;
    private static final int UP_MagicNumber = 1;
    public static final String IMG_PATH = "img/cards_Staff/AcceleratedChanting.png";
    public AcceleratedChanting(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Staff_COLOR, CardRarity.UNCOMMON, CardTarget.NONE);
        StaffmodPatch.ExampleField.ManaCost.set(this, ManaCost);
        this.baseMagicNumber = MagicNumber;
        this.magicNumber = this.baseMagicNumber;
    }

    public void SpendMana() {
        if (this.purgeOnUse || this.isInAutoplay || this.freeToPlay() || EnergyPanel.totalCount >= this.costForTurn) {
            this.addToBot(new SetManaAction_Staff(-ManaCost));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        SpendMana();
        this.addToBot(new DrawCardAction(p, this.magicNumber));
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
        return new AcceleratedChanting();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UP_MagicNumber);
        }

    }
}
