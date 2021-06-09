package SNAct1.monsters;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;

import java.util.HashMap;
import java.util.Map;

import static SNAct1.SNAct1Mod.makeID;

//big thanks to darkglade and squeeny for letting me adapt these
public abstract class AbstractSNActsMonster extends CustomMonster {

    protected Map<Byte, EnemyMoveInfo> moves;
    protected boolean firstMove = true;

    public AbstractSNActsMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
        setUpMisc();
    }

    public AbstractSNActsMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, boolean ignoreBlights) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY, ignoreBlights);
        setUpMisc();
    }

    public AbstractSNActsMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl);
        setUpMisc();
    }

    protected void setUpMisc() {
        moves = new HashMap<>();
        this.dialogX = (this.hb_x - 70.0F) * Settings.scale;
        this.dialogY -= (this.hb_y - 55.0F) * Settings.scale;
    }

    protected void addMove(byte moveCode, Intent intent) {
        this.addMove(moveCode, intent, -1);
    }

    protected void addMove(byte moveCode, Intent intent, int baseDamage) {
        this.addMove(moveCode, intent, baseDamage, 0);
    }
    protected void addMove(byte moveCode, Intent intent, int baseDamage, int multiplier) {
        this.addMove(moveCode, intent, baseDamage, multiplier, false);
    }
    protected void addMove(byte moveCode, Intent intent, int baseDamage, int multiplier, boolean isMultiDamage) {
        this.moves.put(moveCode, new EnemyMoveInfo(moveCode, intent, baseDamage, multiplier, isMultiDamage));
    }

    public void setMoveShortcut(byte next, String text) {
        EnemyMoveInfo info = this.moves.get(next);
        this.setMove(text, next, info.intent, info.baseDamage, info.multiplier, info.isMultiDamage);
    }
    public void setMoveShortcut(byte next) {
        this.setMoveShortcut(next, null);
    }

    public static void playSound(String sound, float volume) {
        if (sound != null) {
            CardCrawlGame.sound.playV(makeID(sound), volume);
        }
    }

    public static void playSound(String sound) {
        playSound(sound, 1.0f);
    }

}
