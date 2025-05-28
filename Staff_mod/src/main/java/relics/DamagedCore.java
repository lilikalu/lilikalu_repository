package relics;

import StaffMod.StaffMod;
import action.SetManaAction_Staff;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DamagedCore extends CustomRelic {
    public static final String ID = StaffMod.makeID(DamagedCore.class.getSimpleName());
    private static final String IMG = "img/relics_Staff/cLanguageProgramBegin.png";
    private static final String IMG_OTL = "img/relics_Staff/outline/cLanguageProgramBegin.png";
    public DamagedCore(){
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.STARTER, AbstractRelic.LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new SetManaAction_Staff(2));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DamagedCore();
    }
}
