package SNAct1.monsters;

import SNAct1.CustomIntent.IntentEnums;
import SNAct1.SNAct1Mod;
import SNAct1.actions.AllyGainBlockAction;
import SNAct1.cards.enemyBossCards.*;
import SNAct1.powers.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import org.apache.logging.log4j.LogManager;

import static SNAct1.util.Wiz.*;

public class BossEliwood extends AbstractAllyCardMonster {

    public static final String ID = SNAct1Mod.makeID("BossEliwood");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final String IMG = "SNAct1Resources/images/monsters/boss/boss-eliwood-sprite.png";

    private static final byte MOVE_DEATHBLOW = 0;
    private static final byte MOVE_MASSBLOCK = 1;
    private static final byte MOVE_CHILLATK = 2;
    private static final byte MOVE_DMGBLOCKBUFF = 3;

    private static final int DEATHBLOW_DMG = 40;
    private static final int DEATHBLOW_DMG_HIGHASC = 41;
    private static final int DEATHBLOW_STRENGTHBUFF = 2;
    public int deathBlowDmg;
    public int deathBlowStr;

    private static final int MASSBLOCK_BLOCK = 32;
    private static final int MASSBLOCK_BLOCK_HIGHASC = 34;
    public int massBlockBlock;

    private static final int CHILLATK_STRENGTHDEBUFF = 5;
    private static final int CHILLATK_STRENGTHDEBUFF_HIGHASC = 6;
    public int chillAtkDebuff;

    private static final int DMGBLOCKBUFF_BONUS = 2;
    private static final int DMGBLOCKBUFF_DURATION = 2;
    public int dmgBlockBuffBonus;
    public int dmgBlockBuffDuration;

    private static final int ELIWOOD_HP = 125;
    private static final int ELIWOOD_HP_HIGHASC = 150;
    private static final int DURANDAL_DAMAGEBONUS = 100;
    private static final int BLAZING_STR_GAIN = 3;
    private static final int BLAZING_DEX_GAIN = 3;

    public BossNinian ninian;
    private int moveCounter = 1;
    private boolean voicelinePlayedAttack;
    private boolean voicelinePlayedBlock;
    private boolean voicelinePlayedDebuff;
    private boolean voicelinePlayedBuff;
    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SNAct1Mod.class.getName());

    public BossEliwood(final float x, final float y) {
        super(BossEliwood.NAME, ID, ELIWOOD_HP, -5.0F, 0, 181.0f, 187.0f, IMG, x, y);
        this.type = EnemyType.BOSS;
        this.dialogX = (this.hb_x - 70.0F) * Settings.scale;
        this.dialogY -= (this.hb_y - 55.0F) * Settings.scale;

        /*
        reminder:
        asc 4 = more boss damage
        asc 9 = more boss HP/defense
        asc 19 = general boss improvement
         */

        this.deathBlowStr = DEATHBLOW_STRENGTHBUFF;
        this.dmgBlockBuffBonus = DMGBLOCKBUFF_BONUS;
        this.dmgBlockBuffDuration = DMGBLOCKBUFF_DURATION;

        if (AbstractDungeon.ascensionLevel >= 4) {
            this.deathBlowDmg = DEATHBLOW_DMG_HIGHASC;
        } else {
            this.deathBlowDmg = DEATHBLOW_DMG;
        }

        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(ELIWOOD_HP_HIGHASC);
        } else {
            this.setHp(ELIWOOD_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.massBlockBlock = MASSBLOCK_BLOCK_HIGHASC;
            this.chillAtkDebuff = CHILLATK_STRENGTHDEBUFF_HIGHASC;
        } else {
            this.massBlockBlock = MASSBLOCK_BLOCK;
            this.chillAtkDebuff = CHILLATK_STRENGTHDEBUFF;
        }

        addMove(MOVE_DEATHBLOW, Intent.ATTACK_BUFF, this.deathBlowDmg);
        addMove(MOVE_MASSBLOCK, Intent.DEFEND, this.massBlockBlock);
        addMove(MOVE_CHILLATK, Intent.STRONG_DEBUFF);
        addMove(MOVE_DMGBLOCKBUFF, Intent.BUFF);

        cardList.add(new BossDeathBlow(this));
        cardList.add(new BossRallyDefense(this));
        cardList.add(new BossChillAttack(this));
        cardList.add(new BossVisionsOfArcadia(this));

    }

    @Override
    public void usePreBattleAction() {
        atb(new SFXAction("EliwoodOpening"));
        atb(new TalkAction(this, DIALOG[0]));
        // init ninian for targeting logic
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo instanceof BossNinian) {
                ninian = (BossNinian)mo;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new WielderOfDurandalPower(this, DURANDAL_DAMAGEBONUS)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BlazingAspectPower(this, BLAZING_STR_GAIN, BLAZING_DEX_GAIN)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArdentAspectPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new InvisEliwoodArdentPower(AbstractDungeon.player)));
        // non-hardmode ninian attacks on turn 1, give block to compensate
        if (AbstractDungeon.ascensionLevel < 19) {
            atb(new GainBlockAction(this, this.massBlockBlock));
        }
    }

    @Override
    public void takeTurn() {
        logger.info("current move is " + this.nextMove);
        if (this.isDead) {
            return;
        }
        super.takeTurn();

        DamageInfo di;
        AbstractCreature target = ninian;

        if (moves.containsKey(this.nextMove)) { // if the byte value for the next intent is valid when this creature takes its turn..
            EnemyMoveInfo emi = moves.get(this.nextMove); // fetch information added above in addmove
            di = new DamageInfo(this, emi.baseDamage, DamageInfo.DamageType.NORMAL);
        } else { // for if the byte value is invalid, we assume this monster is stunned
            di = new DamageInfo(this, 0, DamageInfo.DamageType.NORMAL);
        }
        if (di.base > -1) { // apply game power calculations
            di.applyPowers(this, target);
        }

        switch (this.nextMove) {
            case MOVE_DEATHBLOW: {
                logger.info("bossEliwood about to execute DeathBlow");
                if (!voicelinePlayedAttack) {
                    voicelinePlayedAttack = true;
                    atb(new SFXAction("EliwoodAttacking"));
                    atb(new TalkAction(this, DIALOG[1]));
                }
                atb(new SFXAction("ATTACK_HEAVY"));
                atb(new VFXAction(this, new CleaveEffect(), 0.1F));
                dmg(target, di);
                atb(new ApplyPowerAction(this, this, new StrengthPower(this, deathBlowStr)));
                //blazing aspect logic
                AbstractPower ownStr = this.getPower("StrengthPower");
                AbstractPower tgtStr = target.getPower("StrengthPower");
                int ownStrAmt = 0;
                int tgtStrAmt = 0;

                if (ownStr != null) {
                    ownStrAmt = ownStr.amount;
                }

                if (tgtStr != null) {
                    tgtStrAmt = tgtStr.amount;
                }

                if (ownStrAmt > tgtStrAmt) {
                    int bonusStr = ownStrAmt - tgtStrAmt;
                    applyToTargetNextTurn(this, new StrengthPower(this, bonusStr));
                    applyToTargetNextTurn(this, new LoseStrengthPower(this, bonusStr));
                }
                break;
            }
            case MOVE_MASSBLOCK: {
                logger.info("bossEliwood about to execute MassBlock");
                if (!voicelinePlayedBlock) {
                    voicelinePlayedBlock = true;
                    atb(new SFXAction("EliwoodDefending"));
                    atb(new TalkAction(this, DIALOG[2]));
                }
                block(AbstractDungeon.player, massBlockBlock);
                atb(new AllyGainBlockAction(this, this, massBlockBlock));
                break;
            }
            case MOVE_CHILLATK: {
                logger.info("bossEliwood about to execute ChillAtk");
                if (!voicelinePlayedDebuff) {
                    voicelinePlayedDebuff = true;
                    atb(new SFXAction("EliwoodDebuff"));
                    atb(new TalkAction(this, DIALOG[3]));
                }
                atb(new ApplyPowerAction(target, this, new StrengthPower(target, -chillAtkDebuff)));
                break;
            }
            case MOVE_DMGBLOCKBUFF: {
                logger.info("bossEliwood about to execute Visions");
                if (!voicelinePlayedBuff) {
                    voicelinePlayedBuff = true;
                    atb(new SFXAction("EliwoodBuff"));
                    atb(new TalkAction(this, DIALOG[4]));
                }
                applyToTarget(AbstractDungeon.player, this, new ArcadianVisionPower(AbstractDungeon.player, dmgBlockBuffBonus, dmgBlockBuffDuration, true));
                atb(new ApplyPowerAction(this, this, new ArcadianVisionPower(this, dmgBlockBuffBonus, dmgBlockBuffDuration, true)));
                break;
            }
        }
        if (moveCounter == 4) {
            moveCounter = 1;
        } else {
            moveCounter++;
        }
        atb(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        /*
         * reminder:
         * deathblow = 0, massblock = 1
         * reduce str = 2, visions buff = 3
         * also applies to move array names
         * */
        if (moveCounter == 1) {
            setMoveShortcut(MOVE_DEATHBLOW, MOVES[0], cardList.get(0).makeStatEquivalentCopy());
        }
        if (moveCounter == 2) {
            setMoveShortcut(MOVE_MASSBLOCK, MOVES[1], cardList.get(1).makeStatEquivalentCopy());
        }
        if (moveCounter == 3) {
            setMoveShortcut(MOVE_DMGBLOCKBUFF, MOVES[3], cardList.get(3).makeStatEquivalentCopy());
        }
        if (moveCounter == 4) {
            setMoveShortcut(MOVE_CHILLATK, MOVES[2], cardList.get(2).makeStatEquivalentCopy());
        }
    }

    public void onNinianDeath() {
        if (!isDead && !isDying) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    disappear();
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void die() {
        atb(new SFXAction("EliwoodDeath"));
        atb(new TalkAction(this, DIALOG[5]));
        super.die();
    }
}
