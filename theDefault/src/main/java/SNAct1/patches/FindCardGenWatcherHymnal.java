package SNAct1.patches;

import SNAct1.relics.WatcherHymnal;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

//big thanks to alisonmoons for this
public class FindCardGenWatcherHymnal {
    //We patch the main 3 ShowCardAndAddTo... effects as mostly everything else should extend them
    @SpirePatch(clz = ShowCardAndAddToDiscardEffect.class, method = "update")
    @SpirePatch(clz = ShowCardAndAddToHandEffect.class, method = "update")
    @SpirePatch(clz = ShowCardAndAddToDrawPileEffect.class, method = "update")
    public static class CardCreatedPatch {
        @SpirePrefixPatch
        public static void cardCreated(AbstractGameEffect __instance) {
            float startDuration = ReflectionHacks.getPrivateStatic(ShowCardAndAddToDiscardEffect.class, "EFFECT_DUR");
            if (__instance.duration == startDuration) {
                if (AbstractDungeon.player.hasRelic(WatcherHymnal.ID)) {
                    WatcherHymnal r = (WatcherHymnal)AbstractDungeon.player.getRelic(WatcherHymnal.ID);
                    // r.onCreateCard((AbstractCard) ReflectionHacks.getPrivate(__instance, __instance.getClass(), "card"));
                    r.onCreateCard(); // no need to capture card type because we only want to know amount of cards being made
                }
            }
        }
    }

}
