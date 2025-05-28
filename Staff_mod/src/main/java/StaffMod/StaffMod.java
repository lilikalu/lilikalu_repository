package StaffMod;

import basemod.BaseMod;
import basemod.interfaces.*;
import cards.*;
import characters.Staff;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import patchs.AbstractCardEnum;
import patchs.ThmodClassEnum;
import relics.DamagedCore;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings({"WhileLoopReplaceableByForEach", "SwitchStatementWithTooFewBranches", "ForLoopReplaceableByForEach", "FieldMayBeFinal", "unused"})
@SpireInitializer
public class StaffMod implements RelicGetSubscriber, PostPowerApplySubscriber, PostExhaustSubscriber, PostBattleSubscriber, PostDungeonInitializeSubscriber, EditCharactersSubscriber, PostInitializeSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber, OnCardUseSubscriber, EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostDrawSubscriber, PostEnergyRechargeSubscriber {
    public static final Logger logger = LogManager.getLogger(StaffMod.class.getName());
    public static String MOD_ID = "StaffMod";
    private static final String MOD_BADGE = "img/UI_Staff/badge.png";
    //攻击、技能、能力牌的图片(512)
    private static final String ATTACK_CC = "img/512/bg_attack.png";
    private static final String SKILL_CC = "img/512/bg_skill.png";
    private static final String POWER_CC = "img/512/bg_power.png";
    private static final String ENERGY_ORB_CC = "img/512/card_orb.png";
    //攻击、技能、能力牌的图片(1024)
    private static final String ATTACK_CC_PORTRAIT = "img/1024/portrait_attack.png";
    private static final String SKILL_CC_PORTRAIT = "img/1024/portrait_skill.png";
    private static final String POWER_CC_PORTRAIT = "img/1024/portrait_power.png";
    private static final String ENERGY_ORB_CC_PORTRAIT = "img/1024/card_large_orb.png";
    public static final String CARD_ENERGY_ORB = "img/UI_Staff/card_small_orb.png";
    //选英雄界面的角色图标、选英雄时的背景图片
    private static final String MY_CHARACTER_BUTTON = "img/charSelect/StaffButton.png";
    private static final String MARISA_PORTRAIT = "img/charSelect/StaffPortrait.png";
    public static final Color SILVER = CardHelper.getColor(200, 200, 200);
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    public static ArrayList<AbstractCard> recyclecards = new ArrayList<>();

    public StaffMod() {
        //构造方法，初始化各种参数
        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.Staff_COLOR, SILVER, SILVER, SILVER, SILVER, SILVER, SILVER, SILVER, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC, ATTACK_CC_PORTRAIT, SKILL_CC_PORTRAIT,POWER_CC_PORTRAIT, ENERGY_ORB_CC_PORTRAIT, CARD_ENERGY_ORB);
    }

    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }

    @Override
    public void receiveEditCharacters() {
        //添加角色到MOD中
        logger.info("========================= 开始加载人物 =========================");
        BaseMod.addCharacter(new Staff("Staff"), MY_CHARACTER_BUTTON, MARISA_PORTRAIT, ThmodClassEnum.Staff_CLASS);
        logger.info("========================= 人物加载完成 =========================");
    }
    //初始化整个MOD,一定不能删
    public static void initialize() {
        logger.info("========================= 开始初始化 =========================");
        new StaffMod();
        logger.info("========================= 初始化完成 =========================");
    }

    @Override
    public void receiveEditCards() {
        //将卡牌批量添加
        logger.info("========================= 开始添加卡牌 =========================");
        loadCardsToAdd();
        Iterator<AbstractCard> var1 = this.cardsToAdd.iterator();
        while (var1.hasNext()) {
            AbstractCard card = var1.next();
            BaseMod.addCard(card);
        }
        logger.info("========================= 卡牌添加完成 =========================");
    }

    @Override
    public void receivePostExhaust(AbstractCard c) {}

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower pow, AbstractCreature target, AbstractCreature owner) {
    }


    @Override
    public void receivePowersModified() {}


    @Override
    public void receivePostDungeonInitialize() {}


    @Override
    public void receivePostDraw(AbstractCard arg0) {}

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }


    @Override
    public void receiveEditKeywords() {
        String keywordsPath;
        logger.info("========================= 开始加载关键字 =========================");
        switch (Settings.language) {
            case ZHS:
            default:
                keywordsPath = "localization/ThMod_Staff_keywords-zh.json";
                break;
        }
        Gson gson = new Gson();
        Keywords keywords = gson.fromJson(loadJson(keywordsPath), Keywords.class);
        Keyword[] var4 = keywords.keywords;
        int var5 = var4.length;
        for (int var6 = 0; var6 < var5; var6++) {
            Keyword key = var4[var6];
            BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
        }
        logger.info("========================= 关键字加载完成 =========================");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("========================= 开始读取文本 =========================");
        //读取遗物，卡牌，能力，药水，事件的JSON文本
        String relic, card, power, ui, potion="", event="";
        card = "localization/ThMod_Staff_cards-zh.json";
        relic = "localization/ThMod_Staff_relics-zh.json";
        power = "localization/ThMod_Staff_powers-zh.json";
        ui = "localization/ThMod_Staff_ui-zh.json";

        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
        logger.info("========================= 文本读取完成 =========================");
    }

    private void loadCardsToAdd() {
        //将自定义的卡牌添加到这里
        this.cardsToAdd.clear();
        this.cardsToAdd.add(new Strike());
        this.cardsToAdd.add(new Defend());
        this.cardsToAdd.add(new RestoreMana());
        this.cardsToAdd.add(new Fireball());
        this.cardsToAdd.add(new AcceleratedChanting());
        this.cardsToAdd.add(new Dodge());
        this.cardsToAdd.add(new SwordQi());
        this.cardsToAdd.add(new NightmareHaunting());
        this.cardsToAdd.add(new ManaTide());
        this.cardsToAdd.add(new ManaSource());
        this.cardsToAdd.add(new DivineBuster());
        this.cardsToAdd.add(new Wallop());
        this.cardsToAdd.add(new Charge());
        this.cardsToAdd.add(new FrostArrow());
        this.cardsToAdd.add(new SelfUpgrade());
        this.cardsToAdd.add(new SecretMagic());
        this.cardsToAdd.add(new CeruleanHolyStrike());
        this.cardsToAdd.add(new DoubleFireball());
        this.cardsToAdd.add(new ManaShield());
        this.cardsToAdd.add(new Sacrifice());
        this.cardsToAdd.add(new TripleFireball());
        this.cardsToAdd.add(new FullStrength());
        this.cardsToAdd.add(new DoubleCasting());
        this.cardsToAdd.add(new WideAreaProtection());
        this.cardsToAdd.add(new MagicBlindBox());
        this.cardsToAdd.add(new ManaRecovery());
        this.cardsToAdd.add(new TheWorld());
        this.cardsToAdd.add(new MysteriousBook());
        this.cardsToAdd.add(new BattleMageRing());
        this.cardsToAdd.add(new Overload());
        this.cardsToAdd.add(new BasicShield());
        this.cardsToAdd.add(new Flashback());
        this.cardsToAdd.add(new DivineShooter());
        this.cardsToAdd.add(new SpellPreparation());
        this.cardsToAdd.add(new ArrowStorm());
        this.cardsToAdd.add(new RestrictLock());
        this.cardsToAdd.add(new BackupEnergy());
        this.cardsToAdd.add(new SilentCasting());
        this.cardsToAdd.add(new BottledCurse());
        this.cardsToAdd.add(new Healing());
        this.cardsToAdd.add(new CursedStrike());
        this.cardsToAdd.add(new EmbraceDarkness());
        this.cardsToAdd.add(new StarlightBreaker());
        this.cardsToAdd.add(new MagicBullet());
        this.cardsToAdd.add(new ChaosSpell());
        this.cardsToAdd.add(new Turbo());
        this.cardsToAdd.add(new Nightmare());
    }

    @Override
    public void receiveEditRelics() {
        logger.info("========================= 开始添加遗物 =========================");
        //将自定义的遗物添加到这里
        BaseMod.addRelicToCustomPool(new DamagedCore(),AbstractCardEnum.Staff_COLOR);
        logger.info("========================= 遗物添加完成 =========================");
    }

    @Override
    public void receiveRelicGet(AbstractRelic relic) {
        //移除遗物。
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {}

    @Override
    public void receivePostBattle(AbstractRoom r) {}

    @Override
    public void receivePostInitialize() {}

    @Override
    public void receivePostEnergyRecharge() {
        Iterator<AbstractCard> var1 = recyclecards.iterator();

        while (var1.hasNext()) {
            AbstractCard c = var1.next();
            AbstractCard card = c.makeStatEquivalentCopy();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, false, true, true));
        }
        recyclecards.clear();
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    class Keywords {
        Keyword[] keywords;
    }
}