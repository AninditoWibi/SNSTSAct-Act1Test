package SNAct1.powers;

import SNAct1.SNAct1Mod;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static SNAct1.util.Wiz.applyToTarget;

public class ArdentAspectPower extends TwoAmountPower {
    public static final String POWER_ID = SNAct1Mod.makeID("ArdentAspectPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int playerStrengthGain;
    private int creatureStrengthGain;

    public ArdentAspectPower(AbstractCreature owner, int playerStrengthGain, int creatureStrengthGain) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = playerStrengthGain;
        this.amount2 = creatureStrengthGain;
        updateDescription();
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }

    public void atTurnStart() {
        applyToTarget(AbstractDungeon.player, owner, new StrengthPower(AbstractDungeon.player, amount));
        applyToTarget(owner, owner, new StrengthPower(owner, amount2));
    }

}
