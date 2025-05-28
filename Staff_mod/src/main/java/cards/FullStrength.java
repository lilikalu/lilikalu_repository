package cards;

import StaffMod.StaffMod;
import basemod.abstracts.CustomCard;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patchs.AbstractCardEnum;
import powers.FullStrengthPower;

public class FullStrength extends CustomCard {
    public static final String ID = StaffMod.makeID(FullStrength.class.getSimpleName());
    public static final String IMG_PATH = "img/cards_Staff/FullStrength.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;

    public FullStrength() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, AbstractCardEnum.Staff_COLOR, CardRarity.RARE, CardTarget.NONE);
        CardModifierManager.addModifier(this, new EtherealMod());
        this.cardsToPreview = new StarlightBreaker();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new FullStrengthPower(p)));
        this.addToBot(new MakeTempCardInDiscardAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new FullStrength();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            CardModifierManager.removeModifiersById(this, EtherealMod.ID, false);
        }
    }
}
