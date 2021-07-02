package SNAct1.monsters;

import SNAct1.SNAct1Mod;
import SNAct1.powers.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

import static SNAct1.util.Wiz.atb;
import static SNAct1.util.Wiz.dmg;

public class BossTerra extends AbstractSNActsMonster {
    public static final String ID = SNAct1Mod.makeID("BossTerra");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final String IMG = "SNAct1Resources/images/monsters/boss/boss-terra-sprite.png";

    private static final byte MOVE_FIRAGA = 0;
    private static final byte MOVE_BLIZZARD = 1;
    private static final byte MOVE_RASP = 2;
    private static final byte MOVE_FLOOD = 3;
    private static final byte MOVE_TORNADO = 4;

    private static final int MOVE_FIRAGA_DAMAGE = 18;
    private static final int MOVE_FIRAGA_DAMAGE_HIGHASC = 20;
    private static final int MOVE_FIRAGA_BURNTOADD = 1;
    private static final int MOVE_FIRAGA_BURNTOADD_HIGHASC = 2;
    public int firagaDamage;
    public int firagaBurn;

    private static final int MOVE_BLIZZARD_DAMAGE = 10;
    private static final int MOVE_BLIZZARD_DAMAGE_HIGHASC = 11;
    private static final int MOVE_BLIZZARD_HITS = 2;
    private static final int MOVE_BLIZZARD_HITS_HIGHASC = 2;
    private static final int MOVE_BLIZZARD_BLOCK = 9;
    private static final int MOVE_BLIZZARD_BLOCK_HIGHASC = 11;
    public int blizzardDamage;
    public int blizzardHits;
    public int blizzardBlock;

    private static final int MOVE_RASP_BLOCK = 14;
    private static final int MOVE_RASP_BLOCK_HIGHASC = 18;
    private static final int MOVE_RASP_VULN = 1;
    private static final int MOVE_RASP_VULN_HIGHASC = 2;
    private static final int MOVE_RASP_VULN_KEFKA = 2;
    private static final int MOVE_RASP_VULN_KEFKA_HIGHASC = 1;
    public int raspBlock;
    public int raspVulnToPlayer;
    public int raspVulnToKefka;

    private static final int MOVE_FLOOD_DAMAGE = 9;
    private static final int MOVE_FLOOD_DAMAGE_HIGHASC = 10;
    private static final int MOVE_FLOOD_HITS = 3;
    private static final int MOVE_FLOOD_HITS_HIGHASC = 3;
    private static final int MOVE_FLOOD_EXHAUSTAMT = 2;
    private static final int MOVE_FLOOD_EXHAUSTAMT_HIGHASC = 4;
    public int floodDamage;
    public int floodHits;
    public int floodCardsToExhaust;

    private static final int MOVE_TORNADO_DAMAGE = 24;
    private static final int MOVE_TORNADO_DAMAGE_HIGHASC = 28;
    public int tornadoDamage;

    private static final int TERRA_HP = 116;
    private static final int TERRA_HP_HIGHASC = 121;

    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SNAct1Mod.class.getName());
    private boolean firstMove = true;
    private int moveCounter = 1;
    private boolean voicelinePlayedFiraga = false;
    private boolean voicelinePlayedBlizzard = false;
    private boolean voicelinePlayedRasp= false;
    private boolean voicelinePlayedFlood = false;
    private boolean voicelinePlayedTornado = false;

    BossKefka kefka;

    public BossTerra() {
        this(0.0f, 0.0f);
    }

    public BossTerra(final float x, final float y) {
        super(BossTerra.NAME, ID, TERRA_HP, -5.0F, 0, 281.0f, 495.0f, IMG, x, y);
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
            this.firagaDamage = MOVE_FIRAGA_DAMAGE_HIGHASC;
            this.blizzardDamage = MOVE_BLIZZARD_DAMAGE_HIGHASC;
            this.tornadoDamage = MOVE_TORNADO_DAMAGE_HIGHASC;
            this.floodDamage = MOVE_FLOOD_DAMAGE_HIGHASC;
            this.raspVulnToPlayer = MOVE_RASP_VULN_HIGHASC;
        } else {
            this.firagaDamage = MOVE_FIRAGA_DAMAGE;
            this.blizzardDamage = MOVE_BLIZZARD_DAMAGE;
            this.tornadoDamage = MOVE_TORNADO_DAMAGE;
            this.floodDamage = MOVE_FLOOD_DAMAGE;
            this.raspVulnToPlayer = MOVE_RASP_VULN;
        }

        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(TERRA_HP_HIGHASC);
            this.raspBlock = MOVE_RASP_BLOCK_HIGHASC;
            this.blizzardBlock = MOVE_BLIZZARD_BLOCK_HIGHASC;
        } else {
            setHp(TERRA_HP);
            this.raspBlock = MOVE_RASP_BLOCK;
            this.blizzardBlock = MOVE_BLIZZARD_BLOCK;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.firagaBurn = MOVE_FIRAGA_BURNTOADD_HIGHASC;
            this.blizzardHits = MOVE_BLIZZARD_HITS_HIGHASC;
            this.raspVulnToKefka = MOVE_RASP_VULN_KEFKA_HIGHASC;
            this.floodHits = MOVE_FLOOD_HITS_HIGHASC;
            this.floodCardsToExhaust = MOVE_FLOOD_EXHAUSTAMT_HIGHASC;
        } else {
            this.firagaBurn = MOVE_FIRAGA_BURNTOADD;
            this.blizzardHits = MOVE_BLIZZARD_HITS;
            this.raspVulnToKefka = MOVE_RASP_VULN_KEFKA;
            this.floodHits = MOVE_FLOOD_HITS;
            this.floodCardsToExhaust = MOVE_FLOOD_EXHAUSTAMT;
        }

        addMove(MOVE_FIRAGA, Intent.ATTACK_DEBUFF, this.firagaDamage);
        addMove(MOVE_BLIZZARD, Intent.ATTACK_DEFEND, this.blizzardDamage, this.blizzardHits, true);
        addMove(MOVE_RASP, Intent.DEFEND_DEBUFF);
        addMove(MOVE_FLOOD, Intent.ATTACK_DEBUFF, this. floodDamage, this.floodHits, true);
        addMove(MOVE_TORNADO, Intent.ATTACK);
    }

    @Override
    public void usePreBattleAction() {
        // AbstractDungeon.getCurrRoom().playBgmInstantly("a1_boss_TerraKefka");
        atb(new SFXAction("TerraOpening"));
        atb(new TalkAction(this, DIALOG[0]));
        atb(new ApplyPowerAction(this, this, new SlaveCrownPower(this)));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo instanceof BossKefka) {
                kefka = (BossKefka)mo;
            }
        }
    }

    @Override
    public void takeTurn() {
        logger.info("current Terra move is " + this.nextMove);
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
            case MOVE_FIRAGA: {
                if (!voicelinePlayedFiraga) {
                    voicelinePlayedFiraga = true;
                    atb(new TalkAction(this, DIALOG[1]));
                }
                atb(new VFXAction(this, new VerticalAuraEffect(Color.RED, this.hb.cX,this.hb.cY), 0.33F));
                atb(new SFXAction("ATTACK_FIRE"));
                dmg(AbstractDungeon.player, di);
                break;
            }
            case MOVE_BLIZZARD: {
                if (!voicelinePlayedBlizzard) {
                    voicelinePlayedBlizzard = true;
                    atb(new TalkAction(this, DIALOG[2]));
                }
                if (Settings.FAST_MODE) {
                    atb(new VFXAction(new BlizzardEffect(4, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.275F));
                } else {
                    atb(new VFXAction(new BlizzardEffect(4, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.10F));
                }
                for (int i = 0; i < multiplier; i++) {
                    CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
                    dmg(AbstractDungeon.player, di);
                }
                atb(new GainBlockAction(this, this, blizzardBlock));
                break;
            }
            case MOVE_RASP: {
                if (!voicelinePlayedRasp) {
                    voicelinePlayedRasp = true;
                    atb(new TalkAction(this, DIALOG[3]));
                }
                atb(new VFXAction(this, new VerticalAuraEffect(Color.PURPLE, this.hb.cX,this.hb.cY), 0.33F));
                atb(new VFXAction(new RoomTintEffect(Color.PURPLE, 0.5F)));
                atb(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.34F));
                atb(new VFXAction(new BorderFlashEffect(Color.BLACK)));
                // non cosmetic logic
                atb(new GainBlockAction(this, this, raspBlock));
                atb(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, raspVulnToPlayer, true), raspVulnToKefka));
                atb(new ApplyPowerAction(kefka, this, new VulnerablePower(kefka, raspVulnToKefka, true), raspVulnToKefka));
                break;
            }
            case MOVE_FLOOD: {
                if (!voicelinePlayedFlood) {
                    voicelinePlayedFlood = true;
                    atb(new TalkAction(this, DIALOG[4]));
                }
                for (int i = 0; i < multiplier; i++) {
                    dmg(AbstractDungeon.player, di);
                }
                exhaustCardsInDraw(floodCardsToExhaust);
                exhaustCardsInDiscard(floodCardsToExhaust);
                break;
            }
            case MOVE_TORNADO: {
                if (!voicelinePlayedTornado) {
                    voicelinePlayedTornado = true;
                    atb(new TalkAction(this, DIALOG[5]));
                }
                dmg(AbstractDungeon.player, di);
                // same as deep breath
                if (AbstractDungeon.player.discardPile.size() > 0) {
                    addToBot(new EmptyDeckShuffleAction());
                    addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, false));
                }
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
         * firaga = 0, blizzard = 1, rasp = 2
         * flood = 3,tornado = 4
         * also applies to move array names
         * */
        if (moveCounter == 1) {
            if (AbstractDungeon.aiRng.randomBoolean(boolMoveChance)) {
                setMoveShortcut(MOVE_FIRAGA, MOVES[0]);
            } else {
                setMoveShortcut(MOVE_BLIZZARD, MOVES[1]);
            }
        }
        if (moveCounter == 2) {
            if (lastMove(MOVE_FIRAGA)) {
                setMoveShortcut(MOVE_BLIZZARD, MOVES[1]);
            } else {
                setMoveShortcut(MOVE_FIRAGA, MOVES[0]);
            }
        }
        if (moveCounter == 3 || moveCounter == 6) {
            setMoveShortcut(MOVE_RASP, MOVES[2]);
        }
        if (moveCounter == 4) {
            if (AbstractDungeon.aiRng.randomBoolean(boolMoveChance)) {
                setMoveShortcut(MOVE_FLOOD, MOVES[3]);
            } else {
                setMoveShortcut(MOVE_TORNADO, MOVES[4]);
            }
        }
        if (moveCounter == 5) {
            if (lastMove(MOVE_FLOOD)) {
                setMoveShortcut(MOVE_TORNADO, MOVES[4]);
            } else {
                setMoveShortcut(MOVE_FLOOD, MOVES[3]);
            }
        }
    }

    private static class CardToExhaust {
        public AbstractCard card;
        public CardGroup group;
    }

    private void addCardsToExhaust(ArrayList<CardToExhaust> cards, CardGroup group, int cardsArraySize) {
        // first priority: statuses
        for (AbstractCard c : group.group) {
            if (c.type == AbstractCard.CardType.STATUS) {
                CardToExhaust cte = new CardToExhaust();
                cte.card = c;
                cte.group = group;
                if (cards.size() >= cardsArraySize) {
                    return;
                } else {
                    cards.add(cte);
                }
            }
        }
        // second priority: rares
        for (AbstractCard c : group.group) {
            if (c.rarity == AbstractCard.CardRarity.RARE) {
                CardToExhaust cte = new CardToExhaust();
                cte.card = c;
                cte.group = group;
                if (cards.size() >= cardsArraySize) {
                    return;
                } else {
                    cards.add(cte);
                }
            }
        }
        // third priority: cost 1 cards
        for (AbstractCard c : group.group) {
            if (c.cost == 1 && !c.isCostModified) {
                CardToExhaust cte = new CardToExhaust();
                cte.card = c;
                cte.group = group;
                if (cards.size() >= cardsArraySize) {
                    return;
                } else {
                    cards.add(cte);
                }
            }
        }
        // if somehow the array still has less cards than the limit, so be it.
        // pass the array anyway
    }

    private void exhaustCardsInDraw(int arrSize) {
        ArrayList<CardToExhaust> cards = new ArrayList<>();
        this.addCardsToExhaust(cards, AbstractDungeon.player.drawPile, arrSize);
        for (CardToExhaust cte : cards) {
            atb(new ExhaustSpecificCardAction(cte.card, cte.group));
        }
    }

    private void exhaustCardsInDiscard(int arrSize) {
        ArrayList<CardToExhaust> cards = new ArrayList<>();
        this.addCardsToExhaust(cards, AbstractDungeon.player.discardPile, arrSize);
        for (CardToExhaust cte : cards) {
            atb(new ExhaustSpecificCardAction(cte.card, cte.group));
        }
    }

    public void onKefkaDeath() {
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
        atb(new SFXAction("TerraDeath"));
        atb(new TalkAction(this, DIALOG[6]));
        // non cosmetic logic
        kefka.onTerraDeath();
        super.die(triggerRelics);
        this.onBossVictoryLogic();
    }
}
