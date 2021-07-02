package SNAct1.patches;

import SNAct1.monsters.BossKefka;
import SNAct1.monsters.BossTerra;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.PaperCrane;
import com.megacrit.cardcrawl.relics.PaperFrog;

// thanks to jedi's paperlyon patch
public class TerraKefkaWeakVulnPatch {
    @SpirePatch(clz = VulnerablePower.class, method = "atDamageReceive")
    public static class KefkaVulnerablePatch {
        @SpirePrefixPatch
        public static SpireReturn<Float> Prefix(VulnerablePower __instance, float damage, DamageInfo.DamageType type) {
            AbstractPlayer p = AbstractDungeon.player;
            if (__instance.owner instanceof BossKefka) {
                if (p.hasRelic(PaperFrog.ID))
                    return SpireReturn.Continue();
                return SpireReturn.Return(damage * 1.75F);
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(clz = WeakPower.class, method = "atDamageGive")
    public static class TerraWeakPatch {
        @SpirePrefixPatch
        public static SpireReturn<Float> Prefix(WeakPower __instance, float damage, DamageInfo.DamageType type) {
            AbstractPlayer p = AbstractDungeon.player;
            if (__instance.owner instanceof BossTerra) {
                if (p.hasRelic(PaperCrane.ID))
                    return SpireReturn.Continue();
                return SpireReturn.Return(damage * 0.6F);
            }
            return SpireReturn.Continue();
        }
    }
}
