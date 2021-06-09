package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.applyToTarget;

public class SomberPower extends TwoAmountPower {
    public static final String POWER_ID = SNAct1Mod.makeID(SomberPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int cardsThreshold;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("SomberPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("SomberPower32.png"));

    public SomberPower(AbstractCreature owner, int cardsThreshold, int vulnerableAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.cardsThreshold = cardsThreshold;
        this.amount = cardsThreshold;
        this.amount2 = vulnerableAmt;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + this.amount2 + DESCRIPTIONS[2];
        }
    }

    public void atStartOfTurn() {
        this.amount = cardsThreshold;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.amount--;
        if (this.amount == 0) {
            this.amount = cardsThreshold;
            // do apply power -> vuln to self here
            applyToTarget(owner, owner, new VulnerablePower(owner, amount2, true));
        }
        updateDescription();
    }
}


