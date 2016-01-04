package RS2.skills;

import RS2.Settings;
import RS2.model.player.Client;

public class Agility {
	
	private Client c;
		
	public Agility(Client c) {
		this.c = c;
	}
	
	private boolean[] gnomeCourse = new boolean[6];
	private final int[] EXP = {8,8,5,8,5,8,8,39};
	public void handleGnomeCourse(int object, int objectX, int objectY) {
		if (object == 2286 && objectY > c.getY()) { //net
			c.startAnimation(844);
			c.getPA().movePlayer(c.getX(), c.getY() + 2, 0);
			gnomeCourse[4] = true;
			c.getPA().addSkillXP(EXP[5] * Settings.AGILITY_EXPERIENCE, c.playerAgility);
		} else if (object == 154 || object == 4058) { //tube
			c.startAnimation(844);
			c.getPA().walkTo(0,7);
			gnomeCourse[5] = true;
			if (isDoneGnome())
				giveReward(1);
			c.getPA().addSkillXP(EXP[6] * Settings.AGILITY_EXPERIENCE, c.playerAgility);		
		} else if (object == 2295) {
			c.playerSE = 0x328;//walk
			c.playerSEW = 762;//walk
			c.isRunning = false;
			if (objectX > c.getX())
				c.getPA().walkTo(1,0);
			else if (objectX < c.getX())
				c.getPA().walkTo(-1,0);
			c.getPA().walkTo(0,-7);
			gnomeCourse[0] = true;
			c.getPA().addSkillXP(EXP[0] * Settings.AGILITY_EXPERIENCE, c.playerAgility);
		} else if (object == 2285 && c.heightLevel == 0) {
			c.startAnimation(828);
			c.getPA().movePlayer(c.getX(), c.getY()-2, 1);
			gnomeCourse[1] = true;
			c.getPA().addSkillXP(EXP[1] * Settings.AGILITY_EXPERIENCE, c.playerAgility);
		} else if (object == 2313 && c.heightLevel == 1) {
			c.startAnimation(828);
			c.getPA().movePlayer(c.getX(), c.getY()-2, 2);
			gnomeCourse[2] = true;
			c.getPA().addSkillXP(EXP[2] * Settings.AGILITY_EXPERIENCE, c.playerAgility);
		} else if (object == 2312) {
			c.getPA().walkTo(6,0);
			c.getPA().addSkillXP(EXP[3] * Settings.AGILITY_EXPERIENCE, c.playerAgility);
		} else if (object == 2314) {
			c.getPA().movePlayer(c.getX(), c.getY(), 0);
			gnomeCourse[3] = true;
			c.getPA().addSkillXP(EXP[4] * Settings.AGILITY_EXPERIENCE, c.playerAgility);
		}
	}
	
	private void giveReward(int level) {
		c.sendMessage("You have completed the course and have been given " + level + " tickets.");
		c.getItems().addItem(2996,level);
		if (level == 1)
			c.getPA().addSkillXP(EXP[EXP.length-1] * Settings.AGILITY_EXPERIENCE, c.playerAgility);
		for (int j = 0; j < gnomeCourse.length; j++)
			gnomeCourse[j] = false;
	}	
	private boolean isDoneGnome() {		
		return gnomeCourse[0] && gnomeCourse[1] && gnomeCourse[2] && gnomeCourse[3] && gnomeCourse[4] && gnomeCourse[5];
		//return false;
	}
	
	public void appendObstacles(int objectType) {
	switch(objectType) {
	case 2309:
			if (c.objectX == 2998 && c.objectY == 3917 && c.playerLevel[16] >= 50) {
				c.getPA().movePlayer(2998, 3917, 0);
			} else { c.sendMessage("You need level 50 agility to access this agility course");
			}
		break;
		case 2308:
			if (c.objectX == 2997 && c.objectY == 3931 && c.playerLevel[16] >= 50) {
				c.getPA().movePlayer(2997, 3932, 0);
			} else { c.sendMessage("You need level 50 agility to access this agility course");
			}
		break;
		case 2307:
			if (c.objectX == 2998 && c.objectY == 3931 && c.playerLevel[16] >= 50) {
				c.getPA().movePlayer(2998, 3932, 0);
			} else { c.sendMessage("You need level 50 agility to access this agility course");
			}
		break;
		case 2465:
			c.getPA().movePlayer(3094, 3469, 0);
		break;
		case 2115:
			if(c.playerLevel[16] >= 30) {
				c.getPA().movePlayer(2546, 3569, 0);
			} else { c.sendMessage("You need level 30 agility to access this agility course");
			}
		break;
		case 2116:
			if(c.playerLevel[16] >= 30) {
				c.getPA().movePlayer(2546, 3570, 0);
			} else { c.sendMessage("You need level 30 agility to access this agility course");
			}
		break;
		case 2287:
			if(c.playerLevel[16] >= 30) {
				c.getPA().movePlayer(2552, 3558, 0);
			} else { c.sendMessage("You need level 30 agility to access this agility course");
			}
		break;

case 2282: //ropeswing
		if (c.barbObsticle == 0 && c.playerLevel[16] >= 30) {
			c.getPA().movePlayer(2551, 3549, 0);
                                c.getPA().addSkillXP(122, c.playerAgility);
                                c.barbObsticle = 1;
		} else if (c.playerLevel[16] >= 30) {
			c.getPA().movePlayer(2551, 3549, 0);
                                c.getPA().addSkillXP(1200, c.playerAgility);
		} else {
			c.sendMessage("You need 30 Agility to attempt this obstacle");
		}
break;
case 2294: // ballance beam
		if (c.barbObsticle == 1 && c.playerLevel[16] >= 30) {
			c.obsticle(762, 1, -9, 0, 5000, 2200, "You passed the obsticle succesfully.");
			c.barbObsticle = 2;
		} else if (c.playerLevel[16] >= 30) {
		c.obsticle(762, 1, -9, 0, 5000, 2200, "You passed the obsticle succesfully.");
		} else {
			c.sendMessage("You need 30 Agility to attempt this obstacle");
		}
break;
case 2284: //net
		if (c.barbObsticle == 2) {
			c.agilityDelay(828, 2537, 3546, 1, 1, 1200, "You climbed the nets succesfully.");
			c.barbObsticle = 3;
		} else if (c.playerLevel[16] >= 30) {
			c.agilityDelay(828, 2537, 3546, 1, 1, 1200, "You climbed the nets succesfully.");
		} else {
			c.sendMessage("You need 30 Agility to attempt this obstacle");
		}
break;
case 2302: //balance ledge
		if (c.barbObsticle == 3 && c.playerLevel[16] >= 30) {
			c.getPA().movePlayer(2532, 3547, 1);
                                c.getPA().addSkillXP(1200, c.playerAgility);
                                c.barbObsticle = 4;
		} else if (c.playerLevel[16] >= 30) {
			c.getPA().movePlayer(2532, 3547, 1);
                                c.getPA().addSkillXP(1200, c.playerAgility);
		} else {
			c.sendMessage("You need 30 Agility to attempt this obstacle");
		}
break;
case 3205: //ladder
		if (c.barbObsticle == 4 && c.playerLevel[16] >= 30) {
			c.getPA().movePlayer(2532, 3546, 0);
                                c.getPA().addSkillXP(700, c.playerAgility);
                                c.barbObsticle = 5;
		} else if (c.playerLevel[16] >= 30) {
			c.getPA().movePlayer(2532, 3546, 0);
                                c.getPA().addSkillXP(700, c.playerAgility);
		} else {
			c.sendMessage("You need 30 Agility to attempt this obstacle");
		}
break;
case 1948: //crumbling wall
		if (c.objectX == 2536) {
			if (c.barbObsticle == 5 && c.playerLevel[16] >= 30) {
				c.getPA().movePlayer(2537, 3553, 0);
                                c.getPA().addSkillXP(700, c.playerAgility);
                                c.barbObsticle = 6;
			} else if (c.playerLevel[16] >= 30) {
				c.getPA().movePlayer(2537, 3553, 0);
                                c.getPA().addSkillXP(700, c.playerAgility);
			} else {
				c.sendMessage("You need 30 Agility to attempt this obstacle");
			}
		} else if (c.objectX == 2539) {
			if (c.barbObsticle == 6 && c.playerLevel[16] >= 30) {
				c.getPA().movePlayer(2540, 3553, 0);
                               	c.getPA().addSkillXP(700, c.playerAgility);
                                c.barbObsticle = 7;
			} else if (c.playerLevel[16] >= 30) {
				c.getPA().movePlayer(2540, 3553, 0);
                               	c.getPA().addSkillXP(700, c.playerAgility);
			} else {
				c.sendMessage("You need 30 Agility to attempt this obstacle");
			}
		} else if (c.objectX == 2542) {
			if (c.barbObsticle == 7 && c.playerLevel[16] >= 30) {
				c.getPA().movePlayer(2543, 3553, 0);
                                c.getPA().addSkillXP(700, c.playerAgility);
                                c.getPA().addSkillXP(4000, c.playerAgility);
				c.sendMessage("Congratulations on successfully completing the Barbarian agility course");
					if (c.getItems().freeSlots() > 0) {
						c.getItems().addItem(2996, 2);
                                		c.barbObsticle = 0;
					} else {
						c.sendMessage("You do not have space in your inventory");
						c.getItems().createGroundItem(2996, c.getX(), c.getY(), 2);
		                        	c.barbObsticle = 0;
					}
				return;
			} else if (c.playerLevel[16] >= 30) {
				c.getPA().movePlayer(2543, 3553, 0);
                                c.getPA().addSkillXP(700, c.playerAgility);
			} else {
				c.sendMessage("You need 30 Agility to attempt this obstacle");
			}
		}
break;

case 2288: //pipe
		if (c.absX >= 3004 && c.absX <= 3005 && c.absY >= 3937 && c.absY <= 3938 && c.wildObsticle == 0 && c.playerLevel[16] >= 50) {
			c.obsticle(844, 1, 0, 13, 5000, 5000, "You pulled yourself through the pipes.");
			c.wildObsticle = 1;
		} else if (c.absX >= 3004 && c.absX <= 3005 && c.absY >= 3937 && c.absY <= 3938 && c.playerLevel[16] >= 50) {
			c.obsticle(844, 1, 0, 13, 5000, 5000, "You pulled yourself through the pipes.");
		}
break;
case 2283: // ropeswing
		if (c.absX >= 3004 && c.absX <= 3006 && c.absY >= 3952 && c.absY <= 3953 && c.wildObsticle == 1 && c.playerLevel[16] >= 50) {
			c.getPA().movePlayer(3005, 3958, 0);
                                c.getPA().addSkillXP(1600, c.playerAgility);
                                c.wildObsticle = 2;
		} else if (c.absX >= 3004 && c.absX <= 3006 && c.absY >= 3952 && c.absY <= 3953 && c.playerLevel[16] >= 50) {
			c.getPA().movePlayer(3005, 3958, 0);
                                c.getPA().addSkillXP(1600, c.playerAgility);
		}
			
break;
case 2311: // stepping stones
                        if (c.absX == 3002 && c.absY == 3960 && c.wildObsticle == 2 && c.playerLevel[16] >= 50) {
                                c.obsticle(762, 1, -6, 0, 5000, 3000, "You passed the obsticle succesfully.");
                                c.wildObsticle = 3;
                        } else if (c.absX == 3002 && c.absY == 3960 && c.playerLevel[16] >= 50) {
                                c.obsticle(762, 1, -6, 0, 5000, 3000, "You passed the obsticle succesfully.");
                        }
                break;
case 2297: // ballance beam
                        if (c.absX >= 3001 && c.absX <= 3002 && c.absY >= 3945 && c.absY <= 3946 && c.wildObsticle == 3 && c.playerLevel[16] >= 50) {
                                c.obsticle(762, 1, -7, 0, 5000, 3000, "You passed the obsticle succesfully.");
                                c.wildObsticle = 4;
                        } else if (c.absX >= 3001 && c.absX <= 3002 && c.absY >= 3945 && c.absY <= 3946 && c.playerLevel[16] >= 50) {
                                c.obsticle(762, 1, -7, 0, 5000, 3000, "You passed the obsticle succesfully.");
                        }
                break;
                case 2328: //rocks
                        if (c.absX >= 2993 && c.absX <= 2996 && c.absY == 3937 && c.wildObsticle == 4 && c.playerLevel[16] >= 50) {
                                c.agilityDelay(828, 2995, 3932, 0, 1, 5000, "You climbed the nets succesfully.");
                                c.getPA().addSkillXP(7000, c.playerAgility);
				c.sendMessage("Congratulations on successfully completing the Wilderness agility course");
				if (c.getItems().freeSlots() > 0) {
						c.getItems().addItem(2996, 4);
                                		c.wildObsticle = 0;
					} else {
						c.sendMessage("You do not have space in your inventory");
						c.getItems().createGroundItem( 2996, c.getX(), c.getY(), 4);
		                                c.wildObsticle = 0;
					}
				
					return;
                        } else if (c.absX >= 2993 && c.absX <= 2996 && c.absY == 3937 && c.playerLevel[16] >= 50) {
                                c.agilityDelay(828, 2995, 3932, 0, 1, 5000, "You climbed the nets succesfully.");
                        }
                break;

case 2295: // log balance
                        if (c.absX == 2474 && c.absY == 3436 && c.gnomeObsticle == 0) {
                                c.obsticle(762, 1, 0, -7, 5000, 1000, "You passed the obsticle succesfully.");
                                c.gnomeObsticle = 1;
                        } else if (c.absX == 2474 && c.absY == 3436) {
                                c.obsticle(762, 1, 0, -7, 5000, 1000, "You passed the obsticle succesfully.");
                        }
                break;
                case 2285: //net
                        if (c.gnomeObsticle == 1) {
                                c.agilityDelay(828, 2473, 3424, 1, 1, 500, "You climbed the nets succesfully.");
                                c.gnomeObsticle = 2;
                        } else {
                                c.agilityDelay(828, 2473, 3424, 1, 1, 500, "You climbed the nets succesfully.");
                        }
                break;
                case 2313: //tree
                        if (c.gnomeObsticle == 2) {
                                c.agilityDelay(828, 2473, 3420, 2, 1, 300, "You climbed the tree branch succesfully.");
                                c.gnomeObsticle = 3;
                        } else {
                                c.agilityDelay(828, 2473, 3420, 2, 1, 300, "You climbed the tree branch succesfully.");
                        }
                break;
                
                case 2312: //rope
                        if (c.absX == 2477 && c.absY == 3420 && c.gnomeObsticle == 3) {
                                c.obsticle(762, 1, 7, 0, 5000, 1000, "You passed the obsticle succesfully.");
                                c.gnomeObsticle = 4;
                        }
                        if (c.absX == 2477 && c.absY == 3420) {
                                c.obsticle(762, 1, 7, 0, 5000, 1000, "You passed the obsticle succesfully.");
                        }
                break;
                
                case 2314: //tree branch
                        if (c.gnomeObsticle == 4) {
                                c.agilityDelay(828, 2487, 3421, 0, 0, 1100, "You climbed the tree branch succesfully.");
                                c.gnomeObsticle = 5;
                        } else {
                                c.agilityDelay(828, 2487, 3421, 0, 0, 1100, "You climbed the tree branch succesfully.");
                        }
                break;
                case 2286: //obstacle net
                        if (c.absX >= 2483 && c.absX <= 2488 && c.absY == 3425) {
                                c.agilityDelay(3063, c.absX, c.absY + 2, 0, 1, 300, "You climbed the nets succesfully.");
                                c.turnPlayerTo(c.objectX, c.objectY);
                        }
                        if (c.absX >= 2483 && c.absX <= 2488 && c.absY == 3425 && c.gnomeObsticle == 5) {
                                c.agilityDelay(3063, c.absX, c.absY + 2, 0, 1, 300, "You climbed the nets succesfully.");
                                c.turnPlayerTo(c.objectX, c.objectY);
                                c.gnomeObsticle = 6;
                        }
                break;
                
                case 154: //pipes (last object course)
                        if (c.absX == 2484 && c.absY == 3430 && c.gnomeObsticle == 6) {
                                c.obsticle(844, 1, 0, 7, 5000, 1000, "You Completed the gnome course succesfully!");
                                c.getPA().addSkillXP(3000, c.playerAgility);
				c.sendMessage("Congratulations on successfully completing the gnome agility course");
				if (c.getItems().freeSlots() > 0) {
						c.getItems().addItem(2996, 1);
                                		c.gnomeObsticle = 0;
					} else {
						c.sendMessage("You do not have space in your inventory");
						c.getItems().createGroundItem(2996, c.getX(), c.getY(), 1);
		                                c.gnomeObsticle = 0;
					}
				
					return;
                        }
                        if (c.absX == 2484 && c.absY == 3430) {
                                c.obsticle(844, 1, 0, 7, 5000, 1000, "You pulled yourself through the pipes.");
                        }
                break;
                case 4084:
                        if (c.absX == 2487 && c.absY == 3430 && c.gnomeObsticle == 6) {
                                c.obsticle(844, 1, 0, 7, 5000, 1000, "You Completed the gnome course succesfully!");
                                c.getPA().addSkillXP(3000, c.playerAgility);
				c.sendMessage("Congratulations on successfully completing the gnome agility course");
				if (c.getItems().freeSlots() > 0) {
						c.getItems().addItem(2996, 1);
                                		c.gnomeObsticle = 0;
					} else {
						c.sendMessage("You do not have space in your inventory");
						c.getItems().createGroundItem(2996, c.getX(), c.getY(), 1);
		                                c.gnomeObsticle = 0;
					}
				
					return;
                        }
                        if (c.absX == 2487 && c.absY == 3430) {
                                c.obsticle(844, 1, 0, 7, 5000, 1000, "You pulled yourself through the pipes.");
                        }
                break;
				}
			}
}