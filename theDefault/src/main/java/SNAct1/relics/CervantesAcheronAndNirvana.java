package SNAct1.relics;

import SNAct1.SNAct1Mod;
import SNAct1.powers.SoulChargePower;
import SNAct1.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static SNAct1.SNAct1Mod.makeID;
import static SNAct1.util.Wiz.atb;

public class CervantesAcheronAndNirvana extends CustomRelic {
    public static final String ID = makeID(CervantesAcheronAndNirvana.class.getSimpleName());
    private static String pathToImg = SNAct1Mod.makeRelicPath("CervantesAcheronAndNirvana.png");
    private static String pathToImgOutline = SNAct1Mod.makeRelicOutlinePath("CervantesAcheronAndNirvana.png");

    private static final Texture IMG = TexLoader.getTexture(pathToImg);
    private static final Texture OUTLINE = TexLoader.getTexture(pathToImgOutline);

    boolean triggered = false;

    public CervantesAcheronAndNirvana() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void atTurnStart() {
        if (grayscale) {
            grayscale = false;
        }
        if (triggered) {
            triggered = false;
        }
    }

    @Override
    public void onVictory() {
        if (grayscale) {
            grayscale = false;
        }
        if (triggered) {
            triggered = false;
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        boolean targetHasDebuff = false;
        if (info.type == DamageInfo.DamageType.NORMAL && !triggered) {
            for (AbstractPower power : target.powers) {
                if (power.type == AbstractPower.PowerType.DEBUFF) {
                    targetHasDebuff = true;
                    break;
                }
            }
            if (targetHasDebuff) {
                atb (new GainEnergyAction(1));
                triggered = true;
                grayscale = true;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
