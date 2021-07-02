package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.atb;

public class SurroundingHyperdrivePower extends TwoAmountPower {
    public static final String POWER_ID = SNAct1Mod.makeID(SurroundingHyperdrivePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("SurroundingHyperdrivePower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("SurroundingHyperdrivePower32.png"));

    public SurroundingHyperdrivePower(AbstractCreature owner, int damage, int multiplier) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = damage;
        this.amount2 = multiplier;
        this.type = PowerType.DEBUFF;
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
    }

    public void atEndOfTurn(boolean isPlayer) {
        flash();
        atb(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        for (int i = 0; i < this.amount2; i++) {
            atb(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }
}
