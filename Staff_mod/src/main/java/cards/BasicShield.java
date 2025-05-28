package cards;

import StaffMod.StaffMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import custom_content.AbstractStaffCard;
import patchs.StaffmodPatch;
import patchs.AbstractCardEnum;

public class BasicShield extends AbstractStaffCard {
    public static final String ID = StaffMod.makeID(BasicShield.class.getSimpleName());
    public static final String IMG_PATH = "img/cards_Staff/WideAreaProtection.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private int Number = 0;
    private static final int COST = 0;
    private static final int ManaCost = 1;
    private static final int BLOCK_AMT = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;

    public BasicShield() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, AbstractCardEnum.Staff_COLOR, RARITY, TARGET);
        this.returnToHandOnce = true;
        this.manacost = ManaCost;
        StaffmodPatch.ExampleField.ManaCost.set(this, ManaCost);
        this.baseBlock = BLOCK_AMT;
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        SpendMana();
        this.addToBot(new GainBlockAction(p, p, this.block));
        Number++;
        this.upgradeMagicNumber(1);
    }

    @Override
    public void triggerAtStartOfTurn() {
        Number = 0;
        this.upgradeMagicNumber(-3);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (Number >= 3) {
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public AbstractCard makeCopy(){
        return new BasicShield();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }
}
