package SNAct1.patches;

import SNAct1.powers.ArcadianVisionPower;
import SNAct1.powers.ChillDefensePower;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import SNAct1.powers.NinisGracePower;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "addBlock",
        paramtypez={
                int.class,
        }

)

// thanks to darkglade and squeeny for this
public class FindMonsterBlockGainPatch {
    @SpireInsertPatch(locator = FindMonsterBlockGainPatch.Locator.class, localvars = {"tmp"})
    public static void TriggerOnGainedBlock(AbstractCreature instance, int blockAmount, @ByRef float[] tmp) {
        if (!instance.isPlayer) {
            if (tmp[0] > 0.0F) {
                AbstractCreature enemy = instance;
                for (AbstractPower p : enemy.powers) {
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