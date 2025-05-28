package cards;

import StaffMod.StaffMod;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import custom_content.AbstractStaffCard;
import patchs.AbstractCardEnum;
import patchs.StaffmodPatch;

import java.util.Iterator;

public class StarlightBreaker extends AbstractStaffCard {
    public static final String ID = StaffMod.makeID(StarlightBreaker.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Staff/StarlightBreaker_Staff.png";
    private static final int COST = 10;
    private static final int ManaCost = 10;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;

    public StarlightBreaker() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, AbstractCardEnum.Staff_COLOR, RARITY, TARGET);
        setDisplayRarity(RARITY);
        this.manacost = ManaCost;
        StaffmodPatch.ExampleField.ManaCost.set(this, ManaCost);
        CardModifierManager.addModifier(this, new ExhaustMod());
        CardModifierManager.addModifier(this, new EtherealMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        SpendMana();
        int[] damage = new int[AbstractDungeon.getMonsters().monsters.size()];
        Iterator<AbstractMonster> iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        int i = 0;

        while (iterator.hasNext() && i < damage.length) {
            AbstractMonster mo = iterator.next();
            damage[i++] = mo.maxHealth;
        }

        this.addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE));

    }

    @Override
    public void setDisplayRarity(AbstractCard.CardRarity rarity) {
        this.bannerSmallRegion = ImageMaster.CARD_BANNER_RARE;
        this.bannerLargeRegion = ImageMaster.CARD_BANNER_RARE_L;
        this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_RARE;
        this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_RARE_L;
        this.frameMiddleRegion = ImageMaster.CARD_RARE_FRAME_MID;
        this.frameLeftRegion = ImageMaster.CARD_RARE_FRAME_LEFT;
        this.frameRightRegion = ImageMaster.CARD_RARE_FRAME_RIGHT;
        this.frameMiddleLargeRegion = ImageMaster.CARD_RARE_FRAME_MID_L;
        this.frameLeftLargeRegion = ImageMaster.CARD_RARE_FRAME_LEFT_L;
        this.frameRightLargeRegion = ImageMaster.CARD_RARE_FRAME_RIGHT_L;
    }

    @Override
    public AbstractCard makeCopy() {
        return new StarlightBreaker();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }

}
