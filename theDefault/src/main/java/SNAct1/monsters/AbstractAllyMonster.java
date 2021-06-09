package SNAct1.monsters;

import SNAct1.CustomIntent.IntentEnums;
import SNAct1.monsters.AbstractSNActsMonster;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static SNAct1.SNAct1Mod.makeID;
import static SNAct1.util.Wiz.*;

public abstract class AbstractAllyMonster extends AbstractSNActsMonster {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("AllyStrings"));
    private static final String[] TEXT = uiStrings.TEXT;
    public String allyIcon;
    public boolean massAttackHitsPlayer = false;

    public boolean isAlly = true;

    //essentially lets me have allies that the player can still target with cards
    public boolean isTargetableByPlayer = false;

    public AbstractAllyMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

    public AbstractAllyMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, boolean ignoreBlights) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY, ignoreBlights);
    }

    public AbstractAllyMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl);
    }


    @Override
    public void takeTurn() {
        if (!isTargetableByPlayer) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    halfDead = false;
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void createIntent() {
        super.createIntent();
        applyPowers();
    }

    public void applyPowers(AbstractCreature target) {
        applyPowers(target, -1);
    }

    public void applyPowers(AbstractCreature target, float additionalMultiplier) {
        if (this.nextMove >= 0) {
            DamageInfo info = new DamageInfo(this, moves.get(this.nextMove).baseDamage, DamageInfo.DamageType.NORMAL);
            if (target != adp()) {
                if(info.base > -1) {
                    Color color = new Color(0.0F, 1.0F, 0.0F, 0.5F);
                    ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentColor", color);
                    if (this.intent == IntentEnums.MASS_ATTACK) {
                        if (massAttackHitsPlayer) {
                            info.applyPowers(this, adp());
                            if (additionalMultiplier > 0) {
                                info.output = (int)(info.output * additionalMultiplier);
                            }
                            ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentDmg", info.output);
                            PowerTip intentTip = (PowerTip)ReflectionHacks.getPrivate(this, AbstractMonster.class, "intentTip");
                            if (moves.get(this.nextMove).multiplier > 0) {
                                intentTip.body = TEXT[13] + info.output + TEXT[14] + " " + FontHelper.colorString(String.valueOf(moves.get(this.nextMove).multiplier), "b") + TEXT[16];
                            } else {
                                intentTip.body = TEXT[13] + info.output + TEXT[14] + TEXT[15];
                            }
                        } else {
                            info.applyPowers(this, target);
                            if (additionalMultiplier > 0) {
                                info.output = (int)(info.output * additionalMultiplier);
                            }
                            ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentDmg", info.output);
                            PowerTip intentTip = (PowerTip)ReflectionHacks.getPrivate(this, AbstractMonster.class, "intentTip");
                            if (moves.get(this.nextMove).multiplier > 0) {
                                intentTip.body = TEXT[13] + info.output + TEXT[17] + " " + FontHelper.colorString(String.valueOf(moves.get(this.nextMove).multiplier), "b") + TEXT[16];
                            } else {
                                intentTip.body = TEXT[13] + info.output + TEXT[17] + TEXT[15];
                            }
                        }
                    } else {
                        info.applyPowers(this, target);
                        if (additionalMultiplier > 0) {
                            info.output = (int)(info.output * additionalMultiplier);
                        }
                        ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentDmg", info.output);
                        PowerTip intentTip = (PowerTip)ReflectionHacks.getPrivate(this, AbstractMonster.class, "intentTip");
                        Texture attackImg;
                        if (moves.get(this.nextMove).multiplier > 0) {
                            intentTip.body = TEXT[0] + FontHelper.colorString(target.name, "y") + TEXT[1] + info.output + TEXT[3] + moves.get(this.nextMove).multiplier + TEXT[4];
                            attackImg = getAttackIntent(info.output * moves.get(this.nextMove).multiplier);
                        } else {
                            intentTip.body = TEXT[0] + FontHelper.colorString(target.name, "y") + TEXT[1] + info.output + TEXT[2];
                            attackImg = getAttackIntent(info.output);
                        }
                        ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentImg", attackImg);
                    }
                } else {
                    Color color = new Color(1.0F, 1.0F, 1.0F, 0.5F);
                    ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentColor", color);
                    PowerTip intentTip = (PowerTip)ReflectionHacks.getPrivate(this, AbstractMonster.class, "intentTip");
                    if (this.intent == Intent.DEBUFF || this.intent == Intent.STRONG_DEBUFF) {
                        intentTip.body = TEXT[5] + FontHelper.colorString(target.name, "y") + TEXT[6];
                    }
                    if (this.intent == Intent.BUFF || this.intent == Intent.DEFEND_BUFF) {
                        intentTip.body = TEXT[7];
                    }
                    if (this.intent == Intent.DEFEND || this.intent == Intent.DEFEND_DEBUFF) {
                        intentTip.body = TEXT[8];
                    }
                }
            } else {
                Color color = new Color(1.0F, 1.0F, 1.0F, 0.5F);
                ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentColor", color);
                super.applyPowers();
            }
        }
    }

    @Override
    public void damage(DamageInfo info) {
        //failsafe to stop player from damaging allies
        if (!isTargetableByPlayer && (info.owner == adp() || info.owner == null)) {
            return;
        }
        super.damage(info);
    }

    @Override
    public void renderReticle(SpriteBatch sb) {
        if (isTargetableByPlayer) {
            super.renderReticle(sb);
        }
    }

    public void disappear() {
        this.currentHealth = 0;
        this.loseBlock();
        this.isDead = true;
        this.isDying = true;
        this.healthBarUpdatedEvent();
    }

    @Override
    public void die(boolean triggerRelics) {
        super.die(false);
    }

    public void setAnimationFlip(boolean horizontal, boolean vertical) {
        animation.setFlip(horizontal, vertical);
    }
}