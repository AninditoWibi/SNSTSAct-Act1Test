package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnMyBlockBrokenPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.applyToTarget;

public class EnfeebledBodyPower extends AbstractPower implements OnMyBlockBrokenPower {
    public static final String POWER_ID = SNAct1Mod.makeID(EnfeebledBodyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("EnfeebledBodyPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("EnfeebledBodyPower32.png"));

    public EnfeebledBodyPower(AbstractCreature owner, int weakAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = weakAmt;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onMyBlockBroken() {
        // do inflict weak to owner here
        applyToTarget(owner, owner, new WeakPower(owner, amount, true));
    }
}
