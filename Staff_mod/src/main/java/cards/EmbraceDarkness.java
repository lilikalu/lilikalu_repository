package cards;

import StaffMod.StaffMod;
import action.CurseAction_Staff;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import custom_content.AbstractStaffCard;
import patchs.AbstractCardEnum;
import patchs.StaffmodPatch;

import java.util.Iterator;

public class EmbraceDarkness extends AbstractStaffCard {
    public static final String ID = StaffMod.makeID(EmbraceDarkness.class.getSimpleName());
    public static final String IMG_PATH = "img/cards_Staff/skill.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int ManaCost = 0;
    private static final int BLOCK_AMT = 12;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int MagicNumber = 2;
    private static final int UP_MagicNumber = 1;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL;
    public EmbraceDarkness() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, AbstractCardEnum.Staff_COLOR, RARITY, TARGET);
        this.manacost = ManaCost;
        StaffmodPatch.ExampleField.ManaCost.set(this, ManaCost);
        this.baseBlock = BLOCK_AMT;
        this.baseMagicNumber = MagicNumber;
        this.magicNumber =this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        SpendMana();
        this.addToBot(new GainBlockAction(p, p, this.block));
        for (int i = 0; i < 2; i++) {
            Iterator<AbstractMonster> var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while(var1.hasNext()) {
                this.addToBot(new CurseAction_Staff(m, p, this.magicNumber, true));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EmbraceDarkness();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UP_MagicNumber);
        }
    }
}
