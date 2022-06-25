package dogfight_Z.Ammo;

import java.util.ArrayList;

import dogfight_Z.Aircraft;
import dogfight_Z.GameManagement;
import dogfight_Z.Effects.Particle;
import graphic_Z.Interfaces.Dynamic;
import graphic_Z.Interfaces.ThreeDs;
import graphic_Z.Objects.CharMessObject;
import graphic_Z.utils.GraphicUtils;
import graphic_Z.utils.LinkedListZ;

public class CannonAmmo extends CharMessObject implements Dynamic, Dangerous
{
	protected	 boolean	actived;
	public static final int maxLife = 500;
	public		  int		life;
	public		  int		myCamp;
	public		  int		lifeLeft;
	public		  long		lifeTo;
	public		  float	    speed;
	public		  float	    resistanceRate;
	public		  float[]	temp;
	public		  Aircraft	launcher;
	public		  LinkedListZ<ThreeDs> aircrafts;
    private       GameManagement gameManager;
	
	private static ArrayList<float[]> missileModelData;
	static {
		missileModelData = new ArrayList<float[]>();
		
		float newPonit[];
		newPonit = new float[6];
		newPonit[0] = 0;
		newPonit[1] = 0;
		newPonit[2] = 1250;
		newPonit[3] = 0;
		newPonit[4] = 0;
		newPonit[5] = 0;
		missileModelData.add(newPonit);
	}
	
	public CannonAmmo
	(
	    GameManagement gameManager,
		int   lifeTime,
		int   my_camp,
		float Speed,
		float resistance_rate,
		float Location[],
		float Roll_angle[],
		LinkedListZ<ThreeDs> Aircrafts,
		Aircraft souce
	)
	{
		super(null, 1, DrawingMethod.drawLine);
		this.gameManager = gameManager;
		specialDisplay	= '@';
		temp 			= new float[3];
		actived			= true;
		lifeLeft = life = lifeTime;
		myCamp			= my_camp;
		speed			= Speed;
		resistanceRate	= resistance_rate;
		aircrafts		= Aircrafts;
		launcher		= souce;
		if(lifeTime > maxLife)
			lifeTime = maxLife;
		
		lifeTo			= lifeTime + System.currentTimeMillis();
		
		location[0] = Location[0];
		location[1] = Location[1];
		location[2] = Location[2];
		
		roll_angle[0] = Roll_angle[0];
		roll_angle[1] = Roll_angle[1];
		roll_angle[2] = Roll_angle[2];
		
		points = missileModelData;
		points_count = missileModelData.size();
		
		visible = true;
	}
	
	@Override
	public void go() {
	    
		if(!actived) return;
		if(lifeLeft <= 0) {
			disable();
			return;
		}
		
        Aircraft aJet = null;
		float x, y, z, t, r1, r2;
		for(int repeat = 0; repeat < 5; ++repeat)
		{
			//------------[go street]------------
            speed -= speed * resistanceRate;
            r1 = roll_angle[1];
            r2 = roll_angle[0];
			t  = GraphicUtils.cos(r1) * speed;
			x  = GraphicUtils.sin(r1) * speed;
			y  = GraphicUtils.sin(r2) * t;
			z  = GraphicUtils.cos(r2) * t;
			
			location[0]	-= x;
			location[1]	+= y;
			location[2]	+= z;
			
			for(ThreeDs T : aircrafts) {
				aJet = (Aircraft) T;
				if(aJet.getID().charAt(0) != '\n' && aJet.isAlive() && GraphicUtils.range(location, aJet.location) < 480) {
					if(aJet.getCamp() != myCamp) {
						aJet.getDamage(5, launcher, "Cannon");
						Particle.makeExplosion(gameManager, location, 10, 75000, 0.01F, 0.1F);
						if(aJet.isPlayer()) aJet.getGameManager().colorFlash(255, 255, 128, 128, 16, 16, 2);
						if(launcher.isPlayer()) launcher.getGameManager().colorFlash(255, 255, 128, 96, 72, 0, 2);
						disable();
						return;
					}
				}
			}
		}
		lifeLeft -= 1000;
	}
	
	public final void disable() {
		actived = visible = false;
	}
	
	public final boolean deleted() {
		return !actived;
	}
	
	@Override
	public final int compareTo(Dynamic o) {
		return (int) (getLife() - o.getLife());
	}

	@Override
	public final long getLife() {
		return lifeTo;
	}
	
	public final int getHash() {
		return this.hashCode();
	}

    @Override
    public final int getDamage() {
        //disable();
        return 5;
    }

    @Override
    public final String getLauncherID() {
        return launcher.getID();
    }

    @Override
    public final String getWeaponName() {
        return "Cannon";
    }

    @Override
    public final PointType getPointType() {
        return PointType.rel;
    }
}
