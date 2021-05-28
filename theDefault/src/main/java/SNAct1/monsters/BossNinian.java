package SNAct1.monsters;

import SNAct1.SNAct1Mod;
import SNAct1.powers.EnfeebledBodyPower;
import SNAct1.powers.InvisNinianWeaknessPower;
import SNAct1.powers.SomberPower;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BossNinian extends CustomMonster {
    public static final String ID = SNAct1Mod.makeID("boss_Ninian");
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
    private static final int LIGHTBREATH_DMG_HIGHASC = 21;
    private static final int LIGHTBREATH_BLOCK = 16;
    private static final int LIGHTBREATH_BLOCK_HIGHASC = 20;
    private static final int LIGHTBREATH_STRENGTHBUFF = 6;
    private int lightBreathDmg;
    private int lightBreathBlock;
    private int lightBreathStr;

    private static final int ICEBREATH_DMG = 20;
    private static final int ICEBREATH_DMG_HIGHASC = 24;
    private static final int ICEBREATH_DEBUFF = -2;
    private static final int ICEBREATH_DEBUFF_HIGHASC = -3;
    private static final int ICEBREATH_DEBUFF_DURATION = 1;
    private static final int ICEBREATH_DEBUFF_DURATION_HIGHASC = 1;
    private int iceBreathDmg;
    private int iceBreathDebuff;
    private int iceBreathDebuffDuration;

    private static final int ESCAPEROUTE_BLOCK = 36;
    private static final int ESCAPEROUTE_BLOCK_HIGHASC = 44;
    private int escapeRouteBlock;

    private static final int BLOCKBUFF_DURATION = 1;
    private static final int BLOCKBUFF_DURATION_HIGHASC = 1;
    private static final int BLOCKBUFF_VALUE = 50;
    private static final int BLOCKBUFF_VALUE_HIGHASC = 50;
    private int blockBuffDuration;
    private int blockBuffValue;

    private static final int ULTIMATE_BASEDMG = 34;
    private static final int ULTIMATE_BASEDMG_HIGHASC = 39;
    private int ultimateBaseDmg;

    private static final int DESPAIR_BASESTRPENALTY = -7;
    private static final int DESPAIR_BASESTRPENALTY_HIGHASC = -5;
    private static final int DESPAIR_VULNERABLE_CARDS = 3;
    private static final int DESPAIR_VULNERABLE_CARDS_HIGHASC = 4;
    private static final int DESPAIR_VULNERABLE_VALUE = 1;
    private static final int DESPAIR_VULNERABLE_VALUE_HIGHASC = 1;
    private static final int DESPAIR_WEAKONHIT = 2;
    private static final int DESPAIR_WEAKONHIT_HIGHASC = 1;
    private int despairBasestrpenalty;
    private int despairVulnerableCards;
    private int despairVulnerableValue;
    private int despairWeakOnBlockBreak;

    private static final int NINIAN_HP = 840;
    private static final int NINIAN_HP_HIGHASC = 960;
    private static final int NINIAN_WEAKNESS_BONUS = 100;

    public BossNinian() {
        this(0.0f, 0.0f);
    }

    public BossNinian(final float x, final float y) {
        super(BossNinian.NAME, ID, NINIAN_HP, -5.0F, 0, 555.0f, 415.0f, IMG, x, y);
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

        /* apparently unnecessary for card enemies
        this.damage.add(new DamageInfo(this, this.lightBreathDmg));
        this.damage.add(new DamageInfo(this, this.iceBreathDmg));
        this.damage.add(new DamageInfo(this, this.ultimateBaseDmg));
        */
        

    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.getCurrRoom().playBgmInstantly("a1_boss_NinianEliwood");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.despairBasestrpenalty)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SomberPower(this, despairVulnerableCards, despairVulnerableValue)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new EnfeebledBodyPower(this, this.despairWeakOnBlockBreak)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new InvisNinianWeaknessPower(this, NINIAN_WEAKNESS_BONUS)));
    }

    @Override
    public void takeTurn() {

    }

    @Override
    protected void getMove(int i) {

    }
}



