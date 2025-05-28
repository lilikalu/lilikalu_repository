package cards;

import StaffMod.StaffMod;
import basemod.abstracts.CustomCard;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import custom_content.CustomTags_Staff;
import action.MagicCardFromDeckToHandAction_Staff;
import patchs.AbstractCardEnum;

import java.util.Iterator;

public class SecretMagic extends CustomCard {
    public static final String ID = StaffMod.makeID(SecretMagic.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Staff/skill.png";
    private static final int COST = 0;
    public SecretMagic(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Staff_COLOR, CardRarity.RARE, CardTarget.NONE);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MagicCardFromDeckToHandAction_Staff(1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            boolean hasSkill = false;
            Iterator var5 = p.drawPile.group.iterator();

            while(var5.hasNext()) {
                AbstractCard c = (AbstractCard)var5.next();
                if (c.hasTag(CustomTags_Staff.MAGIC)) {
                    hasSkill = true;
                }
            }

            if (!hasSkill) {
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
                canUse = false;
            }

            return canUse;
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new SecretMagic();
    }

    @Override
    public void upgrade(){
        if (!this.upgraded) {
            upgradeName();
            CardModifierManager.removeModifiersById(this, "basemod:ExhaustCardModifier", false);
        }
    }

}
