package SNAct1.relics;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static SNAct1.util.Wiz.atb;

public class TerraEsperkinsMagicite extends CustomRelic {

    public static final String ID = SNAct1Mod.makeID(TerraEsperkinsMagicite.class.getSimpleName());
    private static String pathToImg = SNAct1Mod.makeRelicPath("TerraEsperkinsMagicite.png");
    private static String pathToImgOutline = SNAct1Mod.makeRelicOutlinePath("TerraEsperkinsMagicite.png");

    private static final Texture IMG = TexLoader.getTexture(pathToImg);
    private static final Texture OUTLINE = TexLoader.getTexture(pathToImgOutline);

    private boolean triggeredThisTurn = false;

    public TerraEsperkinsMagicite() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    public void onExhaust(AbstractCard card) {
        if (!this.triggeredThisTurn) {
            this.triggeredThisTurn = true;
            flash();
            atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            atb(new GainEnergyAction(1));
        }
    }

    public TerraEsperkinsMagicite makeCopy() {
        return new TerraEsperkinsMagicite();
    }
}