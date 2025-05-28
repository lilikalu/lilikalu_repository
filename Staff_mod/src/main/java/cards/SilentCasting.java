package cards;

import StaffMod.StaffMod;
import action.SilentCastingAction_Staff;
import basemod.abstracts.CustomCard;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patchs.AbstractCardEnum;

public class SilentCasting extends CustomCard {
    public static final String ID = StaffMod.makeID(SilentCasting.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    public static final String IMG_PATH = "img/cards_Staff/SilentCasting.png";

    public SilentCasting(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Staff_COLOR, CardRarity.RARE, CardTarget.NONE);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SilentCastingAction_Staff(p, 10));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            CardModifierManager.removeModifiersById(this, ExhaustMod.ID, false);
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new SilentCasting();
    }
}
