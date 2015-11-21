package com.tadams.chess.piece;


public interface IPiece {
	
	public PieceType getType();
	public void move();
	public int[] getPossibleMoves(int[] board);
	
}
