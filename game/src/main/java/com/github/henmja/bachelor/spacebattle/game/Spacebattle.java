package com.github.henmja.bachelor.spacebattle.game;

import com.github.henmja.bachelor.spacebattle.Application;
import com.github.henmja.bachelor.spacebattle.network.GameSession;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

public class Spacebattle extends BasicGame implements ActionListener {
	private URL shieldCrash = this.getClass().getClassLoader()
			.getResource("sounds/shieldCrash.wav");
	private URL shieldSound = this.getClass().getClassLoader()
			.getResource("sounds/Powerup2.wav");
	private URL playerExplosion = this.getClass().getClassLoader()
			.getResource("sounds/explosion.wav");
	private URL hitHurt = this.getClass().getClassLoader()
			.getResource("sounds/Hit_Hurt.wav");
	private URL laserSound = this.getClass().getClassLoader()
			.getResource("sounds/laser.wav");
	private URL gameMusic = this.getClass().getClassLoader()
			.getResource("sounds/SpaceBattle.wav");
	private Clip musicClip;

	private int dx, dy, dx2, dy2, dx3, dy3, dx4, dy4, dx5, dy5, dx6, dy6;
	private int[] astShot;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private static final int MARGIN = 32;

	private static final Color WHITE = new Color(238, 239, 239);
	private static final Color GREY = Color.gray;
	private Color PURPLE = new Color(200, 0, 255);
	private Color BROWN = new Color(156, 93, 82);

	private GameSession gameSession;
	private Polygon player1, player2;
	private Circle forceField;
	private Circle[] asteroids;
	private List<Circle> shot = new ArrayList<Circle>();
	private List<Circle> shot2 = new ArrayList<Circle>();
	private List<String> shotDir = new ArrayList<String>();
	private List<String> shotDir2 = new ArrayList<String>();

	private Timer clk, ffClk;
	private boolean newLocation = false;
	private int mvInt = 5;
	private int player1Health = 10;
	private int player2Health = 10;
	private boolean forceField1, forceField2, gameover;
	private int maxX = 1150;
	private int min = 0;
	private int maxY = 560;
	private RandInt xyInts;
	private int limX1, limX2, limX3, limX4, limX5, limX6, limY1, limY2, limY3,
			limY4, limY5, limY6, intersects;
	private Font winFont;
	private TrueTypeFont winTTF;
	private static final int PADDLE_WIDTH = 32;
	private static final int PADDLE_HEIGHT = 128;
	private int verticalCenter = HEIGHT / 2;
	private int right = WIDTH - MARGIN - PADDLE_WIDTH;
	private float pos, pos2;
	private String p1Rot = "up";
	private String p2Rot = "up";
	private boolean player1Shield, player2Shield;
	private Timer musicClk;
	private boolean exploded;
	private int limX7;
	private int limX8;
	private int limX9;
	private int limX10;
	private int limX11;
	private int limX12;
	private int limX13;
	private int limX14;
	private int limX15;
	private double limY7;
	private double limY8;
	private float limY9;
	private int limY10;
	private int limY11;
	private int limY15;
	private int limY14;
	private int limY13;
	private int limY12;
	private boolean dirRemoved;
	private long gameTimeStart;
	private long gameTimeLast;
	private boolean invulnerable;

	public Spacebattle(String title) {
		super(title);
	}

	public void addAllIntervals(int lim1, int lim2, int lim3, int lim4,
			int lim5, int lim6, double d, double e, float f, int max,
			int limX112, int limX122, int limX132, int limX142, int limX152,
			int maxX2) {

		if (e > d && d > f) {
			xyInts = new RandInt(min, lim5);
			xyInts.addInterval(lim6, lim1);
			xyInts.addInterval(lim2, lim3);
			xyInts.addInterval(lim4, max);
		} else if (f > d && d > e) {
			xyInts = new RandInt(min, lim3);
			xyInts.addInterval(lim4, lim1);
			xyInts.addInterval(lim2, lim5);
			xyInts.addInterval(lim6, max);
		} else if (e > f && f > d) {
			xyInts = new RandInt(min, lim1);
			xyInts.addInterval(lim2, lim5);
			xyInts.addInterval(lim6, lim3);
			xyInts.addInterval(lim4, max);
		} else if (d > e && e > f) {
			xyInts = new RandInt(min, lim5);
			xyInts.addInterval(lim6, lim3);
			xyInts.addInterval(lim4, lim1);
			xyInts.addInterval(lim2, max);
		} else if (f > e && e > d) {
			xyInts = new RandInt(min, lim1);
			xyInts.addInterval(lim2, lim3);
			xyInts.addInterval(lim4, lim5);
			xyInts.addInterval(lim6, max);
		} else if (d > f && f > e) {
			xyInts = new RandInt(min, lim3);
			xyInts.addInterval(lim4, lim5);
			xyInts.addInterval(lim6, lim1);
			xyInts.addInterval(lim2, max);
		} else if (d == e && e < f) {
			xyInts = new RandInt(min, lim1);
			xyInts.addInterval(lim2, lim5);
			xyInts.addInterval(lim6, max);
		} else if (d == e && e > f) {
			xyInts = new RandInt(min, lim5);
			xyInts.addInterval(lim6, lim1);
			xyInts.addInterval(lim2, max);
		} else if (f == e && e > d) {
			xyInts = new RandInt(min, lim1);
			xyInts.addInterval(lim2, lim3);
			xyInts.addInterval(lim4, max);
		} else if (f == e && e < d) {
			xyInts = new RandInt(min, lim3);
			xyInts.addInterval(lim4, lim1);
			xyInts.addInterval(lim2, max);
		} else if (f == d && d > e) {
			xyInts = new RandInt(min, lim3);
			xyInts.addInterval(lim4, lim1);
			xyInts.addInterval(lim2, max);
		} else if (f == d && d < e) {
			xyInts = new RandInt(min, lim1);
			xyInts.addInterval(lim2, lim3);
			xyInts.addInterval(lim4, max);
		} else {
			xyInts = new RandInt(min, lim1);
			xyInts.addInterval(lim2, max);
		}
	}

	public int getPlayer1Health() {
		return player1Health;
	}

	public void setPlayer1Health(int player1Health) {
		this.player1Health = player1Health;
	}

	public int getPlayer2Health() {
		return player2Health;
	}

	public void setPlayer2Health(int player2Health) {
		this.player2Health = player2Health;
	}

	public void movePlayer1(float position) {
		movePlayer(player1, position);
		pos = position;
		if (p1Rot == "up") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "down";
		} else if (p1Rot == "left") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "down";
		} else if (p1Rot == "right") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "down";
		}

	}

	public void movePlayer1Up(float position) {
		movePlayerUp(player1, position);
		pos = position;
		if (p1Rot == "down") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "up";
		} else if (p1Rot == "left") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "up";
		} else if (p1Rot == "right") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "up";
		}

	}

	public void movePlayer1Left(float position) {
		movePlayerLeft(player1, position);
		pos = position;
		if (p1Rot == "up") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "left";
		} else if (p1Rot == "down") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "left";
		} else if (p1Rot == "right") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "left";
		}

	}

	public void movePlayer1Right(float position) {
		movePlayerRight(player1, position);
		pos = position;
		if (p1Rot == "up") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "right";
		} else if (p1Rot == "left") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "right";
		} else if (p1Rot == "down") {
			player1 = (Polygon) player1.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							player1.getCenterX(), player1.getCenterY()));
			p1Rot = "right";
		}

	}

	public void movePlayer2(float position) {
		movePlayer(player2, position);
		pos2 = position;
		if (p2Rot == "up") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "down";
		} else if (p2Rot == "left") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "down";
		} else if (p2Rot == "right") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "down";
		}

	}

	public void movePlayer2Up(float position) {
		movePlayerUp(player2, position);
		pos2 = position;
		if (p2Rot == "down") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "up";
		} else if (p2Rot == "left") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "up";
		} else if (p2Rot == "right") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "up";
		}

	}

	public void movePlayer2Left(float position) {
		movePlayerLeft(player2, position);
		pos2 = position;
		if (p2Rot == "up") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "left";
		} else if (p2Rot == "down") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "left";
		} else if (p2Rot == "right") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "left";
		}

	}

	public void movePlayer2Right(float position) {
		movePlayerRight(player2, position);
		pos2 = position;
		if (p2Rot == "up") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "right";
		} else if (p2Rot == "left") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "right";
		} else if (p2Rot == "down") {
			player2 = (Polygon) player2.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							player2.getCenterX(), player2.getCenterY()));
			p2Rot = "right";
		}

	}

	private void movePlayerLeft(Polygon player, float position) {
		if (player.getCenterX() - MARGIN * 2 - position <= 0) {
			position = 0;
			if (position == 0) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(hitHurt);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (position < 0 || position > 1) {
			return;
		}
		player.setX(player.getX() - position * 5);
	}

	private void movePlayerRight(Polygon player, float position) {
		if (player.getCenterX() + MARGIN * 2 + position >= WIDTH) {
			position = 0;
			if (position == 0) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(hitHurt);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (position < 0 || position > 1) {
			return;
		}
		player.setX(player.getX() + position * 5);
	}

	private void movePlayerUp(Polygon player, float position) {
		if (player.getCenterY() - PADDLE_HEIGHT / 2 - position <= 0) {
			position = 0;
			if (position == 0) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(hitHurt);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (position < 0 || position > 1) {
			return;
		}
		player.setY(player.getY() - position * 5);
	}

	private void movePlayer(Polygon player, float position) {
		if (player.getCenterY() + PADDLE_HEIGHT / 2 + position >= HEIGHT) {
			position = 0;
			if (position == 0) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(hitHurt);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (position < 0 || position > 1) {
			return;
		}
		player.setY(player.getY() + position * 5);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		gameTimeStart = System.currentTimeMillis();
		astShot = new int[6];
		try {
			AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(gameMusic);
			musicClip = AudioSystem.getClip();
			musicClip.open(audioIn);
			musicClip.start();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		musicClk = new Timer(60000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (gameover != true) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(gameMusic);
						musicClip = AudioSystem.getClip();
						musicClip.open(audioIn);
						musicClip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		musicClk.start();

		gameSession = Application.getGameSession();
		winFont = new Font(Font.MONOSPACED, Font.BOLD, 48);
		winTTF = new TrueTypeFont(winFont, true);

		player1 = new Polygon();
		player1.addPoint(MARGIN * 2, verticalCenter + PADDLE_HEIGHT / 2);
		player1.addPoint(MARGIN * 2 + PADDLE_WIDTH, verticalCenter
				+ PADDLE_HEIGHT / 2);
		player1.addPoint(MARGIN * 2 + PADDLE_WIDTH / 2, verticalCenter
				- PADDLE_HEIGHT / 4);

		player2 = new Polygon();
		player2.addPoint(right, verticalCenter + PADDLE_HEIGHT / 2);
		player2.addPoint(right - PADDLE_WIDTH, verticalCenter + PADDLE_HEIGHT
				/ 2);
		player2.addPoint(right - PADDLE_WIDTH / 2, verticalCenter
				- PADDLE_HEIGHT / 4);
		asteroids = new Circle[6];
		for (int i = 0; i < 6; i++) {
			asteroids[i] = new Circle(200 * i, 0, PADDLE_WIDTH);
		}
		for (int i = 0; i < 6; i++) {
			Random rand = new Random();
			int randomNum = rand.nextInt((1 - -1) + 1) + (-1);
			if (randomNum == 0) {
				randomNum += 1;
			}
			if (i == 0) {
				dx = randomNum;
				dy = 1;
			} else if (i == 1) {
				dx2 = randomNum;
				dy2 = 1;
			} else if (i == 2) {
				dx3 = randomNum;
				dy3 = 1;
			} else if (i == 3) {
				dx4 = randomNum;
				dy4 = 1;
			} else if (i == 4) {
				dx5 = randomNum;
				dy5 = 1;
			} else if (i == 5) {
				dx6 = randomNum;
				dy6 = 1;
			}
		}
		limX1 = (int) (player1.getCenterX() - player1.getWidth());
		limX2 = (int) (player1.getCenterX() + player1.getWidth());
		limX3 = (int) (player2.getCenterX() - player2.getWidth());
		limX4 = (int) (player2.getCenterX() + player2.getWidth());
		limX5 = (int) (asteroids[0].getCenterX() - asteroids[0].getWidth());
		limX6 = (int) (asteroids[0].getCenterX() + asteroids[0].getHeight());
		limY1 = (int) (player1.getCenterY() - player1.getHeight());
		limY2 = (int) (player1.getCenterY() + player1.getHeight());
		limY3 = (int) (player2.getCenterY() - player2.getHeight());
		limY4 = (int) (player2.getCenterY() + player2.getHeight());
		limY5 = (int) (asteroids[0].getCenterY() - asteroids[0].getWidth());
		limY6 = (int) (asteroids[0].getCenterY() + asteroids[0].getHeight());
		limX6 = (int) (asteroids[1].getCenterX() - asteroids[1].getWidth());
		limX7 = (int) (asteroids[1].getCenterX() + asteroids[1].getHeight());
		limX8 = (int) (asteroids[2].getCenterX() - asteroids[2].getWidth());
		limX9 = (int) (asteroids[2].getCenterX() + asteroids[2].getHeight());
		limX10 = (int) (asteroids[3].getCenterX() - asteroids[3].getWidth());
		limX11 = (int) (asteroids[3].getCenterX() + asteroids[3].getHeight());
		limX12 = (int) (asteroids[4].getCenterX() - asteroids[4].getWidth());
		limX13 = (int) (asteroids[4].getCenterX() + asteroids[4].getHeight());
		limX14 = (int) (asteroids[5].getCenterX() - asteroids[5].getWidth());
		limX15 = (int) (asteroids[5].getCenterX() + asteroids[5].getHeight());
		limY5 = (int) (asteroids[1].getCenterY() - asteroids[1].getWidth());
		limY6 = (int) (asteroids[1].getCenterY() + asteroids[1].getHeight());
		limY5 = (int) (asteroids[2].getCenterY() - asteroids[2].getWidth());
		limY6 = (int) (asteroids[2].getCenterY() + asteroids[2].getHeight());
		limY5 = (int) (asteroids[3].getCenterY() - asteroids[3].getWidth());
		limY6 = (int) (asteroids[3].getCenterY() + asteroids[3].getHeight());
		limY5 = (int) (asteroids[4].getCenterY() - asteroids[4].getWidth());
		limY6 = (int) (asteroids[4].getCenterY() + asteroids[4].getHeight());
		limY5 = (int) (asteroids[5].getCenterY() - asteroids[5].getWidth());
		limY6 = (int) (asteroids[5].getCenterY() + asteroids[5].getHeight());
		addAllIntervals(limX1, limX2, limX3, limX4, limX5, limX6, limX7, limX8,
				limX9, limX10, limX11, limX12, limX13, limX14, limX15, maxX);
		int randX = xyInts.getRandom();
		addAllIntervals(limY1, limY2, limY3, limY4, limY5, limY6, limY7, limY8,
				limY9, limY10, limY11, limY12, limY13, limY14, limY15, maxY);
		int randY = xyInts.getRandom();
		forceField = new Circle(randX, randY, PADDLE_WIDTH);

		clk = new Timer(mvInt, this);
		clk.start();

		ffClk = new Timer(3000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newLocation = !newLocation;
				if (newLocation) {
					addAllIntervals(limX1, limX2, limX3, limX4, limX5, limX6,
							limX7, limX8, limX9, limX10, limX11, limX12,
							limX13, limX14, limX15, maxX);

					int randX = xyInts.getRandom();
					addAllIntervals(limY1, limY2, limY3, limY4, limY5, limY6,
							limY7, limY8, limY9, limY10, limY11, limY12,
							limY13, limY14, limY15, maxY);

					int randY = xyInts.getRandom();

					forceField = new Circle(randX, randY, PADDLE_WIDTH);

					forceField1 = false;
					forceField2 = false;
					player1Shield = false;
					player2Shield = false;

				}
			}

		});
		ffClk.start();

	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();

		if (input.isKeyPressed(Input.KEY_Q)) {
			container.exit();
		}
		if (player1Shield) {
			if (p1Rot == "left") {
				forceField = new Circle(player1.getCenterX() - 12,
						player1.getCenterY(), PADDLE_WIDTH);
			} else if (p1Rot == "right") {
				forceField = new Circle(player1.getCenterX() + 12,
						player1.getCenterY(), PADDLE_WIDTH);
			} else if (p1Rot == "up") {
				forceField = new Circle(player1.getCenterX(),
						player1.getCenterY() - 12, PADDLE_WIDTH);
			} else if (p1Rot == "down") {
				forceField = new Circle(player1.getCenterX(),
						player1.getCenterY() + 12, PADDLE_WIDTH);
			}
		}
		if (player2Shield) {
			if (p2Rot == "right") {
				forceField = new Circle(player2.getCenterX() + 12,
						player2.getCenterY(), PADDLE_WIDTH);
			}

			else if (p2Rot == "left") {
				forceField = new Circle(player2.getCenterX() - 12,
						player2.getCenterY(), PADDLE_WIDTH);
			} else if (p2Rot == "up") {
				forceField = new Circle(player2.getCenterX(),
						player2.getCenterY() - 12, PADDLE_WIDTH);
			} else if (p2Rot == "down") {
				forceField = new Circle(player2.getCenterX(),
						player2.getCenterY() + 12, PADDLE_WIDTH);
			}
		}
		gameover = player1Health <= 0 || player2Health <= 0;

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		container.setShowFPS(false);
		if (!gameover) {
			g.setColor(WHITE);
			if (3 - ((gameTimeLast - gameTimeStart) / 1000) >= 0) {
				g.drawString(""
						+ (3 - ((gameTimeLast - gameTimeStart) / 1000)),
						WIDTH / 2, 20);
			}
			if ((3 - ((gameTimeLast - gameTimeStart) / 1000)) <= 0
					&& (6 - ((gameTimeLast - gameTimeStart) / 1000)) >= 0) {
				g.drawString("Fight!", WIDTH / 2, HEIGHT / 2);
			}
			if ((3 - ((gameTimeLast - gameTimeStart) / 1000)) <= 0) {
				invulnerable = false;
			} else {
				invulnerable = true;
			}
			g.setColor(GREY);
			g.fill(player1);
			g.setColor(WHITE);
			g.fill(player2);
			g.setColor(PURPLE);
			g.fill(forceField);
			for (int i = 0; i < shot.size(); i++) {
				g.setColor(Color.yellow);
				g.fill(shot.get(i));

			}
			for (int i = 0; i < shot2.size(); i++) {
				g.setColor(Color.yellow);
				g.fill(shot2.get(i));

			}
			for (int i = 0; i < 6; i++) {
				if (!player1.intersects(asteroids[i])
						&& !player2.intersects(asteroids[i])) {
					g.setColor(BROWN);
					g.fill(asteroids[i]);
				}
			}
			g.setColor(Color.white);
			g.fillRect(0, 0, 200, 40);
			g.setColor(Color.red);
			g.fillRect(0, 0, player1Health * 20, 20);
			g.setColor(Color.black);
			g.drawString("PLAYER 1 HP", 50, 20);
			g.setColor(Color.white);
			g.fillRect(1080, 0, 200, 40);
			g.setColor(Color.red);
			g.fillRect(1080, 0, player2Health * 20, 20);
			g.setColor(Color.black);
			g.drawString("PLAYER 2 HP", 1130, 20);
			Random rand = new Random();
			int randomNumStart = rand.nextInt(4) + 1;
			for (int i = 0; i < 6; i++) {
				if (player1.intersects(asteroids[i]) && player1Shield) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(shieldCrash);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
				if (player1.intersects(asteroids[i]) && !player1Shield
						&& !invulnerable) {
					player1Health -= 1;
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(hitHurt);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}

				}
				if (player2.intersects(asteroids[i]) && player2Shield) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(shieldCrash);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
				if (player2.intersects(asteroids[i]) && !player2Shield
						&& !invulnerable) {
					player2Health -= 1;
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(hitHurt);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
				if (player1.intersects(asteroids[i])
						|| player2.intersects(asteroids[i])
						|| asteroids[i].getX() <= 0 - 3 * asteroids[i]
								.getWidth() / 2
						|| asteroids[i].getX() >= 1280
						|| asteroids[i].getY() <= 0 - 3 * asteroids[i]
								.getHeight() / 2 || asteroids[i].getY() >= 720
						|| astShot[i] == 1) {
					addAllIntervals(limX1, limX2, limX3, limX4, limX5, limX6,
							limX7, limX8, limX9, limX10, limX11, limX12,
							limX13, limX14, limX15, maxX);
					int randX = xyInts.getRandom();
					addAllIntervals(limY1, limY2, limY3, limY4, limY5, limY6,
							limY7, limY8, limY9, limY10, limY11, limY12,
							limY13, limY14, limY15, maxY);
					int randY = xyInts.getRandom();
					if (randomNumStart == 1) {
						asteroids[i] = new Circle(randX,
								0 - asteroids[i].getHeight() / 2, PADDLE_WIDTH);
					} else if (randomNumStart == 2) {
						asteroids[i] = new Circle(
								0 - asteroids[i].getWidth() / 2, randY,
								PADDLE_WIDTH);
					} else if (randomNumStart == 3) {
						asteroids[i] = new Circle(
								1280 + asteroids[i].getWidth() / 2, randY,
								PADDLE_WIDTH);
					} else {
						asteroids[i] = new Circle(randX,
								720 + asteroids[i].getWidth() / 2, PADDLE_WIDTH);
					}

					int randomNum = rand.nextInt((1 - -1) + 1) + (-1);
					if (randomNum == 0) {
						randomNum -= 1;
					}

					int randomNumY = rand.nextInt((1 - -1) + 1) + (-1);
					if (randomNumY == 0) {
						randomNumY += 1;
					}
					if (i == 0) {
						dx = randomNum;
						dy = randomNumY;
					} else if (i == 1) {
						dx2 = randomNum;
						dy2 = randomNumY;
					}
					if (i == 2) {
						dx3 = randomNum;
						dy3 = randomNumY;
					}
					if (i == 3) {
						dx4 = randomNum;
						dy4 = randomNumY;
					}
					if (i == 4) {
						dx5 = randomNum;
						dy5 = randomNumY;
					}
					if (i == 5) {
						dx6 = randomNum;
						dy6 = randomNumY;
					}

					astShot[i] = 0;
				}
			}

		} else {
			if (!exploded) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(playerExplosion);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			exploded = true;
			musicClip.stop();

			String message = (player1Health > player2Health ? gameSession
					.getPlayer1Username() : gameSession.getPlayer2Username())
					+ " wins!";

			int messageWidth = winTTF.getWidth(message);
			int messageHeight = winTTF.getHeight(message);

			g.setFont(winTTF);
			g.setColor(WHITE);
			g.drawString(message, (WIDTH - messageWidth) / 2,
					(HEIGHT - messageHeight) / 2);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameTimeLast = System.currentTimeMillis();
		move();
		for (int i = 0; i < 6; i++) {
			if (asteroids[i].intersects(forceField)) {
				astShot[i] = 1;
			}
		}
		for (int i = 0; i < shot.size(); i++) {
			if (shotDir.get(i) == "up") {
				shot.get(i).setCenterY(shot.get(i).getCenterY() - 6);
			} else if (shotDir.get(i) == "down") {
				shot.get(i).setCenterY(shot.get(i).getCenterY() + 6);
			} else if (shotDir.get(i) == "left") {
				shot.get(i).setCenterX(shot.get(i).getCenterX() - 6);
			} else if (shotDir.get(i) == "right") {
				shot.get(i).setCenterX(shot.get(i).getCenterX() + 6);
			}
			if (shot.get(i).intersects(asteroids[0])
					|| shot.get(i).intersects(asteroids[1])
					|| shot.get(i).intersects(asteroids[2])
					|| shot.get(i).intersects(asteroids[3])
					|| shot.get(i).intersects(asteroids[4])
					|| shot.get(i).intersects(asteroids[5])
					|| shot.get(i).intersects(forceField)) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(hitHurt);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			if (shot.get(i).intersects(asteroids[0])) {
				shot.remove(i);
				shotDir.remove(i);
				astShot[0] = 1;
			} else if (shot.get(i).intersects(asteroids[1])) {
				shot.remove(i);
				shotDir.remove(i);
				astShot[1] = 1;
			} else if (shot.get(i).intersects(asteroids[2])) {
				shot.remove(i);
				shotDir.remove(i);
				astShot[2] = 1;
			} else if (shot.get(i).intersects(asteroids[3])) {
				shot.remove(i);
				shotDir.remove(i);
				astShot[3] = 1;
			} else if (shot.get(i).intersects(asteroids[4])) {
				shot.remove(i);
				shotDir.remove(i);
				astShot[4] = 1;
			} else if (shot.get(i).intersects(asteroids[5])) {
				shot.remove(i);
				shotDir.remove(i);
				astShot[5] = 1;
			} else if (shot.get(i).intersects(forceField)) {
				shot.remove(i);
				shotDir.remove(i);
			}

		}
		for (int i = 0; i < shot2.size(); i++) {
			if (shotDir2.get(i) == "up") {
				shot2.get(i).setCenterY(shot2.get(i).getCenterY() - 6);
			} else if (shotDir2.get(i) == "down") {
				shot2.get(i).setCenterY(shot2.get(i).getCenterY() + 6);
			} else if (shotDir2.get(i) == "left") {
				shot2.get(i).setCenterX(shot2.get(i).getCenterX() - 6);
			} else if (shotDir2.get(i) == "right") {
				shot2.get(i).setCenterX(shot2.get(i).getCenterX() + 6);
			}
			if (shot2.get(i).intersects(asteroids[0])
					|| shot2.get(i).intersects(asteroids[1])
					|| shot2.get(i).intersects(asteroids[2])
					|| shot2.get(i).intersects(asteroids[3])
					|| shot2.get(i).intersects(asteroids[4])
					|| shot2.get(i).intersects(asteroids[5])
					|| shot2.get(i).intersects(forceField)) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(hitHurt);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			if (shot2.get(i).intersects(asteroids[0])) {
				shot2.remove(i);
				shotDir2.remove(i);
				astShot[0] = 1;
			} else if (shot2.get(i).intersects(asteroids[1])) {
				shot2.remove(i);
				shotDir2.remove(i);
				astShot[1] = 1;
			} else if (shot2.get(i).intersects(asteroids[2])) {
				shot2.remove(i);
				shotDir2.remove(i);
				astShot[2] = 1;
			} else if (shot2.get(i).intersects(asteroids[3])) {
				shot2.remove(i);
				shotDir2.remove(i);
				astShot[3] = 1;
			} else if (shot2.get(i).intersects(asteroids[4])) {
				shot2.remove(i);
				shotDir2.remove(i);
				astShot[4] = 1;
			} else if (shot2.get(i).intersects(asteroids[5])) {
				shot2.remove(i);
				shotDir2.remove(i);
				astShot[5] = 1;
			} else if (shot2.get(i).intersects(forceField)) {
				shot2.remove(i);
				shotDir2.remove(i);
			}

		}
		for (int i = 0; i < shot.size(); i++) {
			if ((shot.get(i).intersects(player2))
					|| shot.get(i).getCenterX() > right
							+ shot.get(i).getRadius() * 4
					|| shot.get(i).getCenterX() < 0 - shot.get(i).getRadius() * 2
					|| shot.get(i).getCenterY() < 0 - shot.get(i).getRadius() * 2
					|| shot.get(i).getCenterY() > verticalCenter * 2
							+ shot.get(i).getRadius() * 2) {
				if (shot.get(i).intersects(player2) && !player2Shield
						&& !invulnerable) {
					player2Health -= 1;
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(hitHurt);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
				if (shot.get(i).intersects(player2) && player2Shield) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(shieldCrash);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
				shot.remove(shot.get(i));
				shotDir.remove(i);
			}
		}
		for (int i = 0; i < shot2.size(); i++) {
			if ((shot2.get(i).intersects(player1))
					|| shot2.get(i).getCenterX() > right
							+ shot2.get(i).getRadius() * 4
					|| shot2.get(i).getCenterX() < 0 - shot2.get(i).getRadius() * 2
					|| shot2.get(i).getCenterY() < 0 - shot2.get(i).getRadius() * 2
					|| shot2.get(i).getCenterY() > verticalCenter * 2
							+ shot2.get(i).getRadius() * 2) {
				if (shot2.get(i).intersects(player1) && !player1Shield
						&& !invulnerable) {
					player1Health -= 1;
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(hitHurt);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
				if (shot2.get(i).intersects(player1) && player1Shield) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(shieldCrash);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
				shot2.remove(shot2.get(i));
				shotDir2.remove(i);
			}
		}
		collisionDetection();
		forcefieldEngage();
	}

	public void collisionDetection() {
		if ((player1Shield || player2Shield) && player1.intersects(player2)) {
			try {
				AudioInputStream audioIn = AudioSystem
						.getAudioInputStream(shieldCrash);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}

		}
		if (!player1.intersects(player2)) {
			intersects = 1;
		} else {
			try {
				AudioInputStream audioIn = AudioSystem
						.getAudioInputStream(hitHurt);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
			if (player1.getX() < player2.getX()) {
				player1.setX(player1.getX() - 40);
				player2.setX(player2.getX() + 40);
			} else if (player2.getX() < player1.getX()) {
				player1.setX(player1.getX() + 40);
				player2.setX(player2.getX() - 40);
			}
			if (player1.getY() < player2.getY()) {
				player1.setY(player1.getY() - 40);
				player2.setY(player2.getY() + 40);
			} else if (player2.getY() < player1.getY()) {
				player1.setY(player1.getY() + 40);
				player2.setY(player2.getY() - 40);
			}
			if (intersects == 1 && !invulnerable) {
				if (!forceField1 == true) {
					player1Health -= 1;
				}
				if (!forceField2 == true) {
					player2Health -= 1;
				}
				intersects = 0;
			}
		}

	}

	public void forcefieldEngage() {
		if (player1.intersects(forceField) && forceField2 == false) {
			if (forceField1 == false) {
				player1Shield = true;
				ffClk.restart();
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(shieldSound);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			forceField1 = true;

		}
		if (player2.intersects(forceField) && forceField1 == false) {
			if (forceField2 == false) {
				player2Shield = true;
				forceField2 = true;
				ffClk.restart();
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(shieldSound);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	public void p1Shoot() {
		try {
			AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(laserSound);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		String dir = p1Rot;
		shotDir.add(dir);
		if (dir == "up") {
			shot.add(new Circle(player1.getCenterX(), player1.getCenterY()
					- player1.getHeight() / 2 - 16, 16));
		} else if (dir == "down") {
			shot.add(new Circle(player1.getCenterX(), player1.getCenterY()
					+ player1.getHeight() / 2 + 16, 16));
		} else if (dir == "left") {
			shot.add(new Circle(player1.getCenterX() - player1.getWidth() / 2
					- 16, player1.getCenterY(), 16));
		} else if (dir == "right") {
			shot.add(new Circle(player1.getCenterX() + player1.getWidth() / 2
					+ 16, player1.getCenterY(), 16));
		}

	}

	public void p2Shoot() {
		try {
			AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(laserSound);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		String dir = p2Rot;
		shotDir2.add(dir);
		if (dir == "up") {
			shot2.add(new Circle(player2.getCenterX(), player2.getCenterY()
					- player2.getHeight() / 2 - 16, 16));
		} else if (dir == "down") {
			shot2.add(new Circle(player2.getCenterX(), player2.getCenterY()
					+ player2.getHeight() / 2 + 16, 16));
		} else if (dir == "left") {
			shot2.add(new Circle(player2.getCenterX() - player2.getWidth() / 2
					- 16, player2.getCenterY(), 16));
		} else if (dir == "right") {
			shot2.add(new Circle(player2.getCenterX() + player2.getWidth() / 2
					+ 16, player2.getCenterY(), 16));
		}

	}

	public void move() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < 6; i++) {
			if (i == 0) {
				x = dx;
				y = dy;
			} else if (i == 1) {
				x = dx2;
				y = dy2;
			} else if (i == 2) {
				x = dx3;
				y = dy3;
			} else if (i == 3) {
				x = dx4;
				y = dy4;
			} else if (i == 4) {
				x = dx5;
				y = dy5;
			} else if (i == 5) {
				x = dx6;
				y = dy6;
			}
			asteroids[i].setX(asteroids[i].getX() + x);
			asteroids[i].setY(asteroids[i].getY() + y);
		}
	}

}
