package SNAct1.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
                AbstractPower p = instance.getPower(NinisGracePower.POWER_ID);
                if (p != null) {
                    tmp[0] = tmp[0] * ((NinisGracePower)p).getBlockGainMult(); // multiplied by blockGainMult in NinisGracePower
                    p.amount--;
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