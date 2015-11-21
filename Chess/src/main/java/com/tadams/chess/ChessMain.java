package com.tadams.chess;

public class ChessMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChessEngine theEngine = new ChessEngine();
		theEngine.InitGameState();
		System.out.println(theEngine.toString());
		theEngine.runGame();

	}

}
