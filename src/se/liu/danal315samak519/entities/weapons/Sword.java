package se.liu.danal315samak519.entities.weapons;

import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Person;


/**
 * A sword that is swung by a player.
 */
public class Sword extends Weapon
{
    private static final float SWORD_LENGTH = 60.0f, SWORD_WIDTH = 10.0f;

    /**
     * Creates a sword that is swung by a player.
     *
     * @param owner
     */
    @SuppressWarnings("MagicNumber") public Sword(final Person owner) {
	super(owner);
	float ownerSideCenter = owner.getWidth() / 2.0f;
	float swordCenter = 0.0f;

	switch (owner.getDirection()) {
	    case UP:
		setSize(SWORD_WIDTH, SWORD_LENGTH);
		swordCenter = this.getWidth() / 2.0f;
		setLocation((owner.getX() + (ownerSideCenter) - this.getWidth() / swordCenter), (owner.getY() - this.getHeight()));
		break;
	    case DOWN:
		setSize(SWORD_WIDTH, SWORD_LENGTH);
		swordCenter = this.getWidth() / 2.0f;
		setLocation((owner.getX() + (ownerSideCenter) - swordCenter), (owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		setSize(SWORD_LENGTH, SWORD_WIDTH);
		swordCenter = this.getHeight() / 2.0f;
		ownerSideCenter = owner.getHeight() / 2.0f;
		setLocation((owner.getX() - this.getWidth()), (owner.getY() + (ownerSideCenter)) - (swordCenter));
		break;
	    case RIGHT:
		setSize(SWORD_LENGTH, SWORD_WIDTH);
		swordCenter = this.getHeight() / 2.0f;
		ownerSideCenter = owner.getHeight() / 2.0f;
		setLocation((owner.getX() + owner.getWidth()), (owner.getY() + (ownerSideCenter) - (swordCenter)));
		break;
	}

	this.setLifeSpan(5);
	setHitBox();

    }

    @Override public void tick() {
	super.tick();
	followOwner();
    }


}
