package patchs;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import custom_content.AbstractStaffCard;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SuppressWarnings("unused")
public class AbstractStaffCardPatch {

    @SpirePatch(clz = UseCardAction.class, method = "update")
    public static class ReturnToHandPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(CardGroup.class.getName()) && m.getMethodName().equals("moveToDiscardPile"))
                        m.replace("if (this.targetCard instanceof " + AbstractStaffCard.class.getName() + "){" + AbstractStaffCardPatch.class
                                .getName() + ".returnToHandOnce(this.targetCard);}else{$_ = $proceed($$);}");
                }
            };
        }
    }

    public static void returnToHandOnce(AbstractCard card) {
        if (card instanceof AbstractStaffCard) {
            AbstractStaffCard c = (AbstractStaffCard)card;
            if (c.returnToHandOnce) {
                AbstractDungeon.player.hand.moveToHand(card);
                AbstractDungeon.player.onCardDrawOrDiscard();
            } else {
                AbstractDungeon.player.hand.moveToDiscardPile(card);
            }
        }
    }
}
