package cards;

import StaffMod.StaffMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patchs.AbstractCardEnum;
import patchs.StaffmodPatch;

public class WideAreaProtection extends CustomCard {
    public static final String ID = StaffMod.makeID(WideAreaProtection.class.getSimpleName());
    public static final String IMG_PATH = "img/cards_Staff/WideAreaProtection.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;

    private static final int BLOCK_AMT = 12;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int MagicNumber = 2;
    private static final int UP_MagicNumber = 1;
    public WideAreaProtection() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Staff_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = BLOCK_AMT;
        this.baseMagicNumber = MagicNumber;
        this.magicNumber =this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    protected void applyPowersToBlock() {
        int damage = this.baseBlock;
        int amount = StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player);
        this.baseBlock += amount * this.magicNumber;

        super.applyPowersToBlock();
        this.baseBlock = damage;
        this.isBlockModified = (this.block != this.baseBlock);
    }

    @Override
    public AbstractCard makeCopy(){
        return new WideAreaProtection();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.upgradeMagicNumber(UP_MagicNumber);
        }

    }
}
