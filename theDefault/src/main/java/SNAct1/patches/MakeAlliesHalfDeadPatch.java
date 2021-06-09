package SNAct1.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import SNAct1.monsters.AbstractAllyMonster;

import static SNAct1.util.Wiz.atb;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)

// A patch to make allies halfdead at the start of the player's turn
public class MakeAlliesHalfDeadPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static void MakeAlliesHalfDead(GameActionManager instance) {
        if (AbstractDungeon.getCurrRoom() != null) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead) {
                    if (mo instanceof AbstractAllyMonster) {
                        AbstractAllyMonster ally = (AbstractAllyMonster)mo;
                        if (ally.isAlly && !ally.isTargetableByPlayer) {
                            atb(new AbstractGameAction() {
                                @Override
                                public void update() {
                                    mo.halfDead = true;
                                    this.isDone = true;
                                }
                            });
                        }
                    }
                }
            }
        }
    }
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfTurnRelics");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
