package SNAct1.monsters;

import SNAct1.SNAct1Mod;
import SNAct1.powers.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.apache.logging.log4j.LogManager;

import static SNAct1.util.Wiz.atb;

public class BossKefka extends AbstractSNActsMonster {
    public static final String ID = SNAct1Mod.makeID("BossKefka");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final String IMG = "SNAct1Resources/images/monsters/boss/boss-kefka-sprite.png";

    private static final byte MOVE_FRAGFIRE = 0;
    private static final byte MOVE_ZAPTRAP = 1;
    private static final byte MOVE_LAUGH = 2;
    private static final byte MOVE_HYPERDRIVE = 3;
    private static final byte MOVE_HAVOCWING = 4;

    private static final int MOVE_FRAGFIRE_THRESHOLD = 4;
    private static final int MOVE_FRAGFIRE_THRESHOLD_HIGHASC = 3;
    private static final int MOVE_FRAGFIRE_DURATION = 1;
    private static final int MOVE_FRAGFIRE_DURATION_HIGHASC = 2;
    public int fragFireThreshold;
    public int fragFireDuration;

    private static final int MOVE_ZAPTRAP_THRESHOLD = 4;
    private static final int MOVE_ZAPTRAP_THRESHOLD_HIGHASC = 3;
    private static final int MOVE_ZAPTRAP_DMG = 5;
    private static final int MOVE_ZAPTRAP_DMG_HIGHASC = 7;
    private static final int MOVE_ZAPTRAP_DURATION = 1;
    private static final int MOVE_ZAPTRAP_DURATION_HIGHASC = 2;
    public int zapTrapThreshold;
    public int zapTrapDamage;
    public int zapTrapDuration;

    private static final int MOVE_LAUGH_BLOCK = 18;
    private static final int MOVE_LAUGH_BLOCK_HIGHASC = 23;
    private static final int MOVE_LAUGH_WEAK = 1;
    private static final int MOVE_LAUGH_WEAK_HIGHASC = 2;
    private static final int MOVE_LAUGH_WEAK_TERRA = 2;
    private static final int MOVE_LAUGH_WEAK_TERRA_HIGHASC = 1;
    public int laughBlock;
    public int laughWeakToPlayer;
    public int laughWeakToTerra;

    private static final int MOVE_HYPERDRIVE_DAMAGE = 2;
    private static final int MOVE_HYPERDRIVE_DAMAGE_HIGHASC = 3;
    private static final int MOVE_HYPERDRIVE_HITS = 5;
    private static final int MOVE_HYPERDRIVE_HITS_HIGHASC = 6;
    public int hyperdriveDamage;
    public int hyperdriveHits;

    // confusion + pseudo beat of death
    private static final int MOVE_HAVOC_OLD_AMOUNT = 3;
    private static final int MOVE_HAVOC_OLD_AMOUNT_HIGHASC = 4;
    public int havocWingOldAmount;

    // shuffle daze after playing your first card, shuffle slime (and burn) after spending all E
    private static final int MOVE_HAVOC_NEW_AMOUNT = 2;
    private static final int MOVE_HAVOC_NEW_AMOUNT_HIGHASC = 3;
    private static final int MOVE_HAVOC_NEW_DAZEAMT = 2;
    private static final int MOVE_HAVOC_NEW_DAZEAMT_HIGHASC = 3;
    private static final boolean MOVE_HAVOC_NEW_ALSOADDBURN = false;
    private static final boolean MOVE_HAVOC_NEW_ALSOADDBURN_HIGHASC = true;
    public int havocWingNewDuration;
    public int havocWingNewDaze;
    public boolean havocWingNewBurn;

    private static final int KEFKA_HP = 184;
    private static final int KEFKA_HP_HIGHASC = 199;

    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SNAct1Mod.class.getName());
    private boolean firstMove = true;
    private int moveCounter = 1;
    private boolean voicelinePlayedFire = false;
    private boolean voicelinePlayedZap = false;
    private boolean voicelinePlayedLaugh = false;
    private boolean voicelinePlayedHyperdrive = false;
    private boolean voicelinePlayedHavocWing = false;

    BossTerra terra;

    public BossKefka() {
        this(0.0f, 0.0f);
    }

    public BossKefka(final float x, final float y) {
        super(BossKefka.NAME, ID, KEFKA_HP, -5.0F, 0, 380.0f, 550.0f, IMG, x, y);
        this.type = EnemyType.BOSS;
        this.dialogX = (this.hb_x - 70.0F) * Settings.scale;
        this.dialogY -= (this.hb_y - 55.0F) * Settings.scale;

        /*
        reminder:
        asc 4 = more boss damage
        asc 9 = more boss HP/defense
        asc 19 = general boss improvement
         */

        if (AbstractDungeon.ascensionLevel >= 4) {
            this.fragFireThreshold = MOVE_FRAGFIRE_THRESHOLD_HIGHASC;
            this.zapTrapDamage = MOVE_ZAPTRAP_DMG_HIGHASC;
            this.havocWingOldAmount = MOVE_HAVOC_OLD_AMOUNT_HIGHASC;
            this.hyperdriveDamage = MOVE_HYPERDRIVE_DAMAGE_HIGHASC;
        } else {
            this.fragFireThreshold = MOVE_FRAGFIRE_THRESHOLD;
            this.zapTrapDamage = MOVE_ZAPTRAP_DMG;
            this.havocWingOldAmount = MOVE_HAVOC_OLD_AMOUNT;
            this.hyperdriveDamage = MOVE_HYPERDRIVE_DAMAGE;
        }

        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(KEFKA_HP_HIGHASC);
            this.laughBlock = MOVE_LAUGH_BLOCK_HIGHASC;
            this.laughWeakToPlayer = MOVE_LAUGH_WEAK_HIGHASC;
            this.havocWingNewDaze = MOVE_HAVOC_NEW_DAZEAMT_HIGHASC;
        } else {
            setHp(KEFKA_HP);
            this.laughBlock = MOVE_LAUGH_BLOCK;
            this.laughWeakToPlayer = MOVE_LAUGH_WEAK;
            this.havocWingNewDaze = MOVE_HAVOC_NEW_DAZEAMT;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.fragFireDuration = MOVE_FRAGFIRE_DURATION_HIGHASC;
            this.zapTrapThreshold = MOVE_ZAPTRAP_THRESHOLD_HIGHASC;
            this.zapTrapDuration = MOVE_ZAPTRAP_DURATION_HIGHASC;
            this.laughWeakToTerra = MOVE_LAUGH_WEAK_TERRA_HIGHASC;
            this.hyperdriveHits = MOVE_HYPERDRIVE_HITS_HIGHASC;
            this.havocWingNewDuration = MOVE_HAVOC_NEW_AMOUNT_HIGHASC;
            this.havocWingNewBurn = MOVE_HAVOC_NEW_ALSOADDBURN_HIGHASC;
        } else {
            this.fragFireDuration = MOVE_FRAGFIRE_DURATION;
            this.zapTrapThreshold = MOVE_ZAPTRAP_THRESHOLD;
            this.zapTrapDuration = MOVE_ZAPTRAP_DURATION;
            this.laughWeakToTerra = MOVE_LAUGH_WEAK_TERRA;
            this.hyperdriveHits = MOVE_HYPERDRIVE_HITS;
            this.havocWingNewDuration = MOVE_HAVOC_NEW_AMOUNT;
            this.havocWingNewBurn = MOVE_HAVOC_NEW_ALSOADDBURN;
        }

        addMove(MOVE_FRAGFIRE, Intent.DEBUFF);
        addMove(MOVE_ZAPTRAP, Intent.DEBUFF);
        addMove(MOVE_LAUGH, Intent.DEFEND_DEBUFF);
        addMove(MOVE_HYPERDRIVE, Intent.STRONG_DEBUFF);
        addMove(MOVE_HAVOCWING, Intent.STRONG_DEBUFF);
    }

    @Override
    public void usePreBattleAction() {
        // AbstractDungeon.getCurrRoom().playBgmInstantly("a1_boss_TerraKefka");
        atb(new SFXAction("KefkaOpening"));
        atb(new TalkAction(this, DIALOG[0]));
        atb(new ApplyPowerAction(this, this, new CowardlyGeneralPower(this)));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo instanceof BossTerra) {
                terra = (BossTerra)mo;
            }
        }
    }

    @Override
    public void takeTurn() {
        logger.info("current Kefka move is " + this.nextMove);
        DamageInfo di;
        int multiplier = 0;
        if (moves.containsKey(this.nextMove)) { // if the byte value for the next intent is valid when this creature takes its turn..
            EnemyMoveInfo emi = moves.get(this.nextMove); // fetch information added above in addmove
            di = new DamageInfo(this, emi.baseDamage, DamageInfo.DamageType.NORMAL);
            multiplier = emi.multiplier;
        } else { // for if the byte value is invalid, we assume this monster is stunned
            di = new DamageInfo(this, 0, DamageInfo.DamageType.NORMAL);
        }
        if (di.base > -1) { // apply game power calculations
            di.applyPowers(this, AbstractDungeon.player);
        }
        switch (nextMove) {
            case MOVE_FRAGFIRE: {
                if (!voicelinePlayedFire) {
                    voicelinePlayedFire = true;
                    atb(new TalkAction(this, DIALOG[1]));
                }
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new FragrantFirePower(AbstractDungeon.player, fragFireThreshold, fragFireDuration, true)));
                break;
            }
            case MOVE_ZAPTRAP: {
                if (!voicelinePlayedZap) {
                    voicelinePlayedZap = true;
                    atb(new TalkAction(this, DIALOG[2]));
                }
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new ZapTrapPower(AbstractDungeon.player, zapTrapThreshold, zapTrapDuration, zapTrapDamage, true)));
                break;
            }
            case MOVE_LAUGH: {
                if (!voicelinePlayedLaugh) {
                    voicelinePlayedLaugh = true;
                    atb(new TalkAction(this, DIALOG[3]));
                }
                atb(new GainBlockAction(this, this, laughBlock));
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, laughWeakToPlayer, true), laughWeakToPlayer));
                atb(new ApplyPowerAction(terra, this, new WeakPower(terra, laughWeakToTerra, true), laughWeakToTerra));
                break;
            }
            case MOVE_HYPERDRIVE: {
                if (!voicelinePlayedHyperdrive) {
                    voicelinePlayedHyperdrive = true;
                    atb(new TalkAction(this, DIALOG[4]));
                }
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new SurroundingHyperdrivePower(AbstractDungeon.player, hyperdriveDamage, hyperdriveHits)));
                break;
            }
            case MOVE_HAVOCWING: {
                if (!voicelinePlayedHavocWing) {
                    voicelinePlayedHavocWing = true;
                    atb(new TalkAction(this, DIALOG[5]));
                }
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new HavocStruckStatusCardsPower(AbstractDungeon.player, havocWingNewDuration, havocWingNewDaze, havocWingNewBurn, true)));
                // old version, confusion + beat of death
                //atb(new ApplyPowerAction(AbstractDungeon.player, this, new HavocStruckConfusionDamagePower(AbstractDungeon.player, havocWingOldAmount)));
                break;
            }
        }
        if (this.firstMove) {
            this.firstMove = false;
        }
        if (moveCounter == 6) {
            moveCounter = 1;
        } else {
            moveCounter++;
        }
        atb(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        float boolMoveChance = 0.50f;
        /*
         * reminder:
         * fragrant fire = 0, zap trap = 1, laugh = 2
         * hyperdrive = 3, havoc wing = 4
         * also applies to move array names
         * */
        if (moveCounter == 1) {
            if (AbstractDungeon.aiRng.randomBoolean(boolMoveChance)) {
                setMoveShortcut(MOVE_FRAGFIRE, MOVES[0]);
            } else {
                setMoveShortcut(MOVE_ZAPTRAP, MOVES[1]);
            }
        }
        if (moveCounter == 2) {
            if (lastMove(MOVE_FRAGFIRE)) {
                setMoveShortcut(MOVE_ZAPTRAP, MOVES[1]);
            } else {
                setMoveShortcut(MOVE_FRAGFIRE, MOVES[0]);
            }
        }
        if (moveCounter == 3 || moveCounter == 6) {
            setMoveShortcut(MOVE_LAUGH, MOVES[2]);
        }
        if (moveCounter == 4) {
            if (AbstractDungeon.aiRng.randomBoolean(boolMoveChance)) {
                setMoveShortcut(MOVE_HYPERDRIVE, MOVES[3]);
            } else {
                setMoveShortcut(MOVE_HAVOCWING, MOVES[4]);
            }
        }
        if (moveCounter == 5) {
            if (lastMove(MOVE_HYPERDRIVE)) {
                setMoveShortcut(MOVE_HAVOCWING, MOVES[4]);
            } else {
                setMoveShortcut(MOVE_HYPERDRIVE, MOVES[3]);
            }
        }
    }

    public void onTerraDeath() {
        if (!isDead && !isDying) {
            atb(new SFXAction("KefkaFlee"));
            atb(new TalkAction(this, DIALOG[7]));
            atb(new EscapeAction(this));
        }
    }

    @Override
    public void die(boolean triggerRelics) {
        this.useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        atb(new SFXAction("KefkaDeath"));
        atb(new TalkAction(this, DIALOG[6]));
        // non cosmetic logic
        terra.onKefkaDeath();
        super.die(triggerRelics);
        this.onBossVictoryLogic();
    }
}
