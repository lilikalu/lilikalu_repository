package cards;

import StaffMod.StaffMod;
import action.SetManaAction_Staff;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import custom_content.CustomTags_Staff;
import patchs.AbstractCardEnum;

public class ManaRecovery extends CustomCard {
    public static final String ID = StaffMod.makeID(ManaRecovery.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int MagicNumber = 1;
    private static final int UP_MagicNumber = 1;
    public static final String IMG_PATH = "img/cards_Staff/ManaRecovery.png";

    public ManaRecovery() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Staff_COLOR, CardRarity.COMMON, CardTarget.NONE);
        this.baseMagicNumber = MagicNumber;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2).hasTag(CustomTags_Staff.MAGIC)) {
            this.addToBot(new GainEnergyAction(1));
            this.addToBot(new SetManaAction_Staff(this.magicNumber));
            this.addToBot(new DrawCardAction(p, this.magicNumber));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1).hasTag(CustomTags_Staff.MAGIC)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }

    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ManaRecovery();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UP_MagicNumber);
        }
    }
}
