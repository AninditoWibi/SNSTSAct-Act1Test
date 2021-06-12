package SNAct1.monsters;

import SNAct1.CustomIntent.IntentEnums;
import SNAct1.SNAct1Mod;
import SNAct1.actions.DamageAllOtherCharactersAction;
import SNAct1.cards.enemyBossCards.*;
import SNAct1.powers.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;
import org.apache.logging.log4j.LogManager;

import static SNAct1.util.Wiz.*;
import static SNAct1.util.Wiz.atb;

public class BossNinianSimple extends AbstractSNActsMonster {
    public static final String ID = SNAct1Mod.makeID("BossNinianSimple");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final String IMG = "SNAct1Resources/images/monsters/boss/boss-ninian-sprite.png";

    private static final byte MOVE_LIGHTBREATH = 0;
    private static final byte MOVE_ICEBREATH = 1;
    private static final byte MOVE_ESCAPEROUTE = 2;
    private static final byte MOVE_BLOCKBUFF = 3;
    private static final byte MOVE_ULTIMATE = 4;

    private static final int LIGHTBREATH_DMG = 18;
    private static final int LIGHTBREATH_DMG_HIGHASC = 22;
    private static final int LIGHTBREATH_BLOCK = 16;
    private static final int LIGHTBREATH_BLOCK_HIGHASC = 20;
    private static final int LIGHTBREATH_STRENGTHBUFF = 6;
    public int lightBreathDmg;
    public int lightBreathBlock;
    public int lightBreathStr;

    private static final int ICEBREATH_DMG = 21;
    private static final int ICEBREATH_DMG_HIGHASC = 25;
    private static final int ICEBREATH_DEBUFF = -2;
    private static final int ICEBREATH_DEBUFF_HIGHASC = -3;
    private static final int ICEBREATH_DEBUFF_DURATION = 1;
    private static final int ICEBREATH_DEBUFF_DURATION_HIGHASC = 1;
    public int iceBreathDmg;
    public int iceBreathDebuff;
    public int iceBreathDebuffDuration;

    private static final int ESCAPEROUTE_BLOCK = 36;
    private static final int ESCAPEROUTE_BLOCK_HIGHASC = 46;
    public int escapeRouteBlock;

    private static final int BLOCKBUFF_DURATION = 1;
    private static final int BLOCKBUFF_DURATION_HIGHASC = 1;
    private static final int BLOCKBUFF_VALUE = 50;
    private static final int BLOCKBUFF_VALUE_HIGHASC = 50;
    public int blockBuffDuration;
    public int blockBuffValue;

    private static final int ULTIMATE_BASEDMG = 34;
    private static final int ULTIMATE_BASEDMG_HIGHASC = 39;
    public int ultimateBaseDmg;

    private static final int DESPAIR_BASESTRPENALTY = -7;
    private static final int DESPAIR_BASESTRPENALTY_HIGHASC = -5;
    private static final int DESPAIR_VULNERABLE_CARDS = 3;
    private static final int DESPAIR_VULNERABLE_CARDS_HIGHASC = 4;
    private static final int DESPAIR_VULNERABLE_VALUE = 1;
    private static final int DESPAIR_VULNERABLE_VALUE_HIGHASC = 1;
    private static final int DESPAIR_WEAKONHIT = 2;
    private static final int DESPAIR_WEAKONHIT_HIGHASC = 1;
    public int despairBasestrpenalty;
    public int despairVulnerableCards;
    public int despairVulnerableValue;
    public int despairWeakOnBlockBreak;

    private static final int NINIAN_HP = 800;
    private static final int NINIAN_HP_HIGHASC = 880;
    private static final int NINIAN_WEAKNESS_BONUS = 100;

    private int moveCounter = 1;
    private boolean voicelinePlayedAttacking;
    private boolean voicelinePlayedDefending;
    private boolean voicelinePlayedUltimate;
    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SNAct1Mod.class.getName());

    public BossNinianSimple() {
        this(0.0f, 0.0f);
    }

    public BossNinianSimple(final float x, final float y) {
        super(BossNinianSimple.NAME, ID, NINIAN_HP, -5.0F, 0, 506.0f, 468.0f, IMG, x, y);
        this.type = EnemyType.BOSS;
        this.dialogX = (this.hb_x - 70.0F) * Settings.scale;
        this.dialogY -= (this.hb_y - 55.0F) * Settings.scale;

        /*
        reminder:
        asc 4 = more boss damage
        asc 9 = more boss HP/defense
        asc 19 = general boss improvement
         */

        this.lightBreathStr = LIGHTBREATH_STRENGTHBUFF;

        if (AbstractDungeon.ascensionLevel >= 4) {
            this.lightBreathDmg = LIGHTBREATH_DMG_HIGHASC;
            this.iceBreathDmg = ICEBREATH_DMG_HIGHASC;
            this.ultimateBaseDmg = ULTIMATE_BASEDMG_HIGHASC;
            this.despairBasestrpenalty = DESPAIR_BASESTRPENALTY_HIGHASC;
        } else {
            this.lightBreathDmg = LIGHTBREATH_DMG;
            this.iceBreathDmg = ICEBREATH_DMG;
            this.ultimateBaseDmg = ULTIMATE_BASEDMG;
            this.despairBasestrpenalty = DESPAIR_BASESTRPENALTY;
        }

        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(NINIAN_HP_HIGHASC);
            this.blockBuffDuration = BLOCKBUFF_DURATION_HIGHASC;
            this.blockBuffValue = BLOCKBUFF_VALUE_HIGHASC;
            this.despairVulnerableValue = DESPAIR_VULNERABLE_VALUE_HIGHASC;
        } else {
            this.setHp(NINIAN_HP);
            this.blockBuffDuration = BLOCKBUFF_DURATION;
            this.blockBuffValue = BLOCKBUFF_VALUE;
            this.despairVulnerableValue = DESPAIR_VULNERABLE_VALUE;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.lightBreathBlock = LIGHTBREATH_BLOCK_HIGHASC;
            this.iceBreathDebuff = ICEBREATH_DEBUFF_HIGHASC;
            this.iceBreathDebuffDuration = ICEBREATH_DEBUFF_DURATION_HIGHASC;
            this.escapeRouteBlock = ESCAPEROUTE_BLOCK_HIGHASC;
            this.despairVulnerableCards = DESPAIR_VULNERABLE_CARDS_HIGHASC;
            this.despairWeakOnBlockBreak = DESPAIR_WEAKONHIT_HIGHASC;
        } else {
            this.lightBreathBlock = LIGHTBREATH_BLOCK;
            this.iceBreathDebuff = ICEBREATH_DEBUFF;
            this.iceBreathDebuffDuration = ICEBREATH_DEBUFF_DURATION;
            this.escapeRouteBlock = ESCAPEROUTE_BLOCK;
            this.despairVulnerableCards = DESPAIR_VULNERABLE_CARDS;
            this.despairWeakOnBlockBreak = DESPAIR_WEAKONHIT;
        }

        addMove(MOVE_LIGHTBREATH, Intent.ATTACK_BUFF, this.lightBreathDmg);
        addMove(MOVE_ICEBREATH, Intent.ATTACK_DEBUFF, this.iceBreathDmg);
        addMove(MOVE_ESCAPEROUTE, Intent.DEFEND);
        addMove(MOVE_BLOCKBUFF, Intent.BUFF);
        addMove(MOVE_ULTIMATE, Intent.ATTACK, this.ultimateBaseDmg);


    }

    @Override
    public void usePreBattleAction() {
        // AbstractDungeon.getCurrRoom().playBgmInstantly("a1_boss_NinianEliwood");
        atb(new SFXAction("NinianOpening"));
        atb(new TalkAction(this, DIALOG[0]));

        atb(new ApplyPowerAction(this, this, new StrengthPower(this, this.despairBasestrpenalty)));
        atb(new ApplyPowerAction(this, this, new SomberPower(this, despairVulnerableCards, despairVulnerableValue)));
        atb(new ApplyPowerAction(this, this, new EnfeebledBodyPower(this, this.despairWeakOnBlockBreak)));
        atb(new ApplyPowerAction(this, this, new BarricadePower(this)));

    }

    @Override
    public void takeTurn() {

        logger.info("current move counter = " + moveCounter + " and current move is " + this.nextMove);

        DamageInfo di;
        if (moves.containsKey(this.nextMove)) { // if the byte value for the next intent is valid when this creature takes its turn..
            EnemyMoveInfo emi = moves.get(this.nextMove); // fetch information added above in addmove
            di = new DamageInfo(this, emi.baseDamage, DamageInfo.DamageType.NORMAL);
        } else { // for if the byte value is invalid, we assume this monster is stunned
            di = new DamageInfo(this, 0, DamageInfo.DamageType.NORMAL);
        }
        if (di.base > -1) { // apply game power calculations
            di.applyPowers(this, AbstractDungeon.player);
        }

        switch (this.nextMove) {
            case MOVE_LIGHTBREATH:
                logger.info("bossNinian about to execute LightBreath");
                if (!voicelinePlayedAttacking) {
                    voicelinePlayedAttacking = true;
                    atb(new SFXAction("NinianAttacking"));
                    atb(new TalkAction(this, DIALOG[1]));
                }
                atb(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.75F));
                atb(new VFXAction(new BorderFlashEffect(Color.WHITE)));
                atb(new VFXAction(new MindblastEffect(this.dialogX, this.dialogY, this.flipHorizontal)));
                dmg(adp(), di);
                break;
            case MOVE_ICEBREATH:
                logger.info("bossNinian about to execute IceBreatj");
                if (!voicelinePlayedAttacking) {
                    voicelinePlayedAttacking = true;
                    atb(new SFXAction("NinianAttacking"));
                    atb(new TalkAction(this, DIALOG[1]));
                }
                atb(new VFXAction(new RoomTintEffect(Color.BLUE, 0.5F)));
                atb(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.34F));
                CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1F);
                atb(new VFXAction(new BorderFlashEffect(Color.BLUE)));
                dmg(adp(), di);
                applyToTarget(AbstractDungeon.player, this, new ChillDefensePower(AbstractDungeon.player, this.iceBreathDebuff, this.iceBreathDebuffDuration, true));
                break;
            case MOVE_ESCAPEROUTE:
                logger.info("bossNinian about to execute EscRoute");
                if (!voicelinePlayedDefending) {
                    voicelinePlayedDefending = true;
                    atb(new SFXAction("NinianDefending"));
                    atb(new TalkAction(this, DIALOG[2]));
                }
                atb(new GainBlockAction(this, this.escapeRouteBlock));
                break;
            case MOVE_BLOCKBUFF:
                logger.info("bossNinian about to execute BlockBuff");
                if (!voicelinePlayedDefending) {
                    voicelinePlayedDefending = true;
                    atb(new SFXAction("NinianDefending"));
                    atb(new TalkAction(this, DIALOG[2]));
                }
                CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
                applyToTarget(this, this, new NinisGracePower(this, this.blockBuffDuration, this.blockBuffValue));
                break;
            case MOVE_ULTIMATE: // i.e. glacies. use defect's blizzard effect
                logger.info("bossNinian about to execute Ultimate");
                if (!voicelinePlayedUltimate) {
                    voicelinePlayedUltimate = true;
                    atb(new SFXAction("NinianUltimate"));
                    atb(new TalkAction(this, DIALOG[3]));
                }
                if (Settings.FAST_MODE) {
                    atb(new VFXAction(new BlizzardEffect(4, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.275F));
                } else {
                    atb(new VFXAction(new BlizzardEffect(4, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.10F));
                }
                dmg(adp(), di);
                break;
        }
        if (moveCounter == 6) {
            moveCounter = 1;
        } else {
            moveCounter++;
        }
        logger.info("current move counter after BN took her turn is = " + moveCounter);
        atb(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        boolean highAsc = AbstractDungeon.ascensionLevel >= 19;
        logger.info("are we fighting BN on high asc? the answer is " + highAsc + " and we're on asc level " + AbstractDungeon.ascensionLevel );
        /*
         * reminder:
         * light breath = 0, ice breath = 1
         * escape route = 2, ninis grace = 3
         * glacies (ultimate) = 4
         * also applies to move array names
         * */
        if (highAsc) {
            if (moveCounter == 1) {
                setMoveShortcut(MOVE_ESCAPEROUTE, MOVES[2]);
            }
            if (moveCounter == 2) {
                setMoveShortcut(MOVE_ULTIMATE, MOVES[4]);
            }
            if (moveCounter == 3) {
                setMoveShortcut(MOVE_BLOCKBUFF, MOVES[3]);
            }
            if (moveCounter == 4) {
                setMoveShortcut(MOVE_ICEBREATH, MOVES[1]);
            }
            if (moveCounter == 5) {
                setMoveShortcut(MOVE_LIGHTBREATH, MOVES[0]);
            }
            if (moveCounter == 6) {
                setMoveShortcut(MOVE_ULTIMATE, MOVES[4]);
            }
        } else {
            if (moveCounter == 1) {
                setMoveShortcut(MOVE_LIGHTBREATH, MOVES[0]);
            }
            if (moveCounter == 2) {
                setMoveShortcut(MOVE_ICEBREATH, MOVES[1]);
            }
            if (moveCounter == 3) {
                setMoveShortcut(MOVE_BLOCKBUFF, MOVES[3]);
            }
            if (moveCounter == 4) {
                setMoveShortcut(MOVE_LIGHTBREATH, MOVES[0]);
            }
            if (moveCounter == 5) {
                setMoveShortcut(MOVE_ESCAPEROUTE, MOVES[2]);
            }
            if (moveCounter == 6) {
                setMoveShortcut(MOVE_ULTIMATE, MOVES[4]);
            }
        }
    }

    @Override
    public void createIntent() {
        super.createIntent();
        applyPowers();
    }

    @Override
    public void applyPowers() {
        if (this.nextMove == -1) {
            super.applyPowers();
            return;
        }
        DamageInfo di = new DamageInfo(this, moves.get(this.nextMove).baseDamage, DamageInfo.DamageType.NORMAL);
        if (this.nextMove == MOVE_ULTIMATE) {
            if (di.base > -1) {
                int blockToAdd = this.currentBlock;
                di.base += blockToAdd;
                // TODO handle dynamic intent preview here?
            }
        } else {
            super.applyPowers();
        }
    }

    @Override
    public void die(boolean triggerRelics) {
        this.useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        atb(new SFXAction("NinianDeath"));
        atb(new TalkAction(this, DIALOG[4]));
        // non cosmetic logic
        super.die(triggerRelics);
        this.onBossVictoryLogic();
    }

}
