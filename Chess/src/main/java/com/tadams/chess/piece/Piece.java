package com.tadams.chess.piece;
import java.util.ArrayList;

import com.tadams.chess.Location;


public class Piece implements IPiece {

	protected PieceType pieceType;
	protected Location pieceLocation;
	protected Boolean colour;
	protected Location bestMoveLocation;
	protected int bestMoveValue;
	
	public void initPiece(int i, Boolean clr) {
		colour = clr;
		Location l = new Location(i);
		pieceLocation = l;
	}
	
	public Boolean getColour() {
		return colour;
	}
	public int getLocation() {
		return pieceLocation.getLocation();
	}
	
	public PieceType getType() {
		return pieceType;
	}
	


	public void move() {
		// TODO Auto-generated method stub
	
	}

	public int[] getPossibleMoves(int[] board) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLocation(int x) {
		// TODO Auto-generated method stub
		pieceLocation.setLocation(x);
		
	}
	

	
	public int evaluateMoves(int[] board) {
		int maxMoveValue = -1;
		int[] moves = getPossibleMoves(board);
		for(int i = 0; i< moves.length;i++) {
			MoveType mt = MoveGenerator.checkBlockCapture(board, moves[i], colour);
			int moveValue=0;
			if(mt == MoveType.CAPTURE) {
				moveValue = board[moves[i]] & 0xFF000000;
			}
			if(moveValue > maxMoveValue) {
				bestMoveLocation = new Location(moves[i]);
				maxMoveValue = moveValue;
			}
		}
		bestMoveValue = moveValue;
		return maxMoveValue;
	}
	
	public int getBestMoveLoc() {
		return bestMoveLocation.getLocation();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(pieceType);
		sb.append(colour ? "W" : "B");
		sb.append(pieceLocation.getLocation());
		
		return sb.toString();
		
	}
}
