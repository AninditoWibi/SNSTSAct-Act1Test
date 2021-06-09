package SNAct1.relics;

import SNAct1.SNAct1Mod;
import SNAct1.powers.SoulChargePower;
import SNAct1.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static SNAct1.SNAct1Mod.makeID;
import static SNAct1.util.Wiz.atb;

public class CervantesAcheronAndNirvana extends CustomRelic implements ClickableRelic {
    public static final String ID = makeID(CervantesAcheronAndNirvana.class.getSimpleName());
    private static String pathToImg = SNAct1Mod.makeRelicPath("CervantesAcheronAndNirvana.png");
    private static String pathToImgOutline = SNAct1Mod.makeRelicOutlinePath("CervantesAcheronAndNirvana.png");

    private static final Texture IMG = TexLoader.getTexture(pathToImg);
    private static final Texture OUTLINE = TexLoader.getTexture(pathToImgOutline);

    static final int CHARGES_INITIAL = 3;
    static final int CHARGES_MAX = 6;
    static final int NORMAL_CHARGE_INCREASE = 1;
    static final int ELITE_CHARGE_INCREASE = 2;
    static final int BOSS_CHARGE_INCREASE = 3;
    static final int CHARGES_REQUIRED = 3;
    static final int STRENGTH_GAIN_ONUSE = 3;
    static final int SOULCHARGE_TURNS_ONUSE = 3;
    static final int SOULCHARGE_DAMAGE_BONUS = 33; // 33 = 33% more damage, 50 = 50% more damage, etc

    private boolean usedThisCombat = false;
    private boolean isPlayerTurn = false;

    public CervantesAcheronAndNirvana() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void onEquip() {
        counter = CHARGES_INITIAL;
    }

    @Override
    public void onVictory() {
        if (usedThisCombat) {
            usedThisCombat = false;
        }
        AbstractRoom arm = AbstractDungeon.getCurrRoom();
        if (arm instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss) {
            this.counter += BOSS_CHARGE_INCREASE;
        }
        if (arm instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite) {
            this.counter += ELITE_CHARGE_INCREASE;
        }
        if (arm instanceof com.megacrit.cardcrawl.rooms.MonsterRoom) {
            this.counter += NORMAL_CHARGE_INCREASE;
        }
        if (this.counter > CHARGES_MAX) {
            this.counter = CHARGES_MAX;
        }
    }

    @Override
    public void atBattleStart() {
        grayscale = false;
    }

    @Override
    public void atPreBattle() {
        usedThisCombat = false;
        beginLongPulse(); // Pulse while the player can click on it.
    }

    @Override
    public void atTurnStart() {
        isPlayerTurn = true; // It's our turn!
        beginLongPulse();
    }

    @Override
    public void onPlayerEndTurn() {
        isPlayerTurn = false; // Not our turn now.
        stopPulse();
    }

    @Override
    public void onRightClick() {
        if (usedThisCombat || !isObtained || !isPlayerTurn) {
            // If it has been used this combat, the player doesn't actually have the relic (i.e. it's on display in the shop room), or it's the enemy's turn
            return; // Don't do anything.
        }

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) { // Only if you're in combat
            if (this.counter >= CHARGES_REQUIRED) {
                flash();
                // apply STR and SC powers
                atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STRENGTH_GAIN_ONUSE), STRENGTH_GAIN_ONUSE));
                atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SoulChargePower(AbstractDungeon.player, SOULCHARGE_TURNS_ONUSE, SOULCHARGE_DAMAGE_BONUS, false)));
                // decrement counter and disable relic
                this.counter -= CHARGES_REQUIRED;
                stopPulse(); // stop the pulsing animation
                grayscale = true;
                usedThisCombat = true;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CHARGES_INITIAL + DESCRIPTIONS[1] + CHARGES_MAX + DESCRIPTIONS[2] + NORMAL_CHARGE_INCREASE + DESCRIPTIONS[3] + ELITE_CHARGE_INCREASE + DESCRIPTIONS[4] + BOSS_CHARGE_INCREASE + DESCRIPTIONS[5] + CHARGES_REQUIRED +  DESCRIPTIONS[6] + STRENGTH_GAIN_ONUSE +  DESCRIPTIONS[7] + SOULCHARGE_TURNS_ONUSE +  DESCRIPTIONS[8];
    }
}
