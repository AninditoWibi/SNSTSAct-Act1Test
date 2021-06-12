package SNAct1.relics;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static SNAct1.util.Wiz.atb;

public class IroncladSacredFlame extends CustomRelic {

    public static final String ID = SNAct1Mod.makeID("IroncladSacredFlame");
    private static String pathToImg = SNAct1Mod.makeRelicPath("IroncladSacredFlame.png");
    private static String pathToImgOutline = SNAct1Mod.makeRelicOutlinePath("IroncladSacredFlame.png");

    private static final Texture IMG = TexLoader.getTexture(pathToImg);
    private static final Texture OUTLINE = TexLoader.getTexture(pathToImgOutline);

    static final int USES = 3;
    static final int HEAL_AMOUNT = 2;

    public IroncladSacredFlame() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        counter = USES;
    }

    @Override
    public void atBattleStart() {
        counter = USES;
        grayscale = false;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        flash();
        if (counter > 0 && card.type == AbstractCard.CardType.STATUS) {
            atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            atb(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL_AMOUNT));
            counter--;
            if (counter == 0) {
                grayscale = true;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + USES + DESCRIPTIONS[1] + HEAL_AMOUNT + DESCRIPTIONS[2];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new IroncladSacredFlame();
    }
}
