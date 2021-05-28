package SNAct1.monsters;

import SNAct1.SNAct1Mod;
import SNAct1.powers.*;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BossEliwood extends CustomMonster {

    public static final String ID = SNAct1Mod.makeID("boss_Eliwood");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final String IMG = "SNAct1Resources/images/monsters/boss/boss-eliwood-sprite.png";

    private static final byte MOVE_DEATHBLOW = 0;
    private static final byte MOVE_MASSBLOCK = 1;
    private static final byte MOVE_CHILLATK = 2;
    private static final byte MOVE_DMGBLOCKBUFF = 3;

    private static final int DEATHBLOW_DMG = 50;
    private static final int DEATHBLOW_DMG_HIGHASC = 51;
    private static final int DEATHBLOW_STRENGTHBUFF = 2;
    private int deathBlowDmg;
    private int deathBlowStr;

    private static final int MASSBLOCK_BLOCK = 30;
    private static final int MASSBLOCK_BLOCK_HIGHASC = 32;
    private int massBlockBlock;

    private static final int CHILLATK_STRENGTHDEBUFF = -6;
    private static final int CHILLATK_STRENGTHDEBUFF_HIGHASC = -5;
    private int chillAtkDebuff;

    private static final int DMGBLOCKBUFF_BONUS = 2;
    private static final int DMGBLOCKBUFF_DURATION = 2;
    private int dmgBlockBuffBonus;
    private int dmgBlockBuffDuration;

    private static final int ELIWOOD_HP = 175;
    private static final int ELIWOOD_HP_HIGHASC = 200;
    private static final int DURANDAL_DAMAGEBONUS = 100;
    private static final int ARDENT_PLAYER_STRENGTHBUFF = 3;
    private static final int ARDENT_SELF_STRENGTHBUFF = 1;

    public BossEliwood(final float x, final float y) {
        super(BossNinian.NAME, ID, ELIWOOD_HP, -5.0F, 0, 555.0f, 415.0f, IMG, x, y);
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



    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new WielderOfDurandalPower(this, DURANDAL_DAMAGEBONUS)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BlazingAspectPower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArdentAspectPower(this, ARDENT_PLAYER_STRENGTHBUFF, ARDENT_SELF_STRENGTHBUFF)));
    }


    @Override
    public void takeTurn() {

    }

    @Override
    protected void getMove(int i) {

    }
}
