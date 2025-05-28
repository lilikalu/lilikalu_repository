package patchs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class StaffmodPatch {

    //用于添加int ManaCost到Abstractcard
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class ExampleField {
        public static SpireField<Integer> ManaCost = new SpireField<>(() -> 0);
    }

    //用于添加int Mana到AbstractPlayer
    @SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class ExampleField2 {
        public static SpireField<Integer> Mana = new SpireField<>(() -> 0);
    }


}
