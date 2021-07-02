package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.*;

public class HavocStruckStatusCardsPower extends AbstractPower{
    public static final String POWER_ID = SNAct1Mod.makeID(HavocStruckStatusCardsPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int dazed;
    private boolean addBurn;
    private int slimeAmt = 1;
    private int burnAmt = 1;
    private boolean justApplied = false;
    private boolean dazeShuffled = false;
    private boolean allEnergySpent = false;
    private boolean slimeShuffled = false; // just in case

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("HavocStruckPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("HavocStruckPower32.png"));

    public HavocStruckStatusCardsPower(AbstractCreature owner, int duration, int dazeToAdd, boolean alsoAddBurn, boolean isSourceMonster) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = duration;
        this.dazed = dazeToAdd;
        this.addBurn = alsoAddBurn;
        this.type = AbstractPower.PowerType.DEBUFF;
        if (AbstractDungeon.actionManager.turnHasEnded && isSourceMonster) { this.justApplied = true; }
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            if (addBurn) {
                this.description = DESCRIPTIONS[0] + this.dazed + DESCRIPTIONS[1] + slimeAmt + DESCRIPTIONS[3] + burnAmt + DESCRIPTIONS[4] + this.amount + DESCRIPTIONS[5];
            } else {
                this.description = DESCRIPTIONS[0] + this.dazed + DESCRIPTIONS[1] + slimeAmt + DESCRIPTIONS[3] + burnAmt + DESCRIPTIONS[4] + this.amount + DESCRIPTIONS[5];
            }
        }
        else {
            if (addBurn) {
                this.description = DESCRIPTIONS[0] + this.dazed + DESCRIPTIONS[1] + slimeAmt + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[6];
            } else {
                this.description = DESCRIPTIONS[0] + this.dazed + DESCRIPTIONS[1] + slimeAmt + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[6];
            }
        }
    }

    public void atStartOfTurn() {
        this.dazeShuffled = false;
        this.allEnergySpent = false;
        this.slimeShuffled = false;
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        allEnergySpent = ((card.costForTurn == EnergyPanel.totalCount || card.cost == -1) && EnergyPanel.totalCount != 0);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (!dazeShuffled) {
            // wiz intodiscardmo is only used if we want more than 5 cards to be shuffled into discard
            intoDiscard(new Dazed(), dazed);
            dazeShuffled = true;
        }
        if (allEnergySpent && !slimeShuffled) {
            if (addBurn) {
                intoDrawMo(new Slimed(), slimeAmt);
                intoDrawMo(new Burn(), burnAmt);
            } else {
                intoDrawMo(new Slimed(), slimeAmt);
            }
            slimeShuffled = true;
        }
    }
}
