package custom_content;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractDungeon_Staff extends AbstractDungeon {

    public AbstractDungeon_Staff(String name, String levelId, AbstractPlayer p, ArrayList<String> newSpecialOneTimeEventList) {
        super(name, levelId, p, newSpecialOneTimeEventList);
    }
    public AbstractDungeon_Staff(String name, AbstractPlayer p, SaveFile saveFile) {
        super(name, p, saveFile);

    }

    public static AbstractCard returnTrulyRandomCardInCombat(AbstractCard.CardTags tags) {
        ArrayList<AbstractCard> list = new ArrayList<>();
        Iterator<AbstractCard> var2 = srcCommonCardPool.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = var2.next();
            if (c.hasTag(tags) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcUncommonCardPool.group.iterator();

        while(var2.hasNext()) {
            c = var2.next();
            if (c.hasTag(tags) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcRareCardPool.group.iterator();

        while(var2.hasNext()) {
            c = var2.next();
            if (c.hasTag(tags) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        return list.get(cardRandomRng.random(list.size() - 1));
    }
}
