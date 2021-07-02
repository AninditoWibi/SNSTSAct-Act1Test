package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import basemod.interfaces.OnPowersModifiedSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.*;

// similar implementation to downfall hexaburn
public class LightOfJudgementCardPower extends TwoAmountPower implements NonStackablePower, OnPowersModifiedSubscriber {
    public static final String POWER_ID = SNAct1Mod.makeID(LightOfJudgementCardPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private final int BASE_DAMAGE = 4;
    private final int HITS = 13;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("LightOfJudgementPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("LightOfJudgementPower32.png"));

    public LightOfJudgementCardPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        if (owner.hasPower(StrengthPower.POWER_ID)) {
            this.amount2 = BASE_DAMAGE + (owner.getPower(StrengthPower.POWER_ID)).amount;
        } else {
            this.amount2 = BASE_DAMAGE;
        }
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        atb(new ReducePowerAction(this.owner, this.owner, this, 1));
        if (this.amount == 1) {
            atb(new VFXAction(AbstractDungeon.player, new ScreenOnFireEffect(), 2.0F));
            for (int i = 0; i < HITS; i++) {
                att(new DamageAllEnemiesAction(null,  DamageInfo.createDamageMatrix(amount2, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.LIGHTNING));
            }
        }
    }

    @Override
    public void receivePowersModified() {
        if (owner.hasPower(StrengthPower.POWER_ID)) {
            this.amount2 = BASE_DAMAGE + (owner.getPower(StrengthPower.POWER_ID)).amount;
        } else {
            this.amount2 = BASE_DAMAGE;
        }
        updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[3] + HITS + DESCRIPTIONS[4];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3] + HITS + DESCRIPTIONS[4];
        }
    }
}
