package cards;

import StaffMod.StaffMod;
import basemod.abstracts.CustomCard;
import basemod.cardmods.InnateMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patchs.AbstractCardEnum;
import powers.ManaSourcePower;

public class ManaSource extends CustomCard{
    public static final String ID = StaffMod.makeID(ManaSource.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Staff/ManaSource.png";
    private static final int COST = 1;

    public ManaSource(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, AbstractCardEnum.Staff_COLOR, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new ManaSourcePower(p , 1),  1));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ManaSource();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            CardModifierManager.addModifier(this, new InnateMod());
        }
    }
}
