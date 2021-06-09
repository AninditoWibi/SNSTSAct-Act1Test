package SNAct1.patches;

import SNAct1.powers.ArcadianVisionPower;
import SNAct1.powers.ChillDefensePower;
import SNAct1.powers.NinisGracePower;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "addBlock",
        paramtypez={
                int.class,
        }

)

// thanks to darkglade and squeeny for this
public class FindPlayerBlockGainPatch {
    @SpireInsertPatch(locator = FindPlayerBlockGainPatch.Locator.class, localvars = {"tmp"})
    public static void TriggerOnGainedBlock(AbstractCreature instance, @ByRef float[] tmp) {
        if (instance.isPlayer) {
            if (tmp[0] > 0.0F) {
                for (AbstractPower p : instance.powers) {
                    // tfw i remember starbreaker and her steal-buff card exists
                    if (p.ID.equals(NinisGracePower.POWER_ID)) {
                        NinisGracePower ngp = (NinisGracePower)p;
                        tmp[0] = ngp.onOwnerGainedBlock(tmp[0]);
                    }
                    if (p.ID.equals(ChillDefensePower.POWER_ID)) {
                        ChillDefensePower cdp = (ChillDefensePower)p;
                        tmp[0] = cdp.onOwnerGainedBlock(tmp[0]);
                    }
                    if (p.ID.equals(ArcadianVisionPower.POWER_ID)) {
                        ArcadianVisionPower avp = (ArcadianVisionPower)p;
                        tmp[0] = avp.onOwnerGainedBlock(tmp[0]);
                    }
                }
            }
        }
    }
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
