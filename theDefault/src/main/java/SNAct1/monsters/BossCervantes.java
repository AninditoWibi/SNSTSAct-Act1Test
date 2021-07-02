package SNAct1.monsters;

import SNAct1.SNAct1Mod;
import SNAct1.powers.DreadStormPower;
import SNAct1.powers.SoulChargePower;
import SNAct1.powers.WhiffPunisherPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;
import org.apache.logging.log4j.LogManager;

import static SNAct1.util.Wiz.atb;
import static SNAct1.util.Wiz.dmg;

public class BossCervantes extends AbstractSNActsMonster {
    public static final String ID = SNAct1Mod.makeID("BossCervantes");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final String IMG = "SNAct1Resources/images/monsters/boss/boss-cervantes-sprite.png";

    private static final byte MOVE_DC = 0;
    private static final byte MOVE_DC_B = 1;
    private static final byte MOVE_4_AB = 2;
    private static final byte MOVE_IGDR = 3;
    private static final byte MOVE_DS_B = 4;
    private static final byte MOVE_SOULCHARGE = 5;
    private static final byte MOVE_44_AG = 6;
    private static final byte MOVE_CRITICALEDGE = 7;

    private static final int MOVE_DC_BLOCKTHISTURN = 12;
    private static final int MOVE_DC_BLOCKTHISTURN_HIGHASC = 15;
    private static final int MOVE_DC_BLOCKNEXTTURN = 8;
    private static final int MOVE_DC_BLOCKNEXTTURN_HIGHASC = 10;
    public int dcBlockThisTurn;
    public int dcBlockNextTurn;

    private static final int MOVE_GDR_DAMAGEPERHIT = 7;
    private static final int MOVE_GDR_DAMAGEPERHIT_HIGHASC = 8;
    private static final int MOVE_GDR_HITS = 2;
    private static final int MOVE_GDR_HITS_HIGHASC = 2;
    public int gdrDamage;
    public int gdrHits;

    private static final int MOVE_GUN_4AB_DAMAGE = 14;
    private static final int MOVE_GUN_4AB_DAMAGE_HIGHASC = 16;
    public int gun4ABDamage;

    private static final int MOVE_IGDR_DAMAGEPERHIT = 10;
    private static final int MOVE_IGDR_DAMAGEPERHIT_HIGHASC = 11;
    private static final int MOVE_IGDR_HITS = 2;
    private static final int MOVE_IGDR_HITS_HIGHASC = 2;
    private static final int MOVE_IGDR_WEAK = 1;
    private static final int MOVE_IGDR_WEAK_HIGHASC = 2;
    public int igdrDamage;
    public int igdrHits;
    public int igdrWeakOnHit;

    private static final int MOVE_DSB_DAMAGE = 22;
    private static final int MOVE_DSB_DAMAGE_HIGHASC = 25;
    private static final int MOVE_DSB_FRAIL = 2;
    private static final int MOVE_DSB_FRAIL_HIGHASC = 2;
    public int dsBDamage;
    public int dsBFrailOnHit;

    private static final int MOVE_SC_STRENGTH = 1;
    private static final int MOVE_SC_STRENGTH_HIGHASC = 2;
    private static final int MOVE_SC_SOULCHARGEPOWER_BONUS = 50;
    private static final int MOVE_SC_SOULCHARGEPOWER_BONUS_HIGHASC = 75;
    private static final int MOVE_SC_SOULCHARGEPOWER_DURATION = 3;
    private static final int MOVE_SC_SOULCHARGEPOWER_DURATION_HIGHASC = 3;
    public int scStrength;
    public int scBonus;
    public int scDuration;

    private static final int MOVE_THROW_44AG_DAMAGEPERHIT = 4;
    private static final int MOVE_THROW_44AG_DAMAGEPERHIT_HIGHASC = 5;
    private static final int MOVE_THROW_44AG_HITS = 4;
    private static final int MOVE_THROW_44AG_HITS_HIGHASC = 4;
    private static final int MOVE_THROW_44AG_STRDEBUFF = -1;
    private static final int MOVE_THROW_44AG_STRDEBUFF_HIGHASC = -2;
    private static final int MOVE_THROW_44AG_DEXDEBUFF = -1;
    private static final int MOVE_THROW_44AG_DEXDEBUFF_HIGHASC = -2;
    public int throw44AGDamage;
    public int throw44AGHits;
    public int throw44AGStrDebuff;
    public int throw44AGDexDebuff;

    private static final int MOVE_CRITICALEDGE_DAMAGEPERHIT = 1;
    private static final int MOVE_CRITICALEDGE_DAMAGEPERHIT_HIGHASC = 1;
    private static final int MOVE_CRITICALEDGE_DRAWREDUCTION = 1;
    private static final int MOVE_CRITICALEDGE_DRAWREDUCTION_HIGHASC = 2;
    private static final int MOVE_CRITICALEDGE_HITS = 14;
    private static final int MOVE_CRITICALEDGE_HITS_HIGHASC = 14;
    public int criticalEdgeDamage;
    public int criticalEdgeDrawReduction;
    public int criticalEdgeHits;

    private static final int CERVANTES_HP = 240;
    private static final int CERVANTES_HP_HIGHASC = 256;
    private static final int WHIFFPUNISHER_DEFENSE = 30;
    private static final int WHIFFPUNISHER_DEFENSE_HIGHASC = 40;
    private static final int WHIFFPUNISHER_THRESHOLD = 4;
    private static final int WHIFFPUNISHER_THRESHOLD_HIGHASC = 3;
    public int whiffPunisherDefense;
    public int whiffPunisherThreshold;

    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SNAct1Mod.class.getName());
    private boolean firstMove = true;
    private boolean hasUsedSC = false;
    private boolean voicelinePlayed0 = false;
    private boolean voicelinePlayed1 = false;
    private boolean voicelinePlayed2 = false;
    private boolean voicelinePlayed3 = false;
    private boolean voicelinePlayed4 = false;
    private boolean voicelinePlayed5 = false;
    private boolean voicelinePlayed6 = false;
    private boolean voicelinePlayed7 = false;

    public BossCervantes() {
        this(0.0f, 0.0f);
    }

    public BossCervantes(final float x, final float y) {
        super(BossCervantes.NAME, ID, CERVANTES_HP, -5.0F, 0, 400.0f, 500.0f, IMG, x, y);
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
            this.gdrDamage = MOVE_GDR_DAMAGEPERHIT_HIGHASC;
            this.gun4ABDamage = MOVE_GUN_4AB_DAMAGE_HIGHASC;
            this.igdrDamage = MOVE_IGDR_DAMAGEPERHIT_HIGHASC;
            this.dsBDamage = MOVE_DSB_DAMAGE_HIGHASC;
            this.throw44AGDamage = MOVE_THROW_44AG_DAMAGEPERHIT_HIGHASC;
            this.criticalEdgeDamage = MOVE_CRITICALEDGE_DAMAGEPERHIT_HIGHASC;
            this.scBonus = MOVE_SC_SOULCHARGEPOWER_BONUS_HIGHASC;
            this.scDuration = MOVE_SC_SOULCHARGEPOWER_DURATION_HIGHASC;
        } else {
            this.gdrDamage = MOVE_GDR_DAMAGEPERHIT;
            this.gun4ABDamage = MOVE_GUN_4AB_DAMAGE;
            this.igdrDamage = MOVE_IGDR_DAMAGEPERHIT;
            this.dsBDamage = MOVE_DSB_DAMAGE;
            this.throw44AGDamage = MOVE_THROW_44AG_DAMAGEPERHIT;
            this.criticalEdgeDamage = MOVE_CRITICALEDGE_DAMAGEPERHIT;
            this.scBonus = MOVE_SC_SOULCHARGEPOWER_BONUS;
            this.scDuration = MOVE_SC_SOULCHARGEPOWER_DURATION;
        }

        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(CERVANTES_HP_HIGHASC);
            this.dcBlockThisTurn = MOVE_DC_BLOCKTHISTURN_HIGHASC;
            this.dcBlockNextTurn = MOVE_DC_BLOCKNEXTTURN_HIGHASC;
            this.whiffPunisherDefense = WHIFFPUNISHER_DEFENSE_HIGHASC;
            this.throw44AGStrDebuff = MOVE_THROW_44AG_STRDEBUFF_HIGHASC;
        } else {
            this.setHp(CERVANTES_HP);
            this.dcBlockThisTurn = MOVE_DC_BLOCKTHISTURN;
            this.dcBlockNextTurn = MOVE_DC_BLOCKNEXTTURN;
            this.whiffPunisherDefense = WHIFFPUNISHER_DEFENSE;
            this.throw44AGStrDebuff = MOVE_THROW_44AG_STRDEBUFF;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.gdrHits = MOVE_GDR_HITS_HIGHASC;
            this.igdrHits = MOVE_IGDR_HITS_HIGHASC;
            this.igdrWeakOnHit = MOVE_IGDR_WEAK_HIGHASC;
            this.dsBFrailOnHit = MOVE_DSB_FRAIL_HIGHASC;
            this.scStrength = MOVE_SC_STRENGTH_HIGHASC;
            this.throw44AGHits = MOVE_THROW_44AG_HITS_HIGHASC;
            this.throw44AGDexDebuff = MOVE_THROW_44AG_DEXDEBUFF_HIGHASC;
            this.criticalEdgeHits = MOVE_CRITICALEDGE_HITS_HIGHASC;
            this.criticalEdgeDrawReduction = MOVE_CRITICALEDGE_DRAWREDUCTION_HIGHASC;
            this.whiffPunisherThreshold = WHIFFPUNISHER_THRESHOLD_HIGHASC;
        } else {
            this.gdrHits = MOVE_GDR_HITS;
            this.igdrHits = MOVE_IGDR_HITS;
            this.igdrWeakOnHit = MOVE_IGDR_WEAK;
            this.dsBFrailOnHit = MOVE_DSB_FRAIL;
            this.scStrength = MOVE_SC_STRENGTH;
            this.throw44AGHits = MOVE_THROW_44AG_HITS;
            this.throw44AGDexDebuff = MOVE_THROW_44AG_DEXDEBUFF;
            this.criticalEdgeHits = MOVE_CRITICALEDGE_HITS;
            this.criticalEdgeDrawReduction = MOVE_CRITICALEDGE_DRAWREDUCTION;
            this.whiffPunisherThreshold = WHIFFPUNISHER_THRESHOLD;
        }

        addMove(MOVE_DC, Intent.DEFEND);
        addMove(MOVE_DC_B, Intent.ATTACK, this.gdrDamage, this.gdrHits, true);
        addMove(MOVE_4_AB, Intent.ATTACK, this.gun4ABDamage);
        addMove(MOVE_IGDR, Intent.ATTACK_DEBUFF, this.igdrDamage, this.igdrHits, true);
        addMove(MOVE_DS_B, Intent.ATTACK_DEBUFF, this.dsBDamage);
        addMove(MOVE_SOULCHARGE, Intent.BUFF);
        addMove(MOVE_44_AG, Intent.ATTACK_DEBUFF, this.throw44AGDamage, this.throw44AGHits, true);
        addMove(MOVE_CRITICALEDGE, Intent.ATTACK_DEBUFF, this.criticalEdgeDamage, this.criticalEdgeHits, true);
    }

    @Override
    public void usePreBattleAction() {
        // AbstractDungeon.getCurrRoom().playBgmInstantly("a1_boss_Cervantes");
        atb(new SFXAction("CervantesOpening"));
        atb(new TalkAction(this, DIALOG[0]));
        atb(new ApplyPowerAction(this, this, new DreadStormPower(this, this)));
        atb(new ApplyPowerAction(this, this, new WhiffPunisherPower(this, this, 1, whiffPunisherThreshold, whiffPunisherDefense)));
    }

    @Override
    public void takeTurn() {
        logger.info("current move is " + this.nextMove);
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
            case MOVE_DC: {
                if (!voicelinePlayed0) {
                    voicelinePlayed0 = true;
                    atb(new SFXAction("CervantesDC"));
                    atb(new TalkAction(this, DIALOG[1]));
                }
                atb(new GainBlockAction(this, this, dcBlockThisTurn));
                atb(new ApplyPowerAction(this, this, new NextTurnBlockPower(this, dcBlockNextTurn)));
                break;
            }
            case MOVE_DC_B: {
                if (!voicelinePlayed1) {
                    voicelinePlayed1 = true;
                    atb(new SFXAction("CervantesDCB"));
                    atb(new TalkAction(this, DIALOG[2]));
                }
                atb(new AnimateFastAttackAction(this));
                for (int i = 0; i < multiplier; i++) {
                    dmg(AbstractDungeon.player, di);
                }
                break;
            }
            case MOVE_4_AB: {
                if (!voicelinePlayed2) {
                    voicelinePlayed2 = true;
                    atb(new SFXAction("Cervantes4A+B"));
                    atb(new TalkAction(this, DIALOG[3]));
                }
                atb(new VFXAction(new RoomTintEffect(Color.RED, 0.5F)));
                dmg(AbstractDungeon.player, di);
                break;
            }
            case MOVE_IGDR: {
                if (!voicelinePlayed3) {
                    voicelinePlayed3 = true;
                    atb(new SFXAction("CervantesIGDR"));
                    atb(new TalkAction(this, DIALOG[4]));
                }
                atb(new AnimateFastAttackAction(this));
                for (int i = 0; i < multiplier; i++) {
                    dmg(AbstractDungeon.player, di);
                }
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, igdrWeakOnHit, true), igdrWeakOnHit));
                break;
            }
            case MOVE_DS_B: {
                if (!voicelinePlayed4) {
                    voicelinePlayed4 = true;
                    atb(new SFXAction("CervantesDSB"));
                    atb(new TalkAction(this, DIALOG[5]));
                }
                dmg(AbstractDungeon.player, di);
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, dsBFrailOnHit, true), dsBFrailOnHit));
                break;
            }
            case MOVE_SOULCHARGE: {
                if (!voicelinePlayed5) {
                    voicelinePlayed5 = true;
                    atb(new TalkAction(this, DIALOG[6]));

                }
                atb(new ApplyPowerAction(this, this, new StrengthPower(this, scStrength), scStrength));
                atb(new ApplyPowerAction(this, this, new SoulChargePower(this, scDuration, scBonus, true)));
                break;
            }
            case MOVE_44_AG: {
                if (!voicelinePlayed6) {
                    voicelinePlayed6 = true;
                    atb(new TalkAction(this, DIALOG[7]));
                }
                for (int i = 0; i < multiplier; i++) {
                    dmg(AbstractDungeon.player, di);
                }
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, throw44AGStrDebuff)));
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player, throw44AGDexDebuff)));
                break;
            }
            case MOVE_CRITICALEDGE: {
                if (!voicelinePlayed7) {
                    voicelinePlayed7 = true;
                    atb(new TalkAction(this, DIALOG[8]));
                }
                for (int i = 0; i < multiplier; i++) {
                    dmg(AbstractDungeon.player, di);
                }
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, criticalEdgeDrawReduction)));
                break;
            }
        }
        if (this.firstMove) {
            this.firstMove = false;
        }
        atb(new RollMoveAction(this));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        int soulChargeThreshold = ((int)(Math.ceil((this.maxHealth / 3f))));
        if (!this.isDying && this.currentHealth <= soulChargeThreshold) {
            logger.info("cerv at critical HP, about to use SC");
            if (!hasUsedSC) {
                atb(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.INTERRUPTED));
                setMoveShortcut(MOVE_SOULCHARGE, MOVES[5]);
                createIntent();
                hasUsedSC = true;
            }
        }
    }

    @Override
    protected void getMove(int i) {
        float boolMoveChance = 0.50f;
        float boolNonDCMoveChance = 0.75f;
        DreadStormPower dsp;
        /*
         * reminder:
         * dc = 0, dc b = 1, 4a+b = 2
         * igdr = 3 (whiff punisher), ds b = 4 (dread storm)
         * soulcharge = 5, sc 44a+g = 6, ce = 7
         * also applies to move array names
         * */
        if (this.hasPower(DreadStormPower.POWER_ID)) {
            dsp = (DreadStormPower) this.getPower(DreadStormPower.POWER_ID);
            logger.info("value of DSP dreadstorm trigger = " + dsp.dreadStormTriggered);
            if (dsp.dreadStormTriggered) {
                setMoveShortcut(MOVE_DS_B, MOVES[4]);
                return;
            }
        }
        if (firstMove) {
            setMoveShortcut(MOVE_DC, MOVES[0]);
        }
        if (this.lastMove(MOVE_SOULCHARGE) || this.hasPower(SoulChargePower.POWER_ID)) {
            logger.info("getmove - cerv in soulcharge mode");
            if (AbstractDungeon.aiRng.randomBoolean(boolMoveChance)) {
                setMoveShortcut(MOVE_CRITICALEDGE, MOVES[7]);
                return;
            } else {
                setMoveShortcut(MOVE_44_AG, MOVES[6]);
                return;
            }
        }
        if (!lastTwoMoves(MOVE_DC)) {
            if (AbstractDungeon.aiRng.randomBoolean(boolNonDCMoveChance)) {
                // roll to see if cerv uses DC B or 4A+B
                if (AbstractDungeon.aiRng.randomBoolean(boolMoveChance)) {
                    setMoveShortcut(MOVE_DC_B, MOVES[1]);
                } else {
                    setMoveShortcut(MOVE_4_AB, MOVES[2]);
                }
            } else {
                setMoveShortcut(MOVE_DC, MOVES[0]);
            }
        } else {
            if (AbstractDungeon.aiRng.randomBoolean(boolMoveChance)) {
                setMoveShortcut(MOVE_DC_B, MOVES[1]);
            } else {
                setMoveShortcut(MOVE_4_AB, MOVES[2]);
            }
        }

        // iGDR will never be called here because it triggers via power
        // SC also will never be called here because it triggers once via HP check
    }

    @Override
    public void die(boolean triggerRelics) {
        this.useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        atb(new SFXAction("CervantesDeath"));
        atb(new TalkAction(this, DIALOG[9]));
        // non cosmetic logic
        super.die(triggerRelics);
        this.onBossVictoryLogic();
    }
}
