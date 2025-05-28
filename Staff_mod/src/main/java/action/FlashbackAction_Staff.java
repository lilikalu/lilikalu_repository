package action;

import cards.Flashback;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("ALL")
public class FlashbackAction_Staff extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private ArrayList<AbstractCard> cards = new ArrayList();


    public FlashbackAction_Staff() {
        this.player = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.player.discardPile.isEmpty()) {
                this.isDone = true;
            }else {
                Iterator var1 = this.player.discardPile.group.iterator();
                AbstractCard c1;
                while(var1.hasNext()) {
                    c1 = (AbstractCard)var1.next();
                    if (c1.cardID.equals(Flashback.ID)) {
                        var1.remove();
                        this.cards.add(c1);
                    }
                }
                if (this.player.discardPile.isEmpty()) {
                    this.player.discardPile.group.addAll(this.cards);
                    this.cards.clear();
                    this.isDone = true;
                }else if (this.player.discardPile.size() == 1) {
                    AbstractCard c = this.player.discardPile.getTopCard();
                    this.player.hand.addToHand(c);
                    this.player.discardPile.removeCard(c);
                    c.lighten(false);
                    c.applyPowers();
                    this.player.discardPile.group.addAll(this.cards);
                    this.cards.clear();
                    this.isDone = true;
                }else {
                    AbstractDungeon.gridSelectScreen.open(this.player.discardPile, 1, TEXT[0], false);
                    this.tickDuration();
                }
            }
        }else {
            Iterator c;
            AbstractCard derp;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); derp.unhover()) {
                    derp = (AbstractCard)c.next();
                    this.player.hand.addToHand(derp);
                    this.player.discardPile.removeCard(derp);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.player.hand.refreshHandLayout();

                for(c = this.player.discardPile.group.iterator(); c.hasNext(); derp.target_y = 0.0F) {
                    derp = (AbstractCard)c.next();
                    derp.unhover();
                    derp.target_x = (float)CardGroup.DISCARD_PILE_X;
                }
            }
            this.player.discardPile.group.addAll(this.cards);
            this.cards.clear();
            this.tickDuration();
        }

    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
