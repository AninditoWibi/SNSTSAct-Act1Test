package SNAct1.powers;

import SNAct1.SNAct1Mod;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static SNAct1.util.Wiz.applyToTarget;

public class SomberPower extends TwoAmountPower {
    public static final String POWER_ID = SNAct1Mod.makeID("SomberPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int cardsThreshold;
    private int vulnerableAmt;

    public SomberPower(AbstractCreature owner, int cardsThreshold, int vulnerableAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.cardsThreshold = cardsThreshold;
        this.amount = cardsThreshold;
        this.amount2 = vulnerableAmt;
        updateDescription();
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


