package cards;

import StaffMod.StaffMod;
import basemod.abstracts.CustomCard;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import custom_content.AbstractDungeon_Staff;
import custom_content.CustomTags_Staff;
import patchs.AbstractCardEnum;

public class MysteriousBook extends CustomCard {
    public static final String ID = StaffMod.makeID(MysteriousBook.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int MagicNumber = 3;
    private static final int UP_MagicNumber = 2;
    public static final String IMG_PATH = "img/cards_Staff/skill.png";

    public MysteriousBook() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Staff_COLOR, CardRarity.RARE, CardTarget.NONE);
        this.baseMagicNumber = MagicNumber;
        this.magicNumber = this.baseMagicNumber;
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            AbstractCard card = AbstractDungeon_Staff.returnTrulyRandomCardInCombat(CustomTags_Staff.MAGIC).makeCopy();
            if (card.cost > 0) {
                card.cost = 0;
                card.costForTurn = 0;
                card.isCostModified = true;
            }

            this.addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new MysteriousBook();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UP_MagicNumber);
        }
    }
}
