package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.monsters.BossCervantes;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import org.apache.logging.log4j.LogManager;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.atb;

public class WhiffPunisherPower extends TwoAmountPower {
    public static final String POWER_ID = SNAct1Mod.makeID(WhiffPunisherPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("WhiffPunisherPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("WhiffPunisherPower32.png"));
    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SNAct1Mod.class.getName());

    public int threshold;
    public BossCervantes cervantes;

    // this power is only intended for an instance of cerv
    public WhiffPunisherPower(AbstractCreature owner, BossCervantes cerv, int amount, int attacksToWhiff, int damageReduction) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = damageReduction;
        this.threshold = attacksToWhiff;
        this.cervantes = cerv;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.threshold + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.amount++;
            if (this.amount % threshold == 0) {
                flash();
                if (!cervantes.isDying && cervantes.nextMove != (byte)3) {
                    atb(new GainBlockAction(cervantes, amount2));
                    cervantes.setMoveShortcut((byte)3, BossCervantes.MOVES[3]);
                    cervantes.createIntent();
                    logger.info("WPP CLASS LOG - cerv next move is " + cervantes.nextMove);
                }
                this.amount = 0;
            }
        }
    }
}
