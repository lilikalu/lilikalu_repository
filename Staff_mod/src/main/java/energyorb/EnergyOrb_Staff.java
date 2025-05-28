package energyorb;

import basemod.abstracts.CustomEnergyOrb;

public class EnergyOrb_Staff extends CustomEnergyOrb {
    private static final String[] ORB_TEXTURES = new String[] { "img/UI_Staff/EPanel/layer1.png", "img/UI_Staff/EPanel/layer2.png", "img/UI_Staff/EPanel/layer3.png", "img/UI_Staff/EPanel/layer4.png", "img/UI_Staff/EPanel/layer5.png", "img/UI_Staff/EPanel/layer6.png", "img/UI_Staff/EPanel/layer1d.png", "img/UI_Staff/EPanel/layer2d.png", "img/UI_Staff/EPanel/layer3d.png", "img/UI_Staff/EPanel/layer4d.png", "img/UI_Staff/EPanel/layer5d.png" };
    private static final String ORB_VFX = "img/UI_Staff/vfx.png";
    private static final float[] LAYER_SPEED = new float[] { -40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F };
    public EnergyOrb_Staff(){
        super(ORB_TEXTURES, ORB_VFX, null);
    }
}
