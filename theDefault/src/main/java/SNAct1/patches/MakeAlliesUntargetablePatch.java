package SNAct1.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import javassist.CtBehavior;
import SNAct1.monsters.AbstractAllyMonster;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "updateSingleTargetInput"
)

// A patch to make allies untargetable by the player
public class MakeAlliesUntargetablePatch {
    @SpireInsertPatch(locator = Locator.class, localvars = {"hoveredMonster"})
    public static void MakeHoveredMonsterNull(AbstractPlayer instance, @ByRef AbstractMonster[] hoveredMonster) {
        if (hoveredMonster[0] instanceof AbstractAllyMonster) {
            AbstractAllyMonster ally = (AbstractAllyMonster)hoveredMonster[0];
            if (ally.isAlly && !ally.isTargetableByPlayer) {
                hoveredMonster[0] = null;
            }
        }
    }
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(MonsterGroup.class, "areMonstersBasicallyDead");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
