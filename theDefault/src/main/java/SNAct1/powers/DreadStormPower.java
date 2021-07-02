package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.monsters.BossCervantes;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SNAct1.SNAct1Mod.makePowerPath;

public class DreadStormPower extends AbstractPower {

    public static final String POWER_ID = SNAct1Mod.makeID(DreadStormPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("DreadStormPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("DreadStormPower32.png"));

    public boolean isDreadStormTriggered() {
        return dreadStormTriggered;
    }

    public boolean dreadStormTriggered = false;
    public BossCervantes cervantes;

    // this power is only intended for an instance of cerv
    public DreadStormPower(AbstractCreature owner, BossCervantes cerv) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.cervantes = cerv;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atStartOfTurn() {
        dreadStormTriggered = false;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        // can trigger after DC B, 4A+B, and IGDR
        if (cervantes.nextMove == 1 || cervantes.nextMove == 2 || cervantes.nextMove == 3) {
            if (info.owner == cervantes && damageAmount == 0 && info.type == DamageInfo.DamageType.NORMAL) {
                dreadStormTriggered = true;
            }
        }
    }
}
