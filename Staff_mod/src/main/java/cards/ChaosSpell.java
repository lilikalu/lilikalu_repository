package cards;

import StaffMod.StaffMod;
import action.ChaosSpellAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import custom_content.AbstractStaffCard;
import patchs.AbstractCardEnum;
import patchs.StaffmodPatch;

public class ChaosSpell extends AbstractStaffCard {
    public static final String ID = StaffMod.makeID(StarlightBreaker.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Staff/attack.png";
    private static final int COST = 2;
    private static final int ManaCost = 0;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;

    public ChaosSpell() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, AbstractCardEnum.Staff_COLOR, RARITY, TARGET);
        this.manacost = ManaCost;
        StaffmodPatch.ExampleField.ManaCost.set(this, ManaCost);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        SpendMana();
        if (!this.upgraded) {
            this.addToBot(new ChaosSpellAction(m, p, 40));
        }else {
            this.addToBot(new ChaosSpellAction(m, p, 60));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChaosSpell();
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
