package com.tadams.chess.piece;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
	
	private Boolean moved;
	

	public Pawn() {
		pieceType = PieceType.PAWN;
		moved = false;
	}
	
	public int[] getPossibleMoves(int[] board) {
		int[] moveVert = {1,1,1};
		int[] moveHorz = {-1,0,1};
		
		int dir = colour ? 1 : -1;
		
		
		List<Integer> legalMoves = new ArrayList<Integer>();
		
		int l = pieceLocation.getLocation();
		for(int i = 0; i< moveVert.length; i++ ) {
			if(MoveGenerator.checkValidLocation(l, moveHorz[i],dir * moveVert[i])) {
				legalMoves.add(l+moveHorz[i]+dir * moveVert[i]*8);
			}
		}

		if(!moved) {
			if(MoveGenerator.checkValidLocation(l, 0, dir * 2)) {
				legalMoves.add(l+2*8*dir);
			}
		}

		int[] moves = new int[legalMoves.size()];
		for(int i = 0; i < legalMoves.size(); i++)
			moves[i]=legalMoves.get(i);
		
		return moves;
	}
}
