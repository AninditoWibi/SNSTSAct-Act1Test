package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.adp;
import static SNAct1.util.Wiz.atb;

public class SoulEdgesCallPower extends TwoAmountPower {
    public static final String POWER_ID = SNAct1Mod.makeID(SoulEdgesCallPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int hpLoss;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("SoulEdgesCallPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("SoulEdgesCallPower32.png"));

    public SoulEdgesCallPower(AbstractCreature owner, int scDuration, int scBonus, int hpLoss) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = scDuration;
        this.amount2 = scBonus;
        this.hpLoss = hpLoss;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.hpLoss + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
    }

    public void atStartOfTurnPostDraw() {
        boolean ownerHasSoulCharge = this.owner.hasPower(SoulChargePower.POWER_ID);
        if (!ownerHasSoulCharge) {
            flash();
            atb(new VFXAction(adp(), new VerticalAuraEffect(Color.BLACK, adp().hb.cX, adp().hb.cY), 0.33F));
            atb(new SFXAction("ATTACK_FIRE"));
            atb(new VFXAction(adp(), new VerticalAuraEffect(Color.PURPLE, adp().hb.cX, adp().hb.cY), 0.33F));
            atb(new VFXAction(adp(), new VerticalAuraEffect(Color.CYAN, adp().hb.cX, adp().hb.cY), 0.0F));
            atb(new VFXAction(adp(), new BorderLongFlashEffect(Color.MAGENTA), 0.0F, true));
            atb(new LoseHPAction(this.owner, this.owner, this.hpLoss));
            atb(new ApplyPowerAction(this.owner, this.owner, new SoulChargePower(this.owner, this.amount, this.amount2, false)));
        }
    }
}
