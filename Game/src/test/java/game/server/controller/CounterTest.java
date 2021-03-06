package game.server.controller;

import static org.junit.Assert.*;
import game.server.model.Dinar;
import game.server.model.Game;
import game.server.model.InitialCard;
import game.server.model.Player;
import game.server.model.Region;
import game.server.model.Sheep;
import game.server.model.SheepBlack;
import game.server.model.Shepherd;
import game.server.model.StandardCard;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.rmi.CORBA.Util;

import org.junit.Test;

/**
 * Main game score counter-test class
 * 
 * @author Dario
 *
 */
public class CounterTest {
		
	/**
	 * private constructor test
	 */
	@Test
	public void constructorTest(){
		try {
			Constructor<Util> c = Util.class.getDeclaredConstructor();
			c.setAccessible(true);
			c.newInstance();
		} catch (Exception e) {;}
		
		assertTrue(true);
	}
	
	/**
	 * Check if the score compute of each player is correct
	 */
	@Test
	public void doStatsTest(){
		
		int checktot = 2;
		
		List<Shepherd> sheplist = new ArrayList<Shepherd>();
		sheplist.add(new Shepherd(0, "red"));
		
		List<Dinar> dinlist = new ArrayList<Dinar>();
		dinlist.add(new Dinar());
		
		Player pl = new Player("testplayer", sheplist, dinlist,new InitialCard("Hill", 0));
		pl.addCardToOwned(new StandardCard("Hill", 0, 0));
		
		
		List<List<Region>> regType = new ArrayList<List<Region>>();
		List<Region> reglist = new ArrayList<Region>();
		reglist.add(new Region(0, new Sheep(), "Hill"));
		regType.add(reglist);
		
		Counter.doStats(pl, null, regType);
		
		assertEquals(checktot, pl.getScore());
	}
	
	/**
	 * Check if the score compute of each player is correct and even for black sheep and plot value
	 */
	@Test
	public void doStatsTestB(){
		
		int checktot = 4;
		
		List<Shepherd> sheplist = new ArrayList<Shepherd>();
		sheplist.add(new Shepherd(0, "red"));
		
		List<Dinar> dinlist = new ArrayList<Dinar>();
		dinlist.add(new Dinar());
		
		Player pl = new Player("testplayer", sheplist, dinlist,new InitialCard("Hill", 0));
		pl.addCardToOwned(new StandardCard("Hill", 0, 0));
		
		
		List<List<Region>> regType = new ArrayList<List<Region>>();
		List<Region> reglist = new ArrayList<Region>();
		reglist.add(new Region(0, new Sheep(), "Hill"));
		reglist.get(0).addANewBlackSheep(new SheepBlack());
		regType.add(reglist);
		
		Counter.doStats(pl, null, regType);
		
		assertEquals(checktot, pl.getScore());
	}
	
	/**
	 * Checking if sorting return a correct value
	 */
	@Test
	public void playerScoreSortingTest(){
		
		List<Player> playList = new ArrayList<Player>();
		List<List<Integer>> checkScore = new ArrayList<List<Integer>>();
		
		Player pl0 = new Player();
		Player pl1 = new Player();
		
		pl0.setScore(5);
		pl1.setScore(10);
		
		playList.add(pl0);
		playList.add(pl1);
		
		List<Integer> score1 = new ArrayList<Integer>();
		score1.add(1);
		score1.add(10);
		List<Integer> score0 = new ArrayList<Integer>();
		score0.add(0);
		score0.add(5);
		
		checkScore.add(score1);
		checkScore.add(score0);
		
		//exec
		List<List<Integer>> returnlist = Counter.playersScoreSorting(playList);
		
		assertTrue(
				checkScore.get(0).get(0) == returnlist.get(1).get(0)
				&&
				checkScore.get(1).get(0) == returnlist.get(0).get(0)
				);
	}

	/**
	 * Check score
	 */
	@Test
	public void showScoreStatsTest(){
		Game g = new Game();
		Counter.showScoreStats(g, true);
		assertTrue(g.isAllPlayersInitialized());
	}
	
	

}
