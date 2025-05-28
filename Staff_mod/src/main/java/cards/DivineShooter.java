package cards;

import StaffMod.StaffMod;
import action.DivineShooterAction_Staff;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import custom_content.AbstractStaffCard;
import patchs.StaffmodPatch;
import patchs.AbstractCardEnum;

public class DivineShooter extends AbstractStaffCard {
    public static final String ID = StaffMod.makeID(DivineShooter.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Staff/DivineShooter.png";
    private static final int COST = -1;
    private static final int ManaCost = 0;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_DMG = 1;

    public DivineShooter(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Staff_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        StaffmodPatch.ExampleField.ManaCost.set(this, ManaCost);
        this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DivineShooterAction_Staff(this, p, this.freeToPlayOnce, this.energyOnUse));

    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new DivineShooter();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
