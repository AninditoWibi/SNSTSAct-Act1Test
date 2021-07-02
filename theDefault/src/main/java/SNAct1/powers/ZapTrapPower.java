package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.atb;

public class ZapTrapPower extends TwoAmountPower {
    public static final String POWER_ID = SNAct1Mod.makeID(ZapTrapPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied = false;
    private int cardsThreshold;
    private int damage;
    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("ZapTrapPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("ZapTrapPower32.png"));

    public ZapTrapPower(AbstractCreature owner, int skillsToTrigger, int duration, int damageOnTrigger, boolean isSourceMonster) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = duration;
        this.amount2 = skillsToTrigger;
        this.damage = damageOnTrigger;
        this.cardsThreshold = skillsToTrigger;
        this.type = PowerType.DEBUFF;
        if (AbstractDungeon.actionManager.turnHasEnded && isSourceMonster) { this.justApplied = true; }
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.amount == 1 && amount2 == 1) {
            this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + damage + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
        }
        if (this.amount > 1 && amount2 == 1) {
            this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + damage + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[5];
        }
        if (this.amount == 1 && amount2 > 1) {
            this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[2] + damage + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
        }
        if (this.amount > 1 && amount2 > 1) {
            this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[2] + damage + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[5];
        }
    }

    public void atStartOfTurn() {
        this.amount2 = cardsThreshold;
        updateDescription();
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            atb(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            atb(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            this.amount2--;
            if (this.amount2 == 0) {
                // do deal damage, see beat of death
                flash();
                atb(new DamageAction(AbstractDungeon.player, new DamageInfo(this.owner, this.damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING));
                this.amount2 = cardsThreshold;
            }
        }
    }
}
