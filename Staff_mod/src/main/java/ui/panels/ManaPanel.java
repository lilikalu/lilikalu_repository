package ui.panels;

import StaffMod.StaffMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import patchs.StaffmodPatch;
import patchs.ThmodClassEnum;

public class ManaPanel extends AbstractPanel {
    public static final String ID = StaffMod.makeID(ManaPanel.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final Texture img = new Texture("img/UI_Staff/ManaPanel/Mana3.png");
    private static final Color MPTextColor = new Color(1.0F, 1.0F, 0.86F, 1.0F);
    private final Hitbox TipHitBox = new Hitbox(0.0F, 0.0F, 156.0F * imgScale, 156.0F * imgScale);
    public static final float imgScale = 0.5F * Settings.scale;
    private int Mana = 0;

    public ManaPanel() {
        super(200.0F * Settings.xScale, 300.0F * Settings.yScale, -720.0F * Settings.xScale, 300.0F * Settings.yScale, img, true);
    }

    public void clearMana() {
        StaffmodPatch.ExampleField2.Mana.set(AbstractDungeon.player, 0);
        this.Mana = 0;
    }

    public static boolean canRender() {
        if (CardCrawlGame.isInARun()) {
            return AbstractDungeon.player.chosenClass == ThmodClassEnum.Staff_CLASS;
        }
        return false;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!this.isHidden && canRender()) {
            this.TipHitBox.move(this.current_x, this.current_y);
            sb.setColor(Color.WHITE);
            sb.draw(img, this.current_x - 78.0F, this.current_y - 78.0F, 78.0F, 78.0F, 156.0F, 156.0F, imgScale, imgScale, 0.0F, 0, 0, 156, 156, false, false);
            FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), Integer.toString(this.Mana), this.current_x, this.current_y, MPTextColor.cpy());
            this.TipHitBox.render(sb);
            if (this.TipHitBox.hovered && !AbstractDungeon.isScreenUp) {
                TipHelper.renderGenericTip(this.current_x + 78.0F, this.current_y + 78.0F, uiStrings.TEXT[0], uiStrings.TEXT[1]);
            }
        }
    }

    public void update() {
        updateMana();
        this.TipHitBox.update();
        if (this.TipHitBox.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }
    }

    public void updateMana() {
        this.Mana = StaffmodPatch.ExampleField2.Mana.get(AbstractDungeon.player);
    }
}
