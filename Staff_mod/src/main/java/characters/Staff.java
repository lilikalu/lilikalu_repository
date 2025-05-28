package characters;

import basemod.abstracts.CustomPlayer;
import cards.RestoreMana;
import cards.Defend;
import cards.Fireball;
import cards.Strike;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import energyorb.EnergyOrb_Staff;
import patchs.ThmodClassEnum;
import patchs.AbstractCardEnum;
import relics.DamagedCore;

import java.util.ArrayList;

public class Staff extends CustomPlayer {
    //初始能量
    private static final int ENERGY_PER_TURN = 3;
    //以下图片依次作用为[篝火休息处的角色背影2，篝火休息处的角色背影1，角色死亡后倒下的图片，角色平常站立时的图片 .
    private static final String Staff_SHOULDER_2 = "img/char_Staff/shoulder2.png";
    private static final String Staff_SHOULDER_1 = "img/char_Staff/shoulder1.png";
    private static final String Staff_CORPSE = "img/char_Staff/fallen.png";
    private static final String Staff_STAND = "img/char_Staff/Staff.png";
    //初始生命，最大生命，初始金币,初始的充能球栏位,最后一个应该是进阶14时的最大生命值下降
    private static final int STARTING_HP = 75;
    private static final int MAX_HP = 75;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 5;
    //返回一个颜色
    public static final Color SILVER = CardHelper.getColor(200, 200, 200);

    public Staff(String name) {
        //构造方法，初始化参数
        super(name, ThmodClassEnum.Staff_CLASS, new EnergyOrb_Staff(), null, null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 220.0F * Settings.scale;
        initializeClass(Staff_STAND, Staff_SHOULDER_2, Staff_SHOULDER_1, Staff_CORPSE, getLoadout(), 0.0F, 5.0F, 240.0F, 300.0F, new EnergyManager(ENERGY_PER_TURN));
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        //添加初始卡组
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Fireball.ID);
        retVal.add(RestoreMana.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        //添加初始遗物
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(DamagedCore.ID);
        UnlockTracker.markRelicAsSeen(DamagedCore.ID);
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        String title;
        String flavor;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "Staff";
            flavor = "一个有自我意识的法杖。";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "Staff";
            flavor = "一个有自我意识的法杖。";
        } else {
            title = "Staff";
            flavor = "一个有自我意识的法杖。";
        }

        return new CharSelectInfo(title, flavor, STARTING_HP, MAX_HP,HAND_SIZE , STARTING_GOLD, ASCENSION_MAX_HP_LOSS, this, getStartingRelics(), getStartingDeck(), false);
    }


    @Override
    public String getTitle(PlayerClass playerClass) {
        //应该是进游戏后左上角的角色名
        String title;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "Staff";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "Staff";
        } else {
            title = "Staff";
        }

        return title;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        //选择卡牌颜色
        return AbstractCardEnum.Staff_COLOR;
    }

    @Override
    public Color getCardRenderColor() {
        return SILVER;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return null;
    }

    @Override
    public Color getCardTrailColor() {
        return SILVER;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {}

    public void updateOrb(int orbCount) {
        this.energyOrb.updateOrb(orbCount);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return null;
    }

    @Override
    public String getLocalizedCharacterName() {
        String char_name;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            char_name = "Staff";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            char_name = "Staff";
        } else {
            char_name = "Staff";
        }
        return char_name;
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Staff(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return SpireHeart.DESCRIPTIONS[10];
    }

    @Override
    public Color getSlashAttackColor() {
        return SILVER;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
            AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getVampireText() {
        return null;
    }
    
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
    }
}